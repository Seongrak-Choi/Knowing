package com.teamteam.knowing.ui.view.main.alert

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_UID
import com.teamteam.knowing.data.model.network.response.AlarmList
import com.teamteam.knowing.data.model.network.response.AlarmListResult
import com.teamteam.knowing.databinding.FragmentAlertBinding
import com.teamteam.knowing.ui.adapter.MainAlarmRCAdapter
import com.teamteam.knowing.ui.adapter.MainNoReadAlarmRCAdapter
import com.teamteam.knowing.ui.base.BaseFragment
import com.teamteam.knowing.ui.viewmodel.AlertFragmentViewModel
import java.util.*
import kotlin.collections.ArrayList

class AlertFragment : BaseFragment<FragmentAlertBinding>(FragmentAlertBinding::bind, R.layout.fragment_alert) {
    private lateinit var alertFragmentViewModel: AlertFragmentViewModel

    //읽은 알림 리사이클러뷰 어댑터 선언
    private lateinit var readAdapter : MainAlarmRCAdapter

    //안 읽은 알림 리사이클러뷰 어댑터 선언
    private lateinit var noReadAdapter : MainNoReadAlarmRCAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var array = ArrayList<AlarmList>()
        //어댑터가 만들어 지기 전에 adapter를 참조했다고 오류나서 이렇게 미리 세팅함
        readAdapter = MainAlarmRCAdapter(array,requireContext())
        noReadAdapter = MainNoReadAlarmRCAdapter(array,requireContext())
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
                //알림으로 받아온 데이터가 없으면 읽지 않은 복지 건수 리사이클러뷰와 막대기 안보이게
                binding.linearNoReadAlarm.visibility=View.GONE
                binding.txAlarmCount.text="0건"
            }else{
                val noReadAlarmList = ArrayList<AlarmList>()
                val readAlarmList = ArrayList<AlarmList>()

                //받아온 api 리스트 중 읽은 알람과 아닌 알람을 구분해서 리스트로 저장
                for (i in it){
                    if (i.alarmRead){
                        noReadAlarmList.add(i)
                    }else{
                        readAlarmList.add(i)
                    }
                }

                //읽지 않은 복지 건수 0개이면 해당 리사이클러뷰 안보이게 하기
                if (noReadAlarmList.size==0){
                    binding.linearNoReadAlarm.visibility=View.GONE
                }else{
                    binding.linearNoReadAlarm.visibility=View.VISIBLE
                }

                //읽지 않은 복지 건수 출력
                binding.txAlarmCount.text="${noReadAlarmList.size}건"

                //알람 없다는 일러스트 안보이게 처리
                binding.constraintNoData.visibility = View.INVISIBLE

                //읽은 알람 리사이클러뷰 어댑터 장착
                binding.rcAlarm.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL,false)
                readAdapter = MainAlarmRCAdapter(readAlarmList,requireContext())
                binding.rcAlarm.adapter = readAdapter
                readAdapter.notifyDataSetChanged()


                //읽은 알림 어댑터에서 삭제 버튼 클릭 시 동작하는 리스너
                readAdapter.setOnItemClickListener(object:MainAlarmRCAdapter.OnItemClickListener{
                    override fun onItemClick(value: ArrayList<AlarmList>) {
                        alertFragmentViewModel.tryGetAlarmList(USER_UID)
                    }
                })

                //안 읽은 알람 리사이클러뷰 어댑터 장착
                binding.rcNoReadAlarm.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                noReadAdapter = MainNoReadAlarmRCAdapter(noReadAlarmList,requireContext())
                binding.rcNoReadAlarm.adapter=noReadAdapter
                noReadAdapter.notifyDataSetChanged()

                //안 읽은 알림 어댑터에서 삭제 버튼 클릭 시 동작하는 리스너
                noReadAdapter.setOnItemClickListener(object:MainNoReadAlarmRCAdapter.OnItemClickListener{
                    override fun onItemClick(value: ArrayList<AlarmList>) {
                        alertFragmentViewModel.tryGetAlarmList(USER_UID)
                    }
                })
            }

            //일러스트 레이아웃이 나오게 되면 전체삭제 버튼은 숨기고 일러스트 레이아웃이 안보이면 전체삭제 버튼 보이게 하는 조건문
            if (binding.constraintNoData.visibility==View.INVISIBLE){
                binding.btnAllDelete.visibility=View.VISIBLE
            }else{
                binding.btnAllDelete.visibility=View.INVISIBLE
            }
        })

        //전체 삭제 버튼 클릭 리스너
        binding.btnAllDelete.setOnClickListener {
            alertFragmentViewModel.tryDeleteAlarmList(ApplicationClass.USER_UID)
        }
    }

    override fun onResume() {
        super.onResume()
        //알림 리스트 받아오는 api 호출. onResume에서 함으로써 다른 프레그먼트 왔다 갔다할때마다 다시 리스트 불러옴.
        alertFragmentViewModel.tryGetAlarmList(ApplicationClass.USER_UID)
    }
}