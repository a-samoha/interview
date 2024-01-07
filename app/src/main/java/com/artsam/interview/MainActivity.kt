package com.artsam.interview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.artsam.interview.presentation.screen.interview.InterviewScreen
import com.artsam.interview.presentation.screen.interview.InterviewViewModel
import com.artsam.interview.presentation.screen.main.MainScreen
import com.artsam.interview.presentation.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppTheme {
                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    composable("main") {
                        MainScreen(navController)
                    }
                    composable("interviewScreen") {
                        val viewModel = viewModel<InterviewViewModel>()
                        InterviewScreen(viewModel)
                    }
                }
            }
        }
    }
}
