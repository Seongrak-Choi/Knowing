package com.teamteam.knowing.ui.view.splash

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.common.util.Utility
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.config.ApplicationClass.Companion.sp
import com.teamteam.knowing.databinding.ActivitySplashBinding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.viewmodel.SplashActivityViewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate){
    lateinit var splashActivityViewModel: SplashActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        //해시키 값을 구하기 위한 코드
//        var keyHash = Utility.getKeyHash(this)
//        println("keyHash: $keyHash")


        //sp에서 uid를 불러와서 uid에 저장
        val uid = sp.getString(ApplicationClass.UID_KEY,"").toString()

        //viewModel 설정
        splashActivityViewModel = ViewModelProvider(this).get(SplashActivityViewModel::class.java)

        splashActivityViewModel.isSuccessMove.observe(this, Observer {
            if (it)
                finish()
        })

        //전역 변수에 sp에서 받아온 유저 UID 값이 있는 지 확인
        if (!uid.isNullOrEmpty()){ //값이 있다면
            ApplicationClass.USER_UID = uid
            //LoadingActivity로 이동하는 메소드 실행
            splashActivityViewModel.goLoadingActivity()
        }else{//값이 비어있다면
            //JoinSnsUpActivity로 이동하는 메소드 실행
            splashActivityViewModel.goJoinSnsUpActivity()
        }





//        //화면 하단 softkeyNavigationbar 높이 구하는 방법
//        var softKeyHeight = 0
//        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
//        if (resourceId > 0) {
//            softKeyHeight = resources.getDimensionPixelSize(resourceId)
//        }

       // printHashKey(this)



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

//    fun printHashKey(context: Context): String? {
//        val TAG = "HASH_KEY"
//
//        var hashKey : String? = null
//
//        try {
//            val info : PackageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//
//                var md : MessageDigest
//
//                md = MessageDigest.getInstance("SHA")
//
//                md.update(signature.toByteArray())
//
//                hashKey = String(Base64.encode(md.digest(), 0))
//
//                Log.d(TAG, hashKey)
//
//            }
//
//        } catch (e:Exception){
//
//            Log.e(TAG, e.toString())
//
//        }
//
//        return hashKey
//    }
}