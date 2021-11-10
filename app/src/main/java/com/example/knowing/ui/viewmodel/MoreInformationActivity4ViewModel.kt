package com.example.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MoreInformationActivity4ViewModel(application: Application):AndroidViewModel(application) {
    private val _isSelectHumanities = MutableLiveData<Boolean>()
    private val _isSelectSociety = MutableLiveData<Boolean>()
    private val _isSelectLaw = MutableLiveData<Boolean>()
    private val _isSelectManagement = MutableLiveData<Boolean>()
    private val _isSelectEducation = MutableLiveData<Boolean>()
    private val _isSelectEngineering = MutableLiveData<Boolean>()
    private val _isSelectNature = MutableLiveData<Boolean>()
    private val _isSelectEntertainment = MutableLiveData<Boolean>()
    private val _isSelectMedical = MutableLiveData<Boolean>()
    private val _isSelectEtc = MutableLiveData<Boolean>()

    val isSelectHumanities : MutableLiveData<Boolean>
        get() = _isSelectHumanities

    val isSelectSociety : MutableLiveData<Boolean>
        get() = _isSelectSociety

    val isSelectLaw : MutableLiveData<Boolean>
        get() = _isSelectLaw

    val isSelectManagement : MutableLiveData<Boolean>
        get() = _isSelectManagement

    val isSelectEducation : MutableLiveData<Boolean>
        get() = _isSelectEducation

    val isSelectEngineering : MutableLiveData<Boolean>
        get() = _isSelectEngineering

    val isSelectNature : MutableLiveData<Boolean>
        get() = _isSelectNature

    val isSelectEntertainment : MutableLiveData<Boolean>
        get() = _isSelectEntertainment

    val isSelectMedical : MutableLiveData<Boolean>
        get() = _isSelectMedical

    val isSelectEtc : MutableLiveData<Boolean>
        get() = _isSelectEtc

    /*
    버튼이 중복되서 눌리지 않게 모든 버튼의 클릭 상태를  false로 바꿔주는 메소드
     */
    fun allPressedFalse(){
        _isSelectHumanities.value=false
        _isSelectSociety.value=false
        _isSelectLaw.value=false
        _isSelectManagement.value=false
        _isSelectEducation.value=false
        _isSelectEngineering.value=false
        _isSelectNature.value=false
        _isSelectEntertainment.value=false
        _isSelectMedical.value=false
        _isSelectEtc.value=false
    }

    fun changeBtnHumanities(){
        allPressedFalse() //다른 버튼 다 false로 변경 메소드 실행
        _isSelectHumanities.value=_isSelectHumanities.value != true
    }

    fun changeBtnSociety(){
        allPressedFalse() //다른 버튼 다 false로 변경 메소드 실행
        _isSelectSociety.value=_isSelectHumanities.value != true
    }

    fun changeBtnLaw(){
        allPressedFalse() //다른 버튼 다 false로 변경 메소드 실행
        _isSelectLaw.value=_isSelectHumanities.value != true
    }

    fun changeBtnManagement(){
        allPressedFalse() //다른 버튼 다 false로 변경 메소드 실행
        _isSelectManagement.value=_isSelectHumanities.value != true
    }

    fun changeBtnEducation(){
        allPressedFalse() //다른 버튼 다 false로 변경 메소드 실행
        _isSelectEducation.value=_isSelectHumanities.value != true
    }

    fun changeBtnEngineering(){
        allPressedFalse() //다른 버튼 다 false로 변경 메소드 실행
        _isSelectEngineering.value=_isSelectHumanities.value != true
    }

    fun changeBtnNature(){
        allPressedFalse() //다른 버튼 다 false로 변경 메소드 실행
        _isSelectNature.value=_isSelectHumanities.value != true
    }

    fun changeBtnEntertainment(){
        allPressedFalse() //다른 버튼 다 false로 변경 메소드 실행
        _isSelectEntertainment.value=_isSelectHumanities.value != true
    }

    fun changeBtnMedical(){
        allPressedFalse() //다른 버튼 다 false로 변경 메소드 실행
        _isSelectMedical.value=_isSelectHumanities.value != true
    }

    fun changeBtnEtc(){
        allPressedFalse() //다른 버튼 다 false로 변경 메소드 실행
        _isSelectEtc.value=_isSelectHumanities.value != true
    }
}