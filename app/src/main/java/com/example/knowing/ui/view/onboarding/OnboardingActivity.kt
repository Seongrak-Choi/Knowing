package com.example.knowing.ui.view.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.databinding.ActivityOnboardingBinding
import com.example.knowing.ui.adapter.OnboardingActivityViewPager2Adapter
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.JoinUpSNSActivityViewModel
import com.example.knowing.ui.viewmodel.OnboardingActivityViewModel

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(ActivityOnboardingBinding::inflate) {

    private lateinit var onboardingActivityViewModel: OnboardingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onboardingActivityViewModel =  ViewModelProvider(this).get(OnboardingActivityViewModel::class.java) //뷰모델 장착

        //viewpager2 adapter 설정
        onboardingActivityViewModel.setViewPager2Adapter(binding.viewpager2,this)

        //다음 버튼 클릭 리스너
        binding.btnNext.setOnClickListener {
            onboardingActivityViewModel.setNextFragment(binding.btnNext)
        }
    }

}