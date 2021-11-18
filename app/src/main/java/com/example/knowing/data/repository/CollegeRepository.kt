package com.example.knowing.data.repository

import com.example.knowing.data.remote.api.CollegeInterface

interface CollegeRepository {

    fun getCollegeInfo(response: CollegeInterface)
}