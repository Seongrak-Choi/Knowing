package com.example.knowing.ui.view.more_information

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.R
import com.example.knowing.databinding.ActivityMoreInformation4Binding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.MoreInformationActivity4ViewModel
import com.example.knowing.ui.viewmodel.MoreInformationActivity5ViewModel

class MoreInformationActivity4 : BaseActivity<ActivityMoreInformation4Binding>(ActivityMoreInformation4Binding::inflate) {
    private lateinit var majorBtnMap : MutableMap<Button,Boolean>
    private lateinit var moreInformationActivity4ViewModel: MoreInformationActivity4ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //이 화면은, 오른쪽에서 왼쪽으로 슬라이딩 하면서 켜집니다.
        overridePendingTransition(R.animator.horizon_enter,R.animator.none)

        //뷰모델 장착
        moreInformationActivity4ViewModel = ViewModelProvider(this).get(
            MoreInformationActivity4ViewModel::class.java)

        //인문 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationActivity4ViewModel.isSelectHumanities.observe(this, Observer {
            if (it){
                binding.btnMajor1.background=getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor1.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }else{
                binding.btnMajor1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor1.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //사회 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationActivity4ViewModel.isSelectSociety.observe(this, Observer {
            if (it){
                binding.btnMajor2.background=getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor2.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }else{
                binding.btnMajor2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor2.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //법 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationActivity4ViewModel.isSelectLaw.observe(this, Observer {
            if (it){
                binding.btnMajor3.background=getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor3.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }else{
                binding.btnMajor3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor3.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //경영 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationActivity4ViewModel.isSelectManagement.observe(this, Observer {
            if (it){
                binding.btnMajor4.background=getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor4.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }else{
                binding.btnMajor4.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor4.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })


        //교육 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationActivity4ViewModel.isSelectEducation.observe(this, Observer {
            if (it){
                binding.btnMajor5.background=getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor5.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }else{
                binding.btnMajor5.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor5.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //공학 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationActivity4ViewModel.isSelectEngineering.observe(this, Observer {
            if (it){
                binding.btnMajor6.background=getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor6.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }else{
                binding.btnMajor6.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor6.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //자연 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationActivity4ViewModel.isSelectNature.observe(this, Observer {
            if (it){
                binding.btnMajor7.background=getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor7.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }else{
                binding.btnMajor7.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor7.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //예체능 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationActivity4ViewModel.isSelectEntertainment.observe(this, Observer {
            if (it){
                binding.btnMajor8.background=getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor8.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }else{
                binding.btnMajor8.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor8.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })


        //의약 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationActivity4ViewModel.isSelectMedical.observe(this, Observer {
            if (it){
                binding.btnMajor9.background=getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor9.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }else{
                binding.btnMajor9.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor9.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })

        //기타 버튼 클릭 상태 변경해주기 위해 라이브 데이터 관찰
        moreInformationActivity4ViewModel.isSelectEtc.observe(this, Observer {
            if (it){
                binding.btnMajor10.background=getDrawable(R.drawable.more_information_4_major_btn_checked_bg) //버튼의 선택됨 백그라운드 설정
                binding.btnMajor10.setTextColor(getColor(R.color.more_information_major_btn_checked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }else{
                binding.btnMajor10.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)//버튼의 선택됨 백그라운드 설정
                binding.btnMajor10.setTextColor(getColor(R.color.more_information_major_btn_unchecked_text_color))//버튼의 선택됨 텍스트 컬러로 변경
            }
        })


        //majorBtn1~10까지를 Map으로 선언 값이 선택되어져 있는지 확인하기 위함
        majorBtnMap = mutableMapOf<Button,Boolean>(binding.btnMajor1 to false,
            binding.btnMajor2 to false,
            binding.btnMajor3 to false,
            binding.btnMajor4 to false,
            binding.btnMajor5 to false,
            binding.btnMajor6 to false,
            binding.btnMajor7 to false,
            binding.btnMajor8 to false,
            binding.btnMajor9 to false,
            binding.btnMajor10 to false)

        //majorBtn1~10까지에 클리 리스너 장착
        for (i in majorBtnMap.keys){
            i.setOnClickListener(majorBtnClickListener)
        }

        // moreInformationActivity5로 가기 위한 다음 버튼 클릭 리스너
        binding.btnNext.setOnClickListener {
            var intent = Intent(this,MoreInformationActivity5::class.java)
            startActivity(intent)
        }
    }


    /*
  화면이 종료 될때 실행되는 메소드
   */
    override fun onBackPressed() {
        super.onBackPressed()
        if (isFinishing){
            overridePendingTransition(R.animator.none,R.animator.horizon_exit)
        }
    }



    /*
    btnMajor1~10버튼들의 클릭 리스너
     */
    private val majorBtnClickListener: View.OnClickListener = object:View.OnClickListener{
        override fun onClick(v: View?) {
            when(v){
                binding.btnMajor1->{
                    moreInformationActivity4ViewModel.changeBtnHumanities()
                }
                binding.btnMajor2->{
                    moreInformationActivity4ViewModel.changeBtnSociety()
                }
                binding.btnMajor3->{
                    moreInformationActivity4ViewModel.changeBtnLaw()
                }
                binding.btnMajor4->{
                    moreInformationActivity4ViewModel.changeBtnManagement()
                }
                binding.btnMajor5->{
                    moreInformationActivity4ViewModel.changeBtnEducation()
                }
                binding.btnMajor6->{
                    moreInformationActivity4ViewModel.changeBtnEngineering()
                }
                binding.btnMajor7->{
                    moreInformationActivity4ViewModel.changeBtnNature()
                }
                binding.btnMajor8->{
                    moreInformationActivity4ViewModel.changeBtnEntertainment()
                }
                binding.btnMajor9->{
                    moreInformationActivity4ViewModel.changeBtnMedical()
                }
                binding.btnMajor10->{
                    moreInformationActivity4ViewModel.changeBtnEtc()
                }
            }
        }
    }
}