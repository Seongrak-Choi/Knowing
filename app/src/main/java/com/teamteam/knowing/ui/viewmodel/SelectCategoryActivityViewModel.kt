package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.domain.SignUpUser
import com.teamteam.knowing.data.model.network.response.SignUpResponse
import com.teamteam.knowing.data.remote.api.SignUpInterface
import com.teamteam.knowing.ui.view.main.LoadingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectCategoryActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val myContext = application.applicationContext

    //학생 지원
    private val _isSelectStudentBtnAll = MutableLiveData<Boolean>()
    private val _isSelectStudentBtnInSchoolTuition = MutableLiveData<Boolean>()
    private val _isSelectStudentBtnOutSchoolTuition = MutableLiveData<Boolean>()

    //취업 지원
    private val _isSelectJobBtnAll = MutableLiveData<Boolean>()
    private val _isSelectJobBtnJobSearch = MutableLiveData<Boolean>()
    private val _isSelectJobBtnSmallBusiness = MutableLiveData<Boolean>()
    private val _isSelectJobBtnSpecialField = MutableLiveData<Boolean>()
    private val _isSelectJobBtnOverSeas = MutableLiveData<Boolean>()

    //창업 지원
    private val _isSelectFoundationBtnAll = MutableLiveData<Boolean>()
    private val _isSelectFoundationBtnFoundationOperate = MutableLiveData<Boolean>()
    private val _isSelectFoundationBtnManagement = MutableLiveData<Boolean>()
    private val _isSelectFoundationBtnCapital = MutableLiveData<Boolean>()

    //주거 금융지원
    private val _isSelectDwellingBtnAll = MutableLiveData<Boolean>()
    private val _isSelectDwellingBtnLivingExpense = MutableLiveData<Boolean>()
    private val _isSelectDwellingBtnDwelling = MutableLiveData<Boolean>()
    private val _isSelectDwellingBtnSchoolExpense = MutableLiveData<Boolean>()

    //생활복 지원
    private val _isSelectLifeBtnAll = MutableLiveData<Boolean>()
    private val _isSelectLifeBtnHealth = MutableLiveData<Boolean>()
    private val _isSelectLifeBtnCulture = MutableLiveData<Boolean>()

    //코로나19 지원
    private val _isSelectCoronaBtnAll = MutableLiveData<Boolean>()
    private val _isSelectCoronaBtnBasicIncome = MutableLiveData<Boolean>()
    private val _isSelectCoronaBtnLowIncome = MutableLiveData<Boolean>()
    private val _isSelectCoronaBtnDisasterDamage = MutableLiveData<Boolean>()
    private val _isSelectCoronaBtnJobPreservation = MutableLiveData<Boolean>()
    private val _isSelectCoronaBtnIncentive = MutableLiveData<Boolean>()
    private val _isSelectCoronaBtnPsychologicalSupport = MutableLiveData<Boolean>()

    //적용하기 버튼
    private val _isCorrectBtnNext = MutableLiveData<Boolean>()

    //fireabseAuth 변수
    private lateinit var firebaseAuth: FirebaseAuth


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

    val isCorrectBtnNext: MutableLiveData<Boolean>
        get() = _isCorrectBtnNext

    init {
        _isCorrectBtnNext.value = false
    }


    //학생 지원의 '전체' 버튼을 반전시켜주는 메소드
    fun changeStudentBtnAll() {
        //학생 지원 전체 버튼 반전 시키는 라인
        _isSelectStudentBtnAll.value = _isSelectStudentBtnAll.value != true

        //전체를 제외한 나머지 버튼을 false로 변경
        _isSelectStudentBtnInSchoolTuition.value = false
        _isSelectStudentBtnOutSchoolTuition.value = false
    }

    //학생 지원의 '교내 장학금' 버튼을 반전시켜주는 메소드
    fun changeStudentBtnInSchoolTuition() {
        _isSelectStudentBtnInSchoolTuition.value = _isSelectStudentBtnInSchoolTuition.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        isSelectStudentBtnAll.value = false
    }

    //학생 지원의 '교외 장학금' 버튼을 반전시켜주는 메소드
    fun changeStudentBtnOutSchoolTuition() {
        _isSelectStudentBtnOutSchoolTuition.value =
            _isSelectStudentBtnOutSchoolTuition.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        isSelectStudentBtnAll.value = false
    }


    //취업 지원의 '전체' 버튼을 반전시켜주는 메소드
    fun changeJobBtnAll() {
        //학생 지원 전체 버튼 반전 시키는 라인
        _isSelectJobBtnAll.value = _isSelectJobBtnAll.value != true

        //전체를 제외한 나머지 버튼을 false로 변경
        _isSelectJobBtnJobSearch.value = false
        _isSelectJobBtnSmallBusiness.value = false
        _isSelectJobBtnSpecialField.value = false
        _isSelectJobBtnOverSeas.value = false
    }

    //취업 지원-구직활동지원인턴 버튼을 반전시켜주는 메소드
    fun changeJobBtnJobSearch() {
        _isSelectJobBtnJobSearch.value = _isSelectJobBtnJobSearch.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectJobBtnAll.value = false
    }

    //취업 지원-중소중견기업취업지원 버튼을 반전시켜주는 메소드
    fun changeJobBtnJobSmallBusiness() {
        _isSelectJobBtnSmallBusiness.value = _isSelectJobBtnSmallBusiness.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectJobBtnAll.value = false
    }

    //취업 지원-특수분야취업지원 버튼을 반전시켜주는 메소드
    fun changeJobBtnJobSpecialField() {
        _isSelectJobBtnSpecialField.value = _isSelectJobBtnSpecialField.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectJobBtnAll.value = false
    }

    //취업 지원-해외취업 및 진출 지원 버튼을 반전시켜주는 메소드
    fun changeJobBtnJobOverSeas() {
        _isSelectJobBtnOverSeas.value = _isSelectJobBtnOverSeas.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectJobBtnAll.value = false
    }


    //창업 지원의 '전체' 버튼을 반전시켜주는 메소드
    fun changeFoundationBtnAll() {
        //학생 지원 전체 버튼 반전 시키는 라인
        _isSelectFoundationBtnAll.value = _isSelectFoundationBtnAll.value != true

        //전체를 제외한 나머지 버튼을 false로 변경
        _isSelectFoundationBtnFoundationOperate.value = false
        _isSelectFoundationBtnManagement.value = false
        _isSelectFoundationBtnCapital.value = false
    }

    //창업지원-창업운영지원 및 진출 지원 버튼을 반전시켜주는 메소드
    fun changeFoundationBtnFoundationOperate() {
        _isSelectFoundationBtnFoundationOperate.value =
            _isSelectFoundationBtnFoundationOperate.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectFoundationBtnAll.value = false
    }

    //창업지원-경영지원 버튼을 반전시켜주는 메소드
    fun changeFoundationBtnManagement() {
        _isSelectFoundationBtnManagement.value = _isSelectFoundationBtnManagement.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectFoundationBtnAll.value = false
    }

    //창업지원-자본금지원 버튼을 반전시켜주는 메소드
    fun changeFoundationBtnCapital() {
        _isSelectFoundationBtnCapital.value = _isSelectFoundationBtnCapital.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectFoundationBtnAll.value = false
    }


    //주거 금융 지원의 '전체' 버튼을 반전시켜주는 메소드
    fun changeDwellingBtnAll() {
        //학생 지원 전체 버튼 반전 시키는 라인
        _isSelectDwellingBtnAll.value = _isSelectDwellingBtnAll.value != true

        //전체를 제외한 나머지 버튼을 false로 변경
        _isSelectDwellingBtnLivingExpense.value = false
        _isSelectDwellingBtnDwelling.value = false
        _isSelectDwellingBtnSchoolExpense.value = false
    }

    //주거금융-생활비지원 버튼을 반전시켜주는 메소드
    fun changeDwellingBtnLivingExpense() {
        _isSelectDwellingBtnLivingExpense.value = _isSelectDwellingBtnLivingExpense.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectDwellingBtnAll.value = false
    }

    //주거금융-주거지원 버튼을 반전시켜주는 메소드
    fun changeDwellingBtnDwelling() {
        _isSelectDwellingBtnDwelling.value = _isSelectDwellingBtnDwelling.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectDwellingBtnAll.value = false
    }

    //주거금융-학자금지원 버튼을 반전시켜주는 메소드
    fun changeDwellingBtnSchoolExpense() {
        _isSelectDwellingBtnSchoolExpense.value = _isSelectDwellingBtnSchoolExpense.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectDwellingBtnAll.value = false
    }


    //생활복지 지원의 '전체' 버튼을 반전시켜주는 메소드
    fun changeLifeBtnAll() {
        //학생 지원 전체 버튼 반전 시키는 라인
        _isSelectLifeBtnAll.value = _isSelectLifeBtnAll.value != true

        //전체를 제외한 나머지 버튼을 false로 변경
        _isSelectLifeBtnHealth.value = false
        _isSelectLifeBtnCulture.value = false
    }

    //생활복지-건강 버튼을 반전시켜주는 메소드
    fun changeLifeBtnHealth() {
        _isSelectLifeBtnHealth.value = _isSelectLifeBtnHealth.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectLifeBtnAll.value = false
    }

    //생활복지-문화 버튼을 반전시켜주는 메소드
    fun changeLifeBtnCulture() {
        _isSelectLifeBtnCulture.value = _isSelectLifeBtnCulture.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectLifeBtnAll.value = false
    }


    //코로나19 지원 '전체' 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnAll() {
        //학생 지원 전체 버튼 반전 시키는 라인
        _isSelectCoronaBtnAll.value = _isSelectCoronaBtnAll.value != true

        //전체를 제외한 나머지 버튼을 false로 변경
        _isSelectCoronaBtnBasicIncome.value = false
        _isSelectCoronaBtnLowIncome.value = false
        _isSelectCoronaBtnDisasterDamage.value = false
        _isSelectCoronaBtnJobPreservation.value = false
        _isSelectCoronaBtnIncentive.value = false
        _isSelectCoronaBtnPsychologicalSupport.value = false
    }

    //코로나19-기본소득지원 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnBasicIncome() {
        _isSelectCoronaBtnBasicIncome.value = _isSelectCoronaBtnBasicIncome.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectCoronaBtnAll.value = false
    }

    //코로나19-저소득층지원 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnLowIncome() {
        _isSelectCoronaBtnLowIncome.value = _isSelectCoronaBtnLowIncome.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectCoronaBtnAll.value = false
    }

    //코로나19-재난피해지원 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnDisasterDamage() {
        _isSelectCoronaBtnDisasterDamage.value = _isSelectCoronaBtnDisasterDamage.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectCoronaBtnAll.value = false
    }

    //코로나19-소득일자리보전 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnJobPreservation() {
        _isSelectCoronaBtnJobPreservation.value = _isSelectCoronaBtnJobPreservation.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectCoronaBtnAll.value = false
    }

    //코로나19-기타인센티브 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnIncentive() {
        _isSelectCoronaBtnIncentive.value = _isSelectCoronaBtnIncentive.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectCoronaBtnAll.value = false
    }

    //코로나19-심리지원 버튼을 반전시켜주는 메소드
    fun changeCoronaBtnPsychologicalSupport() {
        _isSelectCoronaBtnPsychologicalSupport.value =
            _isSelectCoronaBtnPsychologicalSupport.value != true

        //학생지원-전체 버튼의 체크 상태를 false로 변경
        _isSelectCoronaBtnAll.value = false
    }


    /*
    모든 카테고리 중 하나의 버튼이라도 선택이 되면 적용하기 버튼이 활성화 되도록 설정
     */
    fun checkIsCorrectBtnNext() {
        if (_isSelectStudentBtnAll.value == true || _isSelectStudentBtnInSchoolTuition.value == true || _isSelectStudentBtnOutSchoolTuition.value == true ||
            _isSelectJobBtnAll.value == true || _isSelectJobBtnJobSearch.value == true || _isSelectJobBtnOverSeas.value == true || _isSelectJobBtnSmallBusiness.value == true ||
            _isSelectJobBtnSpecialField.value == true || _isSelectFoundationBtnAll.value == true || _isSelectFoundationBtnCapital.value == true ||
            _isSelectFoundationBtnManagement.value == true || _isSelectFoundationBtnFoundationOperate.value == true || _isSelectDwellingBtnAll.value == true ||
            _isSelectDwellingBtnDwelling.value == true || _isSelectDwellingBtnLivingExpense.value == true || _isSelectDwellingBtnSchoolExpense.value == true ||
            _isSelectLifeBtnAll.value == true || _isSelectLifeBtnCulture.value == true || _isSelectLifeBtnHealth.value == true || _isSelectCoronaBtnAll.value == true ||
            _isSelectCoronaBtnBasicIncome.value == true || _isSelectCoronaBtnDisasterDamage.value == true || _isSelectCoronaBtnIncentive.value == true ||
            _isSelectCoronaBtnJobPreservation.value == true || _isSelectCoronaBtnLowIncome.value == true || _isSelectCoronaBtnPsychologicalSupport.value == true
        ) {
            _isCorrectBtnNext.value = true
        } else
            _isCorrectBtnNext.value = false
    }

    /*
    학생 지원 선택된 값을 파악해 String으로 공백을 기준으로 반환해 주는 메소드
     */
    fun getStudentCategory(): ArrayList<String> {
        var check_value = ArrayList<String>() //선택된 값들을 ArrayList<string>으로 저장하기 위한 변수

        if (_isSelectStudentBtnAll.value == true) {
            check_value.add("교내장학금")
            check_value.add("교외장학금")
        }
        if (_isSelectStudentBtnOutSchoolTuition.value == true)
            check_value.add("교외장학금")
        if (_isSelectStudentBtnInSchoolTuition.value == true)
            check_value.add("교내장학금")

        return check_value
    }

    /*
    취업 지원 선택된 값을 파악해 String으로 공백을 기준으로 반환해 주는 메소드
     */
    fun getJobCategory(): ArrayList<String> {
        var check_value = ArrayList<String>() //선택된 값들을 ArrayList<string>으로 저장하기 위한 변수
        if (_isSelectJobBtnAll.value == true) {
            check_value.add("구직활동지원인턴")
            check_value.add("중소중견기업취업지원")
            check_value.add("특수분야취업지원")
            check_value.add("해외취업및진출지원")
        }
        if (_isSelectJobBtnSpecialField.value == true)
            check_value.add("특수분야취업지원")
        if (_isSelectJobBtnSmallBusiness.value == true)
            check_value.add("중소중견기업취업지원")
        if (_isSelectJobBtnOverSeas.value == true)
            check_value.add("해외취업및진출지원")
        if (_isSelectJobBtnJobSearch.value == true)
            check_value.add("구직활동지원인턴")

        return check_value
    }

    /*
    창업 지원 선택된 값을 파악해 String으로 공백을 기준으로 반환해 주는 메소드
     */
    fun getFoundationCategory(): ArrayList<String> {
        var check_value = ArrayList<String>() //선택된 값들을 ArrayList<string>으로 저장하기 위한 변수
        if (_isSelectFoundationBtnAll.value == true) {
            check_value.add("창업운영지원")
            check_value.add("경영지원")
            check_value.add("자본금지원")
        }
        if (_isSelectFoundationBtnFoundationOperate.value == true)
            check_value.add("경영지원")
        if (_isSelectFoundationBtnCapital.value == true)
            check_value.add("자본금지원")
        if (_isSelectFoundationBtnManagement.value == true)
            check_value.add("창업운영지원")

        return check_value
    }

    /*
    주거 금융지원 선택된 값을 파악해 String으로 공백을 기준으로 반환해 주는 메소드
     */
    fun getResidentCategory(): ArrayList<String> {
        var check_value = ArrayList<String>() //선택된 값들을 ArrayList<string>으로 저장하기 위한 변수
        if (_isSelectDwellingBtnAll.value == true) {
            check_value.add("생활비지원금융혜택")
            check_value.add("주거지원")
            check_value.add("학자금지원")
        }
        if (_isSelectDwellingBtnSchoolExpense.value == true)
            check_value.add("학자금지원")
        if (_isSelectDwellingBtnLivingExpense.value == true)
            check_value.add("생활비지원금융혜택")
        if (_isSelectDwellingBtnDwelling.value == true)
            check_value.add("주거지원")

        return check_value
    }

    /*
    생활복지지원 선택된 값을 파악해 String으로 공백을 기준으로 반환해 주는 메소드
     */
    fun getLifeCategory(): ArrayList<String> {
        var check_value = ArrayList<String>() //선택된 값들을 ArrayList<string>으로 저장하기 위한 변수
        if (_isSelectLifeBtnAll.value == true) {
            check_value.add("건강")
            check_value.add("문화")
        }
        if (_isSelectLifeBtnHealth.value == true)
            check_value.add("건강")
        if (_isSelectLifeBtnCulture.value == true)
            check_value.add("문화")

        return check_value
    }

    /*
    코로나19 지원 선택된 값을 파악해 String으로 공백을 기준으로 반환해 주는 메소드
     */
    fun getCovidCategory(): ArrayList<String> {
        var check_value = ArrayList<String>() //선택된 값들을 ArrayList<string>으로 저장하기 위한 변수
        if (_isSelectCoronaBtnAll.value == true) {
            check_value.add("기본소득지원")
            check_value.add("저소득층지원")
            check_value.add("재난피해지원")
            check_value.add("소득일자리보전")
            check_value.add("기타인센티브")
            check_value.add("심리지원")
        }
        if (_isSelectCoronaBtnPsychologicalSupport.value == true)
            check_value.add("심리지원")
        if (_isSelectCoronaBtnLowIncome.value == true)
            check_value.add("저소득층지원")
        if (_isSelectCoronaBtnJobPreservation.value == true)
            check_value.add("소득일자리보전")
        if (_isSelectCoronaBtnIncentive.value == true)
            check_value.add("기타인센티브")
        if (_isSelectCoronaBtnDisasterDamage.value == true)
            check_value.add("재난피해지원")
        if (_isSelectCoronaBtnBasicIncome.value == true)
            check_value.add("기본소득지원")

        return check_value
    }


    /*
    이메일로 회원가입한 경우 firebase에 계정 등록하는 메소드
    */
    fun postEmailSignUpToFirebase(user_data: SignUpUser) {
        //firebaseAuth 인스턴스 생성
        firebaseAuth = Firebase.auth

        //firebase에 신규유저 등록
        firebaseAuth.createUserWithEmailAndPassword(user_data.email, user_data.pwd)
            .addOnCompleteListener {
                if (it.isSuccessful) {//유저등록이 성공하면 uid를 넣어서 회원가입 API를 호출한다.
                    println("SelectCategoryActivityViewModel firebase 신규유저 등록 sucees일 때 it 확인:${it.isSuccessful}")
                    tryPostSignUp(firebaseAuth.currentUser!!.uid, user_data)
                } else {
                    println("SelectCategoryActivityViewModel firebase 신규유저 등록 sucess아닐 때 it 확인:${it.isSuccessful}")
                    Log.e("ERROR","firebase에 계정 등록 실패")
                    Toast.makeText(myContext, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }


    /*
    회원가입 API호출 메소드
     */
    fun tryPostSignUp(uid: String, user_data: SignUpUser) {


        val signUpInterface = ApplicationClass.sRetrofit.create(SignUpInterface::class.java)
        signUpInterface.postSignUp(uid, user_data).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful) { //response가 성공적일 때
                    val result = response.body() as SignUpResponse
                    println("회원가입 성공 : ${result.isSuccess}")

                    //회원가입에 성공한 uid를 기기에 저장
                    val editor = ApplicationClass.sp.edit()
                    editor.putString(ApplicationClass.UID_KEY,result.signUpResult.uid)
                    editor.apply()

                    //회원가입에 성공하였으면 LoadingActivity로 이동한다.
                    var intent = Intent(myContext, LoadingActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                    myContext.startActivity(intent)

                } else {//response가 실패했을 때
                    val result = response.body() as SignUpResponse
                    Log.e("ERROR","서버 회원가입 API 실패")
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.e("ERROR", "회원가입 통신 실패")
            }
        })
    }
}