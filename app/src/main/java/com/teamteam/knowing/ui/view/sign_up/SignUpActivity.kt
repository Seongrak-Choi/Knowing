package com.teamteam.knowing.ui.view.sign_up

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamteam.knowing.R
import com.teamteam.knowing.data.model.domain.SignUpUser
import com.teamteam.knowing.databinding.ActivitySignUpBinding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.view.more_information.MoreInformationActivity1
import com.teamteam.knowing.ui.viewmodel.SignUpActivityViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignUpActivity : BaseActivity<ActivitySignUpBinding>(ActivitySignUpBinding::inflate) {
    private lateinit var signUpActivityViewModel: SignUpActivityViewModel
    private var birthValue: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //뷰모델 장착
        signUpActivityViewModel = ViewModelProvider(this).get(SignUpActivityViewModel::class.java)


        //datepicker로 선택한 날짜가 edittext에 출력되도록 observer 패턴 사용
        signUpActivityViewModel.currentEdtTextBirth.observe(this, androidx.lifecycle.Observer {
            binding.edtBirth.setText(it.toString())
        })

        //성별중 남성 버튼 클릭을 라디오 버튼처럼 움직이기 위해 livedate로 false,true 값을 옵저빙한다.
        signUpActivityViewModel.currentBtnMen.observe(this, androidx.lifecycle.Observer {
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
        signUpActivityViewModel.currentBtnWomen.observe(this, androidx.lifecycle.Observer {
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
        signUpActivityViewModel.allIsCorrect.observe(this, Observer {
            binding.btnFinish.isEnabled = it
        })


        //이메일이 중복인지 체크해서 메세지를 출력하고 로그인 버튼을 false로 하기 위해 라이브 데이터 관찰
        signUpActivityViewModel.isCorrectEmailDuplicate.observe(this, Observer {
            if (it) {
                binding.txEmailDuplicateError.visibility=View.INVISIBLE
                binding.imgEmailError.visibility = View.INVISIBLE //입력한 이메일이 중복이 아니면 오류 이미지 숨기기
                signUpActivityViewModel.emailIsCorrect.value = true //입력한 이메일이 중복이 아니면 라이브데이터 변경
                signUpActivityViewModel.checkAllIsCorrect() //버튼 활성화를 위해 모든 조건이 만족하는 지 확인 해 달라는 메소드 호출
            }else{
                binding.imgEmailError.visibility = View.VISIBLE //입력한 이메일이 중복이면 오류 이미지 표시
                binding.txEmailDuplicateError.visibility=View.VISIBLE
                signUpActivityViewModel.emailIsCorrect.value = false //입력한 이메일이 중복이면 라이브데이터 변경
                signUpActivityViewModel.checkAllIsCorrect()//버튼 활성화를 위해 모든 조건이 만족하는 지 확인 해 달라는 메소드 호출
            }
        })


        //생년월일 edittext 클릭하면 그 위에 투명하게 덮혀있는 btnBirth 클릭 리스너 설정
        binding.btnBirth.setOnClickListener {
            showDialog()
        }

        //성별의 남성을 클릭 한 경우
        binding.btnMen.setOnClickListener {
            signUpActivityViewModel.changeValueMen()
        }

        //성별의 여성을 클릭 한 경우
        binding.btnWomen.setOnClickListener {
            signUpActivityViewModel.changeValueWomen()
        }

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
                signUpActivityViewModel.nameIsCorrect.value =
                    binding.edtName.text.toString().isNotEmpty()

                //모든 조건이 만족하는지 검사
                signUpActivityViewModel.checkAllIsCorrect()
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
                signUpActivityViewModel.nameIsCorrect.value =
                    binding.edtName.text.toString().isNotEmpty()

                //모든 조건이 만족하는지 검사
                signUpActivityViewModel.checkAllIsCorrect()
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


        //edtEmail의 키보드를 완료로 바꿔주기 위한 코드
        binding.edtEmail.imeOptions = EditorInfo.IME_ACTION_DONE

        //edtEmail에 포커싱이 될 때 힌트를 없애기 위한 포커스리스너
        binding.edtEmail.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.edtEmail.hint = "" //포커스가 되어지면 hint가 사라지도록 설정
            } else {
                binding.edtEmail.hint = "이메일 주소 입력" //포커스가 없어지면 hint가 생기도록 설정
                if (isEmailValid(binding.edtEmail.text.toString())) {
                    //이메일 형식이 맞다면 중복인지 아닌지 확인하는 api 호출
                    binding.txEmailError.visibility = View.INVISIBLE //이메일 형식 맞으면 오류 텍스트 숨기기
                    signUpActivityViewModel.tryGetEmailIsDuplicate(binding.edtEmail.text.toString())//이메일 중복 api 호출
                } else {
                    binding.imgEmailError.visibility = View.VISIBLE //이메일 형식이 아니라면 오류 이미지 출력
                    binding.txEmailError.visibility = View.VISIBLE //이메일 형식이 아니라면 오류 메세지 출력
                    signUpActivityViewModel.emailIsCorrect.value = false //형식이 일치하면 라이브데이터 변경
                }
                binding.imgEmailCancel.visibility = View.INVISIBLE //edt의 포커싱이 없어지면 캔슬 버튼 숨김

                //모든 조건이 만족하는지 검사 후 라이브데이터 변경
                signUpActivityViewModel.checkAllIsCorrect()
            }
        }

        //edtEmail에 입력시 발동하는 이벤트 리스너
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.imgEmailError.visibility = View.INVISIBLE //edt에 입력이 시작되면 에러 이미지 안보이게
                binding.txEmailError.visibility = View.INVISIBLE//edt에 입력이 시작되면 에러 메세지 안보이게
                binding.imgEmailCancel.visibility = View.VISIBLE //edt에 입력이 시작되면 캔슬 버튼 보이도록

                //입력과 동시에 email형식이 맞는지 확인하는 라인
                signUpActivityViewModel.emailIsCorrect.value =
                    isEmailValid(binding.edtEmail.text.toString())

                //모든 조건이 만족하는지 검사 후 라이브데이터 변경
                //signUpActivityViewModel.checkAllIsCorrect()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        //edtEmail 리셋 시키기 위해 캔슬 이미지 클릭 리스너
        binding.imgEmailCancel.setOnClickListener {
            binding.edtEmail.text = null  // 캔슬 버튼을 누르면 해당 edttext의 데이터 리셋
        }


        //edtEmail키보드 완료 버튼 클릭 이벤트. 완료 버튼을 누르면 포커싱을 없애주기 위한 코드
        binding.edtEmail.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) { //액션 iD가 완료이면
                binding.edtEmail.clearFocus() //edtPwd 포커싱 없애기

                //키보드 밑으로 내리는 코드
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.edtEmail.windowToken, 0)

                handled = true
            }
            handled
        }

        //edtPwd의 키보드를 완료로 바꿔주기 위한 코드
        binding.edtPwd.imeOptions = EditorInfo.IME_ACTION_DONE

        //edtPwd에 포커싱이 될 때 힌트를 없애기 위한 포커스리스너
        binding.edtPwd.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.edtPwd.hint = ""
            } else {
                binding.edtPwd.hint = "영문자와 숫자 포합 8자 이상 입력"
                binding.imgPwdCancel.visibility = View.INVISIBLE //포커싱이 없어지면 cancel 버튼 비활성화

                var passwrod_pattern =
                    "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}\$" //숫자 1개라도 포함, 영어 1개라도 포함, 특수문자 1개라도 포함해서 8~20사이

                if (!Pattern.matches(passwrod_pattern, binding.edtPwd.text.toString())) {  //사용자 입력값과 패턴의 일치를 묻기
                    binding.imgPwdError.visibility = View.VISIBLE //패턴이 일치하지 않으면 에러 이미지 보이도록
                    binding.txPwdError.visibility = View.VISIBLE // 패턴이 일치하지 않으면 에러 텍스트 보이도록
                    signUpActivityViewModel.pwdIsCorrect.value = false //라이브 데이터 변경
                } else { //패턴이 일치하면
                    binding.imgPwdError.visibility = View.INVISIBLE //패턴이 일치하면 에러 이미지 안보이도록
                    binding.txPwdError.visibility = View.INVISIBLE // 패턴이 일치하면 에러 텍스트 안보이도록
                    signUpActivityViewModel.pwdIsCorrect.value = true //라이브 데이터 변경
                }

                //모든 조건이 만족하는지 검사 후 라이브데이터 변경
                signUpActivityViewModel.checkAllIsCorrect()
            }
        }

        //edtPwd 텍스트 체인지 리스너
        binding.edtPwd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.imgPwdCancel.visibility = View.VISIBLE //텍스트 체인지 리스너가 발동하면 캔슬버튼 보이도록
                binding.imgPwdError.visibility = View.INVISIBLE //edt에 데이터가 들어오면 에러 이미지 안보이도록
                binding.txPwdError.visibility = View.INVISIBLE // edt에 데이터가 들어오면 에러 텍스트 안보이도록

                var passwrod_pattern =
                    "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}\$" //숫자 1개라도 포함, 영어 1개라도 포함, 특수문자 1개라도 포함해서 8~20사이

                //비밀번호 형식에 맞는지 안맞는지 확인하는 라인.
                signUpActivityViewModel.pwdIsCorrect.value =
                    Pattern.matches(passwrod_pattern, binding.edtPwd.text.toString())

                //비밀번호를 변경할 때 비밀번호확인과의 값을 비교해서 올바르지 않다는 false값을 라이브데이터에 저장하기 위함.
                signUpActivityViewModel.pwdCheckIsCorrect.value =
                    binding.edtPwd.text.toString() == binding.edtPwdCheck.toString()

                //모든 조건이 만족하는지 검사 후 라이브데이터 변경
                signUpActivityViewModel.checkAllIsCorrect()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        //edtPwd 리셋을 위해 캔슬 버튼 클릭 리스너
        binding.imgPwdCancel.setOnClickListener {
            binding.edtPwd.text = null
        }

        //edtPwd키보드 완료 버튼 클릭 이벤트
        binding.edtPwd.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) { //액션 iD가 완료이면
                binding.edtPwd.clearFocus() //edtPwd 포커싱 없애기

                //키보드 밑으로 내리는 코드
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.edtPwd.windowToken, 0);

                handled = true
            }
            handled
        }


        //edtPwdCheck의 키보드를 완료로 바꿔주기 위한 코드
        binding.edtPwdCheck.imeOptions = EditorInfo.IME_ACTION_DONE

        //edtPwdCheck 포커싱 리스너
        binding.edtPwdCheck.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.edtPwdCheck.hint = ""
            } else { //포커싱이 없어질 대
                binding.edtPwd.hint = "다시 한번 입력"
                binding.imgPwdCheckCancel.visibility = View.INVISIBLE //포커싱이 없어지면 cancel 버튼 비활성화

                //포커싱이 없어질 때 비밀번호와 비밀번호 확인이 같은지 확인해서 뷰모델의 pwdCheckIsCorrect 라이브데이터 변경
                if (binding.edtPwd.text.toString() == binding.edtPwdCheck.text.toString()) {
                    signUpActivityViewModel.pwdCheckIsCorrect.value = true
                    binding.txPwdCheckError.visibility = View.INVISIBLE
                } else {
                    signUpActivityViewModel.pwdCheckIsCorrect.value = false
                    binding.txPwdCheckError.visibility = View.VISIBLE
                }
                //모든 조건이 만족하는지 검사 후 라이브데이터 변경
                signUpActivityViewModel.checkAllIsCorrect()
            }
        }

        //edtPwdCheck 텍스트 체인지 리스너
        binding.edtPwdCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.imgPwdCheckCancel.visibility = View.VISIBLE //텍스트 체인지 리스너가 발동하면 캔슬버튼 보이도록
                binding.imgPwdCheckError.visibility = View.INVISIBLE //edt에 데이터가 들어오면 에러 이미지 안보이도록
                binding.txPwdCheckError.visibility = View.INVISIBLE // edt에 데이터가 들어오면 에러 텍스트 안보이도록

                //비밀번호와 비밀번호 확인이 같은지 확인해서 뷰모델의 pwdCheckIsCorrect 라이브데이터 변경
                signUpActivityViewModel.pwdCheckIsCorrect.value =
                    binding.edtPwd.text.toString() == binding.edtPwdCheck.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        //edtPwdCheck 리셋을 위해 캔슬 버튼 클릭 리스너
        binding.imgPwdCancel.setOnClickListener {
            binding.edtPwdCheck.text = null
        }


        //edtPwdCheck키보드 완료 버튼 클릭 이벤트
        binding.edtPwdCheck.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) { //액션 iD가 완료이면
                binding.edtPwdCheck.clearFocus() //edtPwd 포커싱 없애기

                //키보드 밑으로 내리는 코드
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.edtPwdCheck.windowToken, 0);

                handled = true
            }
            handled
        }


        //edt phone 텍스트 체인지 리스너
        binding.edtPhoneNum.addTextChangedListener(object :
            PhoneNumberFormattingTextWatcher() { //핸드폰 양식에 맞게 입력되도록 폰넘버포맷팅텍스트워쳐씀
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                var inputPhone = binding.edtPhoneNum.text.toString()

                //입력이 12자가 되면 라이브 데이터 true 아니면 false
                signUpActivityViewModel.isCorrectEdtPhoneNum.value = inputPhone.length == 13
            }
        })


        //edtPhone 키보드 완료 버튼 클릭 이벤트
        binding.edtPhoneNum.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) { //액션 iD가 완료이면
                binding.edtPhoneNum.clearFocus() //edtPwd 포커싱 없애기

                //키보드 밑으로 내리는 코드
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.edtPhoneNum.windowToken, 0);

                handled = true
            }
            handled
        }


        //회원가입 버튼 클릭 리스너
        binding.btnFinish.setOnClickListener {
            val intent = Intent(this, MoreInformationActivity1::class.java)
            //폰번호는 "-"빼서 보내기 위해 데이터 가공 후 메소드에 전달
            val phNum = binding.edtPhoneNum.text.toString().replace("-", "")
            //화면에서 입력받은 데이터들을 모아서 SignUpUser형으로 객체 생성
            val user_data = SignUpUser(
                binding.edtEmail.text.toString(),
                binding.edtName.text.toString(),
                binding.edtPwd.text.toString(),
                phNum,
                signUpActivityViewModel.getGenderValue(),
                birthValue,
                provider = "default"
            )
            intent.putExtra("user_data", user_data) //userData객체를 intent로 전달
            startActivity(intent)
        }


        //뒤로가기 아이콘 클릭 리스너
        binding.btnBack.setOnClickListener {
            this.onBackPressed()
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
            strMonth = String.format("%02d", month.value)
            strDay = String.format("%02d", day.value)


//            strMonth = if (month.value < 10) {
//                "0${month.value}"
//            } else {
//                month.value.toString()
//            }

            //선택한 데이터가 한자리 수 이면 앞에 0을 붙혀주기 위한 코드
//            strDay = if (day.value < 10) {
//                "0${day.value}"
//            } else {
//                day.value.toString()
//            }

            //viewmodel의 livedat의 값을 변경해서 화면에 출력하는 용도
            signUpActivityViewModel.currentEdtTextBirth.value = "$strYear / $strMonth / $strDay"

            //intent넘어갈 때 보내주기 위해 int형으로 담아두는 용도
            birthValue = (strYear + strMonth + strDay).toInt()
            dialog.dismiss() //datepicker dialog 종료

            //모든 조건이 만족하는지 검사 후 라이브데이터 변경
            signUpActivityViewModel.checkAllIsCorrect()
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