package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.network.request.UserAddCorrectRequest
import com.teamteam.knowing.data.model.network.response.CollegeResponseModel
import com.teamteam.knowing.data.model.network.response.UserCorrectResponse
import com.teamteam.knowing.data.model.network.response.UserInfoResult
import com.teamteam.knowing.data.remote.api.CollegeInterface
import com.teamteam.knowing.data.remote.api.SignUpInterface
import com.teamteam.knowing.data.repository.DialogSelectDoRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.sign

class MoreInformationCorrectActivityViewModel(application: Application):AndroidViewModel(application) {

    private val _ifSuccessCorrectApi = MutableLiveData<Boolean>()

    val ifSuccessCorrectApi: MutableLiveData<Boolean>
        get() = _ifSuccessCorrectApi

    /*
    MoreInformation1Activity 관련 데이터들
     */
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

//    //액티비티의 관련된 라이브 데이터
//    private val _isAllCorrect = MutableLiveData<Boolean>() // 거주지와, 특별사항이 모든 선택 되어져 있는지 여부를 저장하는 라이브 데이터


    val isSelectDo: MutableLiveData<Boolean>
        get() = _isSelectDo

    val isSelectSi: MutableLiveData<Boolean>
        get() = _isSelectSi

    val doList: MutableLiveData<ArrayList<String>>
        get() = _doList

    val siList: MutableLiveData<ArrayList<String>>
        get() = _siList


    val currentTxDo: MutableLiveData<String>
        get() = _currentTxDo

    val currentTxSi: MutableLiveData<String>
        get() = _currentTxSi

    val currentCheckState: MutableLiveData<Boolean>
        get() = _currentCheckState

    val currentBtnSmallBusiness: MutableLiveData<Boolean>
        get() = _currentBtnSmallBusiness

    val currentBtnLowIncome: MutableLiveData<Boolean>
        get() = _currentBtnLowIncome

    val currentBtnDisabled: MutableLiveData<Boolean>
        get() = _currentBtnDisabled

    val currentBtnFarmer: MutableLiveData<Boolean>
        get() = _currentBtnFarmer

    val currentBtnSolider: MutableLiveData<Boolean>
        get() = _currentBtnSolider

    val currentBtnLocalTalent: MutableLiveData<Boolean>
        get() = _currentBtnLocalTalent

    val isSpecialDialogCorrect: MutableLiveData<Boolean>
        get() = _isSpecialDialogCorrect

//    val isAllCorrect: MutableLiveData<Boolean>
//        get() = _isAllCorrect

    val checkedBtnInfo: MutableLiveData<String>
        get() = _checkedBtnInfo


    init {
        _isSpecialDialogCorrect.value=false
        _currentCheckState.value = false
        _isSelectDo.value=false
        getDoList() //DummyDataClass에서 시/도 리스트를 받아오기위해 메소드 호출
    }


    /*
    시/도 리스트를 DummyDataClass에서 받아오는 메소드
     */
    fun getDoList(){
        _doList.value= DialogSelectDoRepository().getDoList()
    }

