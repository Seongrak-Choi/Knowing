package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.network.response.BookmarkResponse
import com.teamteam.knowing.data.model.network.response.WelfareInfo
import com.teamteam.knowing.data.remote.api.BookmarkInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkFragmentViewModel(application:Application):AndroidViewModel(application) {
    //리사이클러뷰에 출력해줄 리스트 저장할 라이브 데이터
    private val _currentRcList = MutableLiveData<ArrayList<WelfareInfo>>()

    //복지 총 갯수 저장할 라이브 데이터
    private val _currentBookmarkTotal = MutableLiveData<Int>()

    //정렬 부분에 보여질 텍스트 저장하는 라이브데이터
    private val _currentFilter = MutableLiveData<String>()



    val currentRcList : MutableLiveData<ArrayList<WelfareInfo>>
        get() =  _currentRcList

    val currentBookmarkTotal : MutableLiveData<Int>
        get() =  _currentBookmarkTotal

    val currentFilter: MutableLiveData<String>
        get() = _currentFilter


    init {
        _currentFilter.value="높은 금액순"
    }


    /*
    북마크 정보 받아오는 api 호출 해서 리사이클러뷰가 옵저버하는 라이브데이터에 저장
     */
    fun tryGetBookmarkList(uid:String){
        val bookmarkInterface = ApplicationClass.sRetrofit.create(BookmarkInterface::class.java)
        bookmarkInterface.getBookmarkList(uid).enqueue(object: Callback<BookmarkResponse> {
            override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                if (response.isSuccessful){
                    val result = response.body() as BookmarkResponse
                    //받아온 북마크 리스트를 리사이클러뷰를 담당하는 라이브데이터에 저장
                    _currentRcList.value = result.bookmarkResult
                    _currentBookmarkTotal.value = result.bookmarkResult.size

                    //정렬 값에 맞춰 저장
                    when(_currentFilter.value.toString()){
                        "높은 금액순"-> sortHighCost()
                        "낮은 금액순"-> sortLowCost()
                        "마감일순"->sortDeadLine()
                    }
                }else{
                    Log.e("ERROR","북마크 조회 정보 가져오기 실패")
                }
            }

            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                Log.e("ERROR","북마크 조회 통신 실패")
            }
        })
    }


    /*
    북마크 삭제 후 삭제된 복지 정보를 빼고 나머지 복지정보 받아오는 api
     */
    fun tryDeleteBookmarkList(userUid:String,welfareUid:String){
        val bookmarkInterface = ApplicationClass.sRetrofit.create(BookmarkInterface::class.java)
        bookmarkInterface.deleteBookmarkList(userUid,welfareUid).enqueue(object:Callback<BookmarkResponse>{
            override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                if (response.isSuccessful){
                    val result = response.body() as BookmarkResponse
                    //_currentRcList.value = result.bookmarkResult
                    //_currentBookmarkTotal.value = result.bookmarkResult.size
                }else{
                    Log.e("ERROR","북마크 삭제 정보 가져오기 실패")
                }
            }
            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                Log.e("ERROR","북마크 삭제 통신 실패")
            }

        })
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