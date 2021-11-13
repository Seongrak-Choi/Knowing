package com.example.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MoreInformationActivity2ViewModel(application: Application):AndroidViewModel(application) {
    //취업상태 선택하는 버튼 관련된 라이브데이터
    private val _isSelectBtnAll=MutableLiveData<Boolean>()
    private val _isSelectBtnUnEmployment=MutableLiveData<Boolean>()
    private val _isSelectBtnEmployment=MutableLiveData<Boolean>()
    private val _isSelectBtnFreelancer=MutableLiveData<Boolean>()
    private val _isSelectBtnDailyWorker=MutableLiveData<Boolean>()
    private val _isSelectBtnShortWorker=MutableLiveData<Boolean>()
    private val _isSelectBtnPrepEntrepreneur =MutableLiveData<Boolean>()
    private val _isSelectBtnSelfOwnership=MutableLiveData<Boolean>()
    private val _isSelectBtnFarming=MutableLiveData<Boolean>()

    //월소득 저장할 라이브데이터
    private val _currentEdtIncome : MutableLiveData<String> by lazy { MutableLiveData<String>() }

    //'다음'버튼을 활성화 시키기위해 버튼을 하나라도 선택했는지, 월소득을 입력했는지 확인하는 라이브 데이터
    private val _isCorrect=MutableLiveData<Boolean>()

    val isSelectBtnAll : MutableLiveData<Boolean>
        get() = _isSelectBtnAll
    val isSelectBtnUnEmployment : MutableLiveData<Boolean>
        get() = _isSelectBtnUnEmployment
    val isSelectBtnEmployment : MutableLiveData<Boolean>
        get() = _isSelectBtnEmployment
    val isSelectBtnFreelancer : MutableLiveData<Boolean>
        get() = _isSelectBtnFreelancer
    val isSelectBtnDailyWorker : MutableLiveData<Boolean>
        get() = _isSelectBtnDailyWorker
    val isSelectBtnShortWorker : MutableLiveData<Boolean>
        get() = _isSelectBtnShortWorker
    val isSelectBtnPrepEntrepreneur : MutableLiveData<Boolean>
        get() = _isSelectBtnPrepEntrepreneur
    val isSelectBtnSelfOwnership : MutableLiveData<Boolean>
        get() = _isSelectBtnSelfOwnership
    val isSelectBtnFarming : MutableLiveData<Boolean>
        get() = _isSelectBtnFarming

    val currentEdtIncome : MutableLiveData<String>
        get() = _currentEdtIncome

    val isCorrect : MutableLiveData<Boolean>
        get() = _isCorrect



    init {
        _isCorrect.value=false
    }

    //취업상태-전체 버튼 반전시키는 메소드
    fun changeBtnAll(){
        _isSelectBtnAll.value=_isSelectBtnAll.value!=true

        //전체 버튼을 제외한 나머지 버튼의 상태를 false로 변경
        _isSelectBtnUnEmployment.value=false
        _isSelectBtnEmployment.value=false
        _isSelectBtnFreelancer.value=false
        _isSelectBtnDailyWorker.value=false
        _isSelectBtnShortWorker.value=false
        _isSelectBtnPrepEntrepreneur.value=false
        _isSelectBtnSelfOwnership.value=false
        _isSelectBtnFarming.value=false
    }

    //취업상태-미취업자 버튼 반전시키는 메소드
    fun changeBtnUnEmployment(){
        _isSelectBtnUnEmployment.value=_isSelectBtnUnEmployment.value!=true

        _isSelectBtnAll.value=false//전체 버튼은 false로 변경
    }
    //취업상태-재직자 버튼 반전시키는 메소드
    fun changeBtnEmployment(){
        _isSelectBtnEmployment.value=_isSelectBtnEmployment.value!=true
        _isSelectBtnAll.value=false//전체 버튼은 false로 변경
    }
    //취업상태-전체 버튼 반전시키는 메소드
    fun changeBtnFreelancer(){
        _isSelectBtnFreelancer.value=_isSelectBtnFreelancer.value!=true
        _isSelectBtnAll.value=false//전체 버튼은 false로 변경
    }
    //취업상태-전체 버튼 반전시키는 메소드
    fun changeBtnDailyWorker(){
        _isSelectBtnDailyWorker.value=_isSelectBtnDailyWorker.value!=true
        _isSelectBtnAll.value=false//전체 버튼은 false로 변경
    }
    //취업상태-전체 버튼 반전시키는 메소드
    fun changeBtnShortWorker(){
        _isSelectBtnShortWorker.value=_isSelectBtnShortWorker.value!=true
        _isSelectBtnAll.value=false//전체 버튼은 false로 변경
    }
    //취업상태-전체 버튼 반전시키는 메소드
    fun changeBtnPrepEntrepreneur(){
        _isSelectBtnPrepEntrepreneur.value=_isSelectBtnPrepEntrepreneur.value!=true
        _isSelectBtnAll.value=false//전체 버튼은 false로 변경
    }
    //취업상태-전체 버튼 반전시키는 메소드
    fun changeBtnSelfOwnership(){
        _isSelectBtnSelfOwnership.value=_isSelectBtnSelfOwnership.value!=true
        _isSelectBtnAll.value=false//전체 버튼은 false로 변경
    }
    //취업상태-전체 버튼 반전시키는 메소드
    fun changeBtnFarming(){
        _isSelectBtnFarming.value=_isSelectBtnFarming.value!=true
        _isSelectBtnAll.value=false//전체 버튼은 false로 변경
    }

    //다음 버튼을 활성화 시키기 위해 최소 버튼1개 클릭, 월소득이 비어있지 않는지 확인
    fun checkIsCorrect(){
        if ((_isSelectBtnAll.value==true ||_isSelectBtnUnEmployment.value==true || _isSelectBtnEmployment.value==true || _isSelectBtnFreelancer.value==true ||
            _isSelectBtnDailyWorker.value==true|| _isSelectBtnShortWorker.value==true||_isSelectBtnPrepEntrepreneur.value==true||_isSelectBtnSelfOwnership.value==true||_isSelectBtnFarming.value==true)
            && _currentEdtIncome.value!=""){
            _isCorrect.value=true
        }else
            _isCorrect.value=false
    }


}