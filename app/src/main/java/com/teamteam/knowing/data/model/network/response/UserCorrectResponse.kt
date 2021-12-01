package com.teamteam.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName

data class UserCorrectResponse(
@SerializedName("isSuccess")var isSuccess : Boolean,
@SerializedName("code")var code : Int,
@SerializedName("message")var message:String,
@SerializedName("result")var userCorrectResult:UserCorrectResult
)

data class UserCorrectResult(
    @SerializedName("status")var status:String
)