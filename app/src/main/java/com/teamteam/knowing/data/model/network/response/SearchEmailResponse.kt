package com.teamteam.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName

data class SearchEmailResponse (
    @SerializedName("isSuccess")var isSuccess : Boolean,
    @SerializedName("code")var code : Int,
    @SerializedName("message")var message:String,
    @SerializedName("result")var emailSearchResult:EmailSearchResult
)

data class EmailSearchResult(
    @SerializedName("email")var email:String
)