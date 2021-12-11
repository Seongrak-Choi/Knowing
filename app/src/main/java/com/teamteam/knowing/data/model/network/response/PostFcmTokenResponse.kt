package com.teamteam.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName

data class PostFcmTokenResponse(
    @SerializedName("isSuccess")var isSuccess : Boolean,
    @SerializedName("code")var code : Int,
    @SerializedName("message")var message:String,
    @SerializedName("result")var postFcmTokenResult:PostFcmTokenResult
    )

data class PostFcmTokenResult(
    @SerializedName("status")var status:String
)
