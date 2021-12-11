package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.domain.SignUpUser
import com.teamteam.knowing.data.model.network.response.MainWelfareResponse
import com.teamteam.knowing.data.model.network.response.PostFcmTokenResponse
import com.teamteam.knowing.data.model.network.response.UserInfoResult
import com.teamteam.knowing.data.remote.api.SignUpInterface
import com.teamteam.knowing.data.remote.api.WelfareInterface
import com.teamteam.knowing.ui.view.main.LoadingActivity
import com.teamteam.knowing.ui.view.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadingActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext

    //복지 정보 api 성공 했는지 여부를 저장할 라이브데이터
    private val _isSuccessAPI = MutableLiveData<Boolean>()

    //유저 정보 api 성공 했는지 여부를 저장할 라이브데이터
    private val _isSuccessUserAPI = MutableLiveData<Boolean>()


    //유저 정보 api result를 저장할 라이브데이터
    private val _userInfoApiResult = MutableLiveData<SignUpUser>()


    val userInfoApiResult: MutableLiveData<SignUpUser>
        get() = _userInfoApiResult


    val isSuccessAPI: MutableLiveData<Boolean>
        get() = _isSuccessAPI

    val isSuccessUserAPI: MutableLiveData<Boolean>
        get() = _isSuccessUserAPI


    /*
    복지 정보 받아오는 api
     */
    fun tryGetWelfareInfo(uid: String) {
        val welfareInterface = ApplicationClass.sRetrofit.create(WelfareInterface::class.java)
        welfareInterface.getWelfareInfo(uid).enqueue(object : Callback<MainWelfareResponse> {
            override fun onResponse(
                call: Call<MainWelfareResponse>,
                response: Response<MainWelfareResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body() as MainWelfareResponse

                    CoroutineScope(Dispatchers.Main).launch {
                        delay(2000L) //

                        //받아온 복지 정보를 받아서 main화면으로 이동
                        val intent = Intent(mContext, MainActivity::class.java)
                        intent.putExtra("welfareInfo", result)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //activity stack을 지워서 뒤로가면 앱이 꺼져서 해당 activity못오게 함
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                        mContext.startActivity(intent)
                        _isSuccessAPI.value = true
                    }
                } else {
                    Log.e("ERROR", "서버에서 복지 정보 받아오지 못함")
                    Toast.makeText(mContext, "복지 정보를 받아오는데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MainWelfareResponse>, t: Throwable) {
                Log.e("ERROR", "서버에서 복지 정보 받아오지 못함")
                Toast.makeText(mContext, "복지 정보를 받아오는데 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }


    /*
    유저 정보 받아오는 api
     */
    fun tryGetUserInfo(uid: String) {
        val signUpInterface = ApplicationClass.sRetrofit.create(SignUpInterface::class.java)
        signUpInterface.getUserInfo(uid).enqueue(object : Callback<UserInfoResult> {
            override fun onResponse(call: Call<UserInfoResult>, response: Response<UserInfoResult>) {
                if (response.isSuccessful) {
                    val result = response.body() as UserInfoResult
                    _userInfoApiResult.value = result.userInfoResult
                    _isSuccessUserAPI.value = true
                } else {
                    Log.e("ERROR", "유저 정보 받아오기 실패")
                }
            }

            override fun onFailure(call: Call<UserInfoResult>, t: Throwable) {
                Log.e("ERROR", "유저 정보 받아오는 api 통신 실패")
            }

        })
    }

    //유저 fcm토큰 초기화해서 서버에 post하는 api 메소드
    fun tryPostUserFcmToken(userUid:String,fcmtoken:String){
        println("api 호출은 되나?")
        val signUpInterface = ApplicationClass.sRetrofit.create(SignUpInterface::class.java)
        signUpInterface.postUserFCMToken(userUid,fcmtoken).enqueue(object:Callback<PostFcmTokenResponse>{
            override fun onResponse(call: Call<PostFcmTokenResponse>, response: Response<PostFcmTokenResponse>) {
                if (response.isSuccessful){
                    val result = response.body() as PostFcmTokenResponse
                    Log.d("INFO","fcm토큰 post 결과: ${result.postFcmTokenResult.status}")
                }else{
                    Log.e("ERROR","fcm토큰 post api 실패")
                }
            }
            override fun onFailure(call: Call<PostFcmTokenResponse>, t: Throwable) {
                Log.e("ERROR","FCM토큰 POST API 통신 실패")
            }

        })
    }
}