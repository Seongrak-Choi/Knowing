package com.teamteam.knowing.ui.view.more_information

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamteam.knowing.R
import com.teamteam.knowing.data.model.domain.SignUpUser
import com.teamteam.knowing.databinding.ActivityMoreInformation2Binding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.viewmodel.MoreInformationActivity2ViewModel
import java.text.DecimalFormat

class MoreInformationActivity2:BaseActivity<ActivityMoreInformation2Binding>(ActivityMoreInformation2Binding::inflate){
    private lateinit var moreInformationActivity2ViewModel: MoreInformationActivity2ViewModel
    private var result = "" //edtIncome의 데이터를 저장하고 있을 변수
    private val decimalFormat: DecimalFormat = DecimalFormat("#,###") //edtIncome에서 3번째 마다 ,를 찍어주기 위한 포맷팅 설정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //MoreInformation1 activity에서 받은 유저 객체
        var user_data =intent.getSerializableExtra("user_data") as SignUpUser

        //이 화면은, 오른쪽에서 왼쪽으로 슬라이딩 하면서 켜집니다.
        overridePendingTransition(R.animator.horizon_enter,R.animator.none)

        //뷰모델 장착
        moreInformationActivity2ViewModel=ViewModelProvider(this).get(MoreInformationActivity2ViewModel::class.java)

