package com.teamteam.knowing.data.model.network.response

import com.google.gson.annotations.SerializedName

data class BookmarkPostResponse(
    @SerializedName("isSuccess")var isSuccess : Boolean,
    @SerializedName("code")var code : Int,
    @SerializedName("message")var message:String,
    @SerializedName("result")var bookmarkPostResult:BookmarkPostResult
)

data class BookmarkPostResult(
    @SerializedName("result")var bookmarkWhether : String
)
