package com.olfa.aircraftmaintenance.presentation.screens.equipment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olfa.aircraftmaintenance.presentation.components.AppBottomBar
import com.olfa.aircraftmaintenance.presentation.components.AppBottomTab
import com.olfa.aircraftmaintenance.ui.theme.BorderSoft
import com.olfa.aircraftmaintenance.ui.theme.DangerRed
import com.olfa.aircraftmaintenance.ui.theme.NavyDark
import com.olfa.aircraftmaintenance.ui.theme.OceanBlue
import com.olfa.aircraftmaintenance.ui.theme.SuccessGreen
import com.olfa.aircraftmaintenance.ui.theme.SurfaceSoft
import com.olfa.aircraftmaintenance.ui.theme.TextDark
import com.olfa.aircraftmaintenance.ui.theme.TextSoft
import com.olfa.aircraftmaintenance.ui.theme.WarningOrange

data class EquipmentUiModel(
    val id: String,
    val name: String,
    val location: String,
    val status: String,
    val type: String,
    val nextInspection: String,
    val isSynced: Boolean
)

private enum class EquipmentStatusFilter {
    ALL,
    OK,
    WARNING,
    CRITICAL
}

@Composable
fun EquipmentListScreen(
    onEquipmentClick: (String) -> Unit,
    onGoToDashboard: () -> Unit,
    onGoToEquipmentList: () -> Unit,
    onGoToIncidents: () -> Unit,
    onGoToSyncStatus: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(EquipmentStatusFilter.ALL) }
    var filterMenuExpanded by remember { mutableStateOf(false) }

    val equipments = listOf(
        EquipmentUiModel(
            id = "EQ-001",
            name = "Pompe hydraulique",
            location = "Hangar A",
            status = "Avertissement",
            type = "Hydraulique",
            nextInspection = "Aujourd’hui • 14:00",
            isSynced = true
        ),
        EquipmentUiModel(
            id = "EQ-002",
            name = "Valve carburant",
            location = "Hangar B",
            status = "Critique",
            type = "Carburant",
            nextInspection = "Immédiate",
            isSynced = false
        ),
        EquipmentUiModel(
            id = "EQ-003",
            name = "Unité de refroidissement",
            location = "Hangar C",
            status = "OK",
            type = "Refroidissement",
            nextInspection = "18 avr • 09:30",
            isSynced = true
        ),
        EquipmentUiModel(
            id = "EQ-004",
            name = "Capteur de pression",
            location = "Hangar D",
            status = "Avertissement",
            type = "Capteurs",
            nextInspection = "Aujourd’hui • 17:00",
            isSynced = false
        ),
        EquipmentUiModel(
            id = "EQ-005",
            name = "Générateur auxiliaire",
            location = "Hangar A",
            status = "OK",
            type = "Énergie",
            nextInspection = "20 avr • 11:00",
            isSynced = true
        ),
        EquipmentUiModel(
            id = "EQ-006",
            name = "Système de freinage",
            location = "Hangar B",
            status = "Critique",
            type = "Freinage",
            nextInspection = "Immédiate",
            isSynced = false
        )
    )

    val filteredEquipments = equipments
        .filter { equipment ->
            val matchesSearch = searchQuery.isBlank() ||
                    equipment.id.contains(searchQuery, ignoreCase = true) ||
                    equipment.name.contains(searchQuery, ignoreCase = true) ||
                    equipment.location.contains(searchQuery, ignoreCase = true) ||
                    equipment.type.contains(searchQuery, ignoreCase = true)

            val matchesFilter = when (selectedFilter) {
                EquipmentStatusFilter.ALL -> true
                EquipmentStatusFilter.OK -> equipment.status == "OK"
                EquipmentStatusFilter.WARNING -> equipment.status == "Avertissement"
                EquipmentStatusFilter.CRITICAL -> equipment.status == "Critique"
            }

            matchesSearch && matchesFilter
        }
        .sortedBy { equipmentStatusPriority(it.status) }

    val totalCount = equipments.size
    val criticalCount = equipments.count { it.status == "Critique" }
    val warningCount = equipments.count { it.status == "Avertissement" }
    val okCount = equipments.count { it.status == "OK" }

    Scaffold(
        containerColor = SurfaceSoft,
        bottomBar = {
            AppBottomBar(
                selectedTab = AppBottomTab.EQUIPMENT,
                onGoToDashboard = onGoToDashboard,
                onGoToEquipmentList = onGoToEquipmentList,
                onGoToIncidents = onGoToIncidents,
                onGoToSyncStatus = onGoToSyncStatus
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceSoft)
                .statusBarsPadding()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 14.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = "Équipements",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TextDark,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            item {
                MinimalOverviewGrid(
                    totalCount = totalCount,
                    criticalCount = criticalCount,
                    warningCount = warningCount,
                    okCount = okCount
                )
            }

            item {
                SearchAndFilterBar(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    selectedFilter = selectedFilter,
                    onOpenFilter = { filterMenuExpanded = true }
                )
            }

            item {
                Box {
                    DropdownMenu(
                        expanded = filterMenuExpanded,
                        onDismissRequest = { filterMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Tous") },
                            onClick = {
                                selectedFilter = EquipmentStatusFilter.ALL
                                filterMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("OK") },
                            onClick = {
                                selectedFilter = EquipmentStatusFilter.OK
                                filterMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Avertissement") },
                            onClick = {
                                selectedFilter = EquipmentStatusFilter.WARNING
                                filterMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Critique") },
                            onClick = {
                                selectedFilter = EquipmentStatusFilter.CRITICAL
                                filterMenuExpanded = false
                            }
                        )
                    }
                }
            }

            item {
                ListHeader(
                    resultCount = filteredEquipments.size,
                    selectedFilter = selectedFilter
                )
            }

            if (filteredEquipments.isEmpty()) {
                item {
                    EquipmentEmptyState()
                }
            } else {
                items(filteredEquipments) { equipment ->
                    EquipmentItemCard(
                        equipment = equipment,
                        onClick = { onEquipmentClick(equipment.id) }
                    )
                }
            }
        }
    }
}

private fun equipmentStatusPriority(status: String): Int {
    return when (status) {
        "Critique" -> 0
        "Avertissement" -> 1
        "OK" -> 2
        else -> 3
    }
}

@Composable
private fun MinimalOverviewGrid(
    totalCount: Int,
    criticalCount: Int,
    warningCount: Int,
    okCount: Int
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MinimalStatCard(
                modifier = Modifier.weight(1f),
                value = totalCount.toString(),
                label = "Total",
                borderColor = OceanBlue
            )
            MinimalStatCard(
                modifier = Modifier.weight(1f),
                value = criticalCount.toString(),
                label = "Critiques",
                borderColor = DangerRed
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MinimalStatCard(
                modifier = Modifier.weight(1f),
                value = warningCount.toString(),
                label = "Avertissement",
                borderColor = WarningOrange
            )
            MinimalStatCard(
                modifier = Modifier.weight(1f),
                value = okCount.toString(),
                label = "OK",
                borderColor = SuccessGreen
            )
        }
    }
}

