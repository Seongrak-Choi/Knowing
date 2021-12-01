package com.teamteam.knowing.data.remote.api

import com.teamteam.knowing.data.model.network.response.SearchEmailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchEmailInterface {
    @GET("app/users/findId")
    fun getSearchEmail(@Query("name")name:String,@Query("phNum")phNum:String):Call<SearchEmailResponse>
}