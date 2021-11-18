package com.example.knowing.data.remote.api

import com.example.knowing.data.model.network.response.CollegeResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CollegeInterface {
    @GET("cnet/openapi/getOpenApi.json?")
    fun getCollegeInfo(@Query("apiKey")apiKey:String,@Query("svcType")svcType:String,@Query("svcCode")svcCode:String,@Query("gubun")gubun:String,
        @Query("searchSchulNm")searchSchulNm:String,@Query("contentType")contentType:String) : Call<CollegeResponseModel>
}