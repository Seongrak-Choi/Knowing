package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.network.response.AlarmList
import com.teamteam.knowing.data.model.network.response.AlarmListResponse
import com.teamteam.knowing.data.remote.api.AlarmInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlertFragmentViewModel(application: Application):AndroidViewModel(application) {


    //알람 리사이클러뷰에 어댑터에 넣을 리스트뷰
    private val _currentRcAlarmList = MutableLiveData<ArrayList<AlarmList>>()

    val currentRcAlarmList : MutableLiveData<ArrayList<AlarmList>>
        get() = _currentRcAlarmList


    /*
    알람 리스트 받아오는 api 호출 메소드
     */
    fun tryGetAlarmList(userUid:String){
        val alarmInterface =  ApplicationClass.sRetrofit.create(AlarmInterface::class.java)
        alarmInterface.getAlarmList(userUid).enqueue(object:Callback<AlarmListResponse>{
            override fun onResponse(call: Call<AlarmListResponse>, response: Response<AlarmListResponse>) {
                if (response.isSuccessful){
                    val result=response.body() as AlarmListResponse
                    _currentRcAlarmList.value=result.alarmListResult.alarmList
                }else{
                    Log.e("ERROR","알림 페이지에서 알림 리스트 받아오기 호출 실패")
                }
            }
            override fun onFailure(call: Call<AlarmListResponse>, t: Throwable) {
                Log.e("ERROR","알림 페이지에서 알림 리스트 받아오기 통신 실패")
            }

        })
    }
}