package com.example.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MoreInformationActivity3ViewModel(application: Application) : AndroidViewModel(application) {
    //학적사항 버튼 관련 라이브 데이터
    private val _isSelectBtnAll = MutableLiveData<Boolean>()
    private val _isSelectBtnUnderHighSchool = MutableLiveData<Boolean>()
    private val _isSelectBtnGraduateHighSchool = MutableLiveData<Boolean>()
    private val _isSelectBtnBeingCollege = MutableLiveData<Boolean>()
    private val _isSelectBtnGraduateCollege = MutableLiveData<Boolean>()
    private val _isSelectBtnDoctor = MutableLiveData<Boolean>()

    //다음 버튼의 활성화 유무를 책임질 라이브 데이터
    private val _isCorrectBtn = MutableLiveData<Boolean>()

    val isSelectBtnAll: MutableLiveData<Boolean>
        get() = _isSelectBtnAll
    val isSelectBtnUnderHighSchool: MutableLiveData<Boolean>
        get() = _isSelectBtnUnderHighSchool
    val isSelectBtnGraduateHighSchool: MutableLiveData<Boolean>
        get() = _isSelectBtnGraduateHighSchool
    val isSelectBtnBeingCollege: MutableLiveData<Boolean>
        get() = _isSelectBtnBeingCollege
    val isSelectBtnGraduateCollege: MutableLiveData<Boolean>
        get() = _isSelectBtnGraduateCollege
    val isSelectBtnDoctor: MutableLiveData<Boolean>
        get() = _isSelectBtnDoctor

    val isCorrectBtn: MutableLiveData<Boolean>
        get() = _isCorrectBtn


    //전체 버튼 클릭 시
    fun changeBtnAll() {
        allBtnValueFalse()
        _isSelectBtnAll.value = true
    }
    //고졸미만 버튼 클릭 시
    fun changeBtnUnderHighSchool() {
        allBtnValueFalse()
        _isSelectBtnUnderHighSchool.value = true
    }
    //고교졸업 버튼 클릭 시
    fun changeBtnGraduateHighSchool() {
        allBtnValueFalse()
        _isSelectBtnGraduateHighSchool.value = true
    }
    //대학재학 버튼 클릭 시
    fun changeBtnAllBeingCollege() {
        allBtnValueFalse()
        _isSelectBtnBeingCollege.value = true
    }
    //대학 졸업 버튼 클릭 시
    fun changeBtnGraudateCollege() {
        allBtnValueFalse()
        isSelectBtnGraduateCollege.value = true
    }
    //석박사 버튼 클릭 시
    fun changeBtnDoctor() {
        allBtnValueFalse()
        _isSelectBtnDoctor.value = true
    }


    //모든 버튼을 false로 변경해주는 메소드
    private fun allBtnValueFalse() {
        _isSelectBtnAll.value = false
        _isSelectBtnUnderHighSchool.value = false
        _isSelectBtnGraduateHighSchool.value = false
        _isSelectBtnBeingCollege.value = false
        _isSelectBtnGraduateCollege.value=false
        _isSelectBtnDoctor.value = false
    }

    //버튼들 중 하나라도 선택이 되어있다면 다음 버튼 활성화 시키기 위해 버튼 상태 체크하는 메소드
    fun checkIsCorrect(){
        if (_isSelectBtnAll.value==true||_isSelectBtnUnderHighSchool.value==true||_isSelectBtnGraduateHighSchool.value==true||
            _isSelectBtnBeingCollege.value==true||_isSelectBtnGraduateCollege.value==true||_isSelectBtnDoctor.value==true){
            _isCorrectBtn.value=true
        }else
            _isCorrectBtn.value=false
    }
}