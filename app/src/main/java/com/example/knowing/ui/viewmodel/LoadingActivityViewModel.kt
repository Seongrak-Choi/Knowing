package com.example.knowing.ui.viewmodel

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.knowing.config.ApplicationClass
import com.example.knowing.data.model.network.response.MainWelfareResponse
import com.example.knowing.data.remote.api.WelfareInterface
import com.example.knowing.ui.view.main.MainActivity
import com.example.knowing.ui.view.onboarding.OnboardingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadingActivityViewModel(application : Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext

    private val _isSuccessAPI = MutableLiveData<Boolean>()

//    //api결과를 저장할 라이브데이터
//    private val _welfareApiResult = MutableLiveData<MainWelfareResponse>()
//
//    val welfareResult : MutableLiveData<MainWelfareResponse>
//        get() = _welfareApiResult

    val isSuccessAPI : MutableLiveData<Boolean>
        get() = _isSuccessAPI

    /*
    복지 정보 받아오는 api
     */
    fun tryGetWelfareInfo(uid:String){
        val welfareInterface = ApplicationClass.sRetrofit.create(WelfareInterface::class.java)
        welfareInterface.getWelfareInfo(uid).enqueue(object:Callback<MainWelfareResponse>{
            override fun onResponse(call: Call<MainWelfareResponse>, response: Response<MainWelfareResponse>) {
                if (response.isSuccessful){
                    val result = response.body() as MainWelfareResponse

                    //받아온 복지 정보를 받아서 main화면으로 이동
                    val intent = Intent(mContext,MainActivity::class.java)
                    intent.putExtra("welfareInfo",result)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                    mContext.startActivity(intent)
                    _isSuccessAPI.value=true
                }else{
                    Log.e("ERROR","서버에서 복지 정보 받아오지 못함")
                    Toast.makeText(mContext,"복지 정보를 받아오는데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MainWelfareResponse>, t: Throwable) {
                Log.e("ERROR","서버에서 복지 정보 받아오지 못함")
                Toast.makeText(mContext,"복지 정보를 받아오는데 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }

        })
    }
}