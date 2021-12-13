package com.teamteam.knowing.data.model.network.response

import com.google.android.datatransport.cct.StringMerger
import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("isSuccess")var isSuccess : Boolean,
    @SerializedName("code")var code : Int,
    @SerializedName("message")var message:String,
    @SerializedName("result")var notificationResult:ArrayList<NotificationResult>
)

data class NotificationResult(
    @SerializedName("title")var title:String,
    @SerializedName("date")var date:String,
    @SerializedName("content")var content:String,
    @SerializedName("newStatus")var newStatus:Boolean
)