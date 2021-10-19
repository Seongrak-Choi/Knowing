package com.example.knowing.ui.view.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.R
import com.example.knowing.databinding.ActivitySplashBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.SplashActivityViewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate){
    lateinit var splashActivityViewModel: SplashActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModel 설정
        splashActivityViewModel = ViewModelProvider(this).get(SplashActivityViewModel::class.java)

    }
}