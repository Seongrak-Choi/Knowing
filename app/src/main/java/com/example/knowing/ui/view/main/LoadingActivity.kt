package com.example.knowing.ui.view.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.databinding.ActivityLoadingBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.LoadingActivityViewModel

class LoadingActivity : BaseActivity<ActivityLoadingBinding>(ActivityLoadingBinding::inflate){
    lateinit var LoadingActivityViewModel: LoadingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LoadingActivityViewModel = ViewModelProvider(this).get(com.example.knowing.ui.viewmodel.LoadingActivityViewModel::class.java)

        //lottie 애니메이션 시작
        binding.lottieView.playAnimation()

        //statusbar 투명하게 설정(네비게이션bar는 투명해지지 않음)
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT


    }
}