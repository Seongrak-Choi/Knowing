package com.teamteam.knowing.ui.view.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_BIRTH_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_EMAIL_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_GENDER_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_NAME_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_PHONE_NUM_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_PROVIDER_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.sp
import com.teamteam.knowing.databinding.ActivityLoadingBinding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.viewmodel.LoadingActivityViewModel

class LoadingActivity : BaseActivity<ActivityLoadingBinding>(ActivityLoadingBinding::inflate){
    lateinit var loadingActivityViewModel: LoadingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //lottie 애니메이션 시작
        binding.lottieView.playAnimation()

        //sp에서 uid를 불러와서 uid에 저장
        val uid = sp.getString(ApplicationClass.UID_KEY,"").toString()
        //sp에서 받아온 uid를 클래스 변수에 저장
        ApplicationClass.USER_UID = uid

        //sp에서 user_name을 불러와서 저장
        val user_name = sp.getString(USER_NAME_KEY,"")

        //sp에서 불러온 user_name이 처음 회원가입하여 없는 경있는지 없는지 판단해서 라이팅 구분
        if(!user_name.isNullOrEmpty()){
            binding.txTitle1.text="${user_name}님의 복지 수혜 예상\n금액을 계산중이에요!"
        }else{
            binding.txTitle1.text="복지 수혜 예상\n금액을 계산중이에요!"
        }


        //뷰모델 장착
        loadingActivityViewModel = ViewModelProvider(this).get(com.teamteam.knowing.ui.viewmodel.LoadingActivityViewModel::class.java)

        //유저 정보 api 결과 라이브 데이터 관찰
        loadingActivityViewModel.isSuccessUserAPI.observe(this, Observer {
            if (it){
                val editor = sp.edit()
                editor.putString(USER_NAME_KEY,loadingActivityViewModel.userInfoApiResult.value!!.name)
                editor.putString(USER_EMAIL_KEY,loadingActivityViewModel.userInfoApiResult.value!!.email)
                editor.putString(USER_PHONE_NUM_KEY,loadingActivityViewModel.userInfoApiResult.value!!.phNum)
                editor.putString(USER_GENDER_KEY,loadingActivityViewModel.userInfoApiResult.value!!.gender)
                editor.putString(USER_BIRTH_KEY,loadingActivityViewModel.userInfoApiResult.value!!.birth.toString())
                editor.putString(USER_PROVIDER_KEY,loadingActivityViewModel.userInfoApiResult.value!!.provider)
                editor.apply()


                //유저 정보 sharedPreference에 저장한 후 복지정보 api 메소드 호출
                loadingActivityViewModel.tryGetWelfareInfo(ApplicationClass.USER_UID)
            }
        })


        //api 호출이 성공하면 해당 actvity는 종료 시키기 위해 라이브데이터로 관찰
        loadingActivityViewModel.isSuccessAPI.observe(this, Observer {
          //  if (it)
               // this.finish()
        })


        //유저정보 api 메소드 호출
        loadingActivityViewModel.tryGetUserInfo(ApplicationClass.USER_UID)


        //statusbar 투명하게 설정(네비게이션bar는 투명해지지 않음)
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT
    }
}