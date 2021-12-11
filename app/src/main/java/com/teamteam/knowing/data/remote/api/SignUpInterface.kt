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

    //유저 FCM 토큰 서버에 저장하는 api
    @POST("app/users/fcmplus")
    fun postUserFCMToken(@Header("userUid")userUid:String, @Header("fcmtoken")fcmtoken:String):Call<PostFcmTokenResponse>

    //회원 탈퇴 api
    @DELETE("/app/users/userdelete")
    fun deleteWithdrawal(@Header("uid")uid:String) : Call<UserWithdrawalResponse>

    //이메일 중복 여부 확인하는 api
    @GET("/app/users/checkemail")
    fun getEmailDuplicate(@Query("email")email:String) : Call<EmailDuplicateResponse>
}