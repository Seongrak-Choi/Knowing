package com.example.knowing.ui.adapter

import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.knowing.ui.view.onboarding.Onboarding1Fragment
import com.example.knowing.ui.view.onboarding.Onboarding2Fragment

class OnboardingActivityViewPager2Adapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    //private var list = ArrayList<Fragment>() //fragment를 담을 arraylist

    override fun getItemCount(): Int = 2

    /*
    viewPager가 움직이면 position에 해당하는 list에 있는 fragment를 출력하도록 리턴해줌
     */
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                Onboarding1Fragment()
            }
            else -> {
                Onboarding2Fragment()
            }
        }
    }

}