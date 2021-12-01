package com.teamteam.knowing.ui.view.main.mypage.quize_terms

import android.os.Bundle
import com.teamteam.knowing.databinding.ActivityPersonalInformationBinding
import com.teamteam.knowing.ui.base.BaseActivity

class PersonalInformationActivity:BaseActivity<ActivityPersonalInformationBinding>(ActivityPersonalInformationBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}