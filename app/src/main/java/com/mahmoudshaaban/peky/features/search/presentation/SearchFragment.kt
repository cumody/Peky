package com.mahmoudshaaban.peky.features.search.presentation

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudshaaban.peky.core.domain.model.Recipe
import com.mahmoudshaaban.peky.core.error.toFailure
import com.mahmoudshaaban.peky.core.presentation.adapters.PagingLoadStateAdapter
import com.mahmoudshaaban.peky.core.presentation.adapters.RecipePagingAdapter
import com.mahmoudshaaban.peky.core.presentation.dispatchers.RecipeEventDispatcher
import com.mahmoudshaaban.peky.core.util.configureStatusBar
import com.mahmoudshaaban.peky.core.util.navigateToRecipeDetail
import com.mahmoudshaaban.peky.databinding.FragmentSearchBinding
import com.mahmoudshaaban.peky.features.search.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() , RecipeEventDispatcher {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var pagingAdapter: RecipePagingAdapter

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "SearchFragment"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        configureStatusBar()
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagingAdapter = RecipePagingAdapter(this)
        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecipes.adapter = pagingAdapter.withLoadStateFooter(PagingLoadStateAdapter())
        setupListeners()

    }

    private fun setupListeners() {
        binding.includeSearchBar.editTextSearch.addTextChangedListener { text ->
            binding.includeSearchBar.btnCancelSearch.isVisible = !text.isNullOrEmpty()
        }

        binding.includeSearchBar.editTextSearch.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                dismissKeyboard(v)
                requestSearch()
                return@setOnKeyListener true
            }
            false
        }

        binding.includeSearchBar.btnCancelSearch.setOnClickListener {
            binding.includeSearchBar.editTextSearch.text.clear()
        }

        binding.rvRecipes.setOnScrollChangeListener { v, _, _, _, _ ->
            dismissKeyboard(v)
        }

        binding.errorLayout.btnRetry.setOnClickListener {
            requestSearch(shouldRetry = true)
        }

        pagingAdapter.addLoadStateListener(::listenLoadStateChanges)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesFlow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun listenLoadStateChanges(loadStates: CombinedLoadStates) {
        val loadState = loadStates.source.refresh
        binding.progressLayout.spinView.isVisible = loadState is LoadState.Loading
        binding.rvRecipes.isVisible = loadState is LoadState.NotLoading && pagingAdapter.itemCount > 0
        binding.emptyLayout.emptyView.isVisible = loadState is LoadState.NotLoading && pagingAdapter.itemCount == 0
        binding.errorLayout.errorView.isVisible = loadState is LoadState.Error
        if (loadState is LoadState.Error) {
            val failure = loadState.error.toFailure()
            binding.errorLayout.tvErrorSubtitle.text = failure.translate(requireContext())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding == null
    }

    private fun requestSearch(shouldRetry: Boolean = false) {
        val text = binding.includeSearchBar.editTextSearch.text.toString()
        if (text.trim().isNotEmpty() || shouldRetry) {
            viewModel.query = text
            pagingAdapter.refresh()
        }
    }

    private fun dismissKeyboard(view: View) {
        val inputMethodManager = ContextCompat.getSystemService(
            requireContext(), InputMethodManager::class.java
        )
        view.clearFocus()
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onRecipePressed(recipe: Recipe, view: View) {
        dismissKeyboard(binding.includeSearchBar.editTextSearch)
        navigateToRecipeDetail(recipe, view)
    }


}