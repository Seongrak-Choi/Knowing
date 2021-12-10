package com.teamteam.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName

data class AlarmListResponse(
    @SerializedName("isSuccess")var isSuccess : Boolean,
    @SerializedName("code")var code : Int,
    @SerializedName("message")var message:String,
    @SerializedName("result")var alarmListResult:AlarmListResult
)

data class AlarmListResult(
    @SerializedName("alarm")var alarmList:ArrayList<AlarmList>
)

data class AlarmList(
    @SerializedName("uid")var uid:String,
    @SerializedName("title")var title:String,
    @SerializedName("subTitle")var subTitle:String,
    @SerializedName("date")var date:String,
    @SerializedName("postUid")var postUid:String,
    @SerializedName("alarmRead")var alarmRead:Boolean
)