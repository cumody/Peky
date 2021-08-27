package com.mahmoudshaaban.peky.features.categories.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import com.mahmoudshaaban.peky.R
import com.mahmoudshaaban.peky.core.domain.model.Recipe
import com.mahmoudshaaban.peky.core.error.toFailure
import com.mahmoudshaaban.peky.core.presentation.adapters.PagingLoadStateAdapter
import com.mahmoudshaaban.peky.core.presentation.adapters.RecipePagingAdapter
import com.mahmoudshaaban.peky.core.presentation.dispatchers.RecipeEventDispatcher
import com.mahmoudshaaban.peky.core.util.applyTopWindowInsets
import com.mahmoudshaaban.peky.core.util.bindNetworkImage
import com.mahmoudshaaban.peky.core.util.configureStatusBar
import com.mahmoudshaaban.peky.core.util.navigateToRecipeDetail
import com.mahmoudshaaban.peky.databinding.FragmentCategoriesDetailsBinding
import com.mahmoudshaaban.peky.features.categories.presentation.viewmodel.CategoriesDetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CategoriesDetailsFragment : Fragment() , RecipeEventDispatcher {
    private val viewModel: CategoriesDetailsViewModel by activityViewModels()
    private val args: CategoriesDetailsFragmentArgs by navArgs()

    private lateinit var adapter: RecipePagingAdapter

    private var _binding: FragmentCategoriesDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = RecipePagingAdapter(this)
        viewModel.requestRecipesForCategory(args.category)
        configureTransitions()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        configureStatusBar(false)
        _binding = FragmentCategoriesDetailsBinding.inflate(inflater, container, false)
        return binding.root    }


    private fun configureTransitions() {
        val duration = resources.getInteger(R.integer.page_transition_duration)
        val color = requireContext().getColor(R.color.colorBackground)
        val transition = MaterialContainerTransform().apply {
            this.duration = duration.toLong()
            containerColor = color
            drawingViewId = R.id.homeNavHostContainer
        }
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rootView.transitionName = args.category.name

        binding.btnBack.applyTopWindowInsets()

        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
       binding.rvRecipes.adapter = adapter.withLoadStateFooter(PagingLoadStateAdapter())

        binding.tvTitle.text = args.category.name
        bindNetworkImage(binding.ivCategoryItem, args.category.imageUrl)

        binding.errorLayout.btnRetry.setOnClickListener {
            adapter.refresh()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        setupObservers()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        adapter.addLoadStateListener { loadStates ->
            val loadState = loadStates.source.refresh
            binding.progressLayout.spinView.isVisible = loadState is LoadState.Loading
            binding.rvRecipes.isVisible = loadState is LoadState.NotLoading && adapter.itemCount > 0
            binding.errorLayout.errorView.isVisible = loadState is LoadState.Error
            if (loadState is LoadState.Error) {
                val failure = loadState.error.toFailure()
                binding.errorLayout.tvErrorSubtitle.text = failure.translate(requireContext())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesFlow?.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    override fun onRecipePressed(recipe: Recipe, view: View) {
        navigateToRecipeDetail(recipe , view)
    }

}