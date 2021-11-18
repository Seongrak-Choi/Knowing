package com.example.knowing.ui.view.sign_in.search_pwd

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.knowing.databinding.ActivitySearchPwdBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.SearchPwdActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

class SearchPwdActivity :BaseActivity<ActivitySearchPwdBinding>(ActivitySearchPwdBinding::inflate) {

    private lateinit var searchPwdActivityViewModel: SearchPwdActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //뷰모델 장착
        searchPwdActivityViewModel=ViewModelProvider(this).get(SearchPwdActivityViewModel::class.java)


        //재설정 메일 보내기 버튼 활성화를 위해 라이브 데이터 관찰
        searchPwdActivityViewModel.isAllCorrect.observe(this, Observer {
            binding.btnSend.isEnabled=it
        })



        //이름 텍스트 체인지 리스너
        binding.edtName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //이름의 데이터가 비어있지 않으면 isCorrectEdtName true로 변환 아니면 false
                searchPwdActivityViewModel.isCorrectEdtName.value = binding.edtName.length()>0

                //모든 조건이 만족하는지 확인하는 메소드 실행
                searchPwdActivityViewModel.checkIsAllCorrect()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })



        //edtEmail에 입력시 발동하는 이벤트 리스너
        binding.edtEmail.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.imgEmailError.visibility=View.INVISIBLE //edt에 입력이 시작되면 에러 이미지 안보이게
                binding.txEmailError.visibility=View.INVISIBLE//edt에 입력이 시작되면 에러 메세지 안보이게
                binding.imgEmailCancel.visibility=View.VISIBLE //edt에 입력이 시작되면 캔슬 버튼 보이도록

                //이메일 양식과 일치한지 확인
                searchPwdActivityViewModel.isCorrectEdtEmail.value = isEmailValid(binding.edtEmail.text.toString())//이메일 형식이 맞으면 확인을 위한 라이브 데이터 true

                //모든 조건이 만족하는지 확인하는 메소드 실행
                searchPwdActivityViewModel.checkIsAllCorrect()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        //edtEmail에 포커싱이 포커스리스너
        binding.edtEmail.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                binding.edtEmail.hint = "" //포커스가 되어지면 hint가 사라지도록 설정
            }else{
                binding.edtEmail.hint="이메일 주소 입력" //포커스가 없어지면 hint가 생기도록 설정
                binding.imgEmailCancel.visibility=View.INVISIBLE //캔슬 버튼 안보이도록

                if(isEmailValid(binding.edtEmail.text.toString())){//이메일 형식이 맞는 경우
                    searchPwdActivityViewModel.isCorrectEdtEmail.value=true//이메일 형식이 맞으면 확인을 위한 라이브 데이터 true

                    binding.imgEmailError.visibility=View.INVISIBLE //이메일 형식 맞으면 오류 이미지 숨기기
                    binding.txEmailError.visibility=View.INVISIBLE //이메일 형식 맞으면 오류 텍스트 숨기기

                    //모든 조건이 만족하는지 확인하는 메소드 실행
                    searchPwdActivityViewModel.checkIsAllCorrect()
                }else{//이메일 형식이 아닌 경우
                    searchPwdActivityViewModel.isCorrectEdtEmail.value=false//이메일 형식이 틀리면 확인을 위한 라이브 데이터 false
                    binding.imgEmailError.visibility=View.VISIBLE //이메일 형식이 아니라면 오류 이미지 출력
                    binding.txEmailError.visibility=View.VISIBLE //이메일 형식이 아니라면 오류 메세지 출력

                    //모든 조건이 만족하는지 확인하는 메소드 실행
                    searchPwdActivityViewModel.checkIsAllCorrect()
                }
                binding.imgEmailCancel.visibility=View.INVISIBLE //edt의 포커싱이 없어지면 캔슬 버튼 숨김
            }
        }

        //edtEmail 리셋 시키기 위해 캔슬 이미지 클릭 리스너
        binding.imgEmailCancel.setOnClickListener {
            binding.edtEmail.text=null  // 캔슬 버튼을 누르면 해당 edttext의 데이터 리셋
        }


        //edtEmail키보드 완료 버튼 클릭 이벤트. 완료 버튼을 누르면 포커싱을 없애주기 위한 코드
        binding.edtEmail.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if(actionId == EditorInfo.IME_ACTION_DONE){ //액션 iD가 완료이면
                binding.edtEmail.clearFocus() //edtPwd 포커싱 없애기

                //키보드 밑으로 내리는 코드
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.edtEmail.windowToken, 0)

                handled=true
            }
            handled
        }


        // 재설정 메일 보내기 클릭 리스너
        binding.btnSend.setOnClickListener {
            //edtEmail의 데이터를 저장
            var emailAddress = binding.edtEmail.text.toString()
            //비밀번호 재설정 메일 보내주는 메소드 실행
            searchPwdActivityViewModel.sendEmail(emailAddress)

        }



        //뒤로가기 아이콘 클릭 리스너
        binding.btnBack.setOnClickListener {
            this.onBackPressed()
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