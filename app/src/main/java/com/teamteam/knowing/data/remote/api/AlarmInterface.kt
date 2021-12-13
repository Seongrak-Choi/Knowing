package com.teamteam.knowing.data.remote.api

import com.teamteam.knowing.data.model.network.response.AlarmList
import com.teamteam.knowing.data.model.network.response.AlarmListResponse
import com.teamteam.knowing.data.model.network.response.AlarmPostResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AlarmInterface {
    @GET("app/mains/alarm/detailPage")//해당복지가 알람 설정되어 있는지 조회
    fun getIsWelfareApplyToAlarm(@Header("userUid")userUid:String,@Header("welfareUid")welfareUid:String) : Call<AlarmPostResponse>

    @POST("app/mains/alarm/detailPage")//알람 버튼 클릭시 추가 삭제
    fun postChangeAlarm(@Header("userUid")userUid:String,@Header("welfareUid")welfareUid:String): Call<AlarmPostResponse>

    @GET("app/mains/alarm/alarmPage")//알람 리스트 받아오기
    fun getAlarmList(@Header("userUid")userUid:String):Call<AlarmListResponse>

    @DELETE("app/mains/alarm/alarmPage/total")//알림 프레그먼트의 전체 삭제 버튼 눌렀을 때 모든 복지 삭제하기 위한 api
    fun deleteAlarmList(@Header("userUid")userUid: String):Call<AlarmListResponse>

    @DELETE("app/mains/alarm/alarmPage")//알림 삭제 버튼 클릭해서 리스트에서 삭제 하기 위한 api
    fun deleteAlarm(@Header("userUid")userUid: String,@Header("alarmUid")alarmUid: String):Call<AlarmListResponse>

    @POST("app/mains/alarm/alarmPage/read")//알림 읽음 처리하는 메소드
    fun postReadAlarm(@Header("userUid")userUid: String,@Header("alarmUid")alarmUid:String):Call<AlarmListResponse>
}