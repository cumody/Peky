package com.mahmoudshaaban.peky.features.splash

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mahmoudshaaban.peky.R
import com.mahmoudshaaban.peky.core.presentation.viewmodel.DataStoreViewModel
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private val dataStoreViewModel: DataStoreViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = requireActivity().window
        val currentFlags = window.decorView.systemUiVisibility
        var flags = currentFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            flags = flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.navigationBarColor = requireContext().getColor(R.color.colorAccent)
        }
        window.decorView.systemUiVisibility = flags


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleNavigationAction()
    }

    private fun handleNavigationAction() {
        lifecycleScope.launch {
            val onBoardingDidShow = dataStoreViewModel.readOnBoardingDidShow()
            var action = SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment()
            if (onBoardingDidShow) {
                action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
            }
            findNavController().navigate(action)
        }
    }

}