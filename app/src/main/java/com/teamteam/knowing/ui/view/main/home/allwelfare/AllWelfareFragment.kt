package com.teamteam.knowing.ui.view.main.home.allwelfare

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.teamteam.knowing.R
import com.teamteam.knowing.data.model.network.response.MainWelfareResponse
import com.teamteam.knowing.databinding.FragmentMainHomeAllWelfareBinding
import com.teamteam.knowing.ui.adapter.MainHomeAllWelfareRCAdapter
import com.teamteam.knowing.ui.base.BaseFragment
import com.teamteam.knowing.ui.viewmodel.AllWelfareFragmentViewModel
import com.google.android.material.tabs.TabLayout
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.ui.view.main.home.customwelfare.SelectFilterDialog

class AllWelfareFragment : BaseFragment<FragmentMainHomeAllWelfareBinding>(
    FragmentMainHomeAllWelfareBinding::bind,
    R.layout.fragment_main_home_all_welfare) {

    //복지데이터
    private lateinit var welfareInfo : MainWelfareResponse

    //뷰모델 변수
    private lateinit var allWelfareFragmentViewModel:AllWelfareFragmentViewModel

    //부모 뷰페이저2
    lateinit var parentViewPager2 : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //부모 뷰페이저 선언
        parentViewPager2= requireActivity().findViewById(R.id.view_pager2)

        //복지데이터 받기
        //welfareInfo = arguments?.getSerializable("welfareInfo") as MainWelfareResponse
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //statusbar 높이 구하는 방법
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }


        //statusbar 투명하게 해버리고 올라간 만큼 appbar를 내리기 위해 marinTop을 줌
        var params = binding.appBar.layoutParams as ConstraintLayout.LayoutParams
        params.setMargins(0,result+30,0,0)
        binding.appBar.layoutParams = params


        //statusbar 투명하게 해버리고 appbar 마진탑을 주니까 coolapsing에서 애먹어서 걍 statusbar 높이 만큼 임의의 뷰를 넓혀서 덮어버림
        var params2 = binding.imgTopRectangle.layoutParams as ConstraintLayout.LayoutParams
        params2.height=result*2
        binding.imgTopRectangle.layoutParams = params2


        //뷰모델 장착
        allWelfareFragmentViewModel= ViewModelProvider(requireActivity()).get(AllWelfareFragmentViewModel::class.java)

        //뷰모델 라이브데이터에 복지 정보 전달
        allWelfareFragmentViewModel.welfareInfo.value= ApplicationClass.mainWelfareResponse
        //복지 데이터를 입력했으니 알맞게 세팅하기 위함.
        allWelfareFragmentViewModel.settingAllView()

        //총 복지 개수 출력해주는 라이브 데이터 관찰
        allWelfareFragmentViewModel.currentWelfareTotal.observe(viewLifecycleOwner, Observer {
            binding.txWelfareTotal.text=it
        })


        //모든복지 리사이클러뷰에 출력할 라이브데이터 관찰
        allWelfareFragmentViewModel.currentRcList.observe(viewLifecycleOwner, Observer {
            binding.rcAllWelfare.layoutManager= LinearLayoutManager(requireContext())
            val adapter =  MainHomeAllWelfareRCAdapter(it,requireContext())
            binding.rcAllWelfare.adapter = adapter
            adapter.notifyDataSetChanged()

            binding.txWelfareTotal.text="총 ${it.size}건"
        })

        //정렬 부분 나타낼 라이브데이터 관찰
        allWelfareFragmentViewModel.currentFilter.observe(viewLifecycleOwner, Observer {
            binding.txFilter.text=it.toString()
        })

        //필터 클릭 리스너
        binding.btnFilter.setOnClickListener {
            val bottomSheet = SelectAllWelfareFilterDialog()
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }


        //탭 레이아웃 클릭 리스너
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position){
                    0->allWelfareFragmentViewModel.changeRcToEmploy()
                    1->allWelfareFragmentViewModel.changeRcToFoundation()
                    2->allWelfareFragmentViewModel.changeRcToResident()
                    3->allWelfareFragmentViewModel.changeRcToLife()
                    4->allWelfareFragmentViewModel.changeRcToCovid()
                    else->allWelfareFragmentViewModel.changeRcToStudent()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


        //tabLayout의 tab들에게 부모 뷰페이저 스크롤링 막기 위해 클릭 리스너 설정하는 코드
        for (i in 0 until binding.tabLayout.getTabCount()) {
            val tab = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            tab.setOnTouchListener { v, event ->
                when(event.action){
                    MotionEvent.ACTION_DOWN->{
                        parentViewPager2.isUserInputEnabled=false
                    }
                }
                false
            }
        }

        //tablayout터치 이후 네스티드스크롤뷰 자체를 클릭 시에는 스크롤링이 가능하도록 설정
        binding.nestedScroll.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN->{
                    parentViewPager2.isUserInputEnabled=true
                }
            }
            false
        }

        //tablayout터치 이후 네스티드스크롤뷰 자체를 클릭 시에는 스크롤링이 가능하도록 설정
        binding.rcAllWelfare.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN->{
                    parentViewPager2.isUserInputEnabled=true
                }
            }
            false
        }




        //nestedScroll 스크롤 체인지 리스너
        //맨 위로 버튼 visible관리하기 위함
        binding.nestedScroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY>1200){
                binding.btnScrollTop.visibility=View.VISIBLE
            }else{
                binding.btnScrollTop.visibility=View.INVISIBLE
            }
        }

        //맨위로 버튼 클릭 리스너
        binding.btnScrollTop.setOnClickListener {
            binding.nestedScroll.smoothScrollTo(0,0)
        }
    }
}