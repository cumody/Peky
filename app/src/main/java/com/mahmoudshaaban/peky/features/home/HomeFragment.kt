package com.mahmoudshaaban.peky.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.transition.Hold
import com.mahmoudshaaban.peky.R
import com.mahmoudshaaban.peky.core.util.setupWithNavController
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var isPresented = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition = resources.getInteger(R.integer.page_transition_duration)
        exitTransition = Hold().apply {
            duration = transition.toLong()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isPresented) {
            // Prepare reenter transition
            postponeEnterTransition()
            view.doOnPreDraw { startPostponedEnterTransition() }
        }
        isPresented = true

        if (savedInstanceState == null) {
            setupBottomNavigation()
        }


    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {
        val activity = requireActivity()
        val navGraphIds = listOf(
            R.navigation.categories_graph,
            R.navigation.search_graph,
            R.navigation.favourites_graph
        )
        bottomNav.setupWithNavController(
            navGraphIds,
            childFragmentManager,
            R.id.homeNavHostContainer,
            activity.intent
        )
    }

}