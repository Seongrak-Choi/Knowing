package com.example.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName

data class KakaoLoginResponse(
    @SerializedName("isSuccess")var isSuccess : Boolean,
    @SerializedName("code")var code : Int,
    @SerializedName("message")var message:String,
    @SerializedName("result")var kakaoLoginresult:KakaoLoginResult
)

data class KakaoLoginResult(
    @SerializedName("status")var status:String,
    @SerializedName("jwt")var jwt:String
)
