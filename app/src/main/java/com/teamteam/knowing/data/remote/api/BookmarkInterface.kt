package com.teamteam.knowing.data.remote.api

import com.teamteam.knowing.data.model.network.response.BookmarkPostResponse
import com.teamteam.knowing.data.model.network.response.BookmarkResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BookmarkInterface {
    @GET("app/mains/bookmark")//북마크 조회
    fun getBookmarkList(@Header("uid")uid:String) : Call<BookmarkResponse>

    @GET("app/users/bookmark")//북마크 상제 페이지에서 북마크에 등록 되어 있는지 조회
    fun getIsWelfareApplyToBookmark(@Header("userUid")userUid:String, @Header("welfareUid")welfareUid:String) : Call<BookmarkPostResponse>

    @DELETE("app/mains/bookmark")//북마크 삭제
    fun deleteBookmarkList(@Header("userUid")userUid:String, @Header("welfareUid")welfareUid:String) : Call<BookmarkResponse>

    @POST("app/users/bookmark") //추가,삭제
    fun postBookmark(@Header("userUid")userUid:String, @Header("welfareUid")welfareUid:String) : Call<BookmarkPostResponse>
}