package com.teamteam.knowing.data.remote.api

import com.teamteam.knowing.data.model.network.response.AlarmListResponse
import com.teamteam.knowing.data.model.network.response.AlarmPostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AlarmInterface {
    @GET("app/mains/alarm/detailPage")//알람 조회
    fun getIsWelfareApplyToAlarm(@Header("userUid")userUid:String,@Header("welfareUid")welfareUid:String) : Call<AlarmPostResponse>

    @POST("app/mains/alarm/detailPage")//알람 버튼 클릭시 추가 삭제
    fun postChangeAlarm(@Header("userUid")userUid:String,@Header("welfareUid")welfareUid:String): Call<AlarmPostResponse>

    @GET("app/mains/alarm/alarmPage")//알람 리스트 받아오기
    fun getAlarmList(@Header("userUid")userUid:String):Call<AlarmListResponse>
}