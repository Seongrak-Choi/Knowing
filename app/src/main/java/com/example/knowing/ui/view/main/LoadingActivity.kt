package com.example.knowing.ui.view.main

import android.os.Bundle
import com.example.knowing.databinding.ActivityLoadingBinding
import com.example.knowing.ui.base.BaseActivity

class LoadingActivity : BaseActivity<ActivityLoadingBinding>(ActivityLoadingBinding::inflate){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lottieView.setAnimation("lf20_ng9j9lpx_1.json")
        binding.lottieView.loop(true)
        binding.lottieView.playAnimation()
    }
}