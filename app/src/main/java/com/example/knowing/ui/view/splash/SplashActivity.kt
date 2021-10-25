package com.example.knowing.ui.view.splash

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
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

        //화면 하단 softkeyNavigationbar 높이 구하는 방법
        var softKeyHeight = 0
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            softKeyHeight = resources.getDimensionPixelSize(resourceId)
        }


        //statusbar 투명하게 설정
        val window = window
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)


        //soft_key_navigation_bar 만큼 view가 올라오도록 최상단 layout에 패딩을 설정
        binding.constraint.setPadding(0,0,0,softKeyHeight)

    }
}