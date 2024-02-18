package com.assigment.nycschools.viewModels

import com.assigment.nycschools.apiService.FakeApiService
import com.assigment.nycschools.repository.Repository
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NYCSchoolViewModelTest {
    lateinit var viewModel: NYCSchoolViewModel
    lateinit var testDispatcher: TestDispatcher
    lateinit var repository: Repository
    lateinit var apiService: FakeApiService

    @Before
    fun setUp() {
        apiService = FakeApiService()
        repository = Repository(apiService)
    }

    @Test
    fun `check the working of view Model for getAllSchools`() = runTest {

        testDispatcher = UnconfinedTestDispatcher(testScheduler)

        viewModel = NYCSchoolViewModel(repository, testDispatcher)
        viewModel.getAllSchools()
        advanceUntilIdle()
        assertEquals(2, viewModel.listOfSchools.size)
    }

    @Test
    fun `check the working of view Model for getSchool`() = runTest {

        testDispatcher = UnconfinedTestDispatcher(testScheduler)

        viewModel = NYCSchoolViewModel(repository, testDispatcher)
        viewModel.getSchoolInfo("2")
        advanceUntilIdle()
        assertEquals("test1", viewModel.schoolInfo.dbn)
    }
}