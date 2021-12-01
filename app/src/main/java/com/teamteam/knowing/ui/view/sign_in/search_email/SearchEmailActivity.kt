package com.teamteam.knowing.ui.view.sign_in.search_email

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teamteam.knowing.databinding.ActivitySearchEmailBinding
import com.teamteam.knowing.ui.base.BaseActivity
import com.teamteam.knowing.ui.viewmodel.SearchEmailActivityViewModel

class SearchEmailActivity : BaseActivity<ActivitySearchEmailBinding>(ActivitySearchEmailBinding::inflate) {
    private lateinit var searchEmailActivityViewModel: SearchEmailActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //뷰모델 장착
        searchEmailActivityViewModel= ViewModelProvider(this).get(SearchEmailActivityViewModel::class.java)


        //재설정 메일 보내기 버튼 활성화를 위해 라이브 데이터 관찰
        searchEmailActivityViewModel.isAllCorrect.observe(this, Observer {
            binding.btnOk.isEnabled=it
        })


        //이름 텍스트 체인지 리스너
        binding.edtName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //이름의 데이터가 비어있지 않으면 isCorrectEdtName true로 변환 아니면 false
                searchEmailActivityViewModel.isCorrectEdtName.value = binding.edtName.length()>0

                //모든 조건이 만족하는지 확인하는 메소드 실행
                searchEmailActivityViewModel.checkIsAllCorrect()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })


        //edt phone 텍스트 체인지 리스너
        binding.edtPhone.addTextChangedListener(object : PhoneNumberFormattingTextWatcher() { //핸드폰 양식에 맞게 입력되도록 폰넘버포맷팅텍스트워쳐씀
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                var inputPhone = binding.edtPhone.text.toString()

                //입력이 12자가 되면 라이브 데이터 true 아니면 false
                searchEmailActivityViewModel.isCorrectEdtPhoneNum.value = inputPhone.length == 13

                //모든 조건이 만족하는지 확인하는 메소드 실행
                searchEmailActivityViewModel.checkIsAllCorrect()

                println("폰 번호: ${searchEmailActivityViewModel.isCorrectEdtPhoneNum.value}")
            }
        })


        //확인 버튼 클릭 리스너
        binding.btnOk.setOnClickListener {
            searchEmailActivityViewModel.tryGetSearchEmail(binding.edtName.text.toString(),binding.edtPhone.text.toString())
        }

        //뒤로가기 아이콘 클릭 리스너
        binding.btnBack.setOnClickListener {
            this.onBackPressed()
        }
    }

    override fun onStop() {
        super.onStop()

        this.finish()
    }
}