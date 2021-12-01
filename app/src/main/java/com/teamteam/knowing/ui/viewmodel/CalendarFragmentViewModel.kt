package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.network.response.BookmarkResponse
import com.teamteam.knowing.data.model.network.response.WelfareInfo
import com.teamteam.knowing.data.remote.api.BookmarkInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class CalendarFragmentViewModel(application: Application): AndroidViewModel(application) {

    //복지 정보 중 신청기간만 저장할 라이브데이터
    private val _welfareApplyDateList = MutableLiveData<ArrayList<String>>()

    //일수에 신청기간이 포함되어 있는 복지 들을 아래에 출력해 주기 위한 라이브데이터
    private val _currentRcWelfareList = MutableLiveData<ArrayList<WelfareInfo>>()

    //api로 받아온 북마크 복지 데이터들 저장할 라이브 데이터
    private val _currentWelfareList = MutableLiveData<ArrayList<WelfareInfo>>()

    //달력에서 마지막으로 선택한 값이 저장될 라이브 데이터
    private val _finalSelectedDate = MutableLiveData<String>()





    val welfareApplyDateList : MutableLiveData<ArrayList<String>>
        get() = _welfareApplyDateList

    val currentRcWelfareList : MutableLiveData<ArrayList<WelfareInfo>>
        get() = _currentRcWelfareList

    val currentWelfareList : MutableLiveData<ArrayList<WelfareInfo>>
        get() =  _currentWelfareList

    val finalSelectedDate : MutableLiveData<String>
        get() =  _finalSelectedDate



    /*
    북마크 정보 받아오는 api 호출 해서 applyDate만 라이브데이터에 저장
    */
    fun tryGetBookmarkList(uid:String){
        val bookmarkInterface = ApplicationClass.sRetrofit.create(BookmarkInterface::class.java)
        bookmarkInterface.getBookmarkList(uid).enqueue(object: Callback<BookmarkResponse> {
            override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                if (response.isSuccessful){
                    val result = response.body() as BookmarkResponse
                    //받아온 북마크 복지 정보들 라이브데이터에 저장
                    _currentWelfareList.value = result.bookmarkResult

                    //복지 정보를 받아왔다면 마지막으로 클릭했던 date로 다시 한번 리사이클러뷰 초기화
                    setRcList()

                    //api로 받아온 복지 정보 중 applyDate만 저장하는 리스트 생성
                    var applyDateList = ArrayList<String>()

                    //받아온 applyDate만 저장
                    for (i in result.bookmarkResult){
                        applyDateList.add(i.applyDate)
                    }
                    //복지 정보 중 applyDate만 저장하는 리스트를 라이브데이터에 저장
                    _welfareApplyDateList.value = applyDateList
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
                    _currentWelfareList.value = result.bookmarkResult

                    //삭제 후 복지 정보를 다시 받아왔다면 마지막으로 클릭했던 date로 다시 한번 리사이클러뷰 초기화
                    setRcList()

                    //api로 받아온 복지 정보 중 applyDate만 저장하는 리스트 생성
                    var applyDateList = ArrayList<String>()

                    //받아온 applyDate만 저장
                    for (i in result.bookmarkResult){
                        applyDateList.add(i.applyDate)
                    }
                    //복지 정보 중 applyDate만 저장하는 리스트를 라이브데이터에 저장
                    _welfareApplyDateList.value = applyDateList
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
    달력 일 수 클릭하면 일수가 포함된 복지 데이터만 _currentRcWelfareList에 저장하는 메소드
     */
    fun setRcList(){
        var rcWelfareList = ArrayList<WelfareInfo>()
        if (_currentWelfareList.value?.isNotEmpty()==true){
            for (i in _currentWelfareList.value!!){
                if (i.applyDate!="별도공지" && i.applyDate!="연중상시"){
                    val applyDateMining = i.applyDate.split("~")
                    val startYearMonthDay = applyDateMining[0].split(".")
                    val endYearMonthDay = applyDateMining[1].split(".")

                    //특정날짜가 유효한지 확인하는 메소드를 호출해서 참이면 리스트에 저장 아니면 스킵
                    if (isWithinRange(_finalSelectedDate.value.toString(),startYearMonthDay[0]+startYearMonthDay[1]+startYearMonthDay[2],endYearMonthDay[0]+endYearMonthDay[1]+endYearMonthDay[2]))  {
                        rcWelfareList.add(i)
                    }
                }else{
                    //별도공지나 연중상시인 경우는 무조건 보여주기 위해 리스트에 저장
                    rcWelfareList.add(i)
                }
            }
        }
        _currentRcWelfareList.value=rcWelfareList
    }


    @Throws(ParseException::class)
    fun isWithinRange(date: String, startDate: String, endDate: String): Boolean {
        var date = date
        var startDate = startDate
        var endDate = endDate

        if (date.length != 8 || startDate.length != 8 || endDate.length != 8) {
            return false
        }

        date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8)
        startDate = startDate.substring(0, 4) + "-" + startDate.substring(4, 6) + "-" + startDate.substring(6, 8)
        endDate = endDate.substring(0, 4) + "-" + endDate.substring(4, 6) + "-" + endDate.substring(6, 8)

        val localdate: LocalDate = LocalDate.parse(date)
        val startLocalDate: LocalDate = LocalDate.parse(startDate)
        var endLocalDate: LocalDate = LocalDate.parse(endDate)
        endLocalDate = endLocalDate.plusDays(1) // endDate는 포함하지 않으므로 +1일을 해줘야함.
        return !localdate.isBefore(startLocalDate) && localdate.isBefore(endLocalDate)
    }
}