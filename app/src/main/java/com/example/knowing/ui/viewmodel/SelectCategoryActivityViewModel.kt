package com.example.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SelectCategoryActivityViewModel(application: Application):AndroidViewModel(application) {
    //학생 지원
    private val _isSelectStudentBtnAll = MutableLiveData<Boolean>()
    private val _isSelectStudentBtnInSchoolTuition = MutableLiveData<Boolean>()
    private val _isSelectStudentBtnOutSchoolTuition = MutableLiveData<Boolean>()

    //취업 지원
    private val _isSelectJobBtnAll = MutableLiveData<Boolean>()
    private val _isSelectJobBtnJobSearch=MutableLiveData<Boolean>()
    private val _isSelectJobBtnSmallBusiness = MutableLiveData<Boolean>()
    private val _isSelectJobBtnSpecialField=MutableLiveData<Boolean>()
    private val _isSelectJobBtnOverSeas = MutableLiveData<Boolean>()

    //창업 지원
    private val _isSelectFoundationBtnAll=MutableLiveData<Boolean>()
    private val _isSelectFoundationBtnFoundationOperate=MutableLiveData<Boolean>()
    private val _isSelectFoundationBtnManagement=MutableLiveData<Boolean>()
    private val _isSelectFoundationBtnCapital=MutableLiveData<Boolean>()

    //주거 금융지원
    private val _isSelectDwellingBtnAll=MutableLiveData<Boolean>()
    private val _isSelectDwellingBtnLivingExpense=MutableLiveData<Boolean>()
    private val _isSelectDwellingBtnDwelling=MutableLiveData<Boolean>()
    private val _isSelectDwellingBtnSchoolExpense=MutableLiveData<Boolean>()

    //생활복 지원
    private val _isSelectLifeBtnAll=MutableLiveData<Boolean>()
    private val _isSelectLifeBtnHealth=MutableLiveData<Boolean>()
    private val _isSelectLifeBtnCulture=MutableLiveData<Boolean>()

    //코로나19 지원
    private val _isSelectCoronaBtnAll=MutableLiveData<Boolean>()
    private val _isSelectCoronaBtnBasicIncome=MutableLiveData<Boolean>()
    private val _isSelectCoronaBtnLowIncome=MutableLiveData<Boolean>()
    private val _isSelectCoronaBtnDisasterDamage=MutableLiveData<Boolean>()
    private val _isSelectCoronaBtnJobPreservation=MutableLiveData<Boolean>()
    private val _isSelectCoronaBtnIncentive=MutableLiveData<Boolean>()
    private val _isSelectCoronaBtnPsychologicalSupport=MutableLiveData<Boolean>()


    val isSelectStudentBtnAll: MutableLiveData<Boolean>
        get() = _isSelectStudentBtnAll
    val isSelectStudentBtnInSchoolTuition: MutableLiveData<Boolean>
        get() = _isSelectStudentBtnInSchoolTuition
    val isSelectStudentBtnOutSchoolTuition: MutableLiveData<Boolean>
        get() = _isSelectStudentBtnOutSchoolTuition


    val isSelectJobBtnAll: MutableLiveData<Boolean>
        get() = _isSelectJobBtnAll
    val isSelectJobBtnJobSearch: MutableLiveData<Boolean>
        get() = _isSelectJobBtnJobSearch
    val isSelectJobBtnSmallBusiness: MutableLiveData<Boolean>
        get() = _isSelectJobBtnSmallBusiness
    val isSelectJobBtnSpecialField: MutableLiveData<Boolean>
        get() = _isSelectJobBtnSpecialField
    val isSelectJobBtnOverSeas: MutableLiveData<Boolean>
        get() = _isSelectJobBtnOverSeas

    val isSelectFoundationBtnAll: MutableLiveData<Boolean>
        get() = _isSelectFoundationBtnAll
    val isSelectFoundationBtnFoundationOperate: MutableLiveData<Boolean>
        get() = _isSelectFoundationBtnFoundationOperate
    val isSelectFoundationBtnManagement: MutableLiveData<Boolean>
        get() = _isSelectFoundationBtnManagement
    val isSelectFoundationBtnCapital: MutableLiveData<Boolean>
        get() = _isSelectFoundationBtnCapital

    val isSelectDwellingBtnAll: MutableLiveData<Boolean>
        get() = _isSelectDwellingBtnAll
    val isSelectDwellingBtnLivingExpense: MutableLiveData<Boolean>
        get() = _isSelectDwellingBtnLivingExpense
    val isSelectDwellingBtnDwelling: MutableLiveData<Boolean>
        get() = _isSelectDwellingBtnDwelling
    val isSelectDwellingBtnSchoolExpense: MutableLiveData<Boolean>
        get() = _isSelectDwellingBtnSchoolExpense

    val isSelectLifeBtnAll: MutableLiveData<Boolean>
        get() = _isSelectLifeBtnAll
    val isSelectLifeBtnHealth: MutableLiveData<Boolean>
        get() = _isSelectLifeBtnHealth
    val isSelectLifeBtnCulture: MutableLiveData<Boolean>
        get() = _isSelectLifeBtnCulture

    val isSelectCoronaBtnAll: MutableLiveData<Boolean>
        get() = _isSelectCoronaBtnAll
    val isSelectCoronaBtnBasicIncome: MutableLiveData<Boolean>
        get() = _isSelectCoronaBtnBasicIncome
    val isSelectCoronaBtnLowIncome: MutableLiveData<Boolean>
        get() = _isSelectCoronaBtnLowIncome
    val isSelectCoronaBtnDisasterDamage: MutableLiveData<Boolean>
        get() = _isSelectCoronaBtnDisasterDamage
    val isSelectCoronaBtnJobPreservation: MutableLiveData<Boolean>
        get() = _isSelectCoronaBtnJobPreservation
    val isSelectCoronaBtnIncentive: MutableLiveData<Boolean>
        get() = _isSelectCoronaBtnIncentive
    val isSelectCoronaBtnPsychologicalSupport: MutableLiveData<Boolean>
        get() = _isSelectCoronaBtnPsychologicalSupport



    //학생 지원의 '전체' 버튼을 반전시켜주는 메소드
    fun changeStudentBtnAll(){
        //학생 지원 전체 버튼 반전 시키는 라인
        _isSelectStudentBtnAll.value=_isSelectStudentBtnAll.value!=true

        //전체를 제외한 나머지 버튼을 false로 변경
        _isSelectStudentBtnInSchoolTuition.value=false
        _isSelectStudentBtnOutSchoolTuition.value=false
    }

    //학생 지원의 '교내 장학금' 버튼을 반전시켜주는 메소드
    fun changeStudentBtnInSchoolTuition(){
        _isSelectStudentBtnInSchoolTuition.value=_isSelectStudentBtnInSchoolTuition.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        isSelectStudentBtnAll.value=false
    }

    //학생 지원의 '교외 장학금' 버튼을 반전시켜주는 메소드
    fun changeStudentBtnOutSchoolTuition(){
        _isSelectStudentBtnOutSchoolTuition.value=_isSelectStudentBtnOutSchoolTuition.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        isSelectStudentBtnAll.value=false
    }



    //취업 지원의 '전체' 버튼을 반전시켜주는 메소드
    fun changeJobBtnAll(){
        //학생 지원 전체 버튼 반전 시키는 라인
        _isSelectJobBtnAll.value=_isSelectJobBtnAll.value!=true

        //전체를 제외한 나머지 버튼을 false로 변경
        _isSelectJobBtnJobSearch.value=false
        _isSelectJobBtnSmallBusiness.value=false
        _isSelectJobBtnSpecialField.value=false
        _isSelectJobBtnOverSeas.value=false
    }

    //취업 지원-구직활동지원인턴 버튼을 반전시켜주는 메소드
    fun changeJobBtnJobSearch(){
        _isSelectJobBtnJobSearch.value=_isSelectJobBtnJobSearch.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectJobBtnAll.value=false
    }
    //취업 지원-중소중견기업취업지원 버튼을 반전시켜주는 메소드
    fun changeJobBtnJobSmallBusiness(){
        _isSelectJobBtnSmallBusiness.value= _isSelectJobBtnSmallBusiness.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectJobBtnAll.value=false
    }
    //취업 지원-특수분야취업지원 버튼을 반전시켜주는 메소드
    fun changeJobBtnJobSpecialField(){
        _isSelectJobBtnSpecialField.value= _isSelectJobBtnSpecialField.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectJobBtnAll.value=false
    }
    //취업 지원-해외취업 및 진출 지원 버튼을 반전시켜주는 메소드
    fun changeJobBtnJobOverSeas(){
        _isSelectJobBtnOverSeas.value= _isSelectJobBtnOverSeas.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectJobBtnAll.value=false
    }






    //창업 지원의 '전체' 버튼을 반전시켜주는 메소드
    fun changeFoundationBtnAll(){
        //학생 지원 전체 버튼 반전 시키는 라인
        _isSelectFoundationBtnAll.value=_isSelectFoundationBtnAll.value!=true

        //전체를 제외한 나머지 버튼을 false로 변경
        _isSelectFoundationBtnFoundationOperate.value= false
        _isSelectFoundationBtnManagement.value=false
        _isSelectFoundationBtnCapital.value=false
    }
    //창업지원-창업운영지원 및 진출 지원 버튼을 반전시켜주는 메소드
    fun changeFoundationBtnFoundationOperate(){
        _isSelectFoundationBtnFoundationOperate.value= _isSelectFoundationBtnFoundationOperate.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectFoundationBtnAll.value=false
    }
    //창업지원-경영지원 버튼을 반전시켜주는 메소드
    fun changeFoundationBtnManagement(){
        _isSelectFoundationBtnManagement.value= _isSelectFoundationBtnManagement.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectFoundationBtnAll.value=false
    }
    //창업지원-자본금지원 버튼을 반전시켜주는 메소드
    fun changeFoundationBtnCapital(){
        _isSelectFoundationBtnCapital.value= _isSelectFoundationBtnCapital.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectFoundationBtnAll.value=false
    }




    //주거 금융 지원의 '전체' 버튼을 반전시켜주는 메소드
    fun changeDwellingBtnAll(){
        //학생 지원 전체 버튼 반전 시키는 라인
        _isSelectDwellingBtnAll.value=_isSelectDwellingBtnAll.value!=true

        //전체를 제외한 나머지 버튼을 false로 변경
        _isSelectDwellingBtnLivingExpense.value=false
        _isSelectDwellingBtnDwelling.value=false
        _isSelectDwellingBtnSchoolExpense.value=false
    }
    //주거금융-생활비지원 버튼을 반전시켜주는 메소드
    fun changeDwellingBtnLivingExpense(){
        _isSelectDwellingBtnLivingExpense.value= _isSelectDwellingBtnLivingExpense.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectDwellingBtnAll.value=false
    }
    //주거금융-주거지원 버튼을 반전시켜주는 메소드
    fun changeDwellingBtnDwelling(){
        _isSelectDwellingBtnDwelling.value= _isSelectDwellingBtnDwelling.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectDwellingBtnAll.value=false
    }
    //주거금융-학자금지원 버튼을 반전시켜주는 메소드
    fun changeDwellingBtnSchoolExpense(){
        _isSelectDwellingBtnSchoolExpense.value= _isSelectDwellingBtnSchoolExpense.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectDwellingBtnAll.value=false
    }



    //생활복지 지원의 '전체' 버튼을 반전시켜주는 메소드
    fun changeLifeBtnAll(){
        //학생 지원 전체 버튼 반전 시키는 라인
        _isSelectLifeBtnAll.value=_isSelectLifeBtnAll.value!=true

        //전체를 제외한 나머지 버튼을 false로 변경
        _isSelectLifeBtnHealth.value=false
        _isSelectLifeBtnCulture.value=false
    }
    //생활복지-건강 버튼을 반전시켜주는 메소드
    fun changeLifeBtnHealth(){
        _isSelectLifeBtnHealth.value= _isSelectLifeBtnHealth.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectLifeBtnAll.value=false
    }
    //생활복지-문화 버튼을 반전시켜주는 메소드
    fun changeLifeBtnCulture(){
        _isSelectLifeBtnCulture.value= _isSelectLifeBtnCulture.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectLifeBtnAll.value=false
    }


    //코로나19 지원 '전체' 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnAll(){
        //학생 지원 전체 버튼 반전 시키는 라인
        _isSelectCoronaBtnAll.value=_isSelectCoronaBtnAll.value!=true

        //전체를 제외한 나머지 버튼을 false로 변경
        _isSelectCoronaBtnBasicIncome.value=false
        _isSelectCoronaBtnLowIncome.value=false
        _isSelectCoronaBtnDisasterDamage.value=false
        _isSelectCoronaBtnJobPreservation.value=false
        _isSelectCoronaBtnIncentive.value=false
        _isSelectCoronaBtnPsychologicalSupport.value=false
    }
    //코로나19-기본소득지원 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnBasicIncome(){
        _isSelectCoronaBtnBasicIncome.value= _isSelectCoronaBtnBasicIncome.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectCoronaBtnAll.value=false
    }
    //코로나19-저소득층지원 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnLowIncome(){
        _isSelectCoronaBtnLowIncome.value= _isSelectCoronaBtnLowIncome.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectCoronaBtnAll.value=false
    }
    //코로나19-재난피해지원 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnDisasterDamage(){
        _isSelectCoronaBtnDisasterDamage.value= _isSelectCoronaBtnDisasterDamage.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectCoronaBtnAll.value=false
    }
    //코로나19-소득일자리보전 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnJobPreservation(){
        _isSelectCoronaBtnJobPreservation.value= _isSelectCoronaBtnJobPreservation.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectCoronaBtnAll.value=false
    }
    //코로나19-기타인센티브 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnIncentive(){
        _isSelectCoronaBtnIncentive.value= _isSelectCoronaBtnIncentive.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectCoronaBtnAll.value=false
    }
    //코로나19-심리지원 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnPsychologicalSupport(){
        _isSelectCoronaBtnPsychologicalSupport.value= _isSelectCoronaBtnPsychologicalSupport.value!=true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectCoronaBtnAll.value=false
    }
}