package com.teamteam.knowing.data.repository

import com.teamteam.knowing.data.remote.api.CollegeInterface

interface CollegeRepository {

    fun getCollegeInfo(response: CollegeInterface)
}