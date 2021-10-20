package com.example.knowing.ui.view.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.config.ApplicationClass
import com.example.knowing.databinding.ActivityOnBoardingBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.OnBoardingActivityViewModel
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>(ActivityOnBoardingBinding::inflate){
    private lateinit var onBoardingActivityViewModel : OnBoardingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBoardingActivityViewModel = ViewModelProvider(this).get(OnBoardingActivityViewModel::class.java) //뷰모델 장착

        binding.btnLoginNaver.setOnClickListener {
//            onBoardingActivityViewModel.setActivity(this)
//            onBoardingActivityViewModel.getNaverToken()
            val mOAuthLoginInstance = OAuthLogin.getInstance() //네아로 인스턴스 생성
            //인스턴스 초기화 init(context,id,secret,name)
            mOAuthLoginInstance.init(this,
                ApplicationClass().naver_client_id,
                ApplicationClass().naver_client_secret,
                ApplicationClass().naver_client_name)
            mOAuthLoginInstance.startOauthLoginActivity(this, OAuthLogin.mOAuthLoginHandler)

            //네아로의 로그인 callback을 처리할 핸들러
            val mOAuthLoginHandler : OAuthLoginHandler = object : OAuthLoginHandler(){
                override fun run(success: Boolean) {
                    if(success){
                        val accessToken = mOAuthLoginInstance.getAccessToken(applicationContext) //로그인 결과로 얻은 접근 토큰을 반환
                        println(accessToken.toString())
                    }
                }
            }
            println("뭐냐")
        }

    }
}