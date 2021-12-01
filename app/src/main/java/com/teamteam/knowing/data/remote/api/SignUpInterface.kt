package com.teamteam.knowing.data.remote.api

import com.teamteam.knowing.data.model.domain.SignUpUser
import com.teamteam.knowing.data.model.network.request.CategoryCorrectRequest
import com.teamteam.knowing.data.model.network.request.UserCorrectRequest
import com.teamteam.knowing.data.model.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface SignUpInterface {
    @POST("app/users/sign-up")
    fun postSignUp(@Header("uid")uid:String, @Body params : SignUpUser) : Call<SignUpResponse>

    @POST("app/users/usermodify/privacy")
    fun postUserCorrect(@Header("uid")uid:String, @Body params : UserCorrectRequest) : Call<UserCorrectResponse>

    @POST("app/users/usermodify/welfare")
    fun postCategoryCorrect(@Header("uid")uid: String, @Body params:CategoryCorrectRequest):Call<CategoryCorrectResponse>

    //유저 정보 받아오는 api
    @GET("app/users/userInfo")
    fun getUserInfo(@Header("uid")uid:String) : Call<UserInfoResult>

    //회원 탈퇴 api
    @DELETE("/app/users/userdelete")
    fun deleteWithdrawal(@Header("uid")uid:String) : Call<UserWithdrawalResponse>
}