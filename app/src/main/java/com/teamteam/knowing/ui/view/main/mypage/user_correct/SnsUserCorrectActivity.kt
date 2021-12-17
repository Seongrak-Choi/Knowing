package com.teamteam.knowing.ui.view.main.mypage.user_correct

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_BIRTH_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_GENDER_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_NAME_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_PROVIDER_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_UID
import com.teamteam.knowing.config.ApplicationClass.Companion.sp
import com.teamteam.knowing.data.model.network.request.UserCorrectRequest
import com.teamteam.knowing.databinding.ActivitySnsUserCorrectBinding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.view.login.JoinUpSNSActivity
import com.teamteam.knowing.ui.viewmodel.SnsUserCorrectActivityViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern

class SnsUserCorrectActivity :
    BaseActivity<ActivitySnsUserCorrectBinding>(ActivitySnsUserCorrectBinding::inflate) {
    private lateinit var snsUserCorrectActivityViewModel: SnsUserCorrectActivityViewModel

    //다이얼로그
    private lateinit var dialog_withdrawal : Dialog

    private var birthValue: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //뷰모델 장착
        snsUserCorrectActivityViewModel = ViewModelProvider(this).get(SnsUserCorrectActivityViewModel::class.java)


        //datepicker로 선택한 날짜가 edittext에 출력되도록 observer 패턴 사용
        snsUserCorrectActivityViewModel.currentEdtTextBirth.observe(this, androidx.lifecycle.Observer {
            binding.edtBirth.setText(it.toString())
        })

        //성별중 남성 버튼 클릭을 라디오 버튼처럼 움직이기 위해 livedate로 false,true 값을 옵저빙한다.
        snsUserCorrectActivityViewModel.currentBtnMen.observe(this, androidx.lifecycle.Observer {
            if (it) {
                binding.btnMen.setBackgroundResource(R.drawable.sign_up_gender_btn_checked_bg)
                binding.btnMen.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.gender_btn_text_check
                    )
                )
            } else {
                binding.btnMen.setBackgroundResource(R.drawable.sign_up_gender_btn_unchecked_bg)
                binding.btnMen.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.gender_btn_text_uncheck
                    )
                )
            }
        })


        //성별중 여성 버튼 클릭을 라디오 버튼처럼 움직이기 위해 livedate로 false,true 값을 옵저빙한다.
        snsUserCorrectActivityViewModel.currentBtnWomen.observe(this, androidx.lifecycle.Observer {
            if (it) {
                binding.btnWomen.setBackgroundResource(R.drawable.sign_up_gender_btn_checked_bg)
                binding.btnWomen.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.gender_btn_text_check
                    )
                )
            } else {
                binding.btnWomen.setBackgroundResource(R.drawable.sign_up_gender_btn_unchecked_bg)
                binding.btnWomen.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.gender_btn_text_uncheck
                    )
                )
            }
        })


        //회원가입 버튼 활성화를 위해 라이브 데이터 관찰
        snsUserCorrectActivityViewModel.allIsCorrect.observe(this, Observer {
            if (it) {
                binding.btnFinish.isEnabled = it
            }else{
                binding.btnFinish.isEnabled = it
            }
        })


        //회원 수정 api가 성공하면 변경될 라이브데이터 관찰
        snsUserCorrectActivityViewModel.isSuccessApi.observe(this, Observer {
            if (it){
                val editor = sp.edit()
                editor.putString(USER_NAME_KEY,binding.edtName.text.toString())
                editor.putString(USER_GENDER_KEY,snsUserCorrectActivityViewModel.getGenderValue())
                editor.putString(USER_BIRTH_KEY,birthValue.toString())
                editor.apply()

                Toast.makeText(this,"회원정보가 변경되었습니다.",Toast.LENGTH_SHORT).show()
                this.finish()
            }
        })


        //회원 탈퇴 api가 성공하면 변경될 라이브데이터 관찰
        snsUserCorrectActivityViewModel.isSuccessWithdrawal.observe(this, Observer {
            if (it){
                val editor = sp.edit()
                editor.putString(ApplicationClass.UID_KEY,"")
                editor.putString(USER_NAME_KEY,"")
                editor.putString(ApplicationClass.USER_EMAIL_KEY,"")
                editor.putString(ApplicationClass.USER_PROVIDER_KEY,"")
                editor.putString(ApplicationClass.USER_PHONE_NUM_KEY,"")
                editor.putString(USER_GENDER_KEY,"")
                editor.putString(USER_BIRTH_KEY,"")
                editor.apply()

                //JoinUpSns 액티비티로 이동
                val intent = Intent(this, JoinUpSNSActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent)

                finish()
            }
        })


        //edtName의 키보드를 완료로 바꿔주기 위한 코드
        binding.edtName.imeOptions = EditorInfo.IME_ACTION_DONE


        //edtName에 포커싱이 될 때 힌트를 없애기 위한 포커스리스너
        binding.edtName.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.edtName.hint = ""
            } else {
                binding.imgNameCancel.visibility = View.INVISIBLE //포커싱이 끝나면 리셋이 가능한 캔슬 버튼 숨기기
                binding.edtName.hint = "이름 입력"

                //edtName의 데이터가 비어있지 않다면 라이브 데이터 true로 변경
                snsUserCorrectActivityViewModel.nameIsCorrect.value =
                    binding.edtName.text.toString().isNotEmpty()

                //모든 조건이 만족하는지 검사
                snsUserCorrectActivityViewModel.checkAllIsCorrect()
            }
        }

        //edtName 텍스트 체인지 리스너
        binding.edtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.imgNameCancel.visibility =
                    View.VISIBLE //edtText의 데이터가 들어오면 리셋 가능한 캔슬 버튼 활성화


                //edtName의 데이터가 비어있지 않다면 라이브 데이터 true로 변경
                snsUserCorrectActivityViewModel.nameIsCorrect.value =
                    binding.edtName.text.toString().isNotEmpty()

                //모든 조건이 만족하는지 검사
                snsUserCorrectActivityViewModel.checkAllIsCorrect()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        //edtName을 리셋할 수 있는 캔슬 버튼 클릭 리스너
        binding.imgNameCancel.setOnClickListener {
            binding.edtName.text = null // 캔슬 버튼을 누르면 해당 edttext의 데이터 리셋
        }

        //edtName키보드 완료 버튼 클릭 이벤트. 완료 버튼을 누르면 포커싱을 없애주기 위한 코드
        binding.edtName.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) { //액션 iD가 완료이면
                binding.edtName.clearFocus() //edtPwd 포커싱 없애기

                //키보드 밑으로 내리는 코드
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.edtName.windowToken, 0);

                handled = true
            }
            handled
        }



        //무엇으로 로그인 했는지 확인하여 로고 바꿔주기 위한 코드
        when (sp.getString(USER_PROVIDER_KEY,"").toString()){
            "naver"->{
                binding.imgEmail.setImageResource(R.drawable.logo_naver_small)
                binding.txEmail.text = "네이버로 로그인"
            }
            "kakao"->{
                binding.imgEmail.setImageResource(R.drawable.logo_kakao_small)
                binding.txEmail.text = "카카오로 로그인"
            }
            "google"->{
                binding.imgEmail.setImageResource(R.drawable.logo_google_small)
                binding.txEmail.text = "구글로 로그인"
            }
            "facebook"->{
                binding.imgEmail.setImageResource(R.drawable.logo_facebook_small)
                binding.txEmail.text = "페이스북으로 로그인"
            }
        }


        //sp에서 유저의 데이터를 가져와서 라이브데이터에 저장해줌.
        binding.edtName.setText(sp.getString(USER_NAME_KEY,"").toString())
        if(sp.getString(USER_GENDER_KEY,"").toString()=="남성"){
            snsUserCorrectActivityViewModel.currentBtnMen.value=true
        }else
            snsUserCorrectActivityViewModel.currentBtnWomen.value=true

        val birth = sp.getString(USER_BIRTH_KEY,"").toString()

        snsUserCorrectActivityViewModel.currentEdtTextBirth.value=(birth.substring(0,4)+" / "+birth.substring(4,6)+" / "+birth.substring(6,8))

        //api호출 할 때 필요한 int형 birth
        birthValue = birth.toInt()
        snsUserCorrectActivityViewModel.nameIsCorrect.value =true

        snsUserCorrectActivityViewModel.checkAllIsCorrect()





        //생년월일 edittext 클릭하면 그 위에 투명하게 덮혀있는 btnBirth 클릭 리스너 설정
        binding.btnBirth.setOnClickListener {
            showDialog()
        }

        //성별의 남성을 클릭 한 경우
        binding.btnMen.setOnClickListener {
            snsUserCorrectActivityViewModel.changeValueMen()
        }

        //성별의 여성을 클릭 한 경우
        binding.btnWomen.setOnClickListener {
            snsUserCorrectActivityViewModel.changeValueWomen()
        }


        //뒤로가기 아이콘 클릭 리스너
        binding.btnBack.setOnClickListener {
            this.onBackPressed()
        }


        //정말 탈퇴할 건지 묻는 다이얼로그 객체 생성 및 설정
        dialog_withdrawal = Dialog(this)
        dialog_withdrawal.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_withdrawal.setContentView(R.layout.dialog_alert_withdrawal)

        //회원탈퇴하기
        binding.btnWithdrawal.setOnClickListener {
            dialog_withdrawal.show()

            //다이얼로그 내부 확인 누를 때 발동 리스너
            dialog_withdrawal.findViewById<Button>(R.id.dialog_sys_setting_btn_positive).setOnClickListener {
                //회원탈퇴 api 호출
                snsUserCorrectActivityViewModel.tryDeleteWithdrawal(USER_UID)
            }
            //다이얼로그 내부 취소 누를 때 발동 리스너
            dialog_withdrawal.findViewById<Button>(R.id.dialog_sys_setting_btn_negative).setOnClickListener {
                dialog_withdrawal.dismiss()
            }
        }


        //수정하기 버튼 클릭 리스너
        binding.btnFinish.setOnClickListener {
            val user_data = UserCorrectRequest(
                name = binding.edtName.text.toString(),
                gender = snsUserCorrectActivityViewModel.getGenderValue(),
                birth = birthValue
            )
            snsUserCorrectActivityViewModel.tryPatchUserCorrect(USER_UID,user_data)
        }
    }


    /*
    datepicker 출력
     */
    fun showDialog() {
        val dialog = AlertDialog.Builder(this).create()
        val edialog: LayoutInflater = LayoutInflater.from(this)
        val mView: View = edialog.inflate(R.layout.dialog_datepicker, null)

        val year: NumberPicker = mView.findViewById(R.id.yearpicker_datepicker)
        val month: NumberPicker = mView.findViewById(R.id.monthpicker_datepicker)
        val day: NumberPicker = mView.findViewById(R.id.daypicker_datepicker)
        val cancel: Button = mView.findViewById(R.id.btn_cancel)
        val save: Button = mView.findViewById(R.id.btn_ok)

        //  순환 안되게 막기코드인데 나는 순환 되도록 설정함
//        year.wrapSelectorWheel = false
//        month.wrapSelectorWheel = false
//        day.wrapSelectorWheel = false

//        //  editText 설정 해제 왜 필요한지 모름....
//        year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
//        month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        //  최소값 설정
        year.minValue = 1900
        month.minValue = 1
        day.minValue = 1

        //  최대값 설정
        year.maxValue = 2100
        month.maxValue = 12
        day.maxValue = 30

        //기본값 설정
        year.value = 2000
        month.value = 6
        day.value = 15


        //취소 버튼 눌렀을 때 리스너
        cancel.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()
        }


        //확인 눌렀을 때 리스너
        save.setOnClickListener {
            var strYear = year.value.toString() //numberpicker에서 선택한 데이터 중 year 부분을 저장
            var strMonth =
                "" //numberpicker에서 선택한 데이터 중 month 부분을 저장. 어차피 밑에서 1의 자리인 경우 앞에 0을 붙혀주는 작업이 있기 때문에 공백을 초기화만 시켜둠
            var strDay =
                "" //numberpicker에서 선택한 데이터 중 day 부분을 저장. 어차피 밑에서 1의 자리인 경우 앞에 0을 붙혀주는 작업이 있기 때문에 공백을 초기화만 시켜둠

            //선택한 데이터가 한자리 수 이면 앞에 0을 붙혀주기 위한 코드
            strMonth = if (month.value < 10) {
                "0${month.value}"
            } else {
                month.value.toString()
            }

            //선택한 데이터가 한자리 수 이면 앞에 0을 붙혀주기 위한 코드
            strDay = if (day.value < 10) {
                "0${day.value}"
            } else {
                day.value.toString()
            }

            //viewmodel의 livedat의 값을 변경해서 화면에 출력하는 용도
            snsUserCorrectActivityViewModel.currentEdtTextBirth.value =
                "$strYear / $strMonth / $strDay"

            //intent넘어갈 때 보내주기 위해 int형으로 담아두는 용도
            birthValue = (strYear + strMonth + strDay).toInt()
            dialog.dismiss() //datepicker dialog 종료

            //모든 조건이 만족하는지 검사 후 라이브데이터 변경
            snsUserCorrectActivityViewModel.checkAllIsCorrect()
        }

        dialog.setView(mView) //dialog의 view를 설정
        dialog.create() //dialog생성
        dialog.show() //dialog 출력
    }


    //입력 받은 데이터가 이메일 형식인지 확인하는 함수
    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

}
