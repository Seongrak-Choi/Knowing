package com.example.knowing.config

import android.app.Application
import com.example.knowing.R
import com.kakao.sdk.common.KakaoSdk

class ApplicationClass : Application() {
    //네이버아이디로로그인
    val naver_client_id = "ocHtoahCwyIiavMBEr0K" //네이버로 로그인 id
    val naver_client_secret ="VocqkjAIW9" //네이버로 로그인 비밀키
    val naver_client_name ="노잉" //네이버로 로그인 앱 이름


    //카카오계정 로그인에 필요한 kakao_app_key는 string에 선언해 두었음
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }

}