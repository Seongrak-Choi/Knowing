package com.example.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.knowing.data.repository.DialogSelectDoRepository

class MoreInformationActivityViewModel(application: Application):AndroidViewModel(application) {
    private val _currentCheckState = MutableLiveData<Boolean>() //가장, 다문화가정, 한부모가정~ 등의 동의할 경우 체크가 되는 버튼을 컨트롤 하기 위함.
    private val _currentDoList = MutableLiveData<ArrayList<String>>() //도를 선택할 수 있는 도 리스트를 저장할 라이브 데이터

    val currentCheckState:MutableLiveData<Boolean>
        get() = _currentCheckState

    val currentDoList:MutableLiveData<ArrayList<String>>
        get() = _currentDoList

    init {
        _currentCheckState.value=false
        getDoList()
    }

    fun changeBtnBackground(){
        _currentCheckState.value = _currentCheckState.value != true
    }

    fun getDoList(){
        _currentDoList.value=DialogSelectDoRepository().getDoList()
    }

}