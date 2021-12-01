package com.teamteam.knowing.ui.view.main.mypage.quize_terms

import android.content.Intent
import android.os.Bundle
import com.teamteam.knowing.databinding.ActivityQuizeTermsBinding
import com.teamteam.knowing.ui.base.BaseActivity

class QuizeTermsActivity : BaseActivity<ActivityQuizeTermsBinding>(ActivityQuizeTermsBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뒤로가기 버튼 클릭 리스너
        binding.btnBack.setOnClickListener{
            finish()
        }

        //개인정보처리방침 버튼 클릭 리스너
        binding.btnPersonalInformation.setOnClickListener {
            //개인정보처리방침 액티비티로 이동
            val intent = Intent(this,PersonalInformationActivity::class.java)
            startActivity(intent)
        }

        //오픈소스 라이선스
        binding.btnOpenSource.setOnClickListener {
            //오픈소스 액티비티로 이동
            val intent = Intent(this,OpenSourceActivity::class.java)
            startActivity(intent)
        }


        //이용 약관 버튼 클릭 리스너
        binding.btnTermsOfUse.setOnClickListener {
            //이용약관 액티비티로 이동
            val intent = Intent(this,TermsOfUseActivity::class.java)
            startActivity(intent)
        }
    }
}