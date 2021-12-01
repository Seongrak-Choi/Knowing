package com.teamteam.knowing.ui.view.main.mypage.quize_terms

import android.os.Bundle
import com.teamteam.knowing.databinding.ActivityOpenSourceBinding
import com.teamteam.knowing.ui.base.BaseActivity

class OpenSourceActivity:BaseActivity<ActivityOpenSourceBinding>(ActivityOpenSourceBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}