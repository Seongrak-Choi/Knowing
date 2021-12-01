package com.teamteam.knowing.ui.view.agree_terms

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.config.ApplicationClass.Companion.PUSH_ALARM_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.sp
import com.teamteam.knowing.databinding.ActivityAgreeTermsBinding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.view.main.mypage.quize_terms.PersonalInformationActivity
import com.teamteam.knowing.ui.view.main.mypage.quize_terms.TermsOfUseActivity
import com.teamteam.knowing.ui.view.onboarding.OnboardingActivity
import com.teamteam.knowing.ui.viewmodel.AgreeTermsActivityViewModel

class AgreeTermsActivity:BaseActivity<ActivityAgreeTermsBinding>(ActivityAgreeTermsBinding::inflate) {
    private lateinit var agreeTermsActivityViewModel:AgreeTermsActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        //뷰모델 장착
        agreeTermsActivityViewModel=ViewModelProvider(this).get(AgreeTermsActivityViewModel::class.java)


        //확인 버튼 isEnabled 컨트롤 하도록 라이브데이터 관찰
        agreeTermsActivityViewModel.currentOkBtnState.observe(this, Observer {
            binding.btnOk.isEnabled = it
        })

        //모두 동의합니다 체크 버튼 이미지 변경을 위해 라이브데이터 관찰
        agreeTermsActivityViewModel.currentAllBtnState.observe(this, Observer {
            if (it){
                binding.btnAll.setImageResource(R.drawable.ic_agree_checked)
            }else{
                binding.btnAll.setImageResource(R.drawable.ic_agree_uncheck)
            }
        })


        //이용약관 체크 버튼 이미지 변경을 위해 라이브데이터 관찰
        agreeTermsActivityViewModel.currentTermsOfUseBtnState.observe(this, Observer {
            if (it){
                binding.btnTermsOfUse.setImageResource(R.drawable.ic_agree_checked)
            }else{
                binding.btnTermsOfUse.setImageResource(R.drawable.ic_agree_uncheck)
            }
        })



        //개인정보 수집 이용 체크 버튼 이미지 변경을 위해 라이브데이터 관찰
        agreeTermsActivityViewModel.currentPrivateInfoBtnState.observe(this, Observer {
            if (it){
                binding.btnPrivateInfo.setImageResource(R.drawable.ic_agree_checked)
            }else{
                binding.btnPrivateInfo.setImageResource(R.drawable.ic_agree_uncheck)
            }
        })


        //복지 정보 알람 체크 버튼 이미지 변경을 위해 라이브데이터 관찰
        agreeTermsActivityViewModel.currentAlarmBtnState.observe(this, Observer {
            if (it){
                binding.btnAlarm.setImageResource(R.drawable.ic_agree_checked)
            }else{
                binding.btnAlarm.setImageResource(R.drawable.ic_agree_uncheck)
            }
        })



        //모두 동의합니다 체크 버튼 클릭 리스너
        binding.btnAll.setOnClickListener {
            agreeTermsActivityViewModel.changeAllBtnState()
            agreeTermsActivityViewModel.allCollect()
        }

        //이용약관 (필수) 체크 버튼 클릭 리스너
        binding.btnTermsOfUse.setOnClickListener {
            agreeTermsActivityViewModel.changeTermsOfUseBtnState()
            agreeTermsActivityViewModel.allCollect()
        }

        //개인정보 수집 이용 체크 버튼 클릭 리스너
        binding.btnPrivateInfo.setOnClickListener {
            agreeTermsActivityViewModel.changePrivateInfoBtnState()
            agreeTermsActivityViewModel.allCollect()
        }

        //복지 정보 앱 푸시 체크 버튼 클릭 리스너
        binding.btnAlarm.setOnClickListener {
            agreeTermsActivityViewModel.changeAlarmBtnState()
            agreeTermsActivityViewModel.allCollect()
        }


        //이용약관 보러 가기 버튼 클릭 리스너
        binding.btnShowTermsOfUse.setOnClickListener {
            val intent = Intent(this,TermsOfUseActivity::class.java)
            startActivity(intent)
        }

        //개인정보 수집 이용 (필수) 보러 가기 버튼 클릭 리스너
        binding.btnShowPrivateInfo.setOnClickListener {
            val intent = Intent(this,PersonalInformationActivity::class.java)
            startActivity(intent)
        }


        //확인 버튼 클릭 리스너
        binding.btnOk.setOnClickListener {
            //푸쉬 알림 여부 저장
            val editor = sp.edit()
            editor.putBoolean(PUSH_ALARM_KEY,agreeTermsActivityViewModel.currentAlarmBtnState.value!!)
            //onBoarding을 한번 설정했으면 앱을 삭제하기 전까지는 볼 필요 없게 관리하기 위해 sp에 저장
            editor.putBoolean(ApplicationClass.IS_FIRST_KEY,true)
            editor.apply()

            val intent = Intent(this,OnboardingActivity::class.java)
            startActivity(intent)
        }
    }
}