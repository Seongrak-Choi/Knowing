package com.example.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName

data class CollegeResponseModel(
    @SerializedName("dataSearch") var dataSearch:ColleageDataSearch
)

data class ColleageDataSearch(
    @SerializedName("content") var collegeDataSearch:ArrayList<CollegeContent>
)

data class CollegeContent(
    @SerializedName("campusName") var campusName:String,
    @SerializedName("collegeinfourl") var collegeinfourl:String,
    @SerializedName("schoolType") var schoolType:String,
    @SerializedName("link") var link:String,
    @SerializedName("schoolGubun") var schoolGubun:String,
    @SerializedName("adres") var adres:String,
    @SerializedName("schoolName") var schoolName:String,
    @SerializedName("region") var region:String,
    @SerializedName("totalCount") var totalCount:String,
    @SerializedName("estType") var estType:String,
    @SerializedName("seq") var seq:String
)