@Composable
private fun MinimalStatCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    borderColor: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, borderColor.copy(alpha = 0.65f)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = borderColor,
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
private fun SearchAndFilterBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedFilter: EquipmentStatusFilter,
    onOpenFilter: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.weight(1f),
            singleLine = true,
            shape = RoundedCornerShape(22.dp),
            placeholder = {
                Text("Rechercher")
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Recherche",
                    tint = NavyDark
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = OceanBlue,
                unfocusedIndicatorColor = BorderSoft,
                focusedTextColor = TextDark,
                unfocusedTextColor = TextDark,
                focusedPlaceholderColor = TextSoft,
                unfocusedPlaceholderColor = TextSoft
            )
        )

        Surface(
            modifier = Modifier
                .size(56.dp)
                .clickable { onOpenFilter() },
            shape = RoundedCornerShape(18.dp),
            color = Color.White,
            border = BorderStroke(1.dp, BorderSoft)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Outlined.Tune,
                    contentDescription = "Filtrer",
                    tint = when (selectedFilter) {
                        EquipmentStatusFilter.ALL -> NavyDark
                        EquipmentStatusFilter.OK -> SuccessGreen
                        EquipmentStatusFilter.WARNING -> WarningOrange
                        EquipmentStatusFilter.CRITICAL -> DangerRed
                    }
                )
            }
        }
    }
}

@Composable
private fun ListHeader(
    resultCount: Int,
    selectedFilter: EquipmentStatusFilter
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$resultCount équipement(s)",
            style = MaterialTheme.typography.titleMedium,
            color = TextDark,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = when (selectedFilter) {
                EquipmentStatusFilter.ALL -> "• Tous"
                EquipmentStatusFilter.OK -> "• OK"
                EquipmentStatusFilter.WARNING -> "• Avertissement"
                EquipmentStatusFilter.CRITICAL -> "• Critique"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = TextSoft
        )
    }
}

@Composable
private fun EquipmentItemCard(
    equipment: EquipmentUiModel,
    onClick: () -> Unit
) {
    val statusColor = when (equipment.status) {
        "OK" -> SuccessGreen
        "Avertissement" -> WarningOrange
        "Critique" -> DangerRed
        else -> OceanBlue
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
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

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = equipment.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextDark,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${equipment.id} • ${equipment.location}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSoft
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (!equipment.isSynced) {
                        SmallBadge(
                            text = "Sync",
                            textColor = WarningOrange,
                            backgroundColor = WarningOrange.copy(alpha = 0.10f)
                        )
                    }

                    SmallBadge(
                        text = equipment.status,
                        textColor = statusColor,
                        backgroundColor = statusColor.copy(alpha = 0.10f)
                    )

                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = "Ouvrir",
                        tint = TextSoft
                    )
                }
            }

            Text(
                text = "${equipment.type} • Inspection : ${equipment.nextInspection}",
                style = MaterialTheme.typography.bodySmall,
                color = TextSoft
            )
        }
    }
}

@Composable
private fun SmallBadge(
    text: String,
    textColor: Color,
    backgroundColor: Color
) {
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 8.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun EquipmentEmptyState() {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Aucun équipement trouvé",
                style = MaterialTheme.typography.titleMedium,
                color = TextDark,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Essayez un autre mot-clé ou changez le filtre.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSoft
            )
        }
    }
}