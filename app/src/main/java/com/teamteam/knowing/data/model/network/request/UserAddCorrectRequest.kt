package com.teamteam.knowing.data.model.network.request

import com.google.gson.annotations.SerializedName

data class UserAddCorrectRequest(
    @SerializedName("address") var address:String,
    @SerializedName("addressDetail")var addressDetail:String,
    @SerializedName("specialStatus")var specialStatus:ArrayList<String>,
    @SerializedName("incomeLevel")var incomeLevel:String,
    @SerializedName("incomeAvg")var incomeAvg:String,
    @SerializedName("employmentState")var employmentState:ArrayList<String>,
    @SerializedName("schoolRecords")var schoolRecords:String,
    @SerializedName("school")var school:String,
    @SerializedName("mainMajor")var mainMajor:String,
    @SerializedName("subMajor")var subMajor:String,
    @SerializedName("semester")var semester:String,
    @SerializedName("lastSemesterScore")var lastSemesterScore:String,
    )