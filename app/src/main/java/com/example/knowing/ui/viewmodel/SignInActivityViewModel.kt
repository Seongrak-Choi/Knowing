package com.example.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SignInActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val _allIsCorrect = MutableLiveData<Boolean>() //이메일과 패스워드의 참 여부를 판단하여 로그인하기 버튼의 활성화를 책임지는 라이브 데이터
    private val _emailIsCorrect = MutableLiveData<Boolean>()  //입력한 email이 정상인지 체크하는 라이브 데이터
    private val _pwdIsCorrect = MutableLiveData<Boolean>() //입력한 패스워드가 정상인지 체크하는 라이브 데이터

    val allIsCorrect : MutableLiveData<Boolean>
        get() = _allIsCorrect

    val emailIsCorrect : MutableLiveData<Boolean>
        get() = _emailIsCorrect

    val pwdIsCorrect : MutableLiveData<Boolean>
        get() = _pwdIsCorrect

    init {
        _allIsCorrect.value=false
        _emailIsCorrect.value=false
        _pwdIsCorrect.value=false
    }


    /*
    emailIsCorrect와 pwdIsCorrect가 참인 경우 allIsCorrect를 true로 변경해주기 위한 메소드
     */
    fun isAllCorrect(){
        if (_emailIsCorrect.value==true && _pwdIsCorrect.value==true) //둘다 true이면
            _allIsCorrect.value=true //로그인하기 버튼의 활성화를 담당하는 라이브데이터를 true로 변경
        else
            _allIsCorrect.value=false //로그인하기 버튼의 활성화를 담당하는 라이브데이터를 false로 변경
    }

}