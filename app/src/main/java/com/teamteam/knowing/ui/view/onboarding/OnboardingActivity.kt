package com.teamteam.knowing.ui.view.onboarding

import android.animation.*
import android.graphics.Color
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.teamteam.knowing.databinding.ActivityOnboardingBinding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.viewmodel.OnboardingActivityViewModel

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(ActivityOnboardingBinding::inflate) {

    private lateinit var onboardingActivityViewModel: OnboardingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var isFirst = true //처음 viewpager2 changeListener를 호출 할 때 transition이 시작되지 않도록 체크하기 위한 변수

        //viewpager 전환 시 배경이 부드럽게 움직이도록 xml상에서 저장한 transition을 컨트롤하기 위해 변수에 저장
        val transition = binding.viewpager2.background as TransitionDrawable


        //화면 하단 softkeyNavigationbar 높이 구하는 방법
        var softKeyHeight = 0
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            softKeyHeight = resources.getDimensionPixelSize(resourceId)
        }

//        //soft_key_navigation_bar 만큼 view가 올라오도록 최상단 layout에 패딩을 설정
//        binding.constraint.setPadding(0,0,0,softKeyHeight)


        //statusbar 투명하게 설정
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT



        //뷰모델 장착
        onboardingActivityViewModel =  ViewModelProvider(this).get(OnboardingActivityViewModel::class.java)

        //버튼 텍스트를 변경하기 위해 viewmodel에 있는 livedata를 observer패턴으로 감시
        onboardingActivityViewModel.currentBtnText.observe(this, Observer {
            binding.btnNext.text=it.toString()
        })

        onboardingActivityViewModel.isSuccessGo.observe(this, Observer {
            if (it)
                finish()
        })


        //viewpager2 adapter 설정
        onboardingActivityViewModel.setViewPager2Adapter(binding.viewpager2,this)

        //viewpager2와 indicator 연결
        binding.indicator.setViewPager2(binding.viewpager2)

        //viewpager 스크롤 position에 따라 버튼 텍스트 변경하기 위한 리스너
        binding.viewpager2.apply {
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    //해당하는 포지션을 보내주면 해당 포지션에 알맞는 작업을 실행
                    onboardingActivityViewModel.updateBtnText(position)

                    when(position){
                        0->{ //viewpager의 position이 0이면
                            if(!isFirst)//처음 onboardingActivity가 실행될 때 애니메이션이 발동 안하도록 처음인지 아닌지 검사
                                transition.reverseTransition(400) //미리 설정해둔 transition 실행
                            else//처음이라면, 처음인지 아닌지 확인하는 변수를 false로 변경해줌
                                isFirst=false
                        }
                        1->{ //viewpager의 position이 1이면
                            transition.startTransition(400) //미리 설정해둔 transition 실행
                        }
                    }
                }
            })
        }

        //다음 버튼 클릭 리스너
        binding.btnNext.setOnClickListener {
            onboardingActivityViewModel.onNextFragment()
        }
    }

}