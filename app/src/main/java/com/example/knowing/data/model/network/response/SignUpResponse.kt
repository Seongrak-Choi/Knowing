package com.example.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
@SerializedName("isSuccess")var isSuccess : Boolean,
@SerializedName("code")var code : Int,
@SerializedName("message")var message:String,
@SerializedName("result")var signUpResult:SignUpResult
)

data class SignUpResult(
    @SerializedName("uid")var uid:String,
    @SerializedName("userIdx") var userIdx:Int
)