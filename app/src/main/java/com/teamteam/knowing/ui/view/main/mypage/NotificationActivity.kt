package com.teamteam.knowing.ui.view.main.mypage

import android.os.Bundle
import com.teamteam.knowing.databinding.ActivityNotificationBinding
import com.teamteam.knowing.ui.base.BaseActivity

class NotificationActivity:BaseActivity<ActivityNotificationBinding>(ActivityNotificationBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}