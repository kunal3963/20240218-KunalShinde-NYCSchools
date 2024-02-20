package com.assigment.nycschools

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.assigment.nycschools.data.Schools
import com.assigment.nycschools.utils.Constants
import com.assigment.nycschools.utils.NetworkUtils
import com.assigment.nycschools.viewModels.NYCSchoolViewModel

@Composable
fun AllSchools(
    openDrawer: () -> Unit, nycViewModel: NYCSchoolViewModel, context: Context,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "New York Schools",
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (NetworkUtils.isOnline(context)) {
                nycViewModel.getAllSchools()
                ListSchool(nycViewModel, navController)
            } else {
                Toast.makeText(context, Constants.NETWORK_MESSAGE, Toast.LENGTH_SHORT).show()
            }
        }
    }
}


@Composable
fun Details(navController: NavController, nycViewModel: NYCSchoolViewModel, context: Context) {
    val dbn = navController.previousBackStackEntry?.savedStateHandle?.get<String>("dbn")

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "School details",
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.popBackStack() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            showSchoolInformation(nycViewModel, dbn ?: "", context)
        }
    }
}

@Composable()
fun ListSchool(viewModel: NYCSchoolViewModel, navController: NavController) {
    //If status is true then we need to show progress till we get response
    if (viewModel.getProgressBar()) {
        showProgressBar()
    } else {
        LazyColumn {
            itemsIndexed(viewModel.listOfSchools) { index, item ->
                SchoolItem(item, viewModel, navController)
            }
        }
    }
}

@Composable
fun SchoolItem(
    school: Schools,
    viewModel: NYCSchoolViewModel,
    navController: NavController
) {
    Card(
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 8.dp)
            .clickable {
                viewModel.setProgressBar(true)
                navController.currentBackStackEntry?.savedStateHandle?.set("dbn", school.dbn)
                navController.navigate(DrawerScreens.DetailSchool.route)
            },
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = school.school_name,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = "Phone number: " + school.phone_number,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = "Web :" + school.website,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
    }
}

@Composable
fun showSchoolInformation(nycViewModel: NYCSchoolViewModel, dbn: String, context: Context) {
    if (NetworkUtils.isOnline(context)) {
        nycViewModel.getSchoolInfo(dbn)
        showSchool(nycViewModel)
    } else {
        Toast.makeText(context, Constants.NETWORK_MESSAGE, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun showSchool(viewModel: NYCSchoolViewModel) {
    if (viewModel.getProgressBar()) {
        showProgressBar()
    } else if (viewModel.schoolInfo.dbn != "") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                setText(
                    text = "School Name :" + viewModel.schoolInfo.school_name,
                    fontWeight = FontWeight.Bold,
                )
                setText(
                    text = "SAT Data :",
                    fontWeight = FontWeight.Bold,
                )
                setText(
                    text = "Reading Average: " + viewModel.schoolInfo.sat_critical_reading_avg_score,
                )
                setText(
                    text = "Math Average: " + viewModel.schoolInfo.sat_math_avg_score,
                )
                setText(
                    text = "Write Average: " + viewModel.schoolInfo.sat_writing_avg_score,
                )
            }
        }
    } else if (viewModel.schoolInfo.dbn == "") {
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
                    text = Constants.ERROR_MESSAGE.plus(viewModel.schoolInfo.school_name),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
        }
    }
}

@Composable
fun showProgressBar() {
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
}

@Composable
fun setText(text: String, fontSize: TextUnit = 20.sp, fontWeight: FontWeight = FontWeight.Normal,
            style: TextStyle = MaterialTheme.typography.caption, modifier : Modifier = Modifier.padding(bottom = 5.dp)) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        style = style,
        modifier = modifier
    )
}