        //취업상태-전체 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationActivity2ViewModel.isSelectBtnAll.observe(this, Observer {
            if (it){
                binding.btnGetJob1.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob1.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob1.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnGetJob1.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob1.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob1.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-미취업자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationActivity2ViewModel.isSelectBtnUnEmployment.observe(this, Observer {
            if (it){
                binding.btnGetJob2.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob2.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob2.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnGetJob2.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob2.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob2.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-재직자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationActivity2ViewModel.isSelectBtnEmployment.observe(this, Observer {
            if (it){
                binding.btnGetJob3.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob3.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob3.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnGetJob3.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob3.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob3.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-프리랜서 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationActivity2ViewModel.isSelectBtnFreelancer.observe(this, Observer {
            if (it){
                binding.btnGetJob4.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob4.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob4.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnGetJob4.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob4.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob4.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-일용근로자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationActivity2ViewModel.isSelectBtnDailyWorker.observe(this, Observer {
            if (it){
                binding.btnGetJob5.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob5.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob5.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnGetJob5.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob5.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob5.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-단기근로자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationActivity2ViewModel.isSelectBtnShortWorker.observe(this, Observer {
            if (it){
                binding.btnGetJob6.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob6.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob6.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnGetJob6.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob6.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob6.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-예비창업자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationActivity2ViewModel.isSelectBtnPrepEntrepreneur.observe(this, Observer {
            if (it){
                binding.btnGetJob7.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob7.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob7.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnGetJob7.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob7.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob7.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-자영업자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationActivity2ViewModel.isSelectBtnSelfOwnership.observe(this, Observer {
            if (it){
                binding.btnGetJob8.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob8.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob8.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnGetJob8.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob8.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob8.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })
        //취업상태-영농종사자 버튼의 상태를 변경하기 위해 라이브데이터 관찰
        moreInformationActivity2ViewModel.isSelectBtnFarming.observe(this, Observer {
            if (it){
                binding.btnGetJob9.setBackgroundResource(R.drawable.more_information_4_major_btn_checked_bg)
                binding.btnGetJob9.setTextColor(Color.parseColor("#ffffff"))
                binding.btnGetJob9.typeface= Typeface.createFromAsset(assets,"roboto_bold.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            } else{
                binding.btnGetJob9.setBackgroundResource(R.drawable.more_information_4_major_btn_unchecked_bg)
                binding.btnGetJob9.setTextColor(Color.parseColor("#6c6c6c"))
                binding.btnGetJob9.typeface= Typeface.createFromAsset(assets,"roboto_medium.ttf") //선택 시 굵게 폰트 변경하기 위해 에셋에서 불러옴
            }
        })

        //다음 버튼 활성화를 위해 라이브 데이터 관찰
        moreInformationActivity2ViewModel.isCorrect.observe(this, Observer {
            binding.btnNext.isEnabled=it
        })

        //취업상태-전체 버튼 클릭 리스너
        binding.btnGetJob1.setOnClickListener {
            moreInformationActivity2ViewModel.changeBtnAll()

            //'다음'버튼 활성화를 확인하는 메소드 출력
            moreInformationActivity2ViewModel.checkIsCorrect()
        }
        //취업상태-미취업자 버튼 클릭 리스너
        binding.btnGetJob2.setOnClickListener {
            moreInformationActivity2ViewModel.changeBtnUnEmployment()

            //'다음'버튼 활성화를 확인하는 메소드 출력
            moreInformationActivity2ViewModel.checkIsCorrect()
        }
        //취업상태-재직자 버튼 클릭 리스너
        binding.btnGetJob3.setOnClickListener {
            moreInformationActivity2ViewModel.changeBtnEmployment()

            //'다음'버튼 활성화를 확인하는 메소드 출력
            moreInformationActivity2ViewModel.checkIsCorrect()
        }
        //취업상태-프리랜서 버튼 클릭 리스너
        binding.btnGetJob4.setOnClickListener {
            moreInformationActivity2ViewModel.changeBtnFreelancer()

            //'다음'버튼 활성화를 확인하는 메소드 출력
            moreInformationActivity2ViewModel.checkIsCorrect()
        }
        //취업상태-일용근로자 버튼 클릭 리스너
        binding.btnGetJob5.setOnClickListener {
            moreInformationActivity2ViewModel.changeBtnDailyWorker()

            //'다음'버튼 활성화를 확인하는 메소드 출력
            moreInformationActivity2ViewModel.checkIsCorrect()
        }
        //취업상태-단기근로자 버튼 클릭 리스너
        binding.btnGetJob6.setOnClickListener {
            moreInformationActivity2ViewModel.changeBtnShortWorker()

            //'다음'버튼 활성화를 확인하는 메소드 출력
            moreInformationActivity2ViewModel.checkIsCorrect()
        }
        //취업상태-예비창업자 버튼 클릭 리스너
        binding.btnGetJob7.setOnClickListener {
            moreInformationActivity2ViewModel.changeBtnPrepEntrepreneur()

            //'다음'버튼 활성화를 확인하는 메소드 출력
            moreInformationActivity2ViewModel.checkIsCorrect()
        }
        //취업상태-자영업자 버튼 클릭 리스너
        binding.btnGetJob8.setOnClickListener {
            moreInformationActivity2ViewModel.changeBtnSelfOwnership()

            //'다음'버튼 활성화를 확인하는 메소드 출력
            moreInformationActivity2ViewModel.checkIsCorrect()
        }
        //취업상태-영농종사자 버튼 클릭 리스너
        binding.btnGetJob9.setOnClickListener {
            moreInformationActivity2ViewModel.changeBtnFarming()

            //'다음'버튼 활성화를 확인하는 메소드 출력
            moreInformationActivity2ViewModel.checkIsCorrect()
        }



        //'다음'버튼 클릭 리스너
        binding.btnNext.setOnClickListener {
            //moreActivity3로 이동하면서 user_data 넘겨줌
            val intent= Intent(this,MoreInformationActivity3::class.java)
            user_data.incomeLevel=moreInformationActivity2ViewModel.getIncomeLevel(binding.edtIncome.text.toString())
            user_data.incomeAvg=moreInformationActivity2ViewModel.getIncomeAvg(binding.edtIncome.text.toString())
            user_data.employmentState=moreInformationActivity2ViewModel.getEmployState()
            intent.putExtra("user_data",user_data)
            startActivity(intent)
        }

        //뒤로가기 버튼 클릭 리스너
        binding.btnBack.setOnClickListener{
            this.onBackPressed()
        }



        //edtIncome의 포커싱 리스너
        binding.edtIncome.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){//포커싱이 생길 때....

            }else{ //포커싱이 사라질 때...
                //뷰모델의 라이브데이터로 edtIncome의 데이터 저장
                moreInformationActivity2ViewModel.currentEdtIncome.value=binding.edtIncome.text.toString()

                println(binding.edtIncome.text.toString().isNotEmpty())

                //'다음'버튼 활성화를 확인하는 메소드 출력
                moreInformationActivity2ViewModel.checkIsCorrect()
            }
        }


        //edtIncome 완료 버튼 클릭 이벤트. 완료 버튼을 누르면 포커싱을 없애주기 위한 코드
        binding.edtIncome.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if(actionId == EditorInfo.IME_ACTION_DONE){ //액션 iD가 완료이면
                binding.edtIncome.clearFocus() //edtPwd 포커싱 없애기

                //키보드 밑으로 내리는 코드
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.edtIncome.windowToken, 0);

                handled=true
            }
            handled
        }

        //edtIncome 3자리마다 콤마 자동으로 찍어주기 위한 코드
        binding.edtIncome.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                //월 소득이 입력되어져 있는지 아닌지 판단하기 위한 라이브데이터 변경
                moreInformationActivity2ViewModel.isSelectEdtIncome.value =
                    binding.edtIncome.text.toString().isNotEmpty()
                //'다음'버튼 활성화를 확인하는 메소드 출력
                moreInformationActivity2ViewModel.checkIsCorrect()

                if (!TextUtils.isEmpty(s.toString())&&!s.toString().equals(result)){
                    result=decimalFormat.format((s.toString().replace(",","")).toDouble())
                    binding.edtIncome.setText(result)
                    binding.edtIncome.setSelection(result.length)
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
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