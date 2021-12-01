package com.teamteam.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName

data class CategoryCorrectResponse(
@SerializedName("isSuccess")var isSuccess : Boolean,
@SerializedName("code")var code : Int,
@SerializedName("message")var message:String,
@SerializedName("result")var categoryCorrectResult:CategoryCorrectResult
)

data class CategoryCorrectResult(
    @SerializedName("status")var status:String
)