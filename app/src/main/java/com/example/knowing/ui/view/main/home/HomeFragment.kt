package com.example.knowing.ui.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.knowing.R
import com.example.knowing.databinding.FragmentHomeBinding
import com.example.knowing.ui.adapter.HomeFragmentViewPager2Adapter
import com.example.knowing.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home){
    val tabTitleList = arrayListOf("맞춤 복지","나의 캘린더","모든 복지")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewpager2 어댑터 설정
        binding.viewPager2.adapter = HomeFragmentViewPager2Adapter(this)
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        //tabLayout과 viewpager2 연결
        TabLayoutMediator(binding.tabLayout,binding.viewPager2){tab, position ->
            tab.text = tabTitleList[position]
        }.attach()

        //tabLayout의 tab들 마진 설정하는 코드
        for (i in 0 until binding.tabLayout.getTabCount()) {
            val tab = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(15, 15, 15, 15)
            tab.requestLayout()
        }
    }
}