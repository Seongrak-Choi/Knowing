package com.example.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MoreInformationActivity5ViewModel(application: Application):AndroidViewModel(application) {
    private val _currentTxGrade = MutableLiveData<String>() //피커 선택시 선택된 학년을 데이터를 저장할 라이브 데이터
    private val _currentTxSemester = MutableLiveData<String>() //피커 선택시 선택된 학기를 데이터를 저장할 라이브 데이터
    private val _currentCheckState = MutableLiveData<Boolean>() //추가 학기 / 졸업 유예 체크 상태를 확인할 라이브 데이터


    val currentTxGrade: MutableLiveData<String>
        get() = _currentTxGrade

    val currentTxSemester: MutableLiveData<String>
        get() = _currentTxSemester

    val currentCheckState: MutableLiveData<Boolean>
        get() = _currentCheckState


    // 추가 학기 / 졸업 유예 체크 여부를 반전시켜주기 위한 메소드
    fun changeBtnBackground(){
        _currentCheckState.value = _currentCheckState.value != true
    }
}