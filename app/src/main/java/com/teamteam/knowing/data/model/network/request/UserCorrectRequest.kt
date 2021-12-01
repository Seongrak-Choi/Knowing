package com.teamteam.knowing.data.model.network.request

import com.google.gson.annotations.SerializedName

data class UserCorrectRequest(
    @SerializedName("email") val email : String="",
    @SerializedName("name")val name:String="",
    @SerializedName("pwd")val pwd:String = "",
    @SerializedName("phNum")val phNum:String="",
    @SerializedName("gender")val gender:String="",
    @SerializedName("birth")val birth:Int
)
