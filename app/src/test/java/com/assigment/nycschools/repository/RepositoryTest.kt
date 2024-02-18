package com.assigment.nycschools.repository

import com.assigment.nycschools.apiService.FakeApiService
import com.assigment.nycschools.network.ApiService
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class RepositoryTest{

    lateinit var apiService: ApiService
    lateinit var repository: Repository

    @Test
    fun `check repository contents all school data`() = runTest {

        apiService = FakeApiService()
        repository = Repository(apiService)

        val allSchools = repository.getAllSchools()
        assertEquals(2, allSchools.size)
    }

    @Test
    fun `check repository contents school data`() = runTest {

        apiService = FakeApiService()
        repository = Repository(apiService)

        val allSchools = repository.getSchoolInfo("2")
        assertEquals(1, allSchools.size)
    }
}