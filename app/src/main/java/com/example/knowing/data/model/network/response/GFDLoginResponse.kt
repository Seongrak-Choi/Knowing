package com.example.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName

data class GFDLoginResponse(
    @SerializedName("isSuccess")var isSuccess : Boolean,
    @SerializedName("code")var code : Int,
    @SerializedName("message")var message:String,
    @SerializedName("result")var gfdLoginresult:GFDLoginResult
)

data class GFDLoginResult(
    @SerializedName("status")var status:Boolean,
)
