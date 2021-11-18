package com.example.knowing.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.knowing.config.ApplicationClass
import com.example.knowing.data.model.network.response.CollegeResponseModel
import com.example.knowing.data.remote.api.CollegeInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreInformationActivity3ViewModel(application: Application) : AndroidViewModel(application) {
    //activity에서 넘겨받은 application 저장
    val mApplication = application

    //학적사항 버튼 관련 라이브 데이터
    private val _isSelectBtnAll = MutableLiveData<Boolean>()
    private val _isSelectBtnUnderHighSchool = MutableLiveData<Boolean>()
    private val _isSelectBtnGraduateHighSchool = MutableLiveData<Boolean>()
    private val _isSelectBtnBeingCollege = MutableLiveData<Boolean>()
    private val _isSelectBtnGraduateCollege = MutableLiveData<Boolean>()
    private val _isSelectBtnDoctor = MutableLiveData<Boolean>()

    //다음 버튼의 활성화 유무를 책임질 라이브 데이터
    private val _isCorrectBtn = MutableLiveData<Boolean>()


    //대학교 검색 리사이클러뷰를 위한 라이브 데이터
    private val _currentCollegeNameList = MutableLiveData<ArrayList<String>>()
    private val _currentSelectTxCollegeName = MutableLiveData<String>()


    val isSelectBtnAll: MutableLiveData<Boolean>
        get() = _isSelectBtnAll
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

    val isCorrectBtn: MutableLiveData<Boolean>
        get() = _isCorrectBtn

    val currentCollegeNameList: MutableLiveData<ArrayList<String>>
        get() = _currentCollegeNameList

    val currentSelectTxCollegeName: MutableLiveData<String>
        get() = _currentSelectTxCollegeName






    //전체 버튼 클릭 시
    fun changeBtnAll() {
        allBtnValueFalse()
        _isSelectBtnAll.value = true
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
        _isSelectBtnAll.value = false
        _isSelectBtnUnderHighSchool.value = false
        _isSelectBtnGraduateHighSchool.value = false
        _isSelectBtnBeingCollege.value = false
        _isSelectBtnGraduateCollege.value=false
        _isSelectBtnDoctor.value = false
    }

    //버튼들 중 하나라도 선택이 되어있다면 다음 버튼 활성화 시키기 위해 버튼 상태 체크하는 메소드
    fun checkIsCorrect(){
        if (_isSelectBtnAll.value==true||_isSelectBtnUnderHighSchool.value==true||_isSelectBtnGraduateHighSchool.value==true||
            _isSelectBtnBeingCollege.value==true||_isSelectBtnGraduateCollege.value==true||_isSelectBtnDoctor.value==true){
            _isCorrectBtn.value=true
        }else
            _isCorrectBtn.value=false
    }

    /*
    학력사항 버튼 중 선택된 값들을 string 형식으로 공백으로 나눠서 반환하는 메소드
     */
    fun getSchoolRecords():String{
        if (_isSelectBtnAll.value==true)
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
                Toast.makeText(mApplication.applicationContext,"대학교 검색 통신오류",Toast.LENGTH_SHORT).show()
            }

        })
    }
}