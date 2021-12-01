package com.teamteam.knowing.ui.view.sign_in.search_email

import android.os.Bundle
import com.teamteam.knowing.databinding.ActivityLoadingSearchEmailBinding
import com.teamteam.knowing.ui.base.BaseActivity

class LoadingSearchEmailActivity:BaseActivity<ActivityLoadingSearchEmailBinding>(ActivityLoadingSearchEmailBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var name = intent.getStringExtra("name")
        var email = intent.getStringExtra("email")

        //lottie 애니메이션 시작
        binding.lottieView.playAnimation()

        //tx titl1에 고객의 이름을 넣어줌
        binding.txTitle1.text="${name}님의 이메일을 찾았어요!"

        //tx_email에 고객의 이메일을 알려줌
        var email_id=email!!.split("@")
        var email_id_security =email_id[0] //아이디의 뒷 자리 2개를 *로 바꿔주기 위한 데이터 가공
        var email_id_security2 = email_id_security.substring(0,email_id_security.length-2)+"**"

        binding.txEmail.text=email_id_security2+"@"+email_id[1]

        //다음 버튼 클릭 리스너
        binding.btnOk.setOnClickListener {
            this.finish()
        }

//        CoroutineScope(Dispatchers.Main).launch {
//            delay(3000L) //
//            var intent = Intent(application.applicationContext, MainActivity::class.java)
//            application.startActivity(intent)
//        }
    }
}