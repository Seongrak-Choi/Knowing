package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MoreInformationActivity2ViewModel(application: Application):AndroidViewModel(application) {
    //취업상태 선택하는 버튼 관련된 라이브데이터
    private val _isSelectBtnAll=MutableLiveData<Boolean>() //전체 버튼
    private val _isSelectBtnUnEmployment=MutableLiveData<Boolean>() //미취업자
    private val _isSelectBtnEmployment=MutableLiveData<Boolean>() //재직자
    private val _isSelectBtnFreelancer=MutableLiveData<Boolean>() //프리랜서
    private val _isSelectBtnDailyWorker=MutableLiveData<Boolean>() //일용근로자
    private val _isSelectBtnShortWorker=MutableLiveData<Boolean>() //단기근로자
    private val _isSelectBtnPrepEntrepreneur =MutableLiveData<Boolean>() //예비창업자
    private val _isSelectBtnSelfOwnership=MutableLiveData<Boolean>() //자영업자
    private val _isSelectBtnFarming=MutableLiveData<Boolean>() //영농종사자

    //월소득 저장할 라이브데이터
    private val _currentEdtIncome : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _isSelectEdtIncome = MutableLiveData<Boolean>() //월 소득 입력했는지 확인할 라이브 데이터

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
    val isSelectEdtIncome : MutableLiveData<Boolean>
        get() = _isSelectEdtIncome

    val isCorrect : MutableLiveData<Boolean>
        get() = _isCorrect



    init {
        _isCorrect.value=false
        _isSelectEdtIncome.value=false
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
            && _isSelectEdtIncome.value==true){
            _isCorrect.value=true
        }else
            _isCorrect.value=false
    }

    /*
    입력받은 월소득을 기준으로 분위를 알려주는 메소드
     */
    fun getIncomeLevel(income:String):String{
        var income_int=income.replace(",","").toInt()
        if (income_int>14628870){
            return "10"
        }else if(income_int in 9752581..14628870){
            return "9"
        }else if (income_int in 7314436..9752580){
            return "8"
        } else if (income_int in 6339178..7314435){
            return "7"
        } else if (income_int in 4876291..6339177){
            return "6"
        }else if (income_int in 4388662..4876290){
            return "5"
        }else if (income_int in 3413404..4388661){
            return "4"
        }else if (income_int in 2438146..3413403){
            return "3"
        }else if (income_int in 1462888..2438145){
            return "2"
        }else{
            return "1"
        }
    }

    /*
   입력받은 월소득을 기준으로 중위소득 비율을 알려주는 메소드
    */
    fun getIncomeAvg(income:String):String{
        var income_int=income.replace(",","").toInt()
        if (income_int>14628870){
            return "-"
        }else if(income_int in 9752581..14628870){
            return "300"
        }else if (income_int in 7314436..9752580){
            return "200"
        } else if (income_int in 6339178..7314435){
            return "150"
        } else if (income_int in 4876291..6339177){
            return "130"
        }else if (income_int in 4388662..4876290){
            return "100"
        }else if (income_int in 3413404..4388661){
            return "90"
        }else if (income_int in 2438146..3413403){
            return "70"
        }else if (income_int in 1462888..2438145){
            return "50"
        }else{
            return "30"
        }
    }

    /*
    취업상태 선택된 버튼들을 파악해서 string 형식으로 돌려주는 메소드
     */
    fun getEmployState():ArrayList<String>{
        var choice_value = ArrayList<String>()//선택된 버튼들을 공백으로 나눠서 저장하기 위한 변수
        if (_isSelectBtnAll.value==true){
            choice_value.add("미취업자")
            choice_value.add("재직자")
            choice_value.add("프리랜서")
            choice_value.add("일용근로자")
            choice_value.add("단기근로자")
            choice_value.add("예비창업자")
            choice_value.add("자영업자")
            choice_value.add("영농종사자")
            return choice_value
        }else{
            if (_isSelectBtnUnEmployment.value==true)
                choice_value.add("미취업자")
            if (_isSelectBtnEmployment.value==true)
                choice_value.add("재직자")
            if (_isSelectBtnFreelancer.value==true)
                choice_value.add("프리랜서")
            if (_isSelectBtnDailyWorker.value==true)
                choice_value.add("일용근로자")
            if (_isSelectBtnShortWorker.value==true)
                choice_value.add("단기근로자")
            if (_isSelectBtnPrepEntrepreneur.value==true)
                choice_value.add("예비창업자")
            if (_isSelectBtnSelfOwnership.value==true)
                choice_value.add("자영업자")
            if (_isSelectBtnFarming.value==true)
                choice_value.add("영농종사자")
        }
        return choice_value
    }


}