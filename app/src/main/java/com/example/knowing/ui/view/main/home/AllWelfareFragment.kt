package com.example.knowing.ui.view.main.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knowing.R
import com.example.knowing.data.model.network.response.MainWelfareResponse
import com.example.knowing.databinding.FragmentMainHomeAllWelfareBinding
import com.example.knowing.ui.adapter.MainHomeAllWelfareRCAdapter
import com.example.knowing.ui.adapter.MainHomeCustomWelfareRCAdapter
import com.example.knowing.ui.base.BaseFragment
import com.example.knowing.ui.viewmodel.AllWelfareFragmentViewModel
import com.example.knowing.ui.viewmodel.CustomWelfareFragmentViewModel
import com.google.android.material.tabs.TabLayout

class AllWelfareFragment : BaseFragment<FragmentMainHomeAllWelfareBinding>(
    FragmentMainHomeAllWelfareBinding::bind,
    R.layout.fragment_main_home_all_welfare) {

    //복지데이터
    private lateinit var welfareInfo : MainWelfareResponse

    //뷰모델 변수
    private lateinit var allWelfareFragmentViewModel:AllWelfareFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //복지데이터 받기
        welfareInfo = arguments?.getSerializable("welfareInfo") as MainWelfareResponse
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //뷰모델 장착
        allWelfareFragmentViewModel= ViewModelProvider(this).get(AllWelfareFragmentViewModel::class.java)

        //뷰모델 라이브데이터에 복지 정보 전달
        allWelfareFragmentViewModel.welfareInfo.value=welfareInfo
        //복지 데이터를 입력했으니 알맞게 세팅하기 위함.
        allWelfareFragmentViewModel.settingAllView()

        //총 복지 개수 출력해주는 라이브 데이터 관찰
        allWelfareFragmentViewModel.currentWelfareTotal.observe(viewLifecycleOwner, Observer {
            binding.txWelfareTotal.text=it
        })


        //모든복지 리사이클러뷰에 출력할 라이브데이터 관찰
        allWelfareFragmentViewModel.currentRcList.observe(viewLifecycleOwner, Observer {
            binding.rcAllWelfare.layoutManager= LinearLayoutManager(requireContext())
            val adapter =  MainHomeAllWelfareRCAdapter(it)
            binding.rcAllWelfare.adapter = adapter
            adapter.notifyDataSetChanged()
        })

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position){
                    0->allWelfareFragmentViewModel.changeRcToStudent()
                    1->allWelfareFragmentViewModel.changeRcToEmploy()
                    2->allWelfareFragmentViewModel.changeRcToFoundation()
                    3->allWelfareFragmentViewModel.changeRcToResident()
                    4->allWelfareFragmentViewModel.changeRcToLife()
                    else->allWelfareFragmentViewModel.changeRcToCovid()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}