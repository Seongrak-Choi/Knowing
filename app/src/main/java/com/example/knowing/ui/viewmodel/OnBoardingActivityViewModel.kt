package com.example.knowing.ui.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.knowing.config.ApplicationClass
import com.example.knowing.ui.view.login.OnBoardingActivity
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler
import com.nhn.android.naverlogin.OAuthLoginHandler

class OnBoardingActivityViewModel(application: Application) : AndroidViewModel(application){

    private val myApplication = application //application을 전역변수로 사용할 수 있도록 세팅
    private val myContext = myApplication.applicationContext //Context를 전역변수로 사용할 수 있도록 세팅
    private lateinit var activity: OnBoardingActivity
    /*
    네아로 로그인 버튼 클릭 시 실행
    네이버로그인 API로 로그인을 진행하고 토큰을 받아온다.
     */
    fun getNaverToken(){
        val mOAuthLoginInstance = OAuthLogin.getInstance() //네아로 인스턴스 생성
        //인스턴스 초기화 init(context,id,secret,name)
        mOAuthLoginInstance.init(myContext,ApplicationClass().naver_client_id,ApplicationClass().naver_client_secret,ApplicationClass().naver_client_name)
        mOAuthLoginInstance.startOauthLoginActivity(activity,mOAuthLoginHandler)

        //네아로의 로그인 callback을 처리할 핸들러
        val mOAuthLoginHandler : OAuthLoginHandler = object : OAuthLoginHandler(){
            override fun run(success: Boolean) {
                if(success){
                    val accessToken = mOAuthLoginInstance.getAccessToken(myContext) //로그인 결과로 얻은 접근 토큰을 반환
                    println(accessToken.toString())
                }
            }

        }
    }

    fun setActivity(activity: OnBoardingActivity){
        this.activity = activity
    }
}