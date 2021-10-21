package com.example.knowing.ui.view.login

import android.os.Build
import android.os.Bundle

import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.databinding.ActivityOnBoardingBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.OnBoardingActivityViewModel


class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>(ActivityOnBoardingBinding::inflate){
    private lateinit var onBoardingActivityViewModel : OnBoardingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBoardingActivityViewModel = ViewModelProvider(this).get(OnBoardingActivityViewModel::class.java) //뷰모델 장착

        //네이버아이디로 로그인 버튼 클릭 리스너
        binding.btnLoginNaver.setOnClickListener {
            onBoardingActivityViewModel.setActivity(this) //activity의 정보를 viewModel로 넘겨준다. 네이버아이디로로그인 할 때 필요함.
            onBoardingActivityViewModel.getNaverToken() //네이버 로그인을 진행한다.
        }

        //카카오계정 로그인 버튼 클릭 리스너
        binding.btnLoginKakao.setOnClickListener {
            onBoardingActivityViewModel.getKakaoToken()
        }


    }
}