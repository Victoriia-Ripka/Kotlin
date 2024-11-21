package com.example.pw_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pw_6.services.CalculatorService
import com.example.pw_6.ui.calculator.CalculatorScreen
import com.example.pw_6.ui.entry.EntryScreen
import com.example.pw_6.ui.theme.PW6Theme

enum class Routes {
    MAIN_SCREEN,
    CALCULATOR
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PW6Theme {
                val navController = rememberNavController()
                val calculatorService = remember { CalculatorService() }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.MAIN_SCREEN.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Routes.MAIN_SCREEN.name) {
                            EntryScreen(
                                onCalculator1Navigate = { navController.navigate(route = Routes.CALCULATOR.name) }
                            )
                        }
                        composable(route = Routes.CALCULATOR.name) {
                            CalculatorScreen(
                                goBack = { navController.navigate(route = Routes.MAIN_SCREEN.name )},
                                calculatorService = calculatorService
                            )
                        }
                    }
                }
            }
        }
    }
}