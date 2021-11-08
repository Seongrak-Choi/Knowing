package com.example.knowing.data.repository

import com.example.knowing.data.datasource.local.DummyData
import java.util.*
import kotlin.collections.ArrayList

class DialogSelectDoRepository {
    private var do_list=DummyData().getDoList()
    private var gyeonggi_si_list=DummyData().getGyeonggiSiList()

    private var gangwon_si_list = DummyData().getGangwonSiList()
    private var chungbuk_si_list = DummyData().getChungbukSiList()
    private var chungnam_si_list = DummyData().getChungNamSiList()
    private var jeonbuk_si_list = DummyData().getJeonBukSiList()
    private var jeonnam_si_list = DummyData().getJeonNamSiList()
    private var gyeongbuk_si_list =DummyData().getGyeongBukSiList()
    private var gyeongnam_si_list = DummyData().getGyeongNamSiList()
    private var seoul_gu_list = DummyData().getSeoulGuList()
    private var busan_gu_list = DummyData().getBusanGuList()
    private var daegu_gu_list = DummyData().getDaeGuList()
    private var incheon_gu_list = DummyData().getIncheonGuList()
    private var gwangju_gu_list = DummyData().getGwangjuGuList()
    private var deajeon_gu_list = DummyData().getDeajeonGuList()
    private var ulsan_gu_list = DummyData().getUlsanGuList()
    private var jeju_si_list = DummyData().getJejuSiList()
    private var sejong_si_list = DummyData().getSejongSiList()

    //전국의 시/도를 리스트를 반환해주는 메소드
    fun getDoList():ArrayList<String>{
        return do_list
    }

    //경기도의 시/군/구 리스트를 반환해주는 메소드
    fun getGyeonggiSiList():ArrayList<String>{
        return gyeonggi_si_list
    }

    //강원도의 시/군/구 리스트를 반환해주는 메소드
    fun getGangwonSiList():ArrayList<String>{
        return gangwon_si_list
    }

    //충북의 시/군/구 리스트를 반환해주는 메소드
    fun getChungbukSiList():ArrayList<String>{
        return chungbuk_si_list
    }

    //충남의 시/군/구 리스트를 반환해주는 메소드
    fun getChungNamSiList():ArrayList<String>{
        return chungnam_si_list
    }

    //전북의 시/군/구 리스트를 반환해주는 메소드
    fun getJeonBukSiList():ArrayList<String>{
        return jeonbuk_si_list
    }

    //전남의 시/군/구 리스트를 반환해주는 메소드
    fun getJeonNamSiList():ArrayList<String>{
        return jeonnam_si_list
    }

    //경북의 시/군/구 리스트를 반환해주는 메소드
    fun getGyeongBukSiList():ArrayList<String>{
        return gyeongbuk_si_list
    }

    //경남의 시/군/구 리스트를 반환해주는 메소드
    fun getGyeongNamSiList():ArrayList<String>{
        return gyeongnam_si_list
    }

    //서울의 시/군/구 리스트를 반환해주는 메소드
    fun getSeoulGuList():ArrayList<String>{
        return seoul_gu_list
    }


    //부산의 시/군/구 리스트를 반환해주는 메소드
    fun getBusanGuList():ArrayList<String>{
        return busan_gu_list
    }

    //대구의 시/군/구 리스트를 반환해주는 메소드
    fun getDaeGuList():ArrayList<String>{
        return daegu_gu_list
    }

    //인천의 시/군/구 리스트를 반환해주는 메소드
    fun getIncheonGuList():ArrayList<String>{
        return incheon_gu_list
    }

    //광주의 시/군/구 리스트를 반환해주는 메소드
    fun getGwangjuGuList():ArrayList<String>{
        return gwangju_gu_list
    }


    //대전의 시/군/구 리스트를 반환해주는 메소드
    fun getDeajeonGuList():ArrayList<String>{
        return deajeon_gu_list
    }

    //울산의 시/군/구 리스트를 반환해주는 메소드
    fun getUlsanGuList():ArrayList<String>{
        return ulsan_gu_list
    }

    //제주의 시/군/구 리스트를 반환해주는 메소드
    fun getJejuSiList():ArrayList<String>{
        return jeju_si_list
    }

    //세종의 시/군/구 리스트를 반환해주는 메소드
    fun getSejongSiList():ArrayList<String>{
        return sejong_si_list
    }
}