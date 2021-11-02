package com.example.knowing.ui.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.knowing.R

class SignUpActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val mContext = application.applicationContext
    private val _currentEdtTextBirth = MutableLiveData<String>() //datePicker에서 날짜를 선택하면 해당 날짜를 저장하는 라이브데이터
    private val _currentBtnMen = MutableLiveData<Boolean>() // 성별 선택 시 남자를 선택했는지 확인하는 라이브 데이터
    private val _currentBtnWomen = MutableLiveData<Boolean>()// 성별 선택 시 여자를 선택했는지 확인하는 라이브 데이터
    private val _nameIsCorrect = MutableLiveData<Boolean>() //입력한 이름을 저장하는 라이브 데이터
    private val _emailIsCorrect = MutableLiveData<Boolean>()  //입력한 email이 정상인지 체크하는 라이브 데이터
    private val _pwdIsCorrect = MutableLiveData<Boolean>() //입력한 패스워드가 정상인지 체크하는 라이브 데이터
    private val _allIsCorrect = MutableLiveData<Boolean>() //이메일, 패스워드, 성별선택여부, 이름, 날짜선택여부 형식이 정상적인지 아닌지 판단해 회원가입하기 버튼을 활성화 시키는 라이브 데이터

    val currentEdtTextBirth: MutableLiveData<String>
        get() = _currentEdtTextBirth

    val currentBtnMen: MutableLiveData<Boolean>
        get() = _currentBtnMen

    val currentBtnWomen: MutableLiveData<Boolean>
        get() = _currentBtnWomen

    val nameIsCorrect : MutableLiveData<Boolean>
        get() = _nameIsCorrect

    val emailIsCorrect : MutableLiveData<Boolean>
        get() = _emailIsCorrect

    val pwdIsCorrect : MutableLiveData<Boolean>
        get() = _pwdIsCorrect

    val allIsCorrect : MutableLiveData<Boolean>
        get() = _allIsCorrect


    init {
        _currentEdtTextBirth.postValue("")
        _currentBtnMen.postValue(false)
        _currentBtnWomen.postValue(false)
        _nameIsCorrect.postValue(false)
        _pwdIsCorrect.postValue(false)
        _emailIsCorrect.postValue(false)
        _allIsCorrect.postValue(false)
    }

    /*
    성별에서 남성 버튼의 선택 여부를 반전 시켜주는 함수
     */
    fun changeValueMen() {
        _currentBtnMen.postValue(true)
        _currentBtnWomen.postValue(false)
    }

    /*
    성별에서 여성 버튼의 선택 여부를 반전 시켜주는 함수
     */
    fun changeValueWomen() {
        _currentBtnWomen.postValue(true)
        _currentBtnMen.postValue(false)
    }

    /*
    회원가입 버튼을 활성화 하기 위한 모든 조건들이 만족하는지 확인하는 메소드
     */
    fun checkAllIsCorrect(){
        println("이메일 ${_emailIsCorrect.value} / 패스워드 ${_pwdIsCorrect.value}  / 이름 ${_nameIsCorrect.value}  성별  ${isCheckGender()} / 생년월일  ${isInputBirth()}")

        //모든 조건들이 참인 경우 회원가입 버튼의 enabled를 담당하는 라이브 데이터를 true로 바꿔준다.
        if(_emailIsCorrect.value == true && _pwdIsCorrect.value==true && _nameIsCorrect.value==true && isCheckGender() && isInputBirth()){
            allIsCorrect.value=true
        }else
            allIsCorrect.value=false
    }

    /*
    gender가 하나라도 선택이 되어있는지 확인 후 boolean형 반환환
     */
   fun isCheckGender() : Boolean{
        return _currentBtnWomen.value==true || _currentBtnMen.value==true
    }

    /*
    생년월일 Edittext가 비어있지 않으면 생년월일을 입력 했다는 얘기이므로 true 반환
     */
    fun isInputBirth() :Boolean{
        return _currentEdtTextBirth.value.toString().isNotEmpty()
    }

}