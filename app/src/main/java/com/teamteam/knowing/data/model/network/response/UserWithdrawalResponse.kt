package com.teamteam.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName

data class UserWithdrawalResponse(
@SerializedName("isSuccess")var isSuccess : Boolean,
@SerializedName("code")var code : Int,
@SerializedName("message")var message:String,
@SerializedName("result")var userWithdrawalResult:UserWithdrawalResult
)

data class UserWithdrawalResult(
    @SerializedName("result")var result:String
)