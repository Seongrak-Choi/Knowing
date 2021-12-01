package com.teamteam.knowing.ui.view.sign_in.search_pwd

import android.content.Intent
import android.os.Bundle
import com.teamteam.knowing.databinding.ActivityLoadingSearchPwdBinding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.view.sign_in.SignInActivity

class LoadingSearchPwdActivity:BaseActivity<ActivityLoadingSearchPwdBinding>(ActivityLoadingSearchPwdBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //lottie 애니메이션 시작
        binding.lottieView.playAnimation()

        //다음 버튼 클릭 리스너
        binding.btnOk.setOnClickListener {
            val intent=Intent(this,SignInActivity::class.java)
            startActivity(intent)

            this.finish()
        }

//        CoroutineScope(Dispatchers.Main).launch {
//            delay(3000L) //
//            var intent = Intent(application.applicationContext, MainActivity::class.java)
//            application.startActivity(intent)
//        }
    }
}