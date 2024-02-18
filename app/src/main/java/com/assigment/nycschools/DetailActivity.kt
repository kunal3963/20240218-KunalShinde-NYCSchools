package com.assigment.nycschools

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.assigment.nycschools.data.SchoolInfo
import com.assigment.nycschools.ui.theme.NYCSchoolsTheme
import com.assigment.nycschools.utils.Constants
import com.assigment.nycschools.utils.Constants.Companion.ERROR_MESSAGE
import com.assigment.nycschools.utils.NetworkUtils
import com.assigment.nycschools.viewModels.NYCSchoolViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {

    lateinit var schoolName: String
    lateinit var dbn: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbn = intent.getStringExtra(Constants.DBN_MESSAGE)!!
        schoolName = intent.getStringExtra(Constants.SCHOOL_NAME_MESSAGE)!!
        setContent {
            NYCSchoolsTheme {
                val nycViewModel = hiltViewModel<NYCSchoolViewModel>()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopAppBar(title = { Text("School Information") }) },
                    backgroundColor = MaterialTheme.colors.background
                ) {
                    showSchoolInformation(nycViewModel)
                }
            }
        }
    }

    @Composable
    fun showSchoolInformation(nycViewModel: NYCSchoolViewModel) {
        if (NetworkUtils.isOnline(this)) {
            showSchool(nycViewModel.schoolInfo, nycViewModel.progress)
            nycViewModel.getSchoolInfo(dbn)
        } else {
            Toast.makeText(this, Constants.NETWORK_MESSAGE, Toast.LENGTH_SHORT).show()
        }
    }

    //Show SAT data on the view from SchoolInfo object
    @Composable
    fun showSchool(schoolInfo: SchoolInfo, status: Boolean) {
        if (status) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.secondary,
                    strokeWidth = 7.dp
                )
            }
        } else if (schoolInfo.dbn != "") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "School Name :" + schoolInfo.school_name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = "SAT Data :",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = "Reading Average: " + schoolInfo.sat_critical_reading_avg_score,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = "Math Average: " + schoolInfo.sat_math_avg_score,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = "Write Average: " + schoolInfo.sat_writing_avg_score,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
            }
        } else if (schoolInfo.dbn == "") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = ERROR_MESSAGE.plus(schoolName),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    NYCSchoolsTheme {
        Greeting2("Android")
    }
}