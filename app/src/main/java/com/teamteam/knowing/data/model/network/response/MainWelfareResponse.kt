package com.teamteam.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class MainWelfareResponse(
    @SerializedName("isSuccess")var isSuccess : Boolean,
    @SerializedName("code")var code : Int,
    @SerializedName("message")var message:String,
    @SerializedName("result")var mainWelfareResult:MainWelfareResult
): Serializable


data class MainWelfareResult(
    @SerializedName("studentCategory")var studentCategory : ArrayList<WelfareInfo>,
    @SerializedName("employCategory")var employCategory : ArrayList<WelfareInfo>,
    @SerializedName("foundationCategory")var foundationCategory : ArrayList<WelfareInfo>,
    @SerializedName("residentCategory")var residentCategory : ArrayList<WelfareInfo>,
    @SerializedName("lifeCategory")var lifeCategory : ArrayList<WelfareInfo>,
    @SerializedName("covidCategory")var covidCategory : ArrayList<WelfareInfo>,
    @SerializedName("totalCategory")var totalCategory : TotalCategoryResult
): Serializable

data class TotalCategoryResult(
    @SerializedName("studentCategory")var studentCategory : ArrayList<WelfareInfo>,
    @SerializedName("employCategory")var employCategory : ArrayList<WelfareInfo>,
    @SerializedName("foundationCategory")var foundationCategory : ArrayList<WelfareInfo>,
    @SerializedName("residentCategory")var residentCategory : ArrayList<WelfareInfo>,
    @SerializedName("lifeCategory")var lifeCategory : ArrayList<WelfareInfo>,
    @SerializedName("covidCategory")var covidCategory : ArrayList<WelfareInfo>
): Serializable


data class WelfareInfo(
    @SerializedName("uid")var uid:String,
    @SerializedName("name")var name:String,
    @SerializedName("title")var title:String,
    @SerializedName("serviceType")var serviceType: String,
    @SerializedName("maxMoney")var maxMoney: String,
    @SerializedName("minMoney")var minMoney:String,
    @SerializedName("incomeLevel")var incomeLevel:String,
    @SerializedName("category")var category:String,
    @SerializedName("content")var content:String,
    @SerializedName("runDate")var runDate:String,
    @SerializedName("applyDate")var applyDate:String,
    @SerializedName("scale")var scale:String,
    @SerializedName("age")var age:String,
    @SerializedName("address")var address:String,
    @SerializedName("detailTerms")var detailTerms:String,
    @SerializedName("schoolRecords")var schoolRecords:String,
    @SerializedName("employmentState")var employmentState:String,
    @SerializedName("specialStatus")var specialStatus:String,
    @SerializedName("joinLimit")var joinLimit:String,
    @SerializedName("applyMethod")var applyMethod:String,
    @SerializedName("judge")var judge:String,
    @SerializedName("url")var url:String,
    @SerializedName("document")var document:String,
    @SerializedName("manageOffice")var manageOffice:String,
    @SerializedName("phNum")var phNum:String
): Serializable