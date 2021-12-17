package com.teamteam.knowing.ui.view.main.mypage

import android.os.Bundle
import com.teamteam.knowing.config.ApplicationClass.Companion.PUSH_ALARM_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.sp
import com.teamteam.knowing.databinding.ActivityAlarmSettingBinding
import com.teamteam.knowing.ui.base.BaseActivity

class AlarmSettingActivity:BaseActivity<ActivityAlarmSettingBinding>(ActivityAlarmSettingBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //sp에 저장하기 위해 사용할 에디터
        val editor = sp.edit()

        //초기 알람 동의 설정 값을 가져와서 저장
        val currentAlarmState=sp.getBoolean(PUSH_ALARM_KEY,false)

        //마지막으로 설정했던 알람 동의 설정 값을 셋팅
        binding.switchCustom.isChecked=currentAlarmState


        //스위치 상태에 따라 sp에 저장
        binding.switchCustom.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                editor.putBoolean(PUSH_ALARM_KEY,true)
                editor.apply()
            }else{
                editor.putBoolean(PUSH_ALARM_KEY,false)
                editor.apply()
            }
        }

        //뒤로가기 버튼 클릭 리스너
        binding.btnBack.setOnClickListener {
            this.finish()
        }

    }
}