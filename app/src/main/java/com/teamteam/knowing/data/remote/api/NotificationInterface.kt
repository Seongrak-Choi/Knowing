package com.teamteam.knowing.data.remote.api

import com.teamteam.knowing.data.model.network.response.NotificationResponse
import retrofit2.Call
import retrofit2.http.GET

interface NotificationInterface {
    @GET("app/mains/notice")
    fun getNotification():Call<NotificationResponse>
}