package com.olfa.aircraftmaintenance.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olfa.aircraftmaintenance.ui.theme.NavyDark
import com.olfa.aircraftmaintenance.ui.theme.TextSoft

enum class AppBottomTab {
    HOME,
    EQUIPMENT,
    INCIDENTS,
    SYNC
}

@Composable
fun AppBottomBar(
    selectedTab: AppBottomTab,
    onGoToDashboard: () -> Unit,
    onGoToEquipmentList: () -> Unit,
    onGoToIncidents: () -> Unit,
    onGoToSyncStatus: () -> Unit
) {
    Surface(
        color = Color.White,
        tonalElevation = 6.dp,
        modifier = Modifier.navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomBarItem(
                icon = Icons.Outlined.GridView,
                label = "Accueil",
                selected = selectedTab == AppBottomTab.HOME,
                onClick = onGoToDashboard
            )

            BottomBarItem(
                icon = Icons.Outlined.Build,
                label = "Équip.",
                selected = selectedTab == AppBottomTab.EQUIPMENT,
                onClick = onGoToEquipmentList
            )

            BottomBarItem(
                icon = Icons.Outlined.Notifications,
                label = "Incidents",
                selected = selectedTab == AppBottomTab.INCIDENTS,
                onClick = onGoToIncidents
            )

            BottomBarItem(
                icon = Icons.Outlined.Sync,
                label = "Sync",
                selected = selectedTab == AppBottomTab.SYNC,
                onClick = onGoToSyncStatus
            )
        }
    }
}

@Composable
private fun BottomBarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(
                    color = if (selected) NavyDark else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selected) Color.White else TextSoft
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) NavyDark else TextSoft,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}