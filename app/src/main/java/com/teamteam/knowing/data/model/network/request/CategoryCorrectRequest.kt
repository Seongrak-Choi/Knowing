package com.teamteam.knowing.data.model.network.request

import com.google.gson.annotations.SerializedName

data class CategoryCorrectRequest(
    @SerializedName("studentCategory")val studentCategory : ArrayList<String> = ArrayList<String>(),
    @SerializedName("empolyCategory")val empolyCategory : ArrayList<String> = ArrayList<String>(),
    @SerializedName("foundationCategory")val foundationCategory : ArrayList<String> = ArrayList<String>(),
    @SerializedName("residentCategory")val residentCategory : ArrayList<String> = ArrayList<String>(),
    @SerializedName("lifeCategory")val lifeCategory : ArrayList<String> = ArrayList<String>(),
    @SerializedName("covidCategory")val covidCategory : ArrayList<String> = ArrayList<String>(),
)