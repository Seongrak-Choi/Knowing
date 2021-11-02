package com.example.knowing.data.repository

import com.example.knowing.data.datasource.local.DummyData

class DialogSelectDoRepository {
    private var do_list=DummyData().getDoList()

    fun getDoList():ArrayList<String>{
        return do_list
    }
}