package com.example.knowing.ui.view.splash

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.R
import com.example.knowing.config.ApplicationClass
import com.example.knowing.databinding.ActivitySplashBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.SplashActivityViewModel
import java.security.MessageDigest

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

        printHashKey(this)



        //statusbar 투명하게 설정(네비게이션bar도 투명해지는 단점이 존재)
//        val window = window
//        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        //statusbar 투명하게 설정(네비게이션bar는 투명해지지 않음)
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT


//        //soft_key_navigation_bar 만큼 view가 올라오도록 최상단 layout에 패딩을 설정
//        binding.constraint.setPadding(0,0,0,softKeyHeight)

    }

    fun printHashKey(context: Context): String? {
        val TAG = "HASH_KEY"

        var hashKey : String? = null

        try {
            val info : PackageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {

                var md : MessageDigest

                md = MessageDigest.getInstance("SHA")

                md.update(signature.toByteArray())

                hashKey = String(Base64.encode(md.digest(), 0))

                Log.d(TAG, hashKey)

            }

        } catch (e:Exception){

            Log.e(TAG, e.toString())

        }

        return hashKey

    }
}