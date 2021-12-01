package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class AgreeTermsActivityViewModel (application: Application) : AndroidViewModel(application) {
    //모두 동의합니다 체크 상태 확인할 라이브데이터
    private val _currentAllBtnState = MutableLiveData<Boolean>()
    //이용 약관 체크 상태 확인할 라이브데이터
    private val _currentTermsOfUseBtnState = MutableLiveData<Boolean>()
    //개인정보 수집 이용(필수) 체크 상태 확인할 라이브 데이터
    private val _currentPrivateInfoBtnState = MutableLiveData<Boolean>()
    //복지 정보 앱 푸시 알림 수신 동의 체크 상태 확인할 라이브데이터
    private val _currentAlarmBtnState = MutableLiveData<Boolean>()

    //확인 버튼 활성화 여부 저장할 라이브 데이터
    private val _currentOkBtnState = MutableLiveData<Boolean>()

    val currentAllBtnState : MutableLiveData<Boolean>
        get() =   _currentAllBtnState
    val currentTermsOfUseBtnState : MutableLiveData<Boolean>
        get() =   _currentTermsOfUseBtnState
    val currentAlarmBtnState : MutableLiveData<Boolean>
        get() =   _currentAlarmBtnState
    val currentPrivateInfoBtnState : MutableLiveData<Boolean>
        get() =   _currentPrivateInfoBtnState

    val currentOkBtnState : MutableLiveData<Boolean>
        get() =   _currentOkBtnState


    init {
        _currentAllBtnState.value=false
        _currentTermsOfUseBtnState.value=false
        _currentAlarmBtnState.value=false
        _currentPrivateInfoBtnState.value=false
    }

    /*
    모두 동의합니다 반전 시켜주는 메소드
     */
    fun changeAllBtnState(){
        if (_currentAllBtnState.value==true){
            _currentAllBtnState.value=false
            _currentAlarmBtnState.value=false
            _currentPrivateInfoBtnState.value=false
            _currentTermsOfUseBtnState.value=false
        }else{
            _currentAllBtnState.value=true
            _currentAlarmBtnState.value=true
            _currentPrivateInfoBtnState.value=true
            _currentTermsOfUseBtnState.value=true
        }
    }

    /*
    이용약관 (필수) 반전 시켜주는 메소드
     */
    fun changeTermsOfUseBtnState(){
        _currentTermsOfUseBtnState.value=_currentTermsOfUseBtnState.value!=true
    }

    /*
    개인정보 수집 이용(필수) 반전 시켜주는 메소드
    */
    fun changePrivateInfoBtnState(){
        _currentPrivateInfoBtnState.value=_currentPrivateInfoBtnState.value!=true
    }

    /*
    개인정보 수집 이용(필수) 반전 시켜주는 메소드
    */
    fun changeAlarmBtnState(){
        _currentAlarmBtnState.value=_currentAlarmBtnState.value!=true
    }


    /*
    이용 약관과 개인정보 수집 이용 버튼이 클릭 되어 있는지 확인해서 '확인' 버튼을 활성화 여부 구할 메소드
     */
    fun allCollect(){
        if (_currentPrivateInfoBtnState.value==true && _currentTermsOfUseBtnState.value==true){
            _currentOkBtnState.value=true
        }else{
            _currentOkBtnState.value=false
        }
    }
}