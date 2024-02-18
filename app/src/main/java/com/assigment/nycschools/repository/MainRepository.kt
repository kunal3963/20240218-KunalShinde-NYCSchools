package com.assigment.nycschools.repository

import com.assigment.nycschools.data.SchoolInfo
import com.assigment.nycschools.data.Schools
import com.assigment.nycschools.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


//Following MVVM patter to get into from REST api
class MainRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getAllSchools(): List<Schools> {
        return apiService.getAllSchoolInformation()
    }

    suspend fun getSchoolInfo(dbn: String): List<SchoolInfo> {
        return apiService.getSchoolInformation(dbn)
    }
}