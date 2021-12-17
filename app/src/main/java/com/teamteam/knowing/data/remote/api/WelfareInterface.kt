package com.teamteam.knowing.data.remote.api

import com.teamteam.knowing.data.model.network.response.MainWelfareResponse
import com.teamteam.knowing.data.model.network.response.OneOfWelfareInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface WelfareInterface {
    @GET("app/mains/mainview")
    fun getWelfareInfo(@Header("uid") uid:String) : Call<MainWelfareResponse>

    @GET("app/mains/welfareInfo")//복지 정보 하나만 받아오는 api
    fun getOneOfWelfareInfo(@Header("uid")uid:String) : Call<OneOfWelfareInfoResponse>
}