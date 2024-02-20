package com.assigment.nycschools.network

import com.assigment.nycschools.data.SchoolInfo
import com.assigment.nycschools.data.Schools
import com.assigment.nycschools.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//Retrofit instant to get data from server
interface ApiService {

    @GET("s3k6-pzi2.json")
    suspend fun getAllSchoolInformation(): List<Schools>

    @GET("f9bf-2cp4.json")
    suspend fun getSchoolInformation(@Query("dbn") dbn: String): List<SchoolInfo>
}