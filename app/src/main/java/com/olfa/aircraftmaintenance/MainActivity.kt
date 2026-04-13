package com.olfa.aircraftmaintenance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.olfa.aircraftmaintenance.presentation.navigation.AppNavigation
import com.olfa.aircraftmaintenance.ui.theme.AircraftMaintenanceCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AircraftMaintenanceCompanionTheme {
                AppNavigation()
            }
        }
    }
}