package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.R
import com.teamteam.knowing.data.repository.DialogSelectDoRepository

class MoreInformationActivity1ViewModel(application: Application):AndroidViewModel(application) {
    private val mContext = application.applicationContext
    //시/도 선택 다이얼로그 관련된 라이브 데이터
    private val _doList = MutableLiveData<ArrayList<String>>() //도를 선택할 수 있는 도 리스트를 저장할 라이브 데이터
    private val _currentTxDo = MutableLiveData<String>() // 바텀시트다이얼로그에서 시/도 선택을 하면 선택된 시/도가 텍스트에 보이도록 보여질 텍스트를 저장하는 라이브 데이터
    private val _isSelectDo = MutableLiveData<Boolean>() // 시/도를 선택했는지 안했는지 체크하는 라이브 데이터

    //시/군/구 선택 다이얼로그 관련된 라이브 데이터
    private val _siList = MutableLiveData<ArrayList<String>>() //시/군/구를 선택할 수 있게 리사이클러뷰에 필요한 리스트를 저장할 라이브 데이터
    private val _currentTxSi = MutableLiveData<String>() // 바텀시트다이얼로그에서 시/군/구 선택을 하면 선택된 시/군/구가 텍스트에 보이도록 보여질 텍스트를 저장하는 라이브 데이터
    private val _isSelectSi = MutableLiveData<Boolean>() // 시/도를 선택했는지 안했는지 체크하는 라이브 데이터

    // 특별사항 선택 다이얼로그 관련된 라이브 데이터
    private val _currentCheckState = MutableLiveData<Boolean>() // 특별사항 선택 다이얼로그에서 선택사항 없음 체크 상태를 확인할 라이브 데이터
    private val _currentBtnSmallBusiness = MutableLiveData<Boolean>() //중소기업 버튼 체크 상태 저장할 라이브 데이터
    private val _currentBtnLowIncome = MutableLiveData<Boolean>() //저소득층 버튼 체크 상태 저장할 라이브 데이터
    private val _currentBtnDisabled = MutableLiveData<Boolean>() //장애인 버튼 체크 상태 저장할 라이브 데이터
    private val _currentBtnFarmer = MutableLiveData<Boolean>() //농업인 버튼 체크 상태 저장할 라이브 데이터
    private val _currentBtnSolider = MutableLiveData<Boolean>() //군인 버튼 체크 상태 저장할 라이브 데이터
    private val _currentBtnLocalTalent = MutableLiveData<Boolean>() //지역 인재 버튼 체크 상태 저장할 라이브 데이터
    private val _isSpecialDialogCorrect = MutableLiveData<Boolean>()//특별사항 다이얼로그에 버튼이 하나로도 선택될 경우 적용하기 버튼을 활성화 시키기 위한 라이브데이터
    private val _checkedBtnInfo : MutableLiveData<String> by lazy { MutableLiveData<String>() } //특별사항 다이얼로그에서 선택된 버튼들의 정보를 저장할 라이브 데이터

    //액티비티의 관련된 라이브 데이터
    private val _isAllCorrect = MutableLiveData<Boolean>() // 거주지와, 특별사항이 모든 선택 되어져 있는지 여부를 저장하는 라이브 데이터


    val isSelectDo:MutableLiveData<Boolean>
        get() = _isSelectDo

    val isSelectSi:MutableLiveData<Boolean>
        get() = _isSelectSi

      val doList:MutableLiveData<ArrayList<String>>
        get() = _doList

    val siList:MutableLiveData<ArrayList<String>>
        get() = _siList


    val currentTxDo:MutableLiveData<String>
        get() = _currentTxDo

    val currentTxSi:MutableLiveData<String>
        get() = _currentTxSi

    val currentCheckState:MutableLiveData<Boolean>
        get() = _currentCheckState

    val currentBtnSmallBusiness:MutableLiveData<Boolean>
        get() = _currentBtnSmallBusiness

    val currentBtnLowIncome:MutableLiveData<Boolean>
        get() = _currentBtnLowIncome

    val currentBtnDisabled:MutableLiveData<Boolean>
        get() = _currentBtnDisabled

    val currentBtnFarmer:MutableLiveData<Boolean>
        get() = _currentBtnFarmer

    val currentBtnSolider:MutableLiveData<Boolean>
        get() = _currentBtnSolider

    val currentBtnLocalTalent:MutableLiveData<Boolean>
        get() = _currentBtnLocalTalent

    val isSpecialDialogCorrect:MutableLiveData<Boolean>
        get() = _isSpecialDialogCorrect

    val isAllCorrect:MutableLiveData<Boolean>
        get() = _isAllCorrect

    val checkedBtnInfo:MutableLiveData<String>
        get() = _checkedBtnInfo


