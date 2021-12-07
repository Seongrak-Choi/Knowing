package com.teamteam.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName
import com.teamteam.knowing.data.model.domain.SignUpUser

data class EmailDuplicateResponse(
    @SerializedName("isSuccess")var isSuccess : Boolean,
    @SerializedName("code")var code : Int,
    @SerializedName("message")var message:String,
    @SerializedName("result")var emailDuplicateResult: EmailDuplicateResult
)

data class EmailDuplicateResult(
    @SerializedName("status")var status : Boolean
)

