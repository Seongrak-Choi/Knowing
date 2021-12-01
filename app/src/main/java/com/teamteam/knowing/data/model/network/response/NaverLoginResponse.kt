package com.teamteam.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName

data class NaverLoginResponse(
    @SerializedName("isSuccess")var isSuccess : Boolean,
    @SerializedName("code")var code : Int,
    @SerializedName("message")var message:String,
    @SerializedName("result")var naverLoginresult:NaverLoginResult
)

data class NaverLoginResult(
    @SerializedName("status")var status:String,
    @SerializedName("jwt")var jwt:String
)
