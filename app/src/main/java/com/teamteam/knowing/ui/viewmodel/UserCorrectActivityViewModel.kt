package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.network.request.UserCorrectRequest
import com.teamteam.knowing.data.model.network.response.UserCorrectResponse
import com.teamteam.knowing.data.model.network.response.UserWithdrawalResponse
import com.teamteam.knowing.data.remote.api.SignUpInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserCorrectActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val mContext = application.applicationContext
    private val _currentEdtTextBirth =
        MutableLiveData<String>() //datePicker에서 날짜를 선택하면 해당 날짜를 저장하는 라이브데이터
    private val _currentBtnMen = MutableLiveData<Boolean>() // 성별 선택 시 남자를 선택했는지 확인하는 라이브 데이터
    private val _currentBtnWomen = MutableLiveData<Boolean>()// 성별 선택 시 여자를 선택했는지 확인하는 라이브 데이터
    private val _nameIsCorrect = MutableLiveData<Boolean>() //입력한 이름을 저장하는 라이브 데이터
    private val _emailIsCorrect = MutableLiveData<Boolean>()  //입력한 email이 정상인지 체크하는 라이브 데이터
    private val _pwdCheckIsCorrect =
        MutableLiveData<Boolean>() //입력한 비밀번호 확인이 비밀번호와 일치한지 상태를 저장하는 라이브 데이터
    private val _allIsCorrect =
        MutableLiveData<Boolean>() //이메일, 성별선택여부, 이름, 날짜선택여부 형식이 정상적인지 아닌지 판단해 회원가입하기 버튼을 활성화 시키는 라이브 데이터
    private val _isCorrectEdtPhoneNum =
        MutableLiveData<Boolean>() //전화번호를 양식에 맞게 잘 입력했는지 확인하는 라이브 데이터

    //회원수정이 성공했는지 안했는지 저장하는 라이브 데이터
    private val _isSuccessApi = MutableLiveData<Boolean>()

    //회원수정이 성공했는지 안했는지 저장하는 라이브 데이터
    private val _isSuccessWithdrawal = MutableLiveData<Boolean>()


    val isSuccessApi: MutableLiveData<Boolean>
        get() = _isSuccessApi

    val isSuccessWithdrawal: MutableLiveData<Boolean>
        get() = _isSuccessWithdrawal

    val currentEdtTextBirth: MutableLiveData<String>
        get() = _currentEdtTextBirth

    val currentBtnMen: MutableLiveData<Boolean>
        get() = _currentBtnMen

    val currentBtnWomen: MutableLiveData<Boolean>
        get() = _currentBtnWomen

    val nameIsCorrect: MutableLiveData<Boolean>
        get() = _nameIsCorrect

    val emailIsCorrect: MutableLiveData<Boolean>
        get() = _emailIsCorrect


    val allIsCorrect: MutableLiveData<Boolean>
        get() = _allIsCorrect

    val isCorrectEdtPhoneNum: MutableLiveData<Boolean>
        get() = _isCorrectEdtPhoneNum


    init {

    }

    /*
    성별에서 남성 버튼의 선택 여부를 반전 시켜주는 함수
     */
    fun changeValueMen() {
        _currentBtnMen.postValue(true)
        _currentBtnWomen.postValue(false)
    }

    /*
    성별에서 여성 버튼의 선택 여부를 반전 시켜주는 함수
     */
    fun changeValueWomen() {
        _currentBtnWomen.postValue(true)
        _currentBtnMen.postValue(false)
    }

    /*
    회원가입 버튼을 활성화 하기 위한 모든 조건들이 만족하는지 확인하는 메소드
     */
    fun checkAllIsCorrect() {
        //println("이메일 ${_emailIsCorrect.value} / 패스워드 ${_pwdIsCorrect.value}  / 이름 ${_nameIsCorrect.value}  / 전화번호 ${_isCorrectEdtPhoneNum.value}성별  ${isCheckGender()} / 생년월일  ${isInputBirth()}")

        //모든 조건들이 참인 경우 회원가입 버튼의 enabled를 담당하는 라이브 데이터를 true로 바꿔준다.
        if (_emailIsCorrect.value == true && _nameIsCorrect.value == true && isCheckGender() && isInputBirth() && _isCorrectEdtPhoneNum.value == true) {
            allIsCorrect.value = true
        } else
            allIsCorrect.value = false
    }

    /*
    gender가 하나라도 선택이 되어있는지 확인 후 boolean형 반환환
     */
    fun isCheckGender(): Boolean {
        return _currentBtnWomen.value == true || _currentBtnMen.value == true
    }

    /*
    생년월일 Edittext가 비어있지 않으면 생년월일을 입력 했다는 얘기이므로 true 반환
     */
    fun isInputBirth(): Boolean {
        return _currentEdtTextBirth.value.toString().isNotEmpty()
    }

    /*
    성별 중 남성이 선택되어져 있는지 여성이 선택되어져 있는지 결과값 불러오는 메소드
     */
    fun getGenderValue(): String {
        //남성이 선택되어져 있으면 true 아니면 false 반환
        if (_currentBtnMen.value == true)
            return "남성"
        else
            return "여성"
    }


    /*
    회원 정보 수정하는 api 호출 메소드
     */
    fun tryPatchUserCorrect(userUid: String, param: UserCorrectRequest){
        val signUpInterface = ApplicationClass.sRetrofit.create(SignUpInterface::class.java)
        signUpInterface.postUserCorrect(userUid, param)
            .enqueue(object : Callback<UserCorrectResponse> {
                override fun onResponse(call: Call<UserCorrectResponse>, response: Response<UserCorrectResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body() as UserCorrectResponse
                        if (result.userCorrectResult.status == "유저정보수정완료") {
                            _isSuccessApi.value=true
                        }
                    } else {
                        Log.e("ERROR", "유저정보 수정 실패")
                    }
                }

                override fun onFailure(call: Call<UserCorrectResponse>, t: Throwable) {
                    Log.e("ERROR", "회원 정보 수정 통신 오류")
                }
            })
    }


    /*
    탈퇴 하는 api 호출 메소드
     */
    fun tryDeleteWithdrawal(uid:String){
        val signUpInterface = ApplicationClass.sRetrofit.create(SignUpInterface::class.java)
        signUpInterface.deleteWithdrawal(uid).enqueue(object : Callback<UserWithdrawalResponse> {
                override fun onResponse(call: Call<UserWithdrawalResponse>, response: Response<UserWithdrawalResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body() as UserWithdrawalResponse
                        if (result.userWithdrawalResult.result == "유저탈퇴완료") {
                            _isSuccessWithdrawal.value=true
                        }
                    } else {
                        Log.e("ERROR", "회원탈퇴 실패")
                    }
                }

                override fun onFailure(call: Call<UserWithdrawalResponse>, t: Throwable) {
                    Log.e("ERROR", "회원 탈퇴 통신 오류")
                }
            })
    }
}