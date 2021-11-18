package com.example.knowing.data.model.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignUpUser(
    @SerializedName("email")var email : String="", //이메일
    @SerializedName("name")var name : String="", //이름
    @SerializedName("pwd")var pwd:String="", //비번
    @SerializedName("phNum")var phNum:String="", //전화번호
    @SerializedName("gender")var gender:String="", //성별
    @SerializedName("birth")var birth: Int=0, //생년월일
    @SerializedName("address")var address:String="", //시/도
    @SerializedName("addressDetail")var addressDetail:String="", //시/군/구
    @SerializedName("specialStatus")var specialStatus: ArrayList<String> =ArrayList(),//특별사항
    @SerializedName("incomeLevel")var incomeLevel:String="",//소득분위
    @SerializedName("incomeAvg")var incomeAvg:String="", //중위소득
    @SerializedName("employmentState")var employmentState: ArrayList<String> =ArrayList(),  //취업상태
    @SerializedName("schoolRecords")var schoolRecords:String="",//학적사항
    @SerializedName("school")var school:String="", // 학교
    @SerializedName("mainMajor")var mainMajor:String ="", //전공
    @SerializedName("subMajor")var subMajor:String = "", //학과
    @SerializedName("semester")var semester:String ="",// //학년 학기를 저장하는데 학년과 학기를 공백으로 나눔.
    @SerializedName("lastSemesterScore")var lastSemesterScore:String="",//지난학기학점
    @SerializedName("studentCategory")var studentCategory: ArrayList<String> =ArrayList(),//학생지원
    @SerializedName("empolyCategory")var empolyCategory: ArrayList<String> =ArrayList(),//취업지원
    @SerializedName("foundationCategory")var foundationCategory: ArrayList<String> =ArrayList(),//창업지원
    @SerializedName("residentCategory")var residentCategory: ArrayList<String> =ArrayList(),//주거지원
    @SerializedName("lifeCategory")var lifeCategory: ArrayList<String> =ArrayList(),//생활지원
    @SerializedName("covidCategory")var covidCategory: ArrayList<String> =ArrayList(),//코로나19지원
    @SerializedName("bookmark")var bookmark: ArrayList<String> =ArrayList(), //북마크
    @SerializedName("provider")var provider :String="",//로그인 방식(kakao,naver,google,facebook,default)
    @SerializedName("FCMTOKEN")var FCMTOKEN:String=""//FCM 토큰
):Serializable