package com.teamteam.knowing.ui.view.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass.Companion.UID_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_BIRTH_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_EMAIL_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_GENDER_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_NAME_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_PHONE_NUM_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_PROVIDER_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.sp
import com.teamteam.knowing.databinding.FragmentMyPageBinding
import com.teamteam.knowing.ui.base.BaseFragment
import com.teamteam.knowing.ui.view.login.JoinUpSNSActivity
import com.teamteam.knowing.ui.view.main.mypage.quize_terms.QuizeTermsActivity

class MyPageFragment:BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //statusbar 높이 구하는 방법
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }

        //statusbar가 투명해진 만큼 화면이 밀려 올라가는 것을 방지하기 위해 statusbar 높이 만큼 마진을 줌줌
        var params = binding.constraint.layoutParams as FrameLayout.LayoutParams
        params.setMargins(0,result,0,0)
        binding.constraint.layoutParams=params


        //title 사용자 이름 넣어서 textview셋팅
        binding.txName.text="${sp.getString(USER_NAME_KEY,"").toString()}님"


        //회원정보설정 버튼 클릭 리스너
        binding.btnProfileSetting.setOnClickListener {
            //로그인한 계정이 sns 로그인이 아닌 경우
            if (sp.getString(USER_PROVIDER_KEY,"".toString())=="default"){
                //회원 정보 수정하기 액티비로 이동
                val intent = Intent(requireContext(),UserCorrectActivity::class.java)
                requireContext().startActivity(intent)
            }else{
                //회원 정보 수정하기 액티비로 이동
                val intent = Intent(requireContext(),SnsUserCorrectActivity::class.java)
                requireContext().startActivity(intent)
            }

        }

        //알림 설정 버튼 클릭 리스너
        binding.btnAlarmSetting.setOnClickListener {
            //알람 설정 액티비티로 이동
            val intent = Intent(requireContext(),AlarmSettingActivity::class.java)
            startActivity(intent)
        }

        //카테고리 설정 버튼 클릭 리스너
        binding.btnCategorySetting.setOnClickListener {
            //카테고리 수정 액티비티로 이동
            val intent = Intent(requireContext(),CategoryCorrectActivity::class.java)
            startActivity(intent)
        }

        //문의 및 약관 버튼 클릭 리스너
        binding.btnQuizeTerms.setOnClickListener {
            //문의 및 약관 액티비티로 이동
            val intent = Intent(requireContext(), QuizeTermsActivity::class.java)
            startActivity(intent)
        }

        //공시 사항 버튼 클릭 리스너
        binding.btnNotification.setOnClickListener {
            //공지 사항 액티비티로 이동
            val intent = Intent(requireContext(),NotificationActivity::class.java)
            startActivity(intent)
        }


        //로그아웃 버튼 클릭 리스너
        binding.btnLogout.setOnClickListener {
            //로그아웃을 했으니 유저의 대한 정보를 sp에서 삭제한다.
            val editor = sp.edit()
            editor.putString(UID_KEY,"")
            editor.putString(USER_NAME_KEY,"")
            editor.putString(USER_EMAIL_KEY,"")
            editor.putString(USER_PROVIDER_KEY,"")
            editor.putString(USER_PHONE_NUM_KEY,"")
            editor.putString(USER_GENDER_KEY,"")
            editor.putString(USER_BIRTH_KEY,"")
            editor.apply()

            //JoinUpSns 액티비티로 이동
            val intent = Intent(requireContext(),JoinUpSNSActivity::class.java)
            startActivity(intent)


            requireActivity().finish()
        }
    }

    override fun onResume() {
        super.onResume()

        //title 사용자 이름 넣어서 textview셋팅
        binding.txName.text="${sp.getString(USER_NAME_KEY,"").toString()}님"
    }
}