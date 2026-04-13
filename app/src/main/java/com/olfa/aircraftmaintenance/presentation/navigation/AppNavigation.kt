package com.olfa.aircraftmaintenance.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.olfa.aircraftmaintenance.presentation.screens.equipment.EquipmentDetailScreen
import com.olfa.aircraftmaintenance.presentation.screens.equipment.EquipmentListScreen
import com.olfa.aircraftmaintenance.presentation.screens.home.HomeScreen
import com.olfa.aircraftmaintenance.presentation.screens.incident.IncidentListScreen
import com.olfa.aircraftmaintenance.presentation.screens.inspection.InspectionChecklistScreen
import com.olfa.aircraftmaintenance.presentation.screens.inspection.PendingInspectionsScreen
import com.olfa.aircraftmaintenance.presentation.screens.login.LoginScreen
import com.olfa.aircraftmaintenance.presentation.screens.profile.ProfileScreen
import com.olfa.aircraftmaintenance.presentation.screens.sync.SyncStatusScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var currentUsername by rememberSaveable { mutableStateOf("Technician") }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { username ->
                    currentUsername = username
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                username = currentUsername,
                onGoToDashboard = {
                    navController.navigate(Routes.HOME) {
                        launchSingleTop = true
                    }
                },
                onGoToEquipmentList = {
                    navController.navigate(Routes.EQUIPMENT_LIST)
                },
                onGoToProfile = {
                    navController.navigate(Routes.PROFILE)
                },
                onGoToPendingInspections = {
                    navController.navigate(Routes.PENDING_INSPECTIONS)
                },
                onGoToIncidents = {
                    navController.navigate(Routes.INCIDENTS)
                },
                onGoToSyncStatus = {
                    navController.navigate(Routes.SYNC_STATUS)
                },
                onOpenPriorityEquipment = { equipmentId ->
                    navController.navigate(Routes.equipmentDetailRoute(equipmentId))
                }
            )
        }

        composable(Routes.EQUIPMENT_LIST) {
            EquipmentListScreen(
                onEquipmentClick = { equipmentId ->
                    navController.navigate(Routes.equipmentDetailRoute(equipmentId))
                },
                onGoToDashboard = {
                    navController.navigate(Routes.HOME) {
                        launchSingleTop = true
                    }
                },
                onGoToEquipmentList = {
                    navController.navigate(Routes.EQUIPMENT_LIST) {
                        launchSingleTop = true
                    }
                },
                onGoToIncidents = {
                    navController.navigate(Routes.INCIDENTS)
                },
                onGoToSyncStatus = {
                    navController.navigate(Routes.SYNC_STATUS)
                }
            )
        }

        composable(
            route = Routes.EQUIPMENT_DETAIL,
            arguments = listOf(
                navArgument("equipmentId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val equipmentId = backStackEntry.arguments?.getString("equipmentId").orEmpty()

            EquipmentDetailScreen(
                equipmentId = equipmentId,
                onBack = { navController.popBackStack() },
                onStartInspection = {
                    navController.navigate(Routes.inspectionRoute(equipmentId))
                }
            )
        }

        composable(
            route = Routes.INSPECTION,
            arguments = listOf(
                navArgument("equipmentId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val equipmentId = backStackEntry.arguments?.getString("equipmentId").orEmpty()

            InspectionChecklistScreen(
                equipmentId = equipmentId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.PENDING_INSPECTIONS) {
            PendingInspectionsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.INCIDENTS) {
            IncidentListScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SYNC_STATUS) {
            SyncStatusScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}