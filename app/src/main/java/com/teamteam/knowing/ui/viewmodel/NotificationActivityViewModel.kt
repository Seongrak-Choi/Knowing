package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.network.response.NotificationResponse
import com.teamteam.knowing.data.model.network.response.NotificationResult
import com.teamteam.knowing.data.remote.api.NotificationInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivityViewModel(application: Application):AndroidViewModel(application) {

    //공지사항 리사이클러뷰에 출력할 리스트 담당 라이브 데이터
    private val _currentRcList = MutableLiveData<ArrayList<NotificationResult>>()

    val currentRcList : MutableLiveData<ArrayList<NotificationResult>>
        get() = _currentRcList



    /*
    공지사항 받아오는 api 호출 메소드
     */
    fun tryGetNotification(){
        val notificationInterface = ApplicationClass.sRetrofit.create(NotificationInterface::class.java)
        notificationInterface.getNotification().enqueue(object:Callback<NotificationResponse>{
            override fun onResponse(call: Call<NotificationResponse>, response: Response<NotificationResponse>) {
                if (response.isSuccessful){
                    val result = response.body()
                    _currentRcList.value=result!!.notificationResult
                }else{
                   Log.e("ERROR","공지사항 받아오는 api 실패")
                }
            }
            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                Log.e("ERROR","공지사항 받아오는 api 통신 실패")
            }

        })
    }
}