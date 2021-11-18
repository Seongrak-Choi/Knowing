package com.example.knowing.config

import android.app.Application
import android.content.SharedPreferences
import com.example.knowing.R
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    //네이버아이디로로그인
    val naver_client_id = "ocHtoahCwyIiavMBEr0K" //네이버로 로그인 id
    val naver_client_secret ="VocqkjAIW9" //네이버로 로그인 비밀키
    val naver_client_name ="노잉" //네이버로 로그인 앱 이름

    //노잉 서버 URL
    var API_URL = "https://www.makeus-hyun.shop/"

    //대학교 검색 URL
    var COLLEGE_URL = "https://www.career.go.kr/"


    // 코틀린의 전역변수 문법
    companion object {
        //대학교 검색 api 키값
        val COLLEGE_API_KEY="d0e485edcb7264e0e9f5d120b7c9fc11"

        //뭘로 로그인을 하던지 firebase에서 받아오는 UID 저장할 변수
        var USER_UID = ""


        // Retrofit 인스턴스, 앱 실행시 한번만 생성하여 사용합니다.
        lateinit var sRetrofit: Retrofit
        lateinit var collegeRetrofit : Retrofit
    }


    //카카오계정 로그인에 필요한 kakao_app_key는 string에 선언해 두었음
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
        FacebookSdk.sdkInitialize(this)
        AppEventsLogger.activateApp(this)

        //레트로핏 인스턴스 생성
        initRetrofitInstance()
    }


    // 레트로핏 인스턴스를 생성하고, 레트로핏에 각종 설정값들을 지정해줍니다.
    // 연결 타임아웃시간은 5초로 지정이 되어있고, HttpLoggingInterceptor를 붙여서 어떤 요청이 나가고 들어오는지를 보여줍니다.
    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


        // sRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        // collegeRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        // 대학교 이름 검색을 위한 레트로핏 인스턴스 생성
        collegeRetrofit = Retrofit.Builder()
            .baseUrl(COLLEGE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}