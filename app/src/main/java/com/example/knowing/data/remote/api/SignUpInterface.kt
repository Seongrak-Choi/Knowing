package com.example.knowing.data.remote.api

import com.example.knowing.data.model.domain.SignUpUser
import com.example.knowing.data.model.network.response.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignUpInterface {
    @POST("app/users/sign-up")
    fun postSignUp(@Header("uid")uid:String,@Body params : SignUpUser) : Call<SignUpResponse>
}