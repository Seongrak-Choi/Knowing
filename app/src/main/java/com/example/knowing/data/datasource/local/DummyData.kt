package com.example.knowing.data.datasource.local

import java.util.*
import kotlin.collections.ArrayList

class DummyData {
    private var do_list = ArrayList<String>()
    private var gyeonggi_si_list = ArrayList<String>( //경기도에 속해 있는 시/군 리스트
        Arrays.asList("수원시","성남시","의정부시","안양시","부천시","광명시","평택시","동두천시","안산시","고양시","과천시","구리시","남양주시","오산시 ","시흥시 ","군포시","의왕시","하남시",
        "용인시","파주시","이천시","안성시","김포시","화성시","광주시","양주시","포천시","여주시","양주군","여주군","연천군","포천군","가평군","양평군"))
    private var gangwon_si_list = ArrayList<String>( //강원도에 속해있는 시/군 리스트
        Arrays.asList("춘천시","원주시","강릉시","동해시","태백시","속초시","삼척시","홍천군","횡성군","영월군","평창군","정선군","철원군","화천군","양구군","인제군","고성군 ","양양군"))
    private var chungbuk_si_list = ArrayList<String>( //충북에 속해있는 시/군 리스트
        Arrays.asList("청주시","충주시","제천시","청원군","보은군","옥천군","영동군","증평군","진천군","괴산군","음성군","단양군"))
    private var chungnam_si_list = ArrayList<String>( //충남에 속해있는 시/군 리스트
        Arrays.asList("천안시","공주시","보령시","아산시","서산시","논산시","계룡시","당진시","금산군","연기군","부여군","서천군","청양군","홍성군","예산군","태안군","당진군"))
    private var jeonbuk_si_list = ArrayList<String>( //전북에 속해있는 시/군 리스트
        Arrays.asList("전주시","군산시","익산시","정읍시","남원시","김제시","완주군","진안군","무주군","장수군","임실군","순창군","고창군","부안군"))
    private var jeonnam_si_list = ArrayList<String>( //전남에 속해있는 시/군 리스트
        Arrays.asList("목포시","여수시","순천시","나주시","광양시","담양군","곡성군","구례군","고흥군","보성군","화순군","장흥군","강진군","해남군","영암군","무안군","함평군","영광군","장성군","완도군","진도군","신안군"))
    private var gyeongbuk_si_list = ArrayList<String>( //경북에 속해있는 시/군 리스트
        Arrays.asList("포항시","경주시","김천시","안동시","구미시","영주시","영천시","상주시","문경시","경산시","군위군","의성군","청송군","영양군","영덕군","청도군","고령군","성주군","칠곡군","예천군","봉화군","울진군","울릉군"))
    private var gyeongnam_si_list = ArrayList<String>( //경남에 속해있는 시/군 리스트
        Arrays.asList("창원시","마산시","진주시","진해시","통영시","사천시","김해시","밀양시","거제시","양산시","의령군","함안군","창녕군","고성군","남해군","하동군","산청군","함양군","거창군","합천군")
    )
    init {
        do_list.add("강원도")
        do_list.add("경기도")
        do_list.add("경상남도")
        do_list.add("경상북도")
        do_list.add("광주광역시")
        do_list.add("대구광역시")
        do_list.add("대전광역시")
        do_list.add("부산광역시")
        do_list.add("서울특별시")
        do_list.add("세종특별자치시")
        do_list.add("울산광역시")
        do_list.add("인천광역시")
        do_list.add("전라남도")
        do_list.add("전라북도")
        do_list.add("제주특별자치도")
        do_list.add("충청남도")
        do_list.add("충청북도")
    }

    fun getDoList():ArrayList<String>{
        return do_list
    }

    fun getGyeonggiSiList():ArrayList<String>{
        return gyeonggi_si_list
    }
}