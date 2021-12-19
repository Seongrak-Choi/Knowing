package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.data.model.network.response.MainWelfareResponse
import com.teamteam.knowing.data.model.network.response.WelfareInfo

class AllWelfareFragmentViewModel(application: Application):AndroidViewModel(application) {
    //API로 받아온 복지 정보
    private val _welfareInfo : MutableLiveData<MainWelfareResponse> by lazy { MutableLiveData<MainWelfareResponse>() }

    //맞춤복지 리사이클러뷰 출력할 라이브데이터
    private val _currentRcList = MutableLiveData<ArrayList<WelfareInfo>>()

    //총 복지 갯수 저장하는 라이브 데이터
    private val _currentWelfareTotal = MutableLiveData<String>()

    //정렬 부분에 보여질 텍스트 저장하는 라이브데이터
    private val _currentFilter = MutableLiveData<String>()


    val currentRcList : MutableLiveData<ArrayList<WelfareInfo>>
        get() = _currentRcList

    val welfareInfo : MutableLiveData<MainWelfareResponse>
        get() = _welfareInfo

    val currentWelfareTotal : MutableLiveData<String>
        get() = _currentWelfareTotal

    val currentFilter : MutableLiveData<String>
        get() = _currentFilter

    init {
        _currentFilter.value="높은 금액순"
    }



    /*
    복지 정보를 알맞게 라이브데이터에 넣도록 호출하는 메소드
    */
    fun settingAllView(){
        //리사이클러뷰 리스트에 넣는 용
        _currentRcList.value=_welfareInfo.value!!.mainWelfareResult.totalCategory.employCategory
        when(_currentFilter.value.toString()){
            "높은 금액순"-> sortHighCost()
            "낮은 금액순"-> sortLowCost()
            "마감일순"->sortDeadLine()
        }
    }



    /*
  학생 지원으로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToStudent() {
        _currentRcList.value = _welfareInfo.value!!.mainWelfareResult.totalCategory.studentCategory
        when(_currentFilter.value.toString()){
            "높은 금액순"-> sortHighCost()
            "낮은 금액순"-> sortLowCost()
            "마감일순"->sortDeadLine()
        }
    }

    /*
  취업 지원으로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToEmploy() {
        _currentRcList.value = _welfareInfo.value!!.mainWelfareResult.totalCategory.employCategory
        when(_currentFilter.value.toString()){
            "높은 금액순"-> sortHighCost()
            "낮은 금액순"-> sortLowCost()
            "마감일순"->sortDeadLine()
        }
    }

    /*
  창업 지원으로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToFoundation() {
        _currentRcList.value = _welfareInfo.value!!.mainWelfareResult.totalCategory.foundationCategory
        when(_currentFilter.value.toString()){
            "높은 금액순"-> sortHighCost()
            "낮은 금액순"-> sortLowCost()
            "마감일순"->sortDeadLine()
        }
    }

    /*
    주거 금융으로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToResident() {
        _currentRcList.value = _welfareInfo.value!!.mainWelfareResult.totalCategory.residentCategory
        when(_currentFilter.value.toString()){
            "높은 금액순"-> sortHighCost()
            "낮은 금액순"-> sortLowCost()
            "마감일순"->sortDeadLine()
        }
    }

    /*
  생활 복지로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToLife() {
        _currentRcList.value = _welfareInfo.value!!.mainWelfareResult.totalCategory.lifeCategory
        when(_currentFilter.value.toString()){
            "높은 금액순"-> sortHighCost()
            "낮은 금액순"-> sortLowCost()
            "마감일순"->sortDeadLine()
        }
    }

    /*
  코로나 19로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToCovid() {
        _currentRcList.value = _welfareInfo.value!!.mainWelfareResult.totalCategory.covidCategory
        when(_currentFilter.value.toString()){
            "높은 금액순"-> sortHighCost()
            "낮은 금액순"-> sortLowCost()
            "마감일순"->sortDeadLine()
        }
    }


    /*
    높은 금액순으로 정렬하는 메소드
     */
    fun sortHighCost(){
        var sortedList=ArrayList<WelfareInfo>()
        for (i in 0 until _currentRcList.value!!.size){
            var max= _currentRcList.value!![i].maxMoney.toInt()
            var maxIndex = i

            for (j in i+1 until _currentRcList.value!!.size){
                if (max<_currentRcList.value!![j].maxMoney.toInt()){
                    max=_currentRcList.value!![j].maxMoney.toInt()
                    maxIndex=j

                    var temp = _currentRcList.value!![i]
                    _currentRcList.value!![i]=_currentRcList.value!![maxIndex]
                    _currentRcList.value!![maxIndex]=temp
                }
            }
            sortedList.add(_currentRcList.value!![i])
        }
        _currentRcList.value=sortedList
    }


    /*
  낮은 금액순으로 정렬하는 메소드
   */
    fun sortLowCost(){
        var sortedList=ArrayList<WelfareInfo>()
        for (i in 0 until _currentRcList.value!!.size) {
            var max = _currentRcList.value!![i].minMoney.toInt()
            var maxIndex = i

            for (j in i + 1 until _currentRcList.value!!.size) {
                if (max < _currentRcList.value!![j].minMoney.toInt()) {
                    max = _currentRcList.value!![j].maxMoney.toInt()
                    maxIndex = j

                    var temp = _currentRcList.value!![i]
                    _currentRcList.value!![i] = _currentRcList.value!![maxIndex]
                    _currentRcList.value!![maxIndex] = temp
                }
            }
            sortedList.add(_currentRcList.value!![i])
        }
        _currentRcList.value=sortedList
    }


    /*
    마감일자순으로 정렬하는 메소드
    */
    fun sortDeadLine(){
        //정렬된 리스트를 저장할 리스트
        var sortedList=ArrayList<WelfareInfo>()

        //작은 값을 저장하고 있을 리스트
        var soonDeadLineList=ArrayList<WelfareInfo>()

        //applyDate가 연중상시,별도공지 아닌 복지 저장할 리스트
        var deadLineList=ArrayList<WelfareInfo>()

        //applyDate가 연중상시,별도공지 인 복지 저장할 리스트
        var lastList=ArrayList<WelfareInfo>()

        //먼저 split을 할 수 있도록 연중상시,별도공지 인지 아닌지 나눈다.
        for (i in _currentRcList.value!!){
            if (i.applyDate!="연중상시" && i.applyDate!="별도공지"){
                deadLineList.add(i)
            }else{
                lastList.add(i)
            }
        }

        for (i in 0 until deadLineList.size) {
            var soonDeadLine = deadLineList[i].applyDate.split("~")[1].replace(".","")
            var soonDeadLineIndex=i
            for (j in i + 1 until deadLineList.size) {
                if (soonDeadLine.toInt() > deadLineList[j].applyDate.split("~")[1].replace(".","").toInt()) {
                    soonDeadLine = deadLineList[j].applyDate.split("~")[1].replace(".","")
                    soonDeadLineIndex = j

                    var temp = deadLineList[i]
                    deadLineList[i] = deadLineList[soonDeadLineIndex]
                    deadLineList[soonDeadLineIndex] = temp
                }
            }
            soonDeadLineList.add(deadLineList[i])
        }
        sortedList.addAll(soonDeadLineList)
        sortedList.addAll(lastList)
        _currentRcList.value=sortedList
    }

}