package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.network.response.BookmarkPostResponse
import com.teamteam.knowing.data.remote.api.BookmarkInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelfareDetailActivityViewModel(application:Application):AndroidViewModel(application) {
    private val mContext = application.applicationContext

    private val _currentBookmarkWhether = MutableLiveData<Boolean>()


    val currentBookmarkWhether : MutableLiveData<Boolean>
        get() = _currentBookmarkWhether


    /*
    북마크 추가,해제 요청 api 메소드
     */
    fun tryPostBookmark(userUid:String,welfareUid:String){
        val bookmarkInterface = ApplicationClass.sRetrofit.create(BookmarkInterface::class.java)
        bookmarkInterface.postBookmark(userUid,welfareUid).enqueue(object:Callback<BookmarkPostResponse>{
            override fun onResponse(call: Call<BookmarkPostResponse>, response: Response<BookmarkPostResponse>) {
                if (response.isSuccessful){
                    val result = response.body() as BookmarkPostResponse
                    if (result.bookmarkPostResult.bookmarkWhether=="북마크등록"){
                        _currentBookmarkWhether.value = true
                        Toast.makeText(mContext,"북마크 설정",Toast.LENGTH_SHORT).show()
                    }else{
                        _currentBookmarkWhether.value = false
                        Toast.makeText(mContext,"북마크 해제",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Log.e("ERROR","북마크 추가,해제 요청 실패")
                }
            }
            override fun onFailure(call: Call<BookmarkPostResponse>, t: Throwable) {
                Log.e("ERROR","북마크 추가,해제 통신 실패")
            }
        })
    }




    /*
    북마크 추가 되어 있는지 확인하는 api호출 메소드
     */
    fun tryGetIsWelfareApplyToBookmark(userUid:String,welfareUid:String){
        val bookmarkInterface = ApplicationClass.sRetrofit.create(BookmarkInterface::class.java)
        bookmarkInterface.getIsWelfareApplyToBookmark(userUid,welfareUid).enqueue(object:Callback<BookmarkPostResponse>{
            override fun onResponse(call: Call<BookmarkPostResponse>, response: Response<BookmarkPostResponse>) {
                if (response.isSuccessful){
                    val result = response.body() as BookmarkPostResponse
                    _currentBookmarkWhether.value = result.bookmarkPostResult.bookmarkWhether=="북마크등록"
                }else{
                    Log.e("ERROR","북마크 상세페이지 조회 요청 실패")
                }
            }

            override fun onFailure(call: Call<BookmarkPostResponse>, t: Throwable) {
                Log.e("ERROR","북마크 상세페이지 조회 통신 실패")
            }
        })
    }



}