package com.example.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.knowing.R
import com.example.knowing.data.repository.DialogSelectDoRepository

class MoreInformationActivity1ViewModel(application: Application):AndroidViewModel(application) {
    private val mContext = application.applicationContext
    private val _currentCheckState = MutableLiveData<Boolean>() //가장, 다문화가정, 한부모가정~ 등의 동의할 경우 체크가 되는 버튼을 컨트롤 하기 위함.
    private val _doList = MutableLiveData<ArrayList<String>>() //도를 선택할 수 있는 도 리스트를 저장할 라이브 데이터
    private val _siList = MutableLiveData<ArrayList<String>>() //시/군/구를 선택할 수 있게 리사이클러뷰에 필요한 리스트를 저장할 라이브 데이터
    private val _currentTxDo = MutableLiveData<String>() // 바텀시트다이얼로그에서 시/도 선택을 하면 선택된 시/도가 텍스트에 보이도록 보여질 텍스트를 저장하는 라이브 데이터
    private val _currentTxSi = MutableLiveData<String>() // 바텀시트다이얼로그에서 시/군/구 선택을 하면 선택된 시/군/구가 텍스트에 보이도록 보여질 텍스트를 저장하는 라이브 데이터
    private val _isSelectDo = MutableLiveData<Boolean>() // 시/도를 선택했는지 안했는지 체크하는 라이브 데이터
    private val _isSelectSi = MutableLiveData<Boolean>() // 시/도를 선택했는지 안했는지 체크하는 라이브 데이터

    val isSelectDo:MutableLiveData<Boolean>
        get() = _isSelectDo

    val isSelectSi:MutableLiveData<Boolean>
        get() = _isSelectSi

    val currentCheckState:MutableLiveData<Boolean>
        get() = _currentCheckState

    val doList:MutableLiveData<ArrayList<String>>
        get() = _doList

    val siList:MutableLiveData<ArrayList<String>>
        get() = _siList


    val currentTxDo:MutableLiveData<String>
        get() = _currentTxDo

    val currentTxSi:MutableLiveData<String>
        get() = _currentTxSi


    init {
        _isSelectDo.value=false
        _currentCheckState.value=false
        _currentTxDo.value= mContext.getString(R.string.more_information1_choice_do)
        getDoList() //DummyDataClass에서 시/도 리스트를 받아오기위해 메소드 호출
    }

    // '아래 사항 해당시 체크해주세요'의 체크 여부를 반전시켜주기 위한 메소드
    fun changeBtnBackground(){
        _currentCheckState.value = _currentCheckState.value != true
    }

    /*
    시/도 리스트를 DummyDataClass에서 받아오는 메소드
     */
    fun getDoList(){
        _doList.value=DialogSelectDoRepository().getDoList()
    }

    /*
    시/군/구 리스트를 DummyDataClass에서 받아오기 위한 메소드
    시/도의 값을 비교해 일치하는 시/군/구 리스트를 받아서 _siList에 저장한다.
     */
    fun getSiList(){
        println(_currentTxDo.value.toString())
        if(_currentTxDo.value=="강원도")
            _siList.value=DialogSelectDoRepository().getGangwonSiList()
        else if (_currentTxDo.value=="경기도")
            _siList.value=DialogSelectDoRepository().getGyeonggiSiList()
        else if (_currentTxDo.value=="경상남도")
            _siList.value=DialogSelectDoRepository().getGyeongNamSiList()
        else if (_currentTxDo.value=="경상북도")
            _siList.value=DialogSelectDoRepository().getGyeongBukSiList()
        else if (_currentTxDo.value=="광주광역시")
            _siList.value=DialogSelectDoRepository().getGwangjuGuList()
        else if (_currentTxDo.value=="대구광역시")
            _siList.value=DialogSelectDoRepository().getDaeGuList()
        else if (_currentTxDo.value=="대전광역시")
            _siList.value=DialogSelectDoRepository().getDeajeonGuList()
        else if (_currentTxDo.value=="부산광역시")
            _siList.value=DialogSelectDoRepository().getBusanGuList()
        else if (_currentTxDo.value=="서울특별시")
            _siList.value=DialogSelectDoRepository().getSeoulGuList()
        else if (_currentTxDo.value=="세종특별자치시")
            _siList.value=DialogSelectDoRepository().getSejongSiList()
        else if (_currentTxDo.value=="울산광역시")
            _siList.value=DialogSelectDoRepository().getUlsanGuList()
        else if (_currentTxDo.value=="인천광역시")
            _siList.value=DialogSelectDoRepository().getIncheonGuList()
        else if (_currentTxDo.value=="전라남도")
            _siList.value=DialogSelectDoRepository().getJeonNamSiList()
        else if (_currentTxDo.value=="전라북도")
            _siList.value=DialogSelectDoRepository().getJeonBukSiList()
        else if (_currentTxDo.value=="제주특별자치도")
            _siList.value=DialogSelectDoRepository().getJejuSiList()
        else if (_currentTxDo.value=="충청남도")
            _siList.value=DialogSelectDoRepository().getChungNamSiList()
        else if (_currentTxDo.value=="충청북도")
            _siList.value=DialogSelectDoRepository().getChungbukSiList()
        else
            _siList.value=DialogSelectDoRepository().getDoList()
    }

}