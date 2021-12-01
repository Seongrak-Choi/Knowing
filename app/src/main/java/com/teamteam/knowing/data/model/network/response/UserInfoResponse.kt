package com.teamteam.knowing.data.model.network.response

import com.teamteam.knowing.data.model.domain.SignUpUser
import com.google.gson.annotations.SerializedName

data class UserInfoResult(
    @SerializedName("isSuccess")var isSuccess : Boolean,
    @SerializedName("code")var code : Int,
    @SerializedName("message")var message:String,
    @SerializedName("result")var userInfoResult: SignUpUser
)