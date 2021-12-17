package com.teamteam.knowing.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teamteam.knowing.data.model.network.response.MainWelfareResponse
import com.teamteam.knowing.ui.view.main.home.allwelfare.AllWelfareFragment
import com.teamteam.knowing.ui.view.main.home.CalendarFragment
import com.teamteam.knowing.ui.view.main.home.customwelfare.CustomWelfareFragment

class HomeFragmentViewPager2Adapter(fm: Fragment,val welfareInfo:MainWelfareResponse) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 3

    /*
    viewPager가 움직이면 position에 해당하는 list에 있는 fragment를 출력하도록 리턴해줌
     */
    override fun createFragment(position: Int): Fragment {
        var bundle = Bundle()
        bundle.putSerializable("welfareInfo",welfareInfo)
        var customWelfareFragment= CustomWelfareFragment()
        var calendarFragment=CalendarFragment()
        var allWelfareFragment= AllWelfareFragment()

        customWelfareFragment.arguments=bundle
        calendarFragment.arguments=bundle
        allWelfareFragment.arguments=bundle

        return when (position) {
            0 -> {
                customWelfareFragment
            }
            1->{
                calendarFragment
            }
            else -> {
                allWelfareFragment
            }
        }
    }

}