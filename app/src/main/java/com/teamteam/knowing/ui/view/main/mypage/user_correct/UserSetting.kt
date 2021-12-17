package com.teamteam.knowing.ui.view.main.mypage.user_correct

import android.content.Intent
import android.os.Bundle
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_PROVIDER_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.sp
import com.teamteam.knowing.databinding.ActivityUserSettingBinding
import com.teamteam.knowing.ui.base.BaseActivity

class UserSetting:BaseActivity<ActivityUserSettingBinding>(ActivityUserSettingBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //회원정보 수정하기 버튼 클릭 리스너
        binding.btnGoUserCorrect.setOnClickListener {
            //로그인한 계정이 sns 로그인이 아닌 경우
            if (sp.getString(USER_PROVIDER_KEY,"".toString())=="default"){
                //회원 정보 수정하기 액티비로 이동
                val intent = Intent(this, UserCorrectActivity::class.java)
                this.startActivity(intent)
            }else{
                //회원 정보 수정하기 액티비로 이동
                val intent = Intent(this, SnsUserCorrectActivity::class.java)
                this.startActivity(intent)
            }
        }

        //추가 정보 수정하기 버튼 클릭 리스너
        binding.btnGoMoreInformationCorrect.setOnClickListener {
            val intent = Intent(this, MoreInformationCorrectActivity::class.java)
            this.startActivity(intent)
        }


        //뒤로가기 아이콘 클릭 리스너
        binding.btnBack.setOnClickListener {
            this.onBackPressed()
        }
    }
}