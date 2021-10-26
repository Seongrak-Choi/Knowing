package com.example.knowing.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.knowing.ui.view.main.home.AllWelfareFragment
import com.example.knowing.ui.view.main.home.CalendarFragment
import com.example.knowing.ui.view.main.home.CustomWelfareFragment
import com.example.knowing.ui.view.onboarding.Onboarding1Fragment
import com.example.knowing.ui.view.onboarding.Onboarding2Fragment

class HomeFragmentViewPager2Adapter(fm: Fragment) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 3

    /*
    viewPager가 움직이면 position에 해당하는 list에 있는 fragment를 출력하도록 리턴해줌
     */
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                CustomWelfareFragment()
            }
            1->{
                CalendarFragment()
            }
            else -> {
                AllWelfareFragment()
            }
        }
    }

}