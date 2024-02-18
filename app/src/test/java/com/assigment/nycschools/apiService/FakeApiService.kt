package com.assigment.nycschools.apiService

import com.assigment.nycschools.data.SchoolInfo
import com.assigment.nycschools.data.Schools
import com.assigment.nycschools.network.ApiService
import retrofit2.http.GET
import retrofit2.http.Query

class FakeApiService : ApiService {
    suspend override fun getAllSchoolInformation(): List<Schools> {
        return listOf(
            Schools("test1", "test School1", "12345", "www.fake1.com"),
            Schools("test2", "test School2", "67890", "www.fake2.com")
        )
    }

    suspend override fun getSchoolInformation(@Query("dbn") dbn: String): List<SchoolInfo> {
        return listOf(
            SchoolInfo("test1", "test School1", "11111", "22222", "33333"),
        )
    }
}