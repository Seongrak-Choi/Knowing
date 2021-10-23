package com.example.knowing.ui.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.example.knowing.ui.view.login.JoinUpSNSActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivityViewModel(application: Application) : AndroidViewModel(application) {
    init {
        /*
        viewModel이 호출되면 3초 뒤에 메인 화면 또는 로그인 화면으로 이동하는 코드
         */
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L) //
            var intent = Intent(application.applicationContext,JoinUpSNSActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
            application.startActivity(intent)
        }
    }
}