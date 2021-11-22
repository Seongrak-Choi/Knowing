package com.example.knowing.ui.view.main.home

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.R
import com.example.knowing.data.model.network.response.MainWelfareResponse
import com.example.knowing.databinding.FragmentMainHomeCustomWelfareBinding
import com.example.knowing.ui.base.BaseFragment
import com.example.knowing.ui.viewmodel.CustomWelfareFragmentViewModel

class CustomWelfareFragment : BaseFragment<FragmentMainHomeCustomWelfareBinding>(FragmentMainHomeCustomWelfareBinding::bind,
    R.layout.fragment_main_home_custom_welfare){

    //뷰모델 변수 선언
    private lateinit var customWelfareFragmentViewModel:CustomWelfareFragmentViewModel

    //1번재 주제 그래프바 애니메이션 변수
    private lateinit var subject1_anim : ValueAnimator
    //2번재 주제 그래프바 애니메이션 변수
    private lateinit var subject2_anim : ValueAnimator
    //3번재 주제 그래프바 애니메이션 변수
    private lateinit var subject3_anim : ValueAnimator
    //4번재 주제 그래프바 애니메이션 변수
    private lateinit var subject4_anim : ValueAnimator
    //5번재 주제 그래프바 애니메이션 변수
    private lateinit var subject5_anim : ValueAnimator
    //6번재 주제 그래프바 애니메이션 변수
    private lateinit var subject6_anim : ValueAnimator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //복지 정보 더미데이터
        val dumy = MainWelfareResponse("학생","취업","창업","주거 금융","생활 복지","코로나 19",
            10,15,40,5,20,0)

        //뷰모델 장착
        customWelfareFragmentViewModel=ViewModelProvider(this).get(CustomWelfareFragmentViewModel::class.java)

        //뷰모델 라이브데이터에 복지 정보 전달
        customWelfareFragmentViewModel.welfareInfo.value=dumy
        customWelfareFragmentViewModel.settingAllView()


        //첫번째 주제 총 복지 갯수
        customWelfareFragmentViewModel.currentTxSubject1Total.observe(viewLifecycleOwner, Observer {
            binding.txGraphSubject1Total.text=it
        })
        //두번째 주제 총 복지 갯수
        customWelfareFragmentViewModel.currentTxSubject2Total.observe(viewLifecycleOwner, Observer {
            binding.txGraphSubject2Total.text=it
        })
        //세번째 주제 총 복지 갯수
        customWelfareFragmentViewModel.currentTxSubject3Total.observe(viewLifecycleOwner, Observer {
            binding.txGraphSubject3Total.text=it
        })
        //네번째 주제 총 복지 갯수
        customWelfareFragmentViewModel.currentTxSubject4Total.observe(viewLifecycleOwner, Observer {
            binding.txGraphSubject4Total.text=it
        })
        //다섯번째 주제 총 복지 갯수
        customWelfareFragmentViewModel.currentTxSubject5Total.observe(viewLifecycleOwner, Observer {
            binding.txGraphSubject5Total.text=it
        })
        //여섯번째 주제 총 복지 갯수
        customWelfareFragmentViewModel.currentTxSubject6Total.observe(viewLifecycleOwner, Observer {
            binding.txGraphSubject6Total.text=it
        })

        //첫번째 주제 이름 설정하기 위해 라이브데이터 관찰
        customWelfareFragmentViewModel.currentTxSubject1.observe(viewLifecycleOwner, Observer {
            binding.txGraphSubject11.text=it[0]
            binding.txGraphSubject12.text=it[1]
        })

        //두번째 주제 이름 설정하기 위해 라이브데이터 관찰
        customWelfareFragmentViewModel.currentTxSubject2.observe(viewLifecycleOwner, Observer {
            binding.txGraphSubject21.text=it[0]
            binding.txGraphSubject22.text=it[1]
        })

        //세번째 주제 이름 설정하기 위해 라이브데이터 관찰
        customWelfareFragmentViewModel.currentTxSubject3.observe(viewLifecycleOwner, Observer {
            binding.txGraphSubject31.text=it[0]
            binding.txGraphSubject32.text=it[1]
        })

        //네번째 주제 이름 설정하기 위해 라이브데이터 관찰
        customWelfareFragmentViewModel.currentTxSubject4.observe(viewLifecycleOwner, Observer {
            binding.txGraphSubject41.text=it[0]
            binding.txGraphSubject42.text=it[1]
        })

        //다섯번째 주제 이름 설정하기 위해 라이브데이터 관찰
        customWelfareFragmentViewModel.currentTxSubject5.observe(viewLifecycleOwner, Observer {
            binding.txGraphSubject51.text=it[0]
            binding.txGraphSubject52.text=it[1]
        })

        //여섯번째 주제 이름 설정하기 위해 라이브데이터 관찰
        customWelfareFragmentViewModel.currentTxSubject6.observe(viewLifecycleOwner, Observer {
            binding.txGraphSubject61.text=it[0]
            binding.txGraphSubject62.text=it[1]
        })


        //tabLayout의 tab들 마진 설정하는 코드
        for (i in 0 until binding.tabLayoutCustomWelfare.getTabCount()) {
            val tab = (binding.tabLayoutCustomWelfare.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(15, 20, 15, 20)
            tab.requestLayout()
        }
    }

    //화면이 재실행 될 때 마다 애니메이션이 실행 되도록 onResume에서 실행시킴
    override fun onResume() {
        super.onResume()
        /*
        그래프를 담는 레이아웃의 높이를 구해서 디바이스 크기 마다 일정한 높이만큼 그래프를 증가시키기 위해
        constraintGraph 의 옵저버 리스너를 달아서 높이를 구한 뒤 해당 높이의 비율만큼 그래프 애니메이션 실행시키고
        바로 삭제하여 한번만 리스너가 발동하도록 구현

        내부에는 각 6개 주제의 그래프들을 실행하는 애니메이션 넣을 예정
         */
        binding.constraintGraph.viewTreeObserver.addOnGlobalLayoutListener(object:ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                val layoutHeight = binding.constraintGraph.height
                val Anitamon_Duration = 500L //애니메이션 수행시간

                //1번째 주제 애니메이션 실행 부분
                subject1_anim = ValueAnimator.ofInt(binding.viewGraphSubject1.measuredHeight,(layoutHeight* customWelfareFragmentViewModel.biasBarHeightSubject1.value!!).toInt())
                subject1_anim.addUpdateListener {
                    val  h = it.animatedValue as Int
                    val layoutParams : ViewGroup.LayoutParams = binding.viewGraphSubject1.layoutParams
                    layoutParams.height=h
                    binding.viewGraphSubject1.layoutParams=layoutParams
                }
                subject1_anim.duration=Anitamon_Duration
                subject1_anim.start()


                //2번째 주제 애니메이션 실행 부분
                subject2_anim = ValueAnimator.ofInt(binding.viewGraphSubject2.measuredHeight,(layoutHeight*customWelfareFragmentViewModel.biasBarHeightSubject2.value!!).toInt())
                subject2_anim.addUpdateListener {
                    val  h = it.animatedValue as Int
                    val layoutParams : ViewGroup.LayoutParams = binding.viewGraphSubject2.layoutParams
                    layoutParams.height=h
                    binding.viewGraphSubject2.layoutParams=layoutParams
                }
                subject2_anim.duration=Anitamon_Duration
                subject2_anim.start()

                //3번째 주제 애니메이션 실행 부분
                subject3_anim = ValueAnimator.ofInt(binding.viewGraphSubject3.measuredHeight,(layoutHeight*customWelfareFragmentViewModel.biasBarHeightSubject3.value!!).toInt())
                subject3_anim.addUpdateListener {
                    val  h = it.animatedValue as Int
                    val layoutParams : ViewGroup.LayoutParams = binding.viewGraphSubject3.layoutParams
                    layoutParams.height=h
                    binding.viewGraphSubject3.layoutParams=layoutParams
                }
                subject3_anim.duration=Anitamon_Duration
                subject3_anim.start()


                //4번째 주제 애니메이션 실행 부분
                subject4_anim = ValueAnimator.ofInt(binding.viewGraphSubject4.measuredHeight,(layoutHeight*customWelfareFragmentViewModel.biasBarHeightSubject4.value!!).toInt())
                subject4_anim.addUpdateListener {
                    val  h = it.animatedValue as Int
                    val layoutParams : ViewGroup.LayoutParams = binding.viewGraphSubject4.layoutParams
                    layoutParams.height=h
                    binding.viewGraphSubject4.layoutParams=layoutParams
                }
                subject4_anim.duration=Anitamon_Duration
                subject4_anim.start()

                //5번째 주제 애니메이션 실행 부분
                subject5_anim = ValueAnimator.ofInt(binding.viewGraphSubject5.measuredHeight,(layoutHeight*customWelfareFragmentViewModel.biasBarHeightSubject5.value!!).toInt())
                subject5_anim.addUpdateListener {
                    val  h = it.animatedValue as Int
                    val layoutParams : ViewGroup.LayoutParams = binding.viewGraphSubject5.layoutParams
                    layoutParams.height=h
                    binding.viewGraphSubject5.layoutParams=layoutParams
                }
                subject5_anim.duration=Anitamon_Duration
                subject5_anim.start()

                //6번째 주제 애니메이션 실행 부분
                subject6_anim = ValueAnimator.ofInt(binding.viewGraphSubject6.measuredHeight,(layoutHeight*customWelfareFragmentViewModel.biasBarHeightSubject6.value!!).toInt())
                subject6_anim.addUpdateListener {
                    val  h = it.animatedValue as Int
                    val layoutParams : ViewGroup.LayoutParams = binding.viewGraphSubject6.layoutParams
                    layoutParams.height=h
                    binding.viewGraphSubject6.layoutParams=layoutParams
                }
                subject6_anim.duration=Anitamon_Duration
                subject6_anim.start()


                //리스너 한번만 발동하도록 바로 삭제
                binding.constraintGraph.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

    }
}