    init {
        _isSpecialDialogCorrect.value=false
        _currentCheckState.value = false
        _isSelectDo.value=false
        _currentTxDo.value= mContext.getString(R.string.more_information1_choice_do)
        getDoList() //DummyDataClass에서 시/도 리스트를 받아오기위해 메소드 호출
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


    // 특별사항 다이얼로그 중 선택사항없음 체크버튼 체크 상태를 반전시켜주기 위한 메소드
    fun changeBtnBackground(){
        _currentCheckState.value = _currentCheckState.value != true
        // 선택사항 없음이 체크되면 나머지 버튼들의 선택됨을 false로 바꿔주기 위한 조건문
        if (_currentCheckState.value==true){
            _currentBtnSmallBusiness.value=false
            _currentBtnLowIncome.value=false
            _currentBtnDisabled.value=false
            _currentBtnFarmer.value=false
            _currentBtnSolider.value=false
            _currentBtnLocalTalent.value=false
        }
        checkSpecialDialogCorrect()
    }


    //중소기업 버튼의 체크상태를 반전시키기 위한 메소드
    fun changeBtnSmallBusiness(){
        _currentBtnSmallBusiness.value = _currentBtnSmallBusiness.value != true
        //선택사항없음 버튼 체크상태 풀기
        _currentCheckState.value=false
        //스페셜 다이얼로그의 적용하기 버튼의 활성화를 확인하는 메소드
        checkSpecialDialogCorrect()
    }
    //저소득층 버튼의 체크상태를 반전시키기 위한 메소드
    fun changeBtnLowIncome(){
        _currentBtnLowIncome.value = _currentBtnLowIncome.value != true
        //선택사항없음 버튼 체크상태 풀기
        _currentCheckState.value=false
        //스페셜 다이얼로그의 적용하기 버튼의 활성화를 확인하는 메소드
        checkSpecialDialogCorrect()
    }
    //장애인 버튼의 체크상태를 반전시키기 위한 메소드
    fun changeBtnDisabled(){
        _currentBtnDisabled.value = _currentBtnDisabled.value != true
        //선택사항없음 버튼 체크상태 풀기
        _currentCheckState.value=false
        //스페셜 다이얼로그의 적용하기 버튼의 활성화를 확인하는 메소드
        checkSpecialDialogCorrect()
    }
    //농업인 버튼의 체크상태를 반전시키기 위한 메소드
    fun changeBtnFarmer(){
        _currentBtnFarmer.value = _currentBtnFarmer.value !=true
        //선택사항없음 버튼 체크상태 풀기
        _currentCheckState.value=false
        //스페셜 다이얼로그의 적용하기 버튼의 활성화를 확인하는 메소드
        checkSpecialDialogCorrect()
    }
    //군인 버튼의 체크상태를 반전시키기 위한 메소드
    fun changeBtnSolider(){
        _currentBtnSolider.value= _currentBtnSolider.value!=true
        //선택사항없음 버튼 체크상태 풀기
        _currentCheckState.value=false
        //스페셜 다이얼로그의 적용하기 버튼의 활성화를 확인하는 메소드
        checkSpecialDialogCorrect()
    }
    //지역인재 버튼의 체크상태를 반전시키기 위한 메소드
    fun changeBtnLocalTalent(){
        _currentBtnLocalTalent.value=_currentBtnLocalTalent.value!=true
        //선택사항없음 버튼 체크상태 풀기
        _currentCheckState.value=false
        //스페셜 다이얼로그의 적용하기 버튼의 활성화를 확인하는 메소드
        checkSpecialDialogCorrect()
    }



    //특별사항 다이얼로그 중 버튼이 하나라도 선택되어지면 적용하기 버튼이 활성화 되기 위해 버튼 상태들을 확인하는 메소드
    private fun checkSpecialDialogCorrect(){
        _isSpecialDialogCorrect.value = _currentBtnSmallBusiness.value==true || _currentBtnLowIncome.value==true || _currentBtnDisabled.value==true ||
                _currentBtnFarmer.value==true || _currentBtnSolider.value==true || _currentBtnLocalTalent.value==true || _currentCheckState.value==true
    }

    //거주지와 특별사항이 입력 되었는지 확인하기 위해 거주지, 특별사항이 선택이 될 때마다. 선택 상태를 확인하는 메소드
    fun checkIsAllCorrect(){
        //액티비티의 다음 버튼의 활성화를 위해 시/군/구 다이얼로그와 특별사항 다이얼로그의 상태를 확인하는 라이브데이터들을 확인하고 다음 버턴의 상태를 결정
        _isAllCorrect.value = _isSelectSi.value==true && _isSpecialDialogCorrect.value==true
    }

    //특별사항 다이얼로그에서 선택된 버튼들이 무엇인지 확인하기 위해 checkedBtnInfo라이브 데이터에 버튼의 정보를 저장함
    fun getCheckedBtnInfo(){
        //버튼의 정보를 저장할 라이브데이터를 초기화 해줌
        _checkedBtnInfo.value=""

        if (_currentCheckState.value==true)
            _checkedBtnInfo.value+="선택사항 없음-"
        if(_currentBtnSmallBusiness.value==true)
            _checkedBtnInfo.value+="중소기업-"
        if (_currentBtnLowIncome.value==true)
            _checkedBtnInfo.value+="저소득층-"
        if (_currentBtnDisabled.value==true)
            _checkedBtnInfo.value+="장애인-"
        if (_currentBtnFarmer.value==true)
            _checkedBtnInfo.value+="농업인-"
        if (_currentBtnSolider.value==true)
            _checkedBtnInfo.value+="군인-"
        if (_currentBtnLocalTalent.value==true)
            _checkedBtnInfo.value+="지역인재-"
    }

    /*
    특별사항 중 선택된 데이터를 반환해주는 메소드
     */
    fun getSpecialStatus():ArrayList<String>{
        var choice_value = ArrayList<String>() //특별사항 중 선택된 데이터들을 공백을 기준으로 나눠서 저장할 변수
        if (_currentBtnSmallBusiness.value==true)
            choice_value.add("중소기업")
        if (_currentBtnLowIncome.value==true)
            choice_value.add("저소득층")
        if (_currentBtnDisabled.value==true)
            choice_value.add("장애인")
        if (_currentBtnFarmer.value==true)
            choice_value.add("농업인")
        if (_currentBtnSolider.value==true)
            choice_value.add("군인")
        if (_currentBtnLocalTalent.value==true)
            choice_value.add("지역인재")
        if (_currentCheckState.value==true)
            choice_value.add("none")
        return choice_value
    }
}