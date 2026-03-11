package com.example.autouroute.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autouroute.ui.screens.AdministrationScreen
import com.example.autouroute.ui.screens.AuthDialog
import com.example.autouroute.ui.screens.LanguageScreen
import com.example.autouroute.ui.screens.ObservationsScreen
import com.example.autouroute.ui.screens.ProfileScreen
import com.example.autouroute.ui.screens.RegisterScreen
import com.example.autouroute.ui.screens.ReportScreen
import com.example.autouroute.ui.screens.ServicesScreen
import com.example.autouroute.ui.screens.SuccessScreen
import com.example.autouroute.ui.screens.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var showAuthDialog by remember { mutableStateOf(false) }
    var currentAuthPhone by remember { mutableStateOf("") }
    var currentTab by remember { mutableIntStateOf(0) }

    if (showAuthDialog) {
        AuthDialog(
            phone = currentAuthPhone,
            onDismiss = { showAuthDialog = false },
            onValidate = {
                showAuthDialog = false
                navController.navigate(Routes.WELCOME) { 
                    popUpTo(Routes.REGISTER) { inclusive = true } 
                }
            }
        )
    }

    NavHost(
        navController = navController,
        startDestination = Routes.LANGUAGE,
        modifier = Modifier
    ) {
        composable(Routes.LANGUAGE) {
            LanguageScreen(onValider = { navController.navigate(Routes.REGISTER) })
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                onValider = {},
                onAuthRequest = { phone -> 
                    currentAuthPhone = phone
                    showAuthDialog = true 
                }
            )
        }
        composable(Routes.WELCOME) {
            WelcomeScreen(onDecouvrir = { navController.navigate(Routes.MAIN) })
        }
        composable(Routes.MAIN) {
            val tabs = listOf("Services", "Observations", "Profile")
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        tabs.forEachIndexed { index, title ->
                            val selected = currentTab == index
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        when (index) {
                                            0 -> Icons.Default.Home
                                            1 -> Icons.AutoMirrored.Filled.List
                                            else -> Icons.Default.Person
                                        },
                                        contentDescription = title
                                    )
                                },
                                label = { Text(title) },
                                selected = selected,
                                onClick = { currentTab = index }
                            )
                        }
                    }
                }
            ) { padding ->
                when (currentTab) {
                    0 -> androidx.compose.foundation.layout.Box(Modifier.padding(padding)) {
                        ServicesScreen(
                            onServiceClick = { type ->
                                navController.navigate(Routes.report(type))
                            }
                        )
                    }
                    1 -> androidx.compose.foundation.layout.Box(Modifier.padding(padding)) {
                        ObservationsScreen()
                    }
                    2 -> androidx.compose.foundation.layout.Box(Modifier.padding(padding)) {
                        ProfileScreen(
                            onConditionsClick = { navController.navigate(Routes.ADMINISTRATION) },
                            onLogout = {
                                navController.navigate(Routes.LANGUAGE) {
                                    popUpTo(Routes.MAIN) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
        composable(Routes.REPORT) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "entretien"
            ReportScreen(
                type = type,
                onValider = { navController.navigate(Routes.SUCCESS) }
            )
        }
        composable(Routes.SUCCESS) {
            SuccessScreen()
        }
        composable(Routes.ADMINISTRATION) {
            AdministrationScreen()
        }
    }
}