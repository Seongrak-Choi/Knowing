package com.example.knowing.data.repository

import com.example.knowing.data.datasource.local.DummyData

class DialogSelectDoRepository {
    private var do_list=DummyData().getDoList()
    private var gyeonggi_si_list=DummyData().getGyeonggiSiList()
    fun getDoList():ArrayList<String>{
        return do_list
    }

    fun getGyeonggiSiList():ArrayList<String>{
        return gyeonggi_si_list
    }
}