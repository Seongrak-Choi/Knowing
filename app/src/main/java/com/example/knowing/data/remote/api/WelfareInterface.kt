package com.example.knowing.data.remote.api

import com.example.knowing.data.model.network.response.MainWelfareResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface WelfareInterface {
    @GET("app/mains/mainpage")
    fun getWelfareInfo(@Header("uid") uid:String) : Call<MainWelfareResponse>
}