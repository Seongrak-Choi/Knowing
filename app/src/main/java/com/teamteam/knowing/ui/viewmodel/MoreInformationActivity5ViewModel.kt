package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MoreInformationActivity5ViewModel(application: Application):AndroidViewModel(application) {
    private val _currentTxGrade = MutableLiveData<String>() //피커 선택시 선택된 학년을 데이터를 저장할 라이브 데이터
    private val _currentTxSemester = MutableLiveData<String>() //피커 선택시 선택된 학기를 데이터를 저장할 라이브 데이터
    private val _currentCheckState = MutableLiveData<Boolean>() //추가 학기 / 졸업 유예 체크 상태를 확인할 라이브 데이터
    private val _isSelectLow = MutableLiveData<Boolean>() //~2.9 버튼 체크 상태를 확인할 라이브 데이터
    private val _isSelectMedium = MutableLiveData<Boolean>() //3.0~3.4 버튼 체크 상태를 확인할 라이브 데이터
    private val _isSelectHigh = MutableLiveData<Boolean>() // 3.5~3.9 버튼 체크 상태를 확인할 라이브 데이터
    private val _isSelectNone = MutableLiveData<Boolean>() // 해당 없음 버튼 체크 상태를 확인할 라이브 데이터


    val currentTxGrade: MutableLiveData<String>
        get() = _currentTxGrade

    val currentTxSemester: MutableLiveData<String>
        get() = _currentTxSemester

    val currentCheckState: MutableLiveData<Boolean>
        get() = _currentCheckState

    val isSelectLow: MutableLiveData<Boolean>
        get() = _isSelectLow

    val isSelectMedium: MutableLiveData<Boolean>
        get() = _isSelectMedium

    val isSelectHigh: MutableLiveData<Boolean>
        get() = _isSelectHigh

    val isSelectNone: MutableLiveData<Boolean>
        get() = _isSelectNone

    // 추가 학기 / 졸업 유예 체크 여부를 반전시켜주기 위한 메소드
    fun changeBtnBackground(){
        _currentCheckState.value = _currentCheckState.value != true
    }

    //~2.9버튼 체크 여부를 반전시켜주기 위한 메소드
    fun changeBtnLow(){
        //다른 버튼들의 상태는 false로 만들면서 자신은 true를 유지해줘야 버튼 클릭을 취소할 수 있기 때문에
        //자신을 제외한 나머지를 false로 바꿔주는 allBtnFalseSelfTrue와 allBtnFalseSelfFalse메소드를 나눔
        if (_isSelectLow.value==true){ //만약 기존의 값이 true이면 자신의 값은 true로 유지해주는 메소드 실행
            allBtnFalseSelfTrue(_isSelectLow)
        }else{ //만약 기존의 값이 false였다면 자신도 false로 만들어 버리는 메소드 실행
            allBtnFalseSelfFalse()
        }
        _isSelectLow.value=_isSelectLow.value != true
    }

    // 3.0~3.4 버튼 체크 여부를 반전시켜주기 위한 메소드
    fun changeBtnMedium(){
        //다른 버튼들의 상태는 false로 만들면서 자신은 true를 유지해줘야 버튼 클릭을 취소할 수 있기 때문에
        //자신을 제외한 나머지를 false로 바꿔주는 allBtnFalseSelfTrue와 allBtnFalseSelfFalse메소드를 나눔
        if (_isSelectMedium.value==true){ //만약 기존의 값이 true이면 자신의 값은 true로 유지해주는 메소드 실행
            allBtnFalseSelfTrue(_isSelectMedium)
        }else{ //만약 기존의 값이 false였다면 자신도 false로 만들어 버리는 메소드 실행
            allBtnFalseSelfFalse()
        }
        _isSelectMedium.value=_isSelectMedium.value != true
    }


    // 3.5~3.9버튼 체크 여부를 반전시켜주기 위한 메소드
    fun changeBtnHigh(){
        //다른 버튼들의 상태는 false로 만들면서 자신은 true를 유지해줘야 버튼 클릭을 취소할 수 있기 때문에
        //자신을 제외한 나머지를 false로 바꿔주는 allBtnFalseSelfTrue와 allBtnFalseSelfFalse메소드를 나눔
        if (_isSelectHigh.value==true){ //만약 기존의 값이 true이면 자신의 값은 true로 유지해주는 메소드 실행
            allBtnFalseSelfTrue(_isSelectHigh)
        }else{ //만약 기존의 값이 false였다면 자신도 false로 만들어 버리는 메소드 실행
            allBtnFalseSelfFalse()
        }
        _isSelectHigh.value=_isSelectHigh.value != true
    }

    //해당 없음 버튼 체크 여부를 반전시켜주기 위한 메소드
    fun changeBtnNone(){
        //다른 버튼들의 상태는 false로 만들면서 자신은 true를 유지해줘야 버튼 클릭을 취소할 수 있기 때문에
        //자신을 제외한 나머지를 false로 바꿔주는 allBtnFalseSelfTrue와 allBtnFalseSelfFalse메소드를 나눔
        if (_isSelectNone.value==true){ //만약 기존의 값이 true이면 자신의 값은 true로 유지해주는 메소드 실행
            allBtnFalseSelfTrue(_isSelectNone)
        }else{ //만약 기존의 값이 false였다면 자신도 false로 만들어 버리는 메소드 실행
            allBtnFalseSelfFalse()
        }
        _isSelectNone.value=_isSelectNone.value != true
    }

    /*
    선택이 중복되지 않게 버튼 선택 시 나머지 버튼은 false로 바꿔주고 자신은 true로 바꿔주는 메소드
     */
    fun allBtnFalseSelfTrue(data:MutableLiveData<Boolean>){
        _isSelectLow.value=false
        _isSelectMedium.value=false
        _isSelectHigh.value=false
        _isSelectNone.value=false
        data.value=true
    }

    /*
    선택이 중복되지 않게 버튼 선택 시 나머지 버튼은 false로 바꿔주는 메소드
     */
    fun allBtnFalseSelfFalse(){
        _isSelectLow.value=false
        _isSelectMedium.value=false
        _isSelectHigh.value=false
        _isSelectNone.value=false
    }

    /*
    지난 학기 학점에서 선택된 데이터가 무엇인지 String형식으로 반환해주는 메소드
     */
    fun getLastSemesterScore():String{
        if (_isSelectLow.value==true)
            return "~2.9"
        else if (_isSelectMedium.value==true)
            return "3.0~3.4"
        else if (_isSelectHigh.value==true)
            return "3.5~3.9"
        else
            return "없음"
    }
}