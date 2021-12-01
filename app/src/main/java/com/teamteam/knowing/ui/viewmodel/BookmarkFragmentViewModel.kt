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

    private val _currentBookmarkTotal = MutableLiveData<Int>()

    val currentRcList : MutableLiveData<ArrayList<WelfareInfo>>
        get() =  _currentRcList

    val currentBookmarkTotal : MutableLiveData<Int>
        get() =  _currentBookmarkTotal



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
                    _currentRcList.value = result.bookmarkResult
                    _currentBookmarkTotal.value = result.bookmarkResult.size
                }else{
                    Log.e("ERROR","북마크 삭제 정보 가져오기 실패")
                }
            }
            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                Log.e("ERROR","북마크 삭제 통신 실패")
            }

        })
    }
}