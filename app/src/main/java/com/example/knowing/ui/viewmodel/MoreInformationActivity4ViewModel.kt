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
    fun allPressedFalseSelfTrue(data: MutableLiveData<Boolean>){
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
        data.value=true
    }

    /*
  버튼이 중복되서 눌리지 않게 모든 버튼의 클릭 상태를  false로 바꿔주는 메소드
   */
    fun allPressedFalseSelfFalse(){
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
        if (_isSelectHumanities.value==true){//선택된 버튼을 취소할 수 있도록 이미 선택되어져 있는 상태라면...
            allPressedFalseSelfTrue(_isSelectHumanities)//자신을 제외한 나머지 버튼들만 false로 변경
        }else{//선택되어져 있지 않았다면....
            allPressedFalseSelfFalse() //자신을 포함해도 상관없으니 모든 버튼들 false로 변경
        }
        _isSelectHumanities.value=_isSelectHumanities.value != true
    }

    fun changeBtnSociety(){
        if (_isSelectSociety.value==true){//선택된 버튼을 취소할 수 있도록 이미 선택되어져 있는 상태라면...
            allPressedFalseSelfTrue(_isSelectSociety)//자신을 제외한 나머지 버튼들만 false로 변경
        }else{//선택되어져 있지 않았다면....
            allPressedFalseSelfFalse() //자신을 포함해도 상관없으니 모든 버튼들 false로 변경
        }
        _isSelectSociety.value=_isSelectSociety.value != true
    }

    fun changeBtnLaw(){
        if (_isSelectLaw.value==true){//선택된 버튼을 취소할 수 있도록 이미 선택되어져 있는 상태라면...
            allPressedFalseSelfTrue(_isSelectLaw)//자신을 제외한 나머지 버튼들만 false로 변경
        }else{//선택되어져 있지 않았다면....
            allPressedFalseSelfFalse() //자신을 포함해도 상관없으니 모든 버튼들 false로 변경
        }
        _isSelectLaw.value=_isSelectLaw.value != true
    }

    fun changeBtnManagement(){
        if (_isSelectManagement.value==true){//선택된 버튼을 취소할 수 있도록 이미 선택되어져 있는 상태라면...
            allPressedFalseSelfTrue(_isSelectManagement)//자신을 제외한 나머지 버튼들만 false로 변경
        }else{//선택되어져 있지 않았다면....
            allPressedFalseSelfFalse() //자신을 포함해도 상관없으니 모든 버튼들 false로 변경
        }
        _isSelectManagement.value=_isSelectManagement.value != true
    }

    fun changeBtnEducation(){
        if (_isSelectEducation.value==true){//선택된 버튼을 취소할 수 있도록 이미 선택되어져 있는 상태라면...
            allPressedFalseSelfTrue(_isSelectEducation)//자신을 제외한 나머지 버튼들만 false로 변경
        }else{//선택되어져 있지 않았다면....
            allPressedFalseSelfFalse() //자신을 포함해도 상관없으니 모든 버튼들 false로 변경
        }
        _isSelectEducation.value=_isSelectEducation.value != true
    }

    fun changeBtnEngineering(){
        if (_isSelectEngineering.value==true){//선택된 버튼을 취소할 수 있도록 이미 선택되어져 있는 상태라면...
            allPressedFalseSelfTrue(_isSelectEngineering)//자신을 제외한 나머지 버튼들만 false로 변경
        }else{//선택되어져 있지 않았다면....
            allPressedFalseSelfFalse() //자신을 포함해도 상관없으니 모든 버튼들 false로 변경
        }
        _isSelectEngineering.value=_isSelectEngineering.value != true
    }

    fun changeBtnNature(){
        if (_isSelectNature.value==true){//선택된 버튼을 취소할 수 있도록 이미 선택되어져 있는 상태라면...
            allPressedFalseSelfTrue(_isSelectNature)//자신을 제외한 나머지 버튼들만 false로 변경
        }else{//선택되어져 있지 않았다면....
            allPressedFalseSelfFalse() //자신을 포함해도 상관없으니 모든 버튼들 false로 변경
        }
        _isSelectNature.value=_isSelectNature.value != true
    }

    fun changeBtnEntertainment(){
        if (_isSelectEntertainment.value==true){//선택된 버튼을 취소할 수 있도록 이미 선택되어져 있는 상태라면...
            allPressedFalseSelfTrue(_isSelectEntertainment)//자신을 제외한 나머지 버튼들만 false로 변경
        }else{//선택되어져 있지 않았다면....
            allPressedFalseSelfFalse() //자신을 포함해도 상관없으니 모든 버튼들 false로 변경
        }
        _isSelectEntertainment.value=_isSelectEntertainment.value != true
    }

    fun changeBtnMedical(){
        if (_isSelectMedical.value==true){//선택된 버튼을 취소할 수 있도록 이미 선택되어져 있는 상태라면...
            allPressedFalseSelfTrue(_isSelectMedical)//자신을 제외한 나머지 버튼들만 false로 변경
        }else{//선택되어져 있지 않았다면....
            allPressedFalseSelfFalse() //자신을 포함해도 상관없으니 모든 버튼들 false로 변경
        }
        _isSelectMedical.value=_isSelectMedical.value != true
    }

    fun changeBtnEtc(){
        if (_isSelectEtc.value==true){//선택된 버튼을 취소할 수 있도록 이미 선택되어져 있는 상태라면...
            allPressedFalseSelfTrue(_isSelectEtc)//자신을 제외한 나머지 버튼들만 false로 변경
        }else{//선택되어져 있지 않았다면....
            allPressedFalseSelfFalse() //자신을 포함해도 상관없으니 모든 버튼들 false로 변경
        }
        _isSelectEtc.value=_isSelectEtc.value != true
    }
}