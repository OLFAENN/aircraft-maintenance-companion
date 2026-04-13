package com.olfa.aircraftmaintenance.presentation.screens.equipment

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olfa.aircraftmaintenance.ui.theme.DangerRed
import com.olfa.aircraftmaintenance.ui.theme.NavyDark
import com.olfa.aircraftmaintenance.ui.theme.OceanBlue
import com.olfa.aircraftmaintenance.ui.theme.SuccessGreen
import com.olfa.aircraftmaintenance.ui.theme.SurfaceSoft
import com.olfa.aircraftmaintenance.ui.theme.TextDark
import com.olfa.aircraftmaintenance.ui.theme.TextSoft
import com.olfa.aircraftmaintenance.ui.theme.WarningOrange

private data class EquipmentDetailUi(
    val id: String,
    val name: String,
    val type: String,
    val location: String,
    val status: String,
    val lastInspection: String,
    val nextInspection: String,
    val syncStatus: String,
    val actionText: String
)

@Composable
fun EquipmentDetailScreen(
    equipmentId: String,
    onBack: () -> Unit,
    onStartInspection: () -> Unit
) {
    val equipment = rememberEquipmentDetail(equipmentId)
    val statusColor = statusColorFor(equipment.status)

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
            DetailTopBar(
                title = "Équipement",
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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    color = statusColor.copy(alpha = 0.10f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Build,
                                contentDescription = null,
                                tint = statusColor
                            )
                        }

                        Spacer(modifier = Modifier.padding(horizontal = 6.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = equipment.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = TextDark,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = equipment.id,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSoft
                            )
                        }

                        DetailBadge(
                            text = equipment.status,
                            textColor = statusColor,
                            backgroundColor = statusColor.copy(alpha = 0.10f)
                        )
                    }

                    Text(
                        text = equipment.actionText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = statusColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                InfoMiniCard(
                    modifier = Modifier.weight(1f),
                    title = "Type",
                    value = equipment.type
                )
                InfoMiniCard(
                    modifier = Modifier.weight(1f),
                    title = "Localisation",
                    value = equipment.location
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
                        text = "Informations",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextDark,
                        fontWeight = FontWeight.Bold
                    )

                    InfoRow(
                        icon = Icons.Outlined.LocationOn,
                        label = "Zone",
                        value = equipment.location
                    )

                    InfoRow(
                        icon = Icons.Outlined.Schedule,
                        label = "Dernière inspection",
                        value = equipment.lastInspection
                    )

                    InfoRow(
                        icon = Icons.Outlined.Schedule,
                        label = "Prochaine inspection",
                        value = equipment.nextInspection
                    )

                    InfoRow(
                        icon = Icons.Outlined.Sync,
                        label = "Synchronisation",
                        value = equipment.syncStatus
                    )
                }
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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Actions",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextDark,
                        fontWeight = FontWeight.Bold
                    )

                    ActionRow(
                        title = "Lancer une inspection",
                        subtitle = "Ouvrir la checklist de contrôle",
                        onClick = onStartInspection
                    )
                }
            }

            Button(
                onClick = onStartInspection,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
            ) {
                Text("Commencer l’inspection", color = Color.White)
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
private fun DetailTopBar(
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
private fun InfoMiniCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String
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
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = TextSoft
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = Color(0xFFF4F7FB),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = NavyDark
            )
        }

        Spacer(modifier = Modifier.padding(horizontal = 6.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
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

@Composable
private fun DetailBadge(
    text: String,
    textColor: Color,
    backgroundColor: Color
) {
    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ActionRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = TextDark,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSoft
            )
        }

        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = TextSoft
        )
    }
}

private fun statusColorFor(status: String): Color {
    return when (status) {
        "OK" -> SuccessGreen
        "Avertissement" -> WarningOrange
        "Critique" -> DangerRed
        else -> OceanBlue
    }
}

private fun rememberEquipmentDetail(equipmentId: String): EquipmentDetailUi {
    return when (equipmentId) {
        "EQ-001" -> EquipmentDetailUi(
            id = "EQ-001",
            name = "Pompe hydraulique",
            type = "Hydraulique",
            location = "Hangar A",
            status = "Avertissement",
            lastInspection = "10 avr 2026",
            nextInspection = "Aujourd’hui • 14:00",
            syncStatus = "Synchronisé",
            actionText = "Inspection requise aujourd’hui"
        )
        "EQ-002" -> EquipmentDetailUi(
            id = "EQ-002",
            name = "Valve carburant",
            type = "Carburant",
            location = "Hangar B",
            status = "Critique",
            lastInspection = "08 avr 2026",
            nextInspection = "Immédiate",
            syncStatus = "En attente de synchronisation",
            actionText = "Contrôle immédiat requis"
        )
        "EQ-003" -> EquipmentDetailUi(
            id = "EQ-003",
            name = "Unité de refroidissement",
            type = "Refroidissement",
            location = "Hangar C",
            status = "OK",
            lastInspection = "09 avr 2026",
            nextInspection = "18 avr 2026 • 09:30",
            syncStatus = "Synchronisé",
            actionText = "Suivi planifié"
        )
        "EQ-004" -> EquipmentDetailUi(
            id = "EQ-004",
            name = "Capteur de pression",
            type = "Capteurs",
            location = "Hangar D",
            status = "Avertissement",
            lastInspection = "10 avr 2026",
            nextInspection = "Aujourd’hui • 17:00",
            syncStatus = "En attente de synchronisation",
            actionText = "Vérification avant fin de journée"
        )
        "EQ-005" -> EquipmentDetailUi(
            id = "EQ-005",
            name = "Générateur auxiliaire",
            type = "Énergie",
            location = "Hangar A",
            status = "OK",
            lastInspection = "07 avr 2026",
            nextInspection = "20 avr 2026 • 11:00",
            syncStatus = "Synchronisé",
            actionText = "Maintenance planifiée"
        )
        else -> EquipmentDetailUi(
            id = equipmentId,
            name = "Système de freinage",
            type = "Freinage",
            location = "Hangar B",
            status = "Critique",
            lastInspection = "08 avr 2026",
            nextInspection = "Immédiate",
            syncStatus = "En attente de synchronisation",
            actionText = "Équipement à isoler et inspecter"
        )
    }
}