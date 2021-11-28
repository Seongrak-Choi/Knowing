package com.example.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.knowing.data.model.network.response.MainWelfareResponse
import com.example.knowing.data.model.network.response.WelfareInfo

class AllWelfareFragmentViewModel(application: Application):AndroidViewModel(application) {
    //API로 받아온 복지 정보
    private val _welfareInfo : MutableLiveData<MainWelfareResponse> by lazy { MutableLiveData<MainWelfareResponse>() }

    //맞춤복지 리사이클러뷰 출력할 라이브데이터
    private val _currentRcList = MutableLiveData<ArrayList<WelfareInfo>>()

    //총 복지 갯수 저장하는 라이브 데이터
    private val _currentWelfareTotal = MutableLiveData<String>()


    val currentRcList : MutableLiveData<ArrayList<WelfareInfo>>
        get() = _currentRcList

    val welfareInfo : MutableLiveData<MainWelfareResponse>
        get() = _welfareInfo

    val currentWelfareTotal : MutableLiveData<String>
        get() = _currentWelfareTotal



    /*
    복지 정보를 알맞게 라이브데이터에 넣도록 호출하는 메소드
    */
    fun settingAllView(){

        //카테고리별 복지 개수 계산해서 변경하기 위해 라이브데이터 변경 메소드 호출
        getCategoryCount()

        //리사이클러뷰 리스트에 넣는 용
        _currentRcList.value=_welfareInfo.value?.mainWelfareResult?.totalCategory!!.studentCategory
    }

    /*
    카테고리별 복지 개수 계산해서 currentWelfareTotal 변경
     */
    fun getCategoryCount(){
        val totalCategoryCount = _welfareInfo.value!!.mainWelfareResult.totalCategory.studentCategory.size+ _welfareInfo.value!!.mainWelfareResult.totalCategory.employCategory.size+
                _welfareInfo.value!!.mainWelfareResult.totalCategory.foundationCategory.size+_welfareInfo.value!!.mainWelfareResult.totalCategory.lifeCategory.size+
                _welfareInfo.value!!.mainWelfareResult.totalCategory.covidCategory.size
        _currentWelfareTotal.value="총 ${totalCategoryCount}건"
    }


    /*
학생 지원으로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
 */
    fun changeRcToStudent() {
        _currentRcList.value = _welfareInfo.value?.mainWelfareResult?.studentCategory
    }

    /*
  취업 지원으로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToEmploy() {
        _currentRcList.value = _welfareInfo.value?.mainWelfareResult?.employCategory
    }

    /*
  창업 지원으로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToFoundation() {
        _currentRcList.value = _welfareInfo.value?.mainWelfareResult?.foundationCategory
    }

    /*
    주거 금융으로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToResident() {
        _currentRcList.value = _welfareInfo.value?.mainWelfareResult?.residentCategory
    }

    /*
  생활 복지로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToLife() {
        _currentRcList.value = _welfareInfo.value?.mainWelfareResult?.lifeCategory
    }

    /*
  코로나 19로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToCovid() {
        _currentRcList.value = _welfareInfo.value?.mainWelfareResult?.covidCategory
    }

}