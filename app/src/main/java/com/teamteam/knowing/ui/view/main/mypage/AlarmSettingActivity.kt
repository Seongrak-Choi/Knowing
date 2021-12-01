package com.teamteam.knowing.ui.view.main.mypage

import android.os.Bundle
import com.teamteam.knowing.databinding.ActivityAlarmSettingBinding
import com.teamteam.knowing.ui.base.BaseActivity

class AlarmSettingActivity:BaseActivity<ActivityAlarmSettingBinding>(ActivityAlarmSettingBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뒤로가기 버튼 클릭 리스너
        binding.btnBack.setOnClickListener {
            this.finish()
        }

    }
}