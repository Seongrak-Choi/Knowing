package com.teamteam.knowing.ui.view.main.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.teamteam.knowing.R
import com.teamteam.knowing.data.model.network.response.MainWelfareResponse
import com.teamteam.knowing.databinding.FragmentMainHomeBinding
import com.teamteam.knowing.ui.adapter.HomeFragmentViewPager2Adapter
import com.teamteam.knowing.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentMainHomeBinding>(FragmentMainHomeBinding::bind, R.layout.fragment_main_home){
    val tabTitleList = arrayListOf("맞춤 복지","나의 캘린더","모든 복지")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //복지 정보 받기
        val welfareInfo = arguments?.getSerializable("welfareInfo") as MainWelfareResponse

        //statusbar 높이 구하는 방법
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }


//        var params = binding.viewPager2.layoutParams as ConstraintLayout.LayoutParams
//        params.setMargins(0,result,0,0)
//        binding.viewPager2.layoutParams = params


        //viewpager2 어댑터 설정
        binding.viewPager2.adapter = HomeFragmentViewPager2Adapter(this,welfareInfo)
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager2.isSaveEnabled = false

        //tabLayout과 viewpager2 연결
        TabLayoutMediator(binding.tabLayout,binding.viewPager2){tab, position ->
            tab.text = tabTitleList[position]
        }.attach()

        //tabLayout의 tab들 마진 설정하는 코드
        for (i in 0 until binding.tabLayout.getTabCount()) {
            val tab = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(15, 20, 15, 20)
            tab.requestLayout()
        }


        //뷰페이저 스크롤 막는 코드
        binding.viewPager2.isUserInputEnabled = false

        //tabLayout 메뉴 누를 때 애니메이션 효과 없애기 위한 코드
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { binding.viewPager2?.setCurrentItem(it,false) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}