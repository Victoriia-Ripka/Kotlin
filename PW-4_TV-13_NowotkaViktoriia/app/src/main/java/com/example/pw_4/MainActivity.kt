package com.example.pw_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pw_4.ui.theme.PW4Theme
import androidx.navigation.compose.rememberNavController
import com.example.pw_4.services.CalculatorService
import com.example.pw_4.ui.calculators.Calculator1Screen
import com.example.pw_4.ui.calculators.Calculator2Screen
import com.example.pw_4.ui.calculators.Calculator3Screen
import com.example.pw_4.ui.entry.EntryScreen

enum class Routes {
    MAIN_SCREEN,
    CALCULATOR_1,
    CALCULATOR_2,
    CALCULATOR_3
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PW4Theme {
                val navController = rememberNavController()
                val calculatorService = CalculatorService()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.MAIN_SCREEN.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Routes.MAIN_SCREEN.name) {
                            EntryScreen(
                                onCalculator1Navigate = { navController.navigate(route = Routes.CALCULATOR_1.name) },
                                onCalculator2Navigate = { navController.navigate(route = Routes.CALCULATOR_2.name) },
                                onCalculator3Navigate = { navController.navigate(route = Routes.CALCULATOR_3.name) },
                            )
                        }
                        composable(route = Routes.CALCULATOR_1.name) {
                            Calculator1Screen(
                                goBack = { navController.navigate(route = Routes.MAIN_SCREEN.name )},
                                calculatorService = calculatorService
                            )
                        }
                        composable(route = Routes.CALCULATOR_2.name) {
                            Calculator2Screen(
                                goBack = { navController.navigate(route = Routes.MAIN_SCREEN.name )},
                                calculatorService = calculatorService
                            )
                        }
                        composable(route = Routes.CALCULATOR_3.name) {
                            Calculator3Screen(
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