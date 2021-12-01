package com.teamteam.knowing.ui.view.sign_in

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamteam.knowing.databinding.ActivitySignInBinding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.view.sign_in.search_email.SearchEmailActivity
import com.teamteam.knowing.ui.view.sign_in.search_pwd.SearchPwdActivity
import com.teamteam.knowing.ui.viewmodel.SignInActivityViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignInActivity : BaseActivity<ActivitySignInBinding>(ActivitySignInBinding::inflate) {

    private lateinit var signInActivityViewModel: SignInActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModel 장착
        signInActivityViewModel= ViewModelProvider(this).get(SignInActivityViewModel::class.java)

        //로그인하기 버튼의 활성화 여부를 viewModel의 allIsCorrect를 관찰하면서 변경하기 위해 옵저버 패턴 사용
        signInActivityViewModel.allIsCorrect.observe(this, Observer {
            binding.btnLogin.isEnabled=it
        })

        //edtEmail에 포커싱이 될 때 힌트를 없애기 위한 포커스리스너
        binding.edtEmail.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                binding.edtEmail.hint = "" //포커스가 되어지면 hint가 사라지도록 설정
            }else{
                binding.edtEmail.hint="이메일 주소 입력" //포커스가 없어지면 hint가 생기도록 설정
                if(isEmailValid(binding.edtEmail.text.toString())){
                    binding.imgEmailError.visibility= View.INVISIBLE //이메일 형식 맞으면 오류 이미지 숨기기
                    binding.txEmailError.visibility= View.INVISIBLE //이메일 형식 맞으면 오류 텍스트 숨기기
                    signInActivityViewModel.emailIsCorrect.value=true //이메일 형식이 맞지 않으면 뷰모델의 라이브데이터 false로 변경
                }else{
                    binding.imgEmailError.visibility= View.VISIBLE //이메일 형식이 아니라면 오류 이미지 출력
                    binding.txEmailError.visibility= View.VISIBLE //이메일 형식이 아니라면 오류 메세지 출력
                    signInActivityViewModel.emailIsCorrect.value=false //이메일 형식이 맞으면 뷰모델의 라이브데이터 true 로 변경
                }
                binding.imgEmailCancel.visibility= View.INVISIBLE //edt의 포커싱이 없어지면 캔슬 버튼 숨김

                signInActivityViewModel.isAllCorrect() //포커싱이 없어질 때 '로그인하기' 버튼 활성화 여부를 확인하기 위한 메소드 호출
            }
        }


        //edtEmail에 입력시 발동하는 이벤트 리스너
        binding.edtEmail.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.imgEmailError.visibility=View.INVISIBLE //edt에 입력이 시작되면 에러 이미지 안보이게
                binding.txEmailError.visibility=View.INVISIBLE//edt에 입력이 시작되면 에러 메세지 안보이게
                binding.imgEmailCancel.visibility=View.VISIBLE //edt에 입력이 시작되면 캔슬 버튼 보이도록
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        //edtEmail 리셋 시키기 위해 캔슬 이미지 클릭 리스너
        binding.imgEmailCancel.setOnClickListener {
            binding.edtEmail.text=null  // 캔슬 버튼을 누르면 해당 edttext의 데이터 리셋
        }



        //edtPwd에 포커싱이 될 때 힌트를 없애기 위한 포커스리스너
        binding.edtPwd.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                binding.edtPwd.hint = ""
            }else {
                binding.edtPwd.hint = "영문자와 숫자 포합 8자 이상 입력"
                binding.imgPwdCancel.visibility = View.INVISIBLE //포커싱이 없어지면 cancel 버튼 비활성화

                var passwrod_pattern =
                    "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}\$" //숫자 1개라도 포함, 영어 1개라도 포함, 특수문자 1개라도 포함해서 8~20사이

                if (!Pattern.matches(passwrod_pattern,binding.edtPwd.text.toString())){  //사용자 입력값과 패턴의 일치를 묻기
                    binding.imgPwdError.visibility=View.VISIBLE //패턴이 일치하지 않으면 에러 이미지 보이도록
                    binding.txPwdError.visibility=View.VISIBLE // 패턴이 일치하지 않으면 에러 텍스트 보이도록
                    signInActivityViewModel.pwdIsCorrect.value=false //이메일 형식이 맞지 않으면 뷰모델의 라이브데이터 false로 변경
                }else{ //패턴이 일치하면
                    binding.imgPwdError.visibility=View.INVISIBLE //패턴이 일치하면 에러 이미지 보이지 않도록
                    binding.txPwdError.visibility=View.INVISIBLE // 패턴이 일치하면 에러 텍스트 보이지 않도록
                    signInActivityViewModel.pwdIsCorrect.value=true //이메일 형식이 맞지 않으면 뷰모델의 라이브데이터 true로 변경
                }
                signInActivityViewModel.isAllCorrect() //포커싱이 없어질 때 '로그인하기' 버튼 활성화 여부를 확인하기 위한 메소드 호출
            }
        }


        //edtPwd 텍스트 체인지 리스너
        binding.edtPwd.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.imgPwdCancel.visibility=View.VISIBLE //텍스트 체인지 리스너가 발동하면 캔슬버튼 보이도록
                binding.imgPwdError.visibility=View.INVISIBLE //edt에 데이터가 들어오면 에러 이미지 안보이도록
                binding.txPwdError.visibility=View.INVISIBLE // edt에 데이터가 들어오면 에러 텍스트 안보이도록
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        //edtPwd 리셋을 위해 캔슬 버튼 클릭 리스너
        binding.imgPwdCancel.setOnClickListener {
            binding.edtPwd.text=null
        }



        //키보드 완료 버튼 클릭 이벤트
        binding.edtPwd.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if(actionId == EditorInfo.IME_ACTION_DONE){ //액션 iD가 완료이면
                binding.edtPwd.clearFocus() //edtPwd 포커싱 없애기

                //키보드 밑으로 내리는 코드
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.edtPwd.windowToken, 0);

                handled=true
            }
            handled
        }

        //이메일 찾기 버튼 클릭 리스너
        binding.btnSearchEmail.setOnClickListener {
            //이메일 찾는 화면으로 이동
            val intent = Intent(this,SearchEmailActivity::class.java)
            startActivity(intent)
        }

        //비밀번호 재설정버튼 클릭 리스너
        binding.btnSearchPwd.setOnClickListener {
            //비밀번호 재설정버튼 화면으로 이동
            val intent = Intent(this,SearchPwdActivity::class.java)
            startActivity(intent)
        }


        //로그인하기 버튼 클릭 리스너
        binding.btnLogin.setOnClickListener {
            signInActivityViewModel.tryLoginWithFirebase(binding.edtEmail.text.toString(),binding.edtPwd.text.toString())
        }
    }


    //입력 받은 데이터가 이메일 형식인지 확인하는 함수
    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }
}