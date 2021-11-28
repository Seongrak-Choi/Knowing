package com.example.knowing.ui.view.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.config.ApplicationClass
import com.example.knowing.databinding.ActivityLoadingBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.LoadingActivityViewModel

class LoadingActivity : BaseActivity<ActivityLoadingBinding>(ActivityLoadingBinding::inflate){
    lateinit var loadingActivityViewModel: LoadingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        sp에 uid 임시 저장용
//        val editor = ApplicationClass.sp.edit()
//        editor.putString(ApplicationClass.UID_KEY,"MHQ72TN4d8dFFL2b74Ldy4s3EHa2")
//        editor.apply()

        //sp에서 uid를 불러와서 uid에 저장
        val sp = getSharedPreferences("KNOWING", MODE_PRIVATE)
        val uid = sp.getString(ApplicationClass.UID_KEY," ")

        loadingActivityViewModel = ViewModelProvider(this).get(com.example.knowing.ui.viewmodel.LoadingActivityViewModel::class.java)

        //복지정보 api 메소드 호출
        loadingActivityViewModel.tryGetWelfareInfo(uid.toString())

        //api 호출이 성공하면 해당 actvity는 종료 시키기 위해 라이브데이터로 관찰
        loadingActivityViewModel.isSuccessAPI.observe(this, Observer {
            if (it)
                this.finish()
        })

        //lottie 애니메이션 시작
        binding.lottieView.playAnimation()

        //statusbar 투명하게 설정(네비게이션bar는 투명해지지 않음)
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT
    }
}