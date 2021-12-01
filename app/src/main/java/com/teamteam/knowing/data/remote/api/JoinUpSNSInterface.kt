package com.teamteam.knowing.data.remote.api

import com.teamteam.knowing.data.model.network.response.GFDLoginResponse
import com.teamteam.knowing.data.model.network.response.KakaoLoginResponse
import com.teamteam.knowing.data.model.network.response.NaverLoginResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface JoinUpSNSInterface {
    @GET("app/users/naver-login")
    fun getNaverCustomToken(@Header("access-token")access_token:String) : Call<NaverLoginResponse>

    @GET("app/users/kakao-login")
    fun getKakaoCustomToken(@Header("access-token")access_token: String):Call<KakaoLoginResponse>

    //구글,페이스북,디폴트로 firebase에 등록한 계정의 uid가 서버 db에 있는지 판단하기 위한 api 통신
    @GET("app/users/checkuid")
    fun getIsGFDInDB(@Header("uid")access_token: String):Call<GFDLoginResponse>
}