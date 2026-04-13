package com.olfa.aircraftmaintenance.presentation.screens.sync

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CloudDone
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olfa.aircraftmaintenance.ui.theme.NavyDark
import com.olfa.aircraftmaintenance.ui.theme.OceanBlue
import com.olfa.aircraftmaintenance.ui.theme.SuccessGreen
import com.olfa.aircraftmaintenance.ui.theme.SurfaceSoft
import com.olfa.aircraftmaintenance.ui.theme.TextDark
import com.olfa.aircraftmaintenance.ui.theme.TextSoft
import com.olfa.aircraftmaintenance.ui.theme.WarningOrange

@Composable
fun SyncStatusScreen(
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = SurfaceSoft
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceSoft)
                .statusBarsPadding()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            SyncTopBar(
                title = "Synchronisation",
                onBack = onBack
            )

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "État actuel",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSoft
                    )

                    Text(
                        text = "Mode hors ligne disponible",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextDark,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Les éléments en attente seront envoyés dès que le réseau sera disponible.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSoft
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SyncMiniCard(
                    modifier = Modifier.weight(1f),
                    value = "2",
                    label = "Inspections",
                    accent = WarningOrange
                )
                SyncMiniCard(
                    modifier = Modifier.weight(1f),
                    value = "1",
                    label = "Incidents",
                    accent = WarningOrange
                )
                SyncMiniCard(
                    modifier = Modifier.weight(1f),
                    value = "09:42",
                    label = "Dernière sync",
                    accent = SuccessGreen
                )
            }

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Détails",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextDark,
                        fontWeight = FontWeight.Bold
                    )

                    SyncInfoRow(
                        icon = Icons.Outlined.CloudOff,
                        title = "État réseau",
                        value = "Connexion instable",
                        accent = OceanBlue
                    )

                    SyncInfoRow(
                        icon = Icons.Outlined.CloudUpload,
                        title = "Inspections en attente",
                        value = "2 éléments",
                        accent = WarningOrange
                    )

                    SyncInfoRow(
                        icon = Icons.Outlined.Sync,
                        title = "Incidents en attente",
                        value = "1 élément",
                        accent = WarningOrange
                    )

                    SyncInfoRow(
                        icon = Icons.Outlined.CloudDone,
                        title = "Dernière synchronisation",
                        value = "Aujourd’hui à 09:42",
                        accent = SuccessGreen
                    )
                }
            }

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
            ) {
                Text("Lancer la synchronisation", color = Color.White)
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text("Retour")
            }
        }
    }
}

@Composable
private fun SyncTopBar(
    title: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(42.dp)
                .clickable { onBack() },
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Retour",
                    tint = TextDark
                )
            }
        }

        Spacer(modifier = Modifier.padding(horizontal = 6.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = TextDark,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun SyncMiniCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    accent: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = accent,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = TextDark,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun SyncInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    accent: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(
                    color = accent.copy(alpha = 0.10f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accent
            )
        }

        Spacer(modifier = Modifier.padding(horizontal = 6.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = TextSoft
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = TextDark,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}