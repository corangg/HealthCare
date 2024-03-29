package com.example.healthcare.ui.composable.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.healthcare.R
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ui.composable.Main.Exercise.exerciseView
import com.example.healthcare.ui.composable.Main.Profile.ProfileView
import com.example.healthcare.ui.composable.Main.Record.RecordView

@Composable
fun MyAppPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF121212)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) {innerPadding ->
            NavigationGraph(navController = navController,innerPadding)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController){
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("운동", "기록", "프로필")


    BottomNavigation(modifier = Modifier
        .height(60.dp)
        .border(2.dp, Color.Black), backgroundColor = Color(0xFF2D2D2D)) {
        val currentRoute = currentRoute(navController)
        items.forEach {
            BottomNavigationItem(
                icon = {
                    when (it) {
                        "운동" -> Icon(painterResource(id = R.drawable.ic_exercise), contentDescription = null, modifier = Modifier
                            .size(36.dp)
                            .padding(top = 6.dp))
                        "기록" -> Icon(painterResource(id = R.drawable.ic_graph), contentDescription = null, modifier = Modifier
                            .size(36.dp)
                            .padding(bottom = 4.dp, top = 8.dp))
                        "프로필" -> Icon(painterResource(id = R.drawable.ic_name), contentDescription = null, modifier = Modifier
                            .size(36.dp)
                            .padding(bottom = 4.dp, top = 8.dp))
                    }
                },
                label = { Text(it, fontSize = 12.sp) },
                selected = currentRoute == it,
                onClick = {navController.navigate(it){
                    navController.graph.startDestinationRoute?.let{
                        popUpTo(it){saveState = true}
                    }
                    launchSingleTop = true
                    restoreState = true
                } },
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.LightGray
            )
        }
    }
}



@Composable
fun NavigationGraph(navController: NavHostController, innerPadding: PaddingValues) {
    val viewModel: MainViewModel = hiltViewModel()
    NavHost(navController, startDestination = "운동") {
        composable("운동") { showView(0, viewModel) }
        composable("기록") {showView(1, viewModel) }
        composable("프로필") {showView(2, viewModel)  }
    }
}

@Composable
fun showView(index: Int, viewModel: MainViewModel){
    when(index){
        0-> exerciseView(viewModel)
        1-> RecordView(viewModel)
        2-> ProfileView(viewModel)
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyAppPreview()
}