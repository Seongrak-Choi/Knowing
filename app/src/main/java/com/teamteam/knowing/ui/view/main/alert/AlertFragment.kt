package com.teamteam.knowing.ui.view.main.alert

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.network.response.AlarmList
import com.teamteam.knowing.data.model.network.response.AlarmListResponse
import com.teamteam.knowing.data.model.network.response.WelfareInfo
import com.teamteam.knowing.databinding.FragmentAlertBinding
import com.teamteam.knowing.ui.adapter.MainAlarmRCAdapter
import com.teamteam.knowing.ui.adapter.MainBookmarkRCAdapter
import com.teamteam.knowing.ui.base.BaseFragment
import com.teamteam.knowing.ui.viewmodel.AlertFragmentViewModel
import com.teamteam.knowing.ui.viewmodel.BookmarkFragmentViewModel

class AlertFragment : BaseFragment<FragmentAlertBinding>(FragmentAlertBinding::bind, R.layout.fragment_alert) {
    private lateinit var alertFragmentViewModel: AlertFragmentViewModel

    //알림 리사이클러뷰 어댑터 선언
    private lateinit var adapter : MainAlarmRCAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var array = ArrayList<AlarmList>()
        //어댑터가 만들어 지기 전에 adapter를 참조했다고 오류나서 이렇게 미리 세팅함
        adapter = MainAlarmRCAdapter(array,requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //statusbar 높이 구하는 방법
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }

        //statusbar가 투명해진 만큼 화면이 밀려 올라가는 것을 방지하기 위해 statusbar 높이 만큼 마진을 줌줌
        var params = binding.constraint.layoutParams as FrameLayout.LayoutParams
        params.setMargins(0,result,0,0)
        binding.constraint.layoutParams=params

        //뷰모델 장착
        alertFragmentViewModel= ViewModelProvider(this).get(AlertFragmentViewModel::class.java)


        //알림 리사이클러뷰 어댑터에 들어갈 리스트 관찰
        alertFragmentViewModel.currentRcAlarmList.observe(viewLifecycleOwner, Observer {
            //만약 북마크의 데이터가 0개라면 게시물이 없다는 일러스트 보이게 처리리
            if (it.size==0){
                binding.rcAlarm.visibility=View.INVISIBLE
                binding.constraintNoData.visibility = View.VISIBLE
            }else{
                binding.constraintNoData.visibility = View.INVISIBLE
                binding.rcAlarm.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL,false)
                adapter = MainAlarmRCAdapter(it,requireContext())
                binding.rcAlarm.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        //알림 리스트 받아오는 api 호출. onResume에서 함으로써 다른 프레그먼트 왔다 갔다할때마다 다시 리스트 불러옴.
        alertFragmentViewModel.tryGetAlarmList(ApplicationClass.USER_UID)
    }
}