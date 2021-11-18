package com.example.knowing.ui.view.more_information

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.R
import com.example.knowing.config.ApplicationClass
import com.example.knowing.data.model.domain.SignUpUser
import com.example.knowing.databinding.ActivityMoreInformation3Binding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.MoreInformationActivity3ViewModel

class MoreInformationActivity3:BaseActivity<ActivityMoreInformation3Binding>(ActivityMoreInformation3Binding::inflate){
    private lateinit var moreInformationActivity3ViewModel:MoreInformationActivity3ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //MoreInformation2 activity에서 받은 유저 객체
        var user_data =intent.getSerializableExtra("user_data") as SignUpUser

        //이 화면은, 오른쪽에서 왼쪽으로 슬라이딩 하면서 켜집니다.
        overridePendingTransition(R.animator.horizon_enter,R.animator.none)

        //뷰모델 장착
        moreInformationActivity3ViewModel=ViewModelProvider(this).get(MoreInformationActivity3ViewModel::class.java)

        //전체 버튼의 선택 유무를 위해 라이브 데이터 관찰
        moreInformationActivity3ViewModel.isSelectBtnAll.observe(this, Observer {
            if (it){
                binding.btnRegister1.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnRegister1.setTextColor(Color.parseColor("#ffffff"))
                binding.btnRegister1.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnRegister1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnRegister1.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnRegister1.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //고졸미만 버튼의 선택 유무를 위해 라이브 데이터 관찰
        moreInformationActivity3ViewModel.isSelectBtnUnderHighSchool.observe(this, Observer {
            if (it){
                binding.btnRegister2.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnRegister2.setTextColor(Color.parseColor("#ffffff"))
                binding.btnRegister2.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnRegister2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnRegister2.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnRegister2.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //고교졸업 버튼의 선택 유무를 위해 라이브 데이터 관찰
        moreInformationActivity3ViewModel.isSelectBtnGraduateHighSchool.observe(this, Observer {
            if (it){
                binding.btnRegister3.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnRegister3.setTextColor(Color.parseColor("#ffffff"))
                binding.btnRegister3.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnRegister3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnRegister3.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnRegister3.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //대학재학 버튼의 선택 유무를 위해 라이브 데이터 관찰
        moreInformationActivity3ViewModel.isSelectBtnBeingCollege.observe(this, Observer {
            if (it){
                binding.btnRegister4.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnRegister4.setTextColor(Color.parseColor("#ffffff"))
                binding.btnRegister4.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnRegister4.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnRegister4.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnRegister4.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //대학졸업 의 선택 유무를 위해 라이브 데이터 관찰
        moreInformationActivity3ViewModel.isSelectBtnGraduateCollege.observe(this, Observer {
            if (it){
                binding.btnRegister5.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnRegister5.setTextColor(Color.parseColor("#ffffff"))
                binding.btnRegister5.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnRegister5.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnRegister5.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnRegister5.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //석박사 버튼의 선택 유무를 위해 라이브 데이터 관찰
        moreInformationActivity3ViewModel.isSelectBtnDoctor.observe(this, Observer {
            if (it){
                binding.btnRegister6.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnRegister6.setTextColor(Color.parseColor("#ffffff"))
                binding.btnRegister6.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnRegister6.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnRegister6.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnRegister6.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })


        //다이얼로그에서 선택한 대학교 이름이 TextView에 보이기 위해 선택한 학교 이름을 저장하고 있는 라이브데이터 관찰
        moreInformationActivity3ViewModel.currentSelectTxCollegeName.observe(this, Observer {
            binding.txCollegeName.text=it
        })


        //다음 버튼 활성화를 위해 라이브 데이터 관찰
        moreInformationActivity3ViewModel.isCorrectBtn.observe(this, Observer {
            binding.btnNext.isEnabled=it
        })


        //전체 버튼 클릭 리스너
        binding.btnRegister1.setOnClickListener {
            moreInformationActivity3ViewModel.changeBtnAll()
            //버튼 하나라도 선택되어 있는지 확인하는 메소드 호출
            moreInformationActivity3ViewModel.checkIsCorrect()
        }
        //고졸 미만 버튼 클릭 리스너
        binding.btnRegister2.setOnClickListener {
            moreInformationActivity3ViewModel.changeBtnUnderHighSchool()
            //버튼 하나라도 선택되어 있는지 확인하는 메소드 호출
            moreInformationActivity3ViewModel.checkIsCorrect()
        }
        //고교졸업 버튼 클릭 리스너
        binding.btnRegister3.setOnClickListener {
            moreInformationActivity3ViewModel.changeBtnGraduateHighSchool()
            //버튼 하나라도 선택되어 있는지 확인하는 메소드 호출
            moreInformationActivity3ViewModel.checkIsCorrect()
        }
        //대학재학 버튼 클릭 리스너
        binding.btnRegister4.setOnClickListener {
            moreInformationActivity3ViewModel.changeBtnAllBeingCollege()
            //버튼 하나라도 선택되어 있는지 확인하는 메소드 호출
            moreInformationActivity3ViewModel.checkIsCorrect()
        }
        //대학졸업 버튼 클릭 리스너
        binding.btnRegister5.setOnClickListener {
            moreInformationActivity3ViewModel.changeBtnGraudateCollege()
            //버튼 하나라도 선택되어 있는지 확인하는 메소드 호출
            moreInformationActivity3ViewModel.checkIsCorrect()
        }
        //석박사 버튼 클릭 리스너
        binding.btnRegister6.setOnClickListener {
            moreInformationActivity3ViewModel.changeBtnDoctor()
            //버튼 하나라도 선택되어 있는지 확인하는 메소드 호출
            moreInformationActivity3ViewModel.checkIsCorrect()
        }


        //학교 선택 버튼 클릭 리스너
        binding.btnSelectSchool.setOnClickListener {
            val bottomSheet = SelectCollegeDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }



        //'다음'버튼 클릭 리스너
        binding.btnNext.setOnClickListener {
            //moreActivity4로 이동
            val intent= Intent(this,MoreInformationActivity4::class.java)
            user_data.schoolRecords=moreInformationActivity3ViewModel.getSchoolRecords()
            user_data.school=moreInformationActivity3ViewModel.currentSelectTxCollegeName.value.toString()
            intent.putExtra("user_data",user_data)
            startActivity(intent)
        }

        //뒤로가기 버튼 클릭 리스너
        binding.btnBack.setOnClickListener{
            this.onBackPressed()
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
}