    /*
    시/군/구 리스트를 DummyDataClass에서 받아오기 위한 메소드
    시/도의 값을 비교해 일치하는 시/군/구 리스트를 받아서 _siList에 저장한다.
     */
    fun getSiList(){
        if(_currentTxDo.value=="강원도")
            _siList.value= DialogSelectDoRepository().getGangwonSiList()
        else if (_currentTxDo.value=="경기도")
            _siList.value= DialogSelectDoRepository().getGyeonggiSiList()
        else if (_currentTxDo.value=="경상남도")
            _siList.value= DialogSelectDoRepository().getGyeongNamSiList()
        else if (_currentTxDo.value=="경상북도")
            _siList.value= DialogSelectDoRepository().getGyeongBukSiList()
        else if (_currentTxDo.value=="광주광역시")
            _siList.value= DialogSelectDoRepository().getGwangjuGuList()
        else if (_currentTxDo.value=="대구광역시")
            _siList.value= DialogSelectDoRepository().getDaeGuList()
        else if (_currentTxDo.value=="대전광역시")
            _siList.value= DialogSelectDoRepository().getDeajeonGuList()
        else if (_currentTxDo.value=="부산광역시")
            _siList.value= DialogSelectDoRepository().getBusanGuList()
        else if (_currentTxDo.value=="서울특별시")
            _siList.value= DialogSelectDoRepository().getSeoulGuList()
        else if (_currentTxDo.value=="세종특별자치시")
            _siList.value= DialogSelectDoRepository().getSejongSiList()
        else if (_currentTxDo.value=="울산광역시")
            _siList.value= DialogSelectDoRepository().getUlsanGuList()
        else if (_currentTxDo.value=="인천광역시")
            _siList.value= DialogSelectDoRepository().getIncheonGuList()
        else if (_currentTxDo.value=="전라남도")
            _siList.value= DialogSelectDoRepository().getJeonNamSiList()
        else if (_currentTxDo.value=="전라북도")
            _siList.value= DialogSelectDoRepository().getJeonBukSiList()
        else if (_currentTxDo.value=="제주특별자치도")
            _siList.value= DialogSelectDoRepository().getJejuSiList()
        else if (_currentTxDo.value=="충청남도")
            _siList.value= DialogSelectDoRepository().getChungNamSiList()
        else if (_currentTxDo.value=="충청북도")
            _siList.value= DialogSelectDoRepository().getChungbukSiList()
        else
            _siList.value= DialogSelectDoRepository().getDoList()
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

//    //거주지와 특별사항이 입력 되었는지 확인하기 위해 거주지, 특별사항이 선택이 될 때마다. 선택 상태를 확인하는 메소드
//    fun checkIsAllCorrect(){
//        //액티비티의 다음 버튼의 활성화를 위해 시/군/구 다이얼로그와 특별사항 다이얼로그의 상태를 확인하는 라이브데이터들을 확인하고 다음 버턴의 상태를 결정
//        _isAllCorrect.value = _isSelectSi.value==true && _isSpecialDialogCorrect.value==true
//    }

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




    /*
    MoreInformation2ActivityViewModel관련 데이터
     */
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
    //처음 셋팅을 위한 라이브 데이터
    private val _initIncomeValue = MutableLiveData<String>()

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
    val initIncomeValue : MutableLiveData<String>
        get() = _initIncomeValue

    val isCorrect : MutableLiveData<Boolean>
       get() = _isCorrect




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
        if (_isSelectEdtIncome.value==true){
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





    /*
    MoreInformation3Activity
     */
    //학적사항 버튼 관련 라이브 데이터
    private val _isSelectSchoolRecordsBtnAll = MutableLiveData<Boolean>()
    private val _isSelectBtnUnderHighSchool = MutableLiveData<Boolean>()
    private val _isSelectBtnGraduateHighSchool = MutableLiveData<Boolean>()
    private val _isSelectBtnBeingCollege = MutableLiveData<Boolean>()
    private val _isSelectBtnGraduateCollege = MutableLiveData<Boolean>()
    private val _isSelectBtnDoctor = MutableLiveData<Boolean>()



    //대학교 검색 리사이클러뷰를 위한 라이브 데이터
    private val _currentCollegeNameList = MutableLiveData<ArrayList<String>>()
    private val _currentSelectTxCollegeName = MutableLiveData<String>()

    val isSelectSchoolRecordsBtnAll: MutableLiveData<Boolean>
        get() = _isSelectSchoolRecordsBtnAll
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
    val currentCollegeNameList: MutableLiveData<ArrayList<String>>
        get() = _currentCollegeNameList

    val currentSelectTxCollegeName: MutableLiveData<String>
        get() = _currentSelectTxCollegeName






    //학적사항 전체 버튼 클릭 시
    fun changeSchoolRecordsBtnAll() {
        allBtnValueFalse()
        _isSelectSchoolRecordsBtnAll.value = true
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
        _isSelectSchoolRecordsBtnAll.value = false
        _isSelectBtnUnderHighSchool.value = false
        _isSelectBtnGraduateHighSchool.value = false
        _isSelectBtnBeingCollege.value = false
        _isSelectBtnGraduateCollege.value=false
        _isSelectBtnDoctor.value = false
    }



    /*
    학력사항 버튼 중 선택된 값들을 string 형식으로 공백으로 나눠서 반환하는 메소드
     */
    fun getSchoolRecords():String{
        if (_isSelectSchoolRecordsBtnAll.value==true)
            return "전체"
        else if (_isSelectBtnUnderHighSchool.value==true)
            return "고졸미만"
        else if (_isSelectBtnGraduateHighSchool.value==true)
            return "고교졸업"
        else if (_isSelectBtnBeingCollege.value==true)
            return "대학재학"
        else if (_isSelectBtnGraduateCollege.value==true)
            return "대학졸업"
        else
            return "석박사"
    }



    /*
    대학교 검색을 위한 retrofit통신
     */
    fun tryGetCollege(apiKey:String,svcType:String,svcCode:String,gubun:String,searchSchulNm:String,contentType:String){
        //대학교 이름만 저장할 ArrayList<String>
        var name_list = ArrayList<String>()

        //CollegeInterface를 retrofit을 이용해 인스턴스화 시킴
        val collegeInterface = ApplicationClass.collegeRetrofit.create(CollegeInterface::class.java)
        collegeInterface.getCollegeInfo(apiKey, svcType, svcCode, gubun, searchSchulNm, contentType).enqueue(object:
            Callback<CollegeResponseModel> {
            override fun onResponse(
                call: Call<CollegeResponseModel>,
                response: Response<CollegeResponseModel>
            ) {
                if (response.isSuccessful){
                    val result = response.body() as CollegeResponseModel
                    val collegeInfoList=result.dataSearch.collegeDataSearch

                    //받아온 대학교 개수 만큼 for문을 돈다
                    for (i in collegeInfoList){
                        name_list.add(i.schoolName) //받아온 대학교 중 학교 이름만 저장한다.
                    }
                    _currentCollegeNameList.value=name_list //이름만 저장한 리스트를 라이브데이터에 넣어준다.
                }
            }
            override fun onFailure(call: Call<CollegeResponseModel>, t: Throwable) {
                Toast.makeText(mContext.applicationContext,"대학교 검색 통신오류", Toast.LENGTH_SHORT).show()
            }

        })
    }





    /*
    MoreInformationActivity4
     */
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

    /*
    전공 계열 선택 데이터를 반환해주는 메소드
     */
    fun getMainMajor():String{
        if (_isSelectHumanities.value==true)
            return "인문"
        if (_isSelectSociety.value==true)
            return "사회"
        if (_isSelectLaw.value==true)
            return "법"
        if (_isSelectManagement.value==true)
            return "경영"
        if (_isSelectEducation.value==true)
            return "교육"
        if (_isSelectEngineering.value==true)
            return "공학"
        if (_isSelectNature.value==true)
            return "자연"
        if (_isSelectEntertainment.value==true)
            return "예체능"
        if (_isSelectMedical.value==true)
            return "의약"
        if (_isSelectEtc.value==true)
            return "기타"
        return ""
    }



    /*
    MoreInformation5Activity
     */
    private val _currentTxGrade = MutableLiveData<String>() //피커 선택시 선택된 학년을 데이터를 저장할 라이브 데이터
    private val _currentTxSemester = MutableLiveData<String>() //피커 선택시 선택된 학기를 데이터를 저장할 라이브 데이터
    private val _currentAddCheckState = MutableLiveData<Boolean>() //추가 학기 / 졸업 유예 체크 상태를 확인할 라이브 데이터
    private val _isSelectLow = MutableLiveData<Boolean>() //~2.9 버튼 체크 상태를 확인할 라이브 데이터
    private val _isSelectMedium = MutableLiveData<Boolean>() //3.0~3.4 버튼 체크 상태를 확인할 라이브 데이터
    private val _isSelectHigh = MutableLiveData<Boolean>() // 3.5~3.9 버튼 체크 상태를 확인할 라이브 데이터
    private val _isSelectNone = MutableLiveData<Boolean>() // 해당 없음 버튼 체크 상태를 확인할 라이브 데이터


    val currentTxGrade: MutableLiveData<String>
        get() = _currentTxGrade

    val currentTxSemester: MutableLiveData<String>
        get() = _currentTxSemester

    val currentAddCheckState: MutableLiveData<Boolean>
        get() = _currentAddCheckState

    val isSelectLow: MutableLiveData<Boolean>
        get() = _isSelectLow

    val isSelectMedium: MutableLiveData<Boolean>
        get() = _isSelectMedium

    val isSelectHigh: MutableLiveData<Boolean>
        get() = _isSelectHigh

    val isSelectNone: MutableLiveData<Boolean>
        get() = _isSelectNone

    // 추가 학기 / 졸업 유예 체크 여부를 반전시켜주기 위한 메소드
    fun changeAddBtnBackground(){
        _currentAddCheckState.value = _currentAddCheckState.value != true
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



    /*
    기존의 유저 정보를 받아오는 api 호출 메소드
     */
    fun tryGetUserInfo(uid:String){
        val signUpInterface = ApplicationClass.sRetrofit.create(SignUpInterface::class.java)
        signUpInterface.getUserInfo(uid).enqueue(object:Callback<UserInfoResult>{
            override fun onResponse(call: Call<UserInfoResult>, response: Response<UserInfoResult>) {
                if (response.isSuccessful){
                    val result=response.body()!!.userInfoResult
                    _currentTxDo.value= result.address
                    _isSelectDo.value=true

                    _currentTxSi.value= result.addressDetail
                    _isSelectSi.value=true


                    //서버에서 받아온 특별사항 맞게 셋팅
                    for (i in result.specialStatus){
                        when(i){
                            "중소기업"->_currentBtnSmallBusiness.value=true
                            "저소득층"->_currentBtnLowIncome.value=true
                            "장애인"->_currentBtnDisabled.value=true
                            "농업인"->_currentBtnFarmer.value=true
                            "군인"->_currentBtnSolider.value=true
                            "지역인재"->_currentBtnLocalTalent.value=true
                            "none"->_currentCheckState.value=true
                        }
                    }
                    getSpecialStatus()
                    getCheckedBtnInfo()

                    //취업상태 버튼 셋팅
                    if (result.employmentState.size==8){
                        _isSelectBtnAll.value=true
                    }else{
                        for (i in result.employmentState){
                            when(i){
                                "미취업자"->_isSelectBtnUnEmployment.value=true
                                "재직자"->_isSelectBtnEmployment.value=true
                                "프리랜서"->_isSelectBtnFreelancer.value=true
                                "일용근로자"->_isSelectBtnDailyWorker.value=true
                                "단기근로자"->_isSelectBtnShortWorker.value=true
                                "예비창업자"->_isSelectBtnPrepEntrepreneur.value=true
                                "자영업자"->_isSelectBtnSelfOwnership.value=true
                                "영농종사자"->_isSelectBtnFarming.value=true
                            }
                        }
                    }
                    getEmployState()


                    when(result.schoolRecords){
                        "전체"->_isSelectSchoolRecordsBtnAll.value=true
                        "고졸미만"->_isSelectBtnUnderHighSchool.value=true
                        "고교졸업"->_isSelectBtnGraduateHighSchool.value=true
                        "대학재학"->_isSelectBtnBeingCollege.value=true
                        "대학졸업"->_isSelectBtnGraduateCollege.value=true
                        "석박사"->_isSelectBtnDoctor.value=true
                    }
                    getSchoolRecords()


                }
            }
            override fun onFailure(call: Call<UserInfoResult>, t: Throwable) {
            }

        })
    }



    /*
    수정하기 버튼 클릭하면 서버로 수정된 정보 보내는 api 호출 메소드
     */
    fun tryPatchUserAddCorrect(userUid:String,params:UserAddCorrectRequest){
        val signUpInterface = ApplicationClass.sRetrofit.create(SignUpInterface::class.java)
        signUpInterface.postUserAddCorrect(userUid,params).enqueue(object:Callback<UserCorrectResponse>{
            override fun onResponse(call: Call<UserCorrectResponse>, response: Response<UserCorrectResponse>) {
                if (response.isSuccessful){
                    Log.i("INFO","유저 추가 정보수정완료")
                    _ifSuccessCorrectApi.value = true
                }else{
                    Log.e("ERROR","유저 추가 정보수정실패")
                    _ifSuccessCorrectApi.value = false
                }
            }
            override fun onFailure(call: Call<UserCorrectResponse>, t: Throwable) {
                Log.e("ERROR","유저 추가 정보 수정 통실 실패")
                _ifSuccessCorrectApi.value = false
            }
        })
    }
}