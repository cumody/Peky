package com.mahmoudshaaban.peky.features.onboarding.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mahmoudshaaban.peky.R
import com.mahmoudshaaban.peky.core.presentation.viewmodel.DataStoreViewModel
import com.mahmoudshaaban.peky.core.util.configureStatusBar
import com.mahmoudshaaban.peky.features.onboarding.constants.onBoardingItems
import com.mahmoudshaaban.peky.features.onboarding.presentation.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_on_boarding.*


class OnBoardingFragment : Fragment() {

    private val dataStoreViewModel: DataStoreViewModel by activityViewModels()
    private lateinit var adatper: ViewPagerAdapter
    private var currentIndex = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureStatusBar()
        return inflater.inflate(R.layout.fragment_on_boarding, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adatper = ViewPagerAdapter(onBoardingItems)
        viewPager.adapter = adatper
        dotsIndicator.setViewPager2(viewPager)

        //Remove OverScroll
        val child = viewPager.getChildAt(0)
        (child as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER


        registerOnChangeCallBack()

        buttonStart.setOnClickListener {
            nextPage()
        }

    }

    private fun registerOnChangeCallBack() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                currentIndex = position
            }
        })
    }

    private fun nextPage() {
        if (currentIndex >= 2) {
            dataStoreViewModel.writeOnBoardingDidShow()
            val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToHomeFragment()
            findNavController().navigate(action)
        } else {
            viewPager.setCurrentItem(currentIndex + 1, true)
        }
    }


}