package com.olfa.aircraftmaintenance.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
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
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olfa.aircraftmaintenance.presentation.components.AppBottomBar
import com.olfa.aircraftmaintenance.presentation.components.AppBottomTab
import com.olfa.aircraftmaintenance.ui.theme.BlueSoft
import com.olfa.aircraftmaintenance.ui.theme.BorderSoft
import com.olfa.aircraftmaintenance.ui.theme.DangerRed
import com.olfa.aircraftmaintenance.ui.theme.IceBlue
import com.olfa.aircraftmaintenance.ui.theme.NavyDark
import com.olfa.aircraftmaintenance.ui.theme.OceanBlue
import com.olfa.aircraftmaintenance.ui.theme.SuccessGreen
import com.olfa.aircraftmaintenance.ui.theme.SurfaceSoft
import com.olfa.aircraftmaintenance.ui.theme.TextDark
import com.olfa.aircraftmaintenance.ui.theme.TextSoft
import com.olfa.aircraftmaintenance.ui.theme.WarningOrange

private data class SummaryCardUi(
    val title: String,
    val value: String,
    val description: String,
    val icon: ImageVector,
    val accent: Color,
    val iconBackground: Color,
    val cardBackground: Color,
    val onClick: () -> Unit
)

private data class PriorityEquipmentUi(
    val id: String,
    val code: String,
    val name: String,
    val location: String,
    val status: String,
    val dueText: String
)

@Composable
fun HomeScreen(
    username: String,
    onGoToDashboard: () -> Unit,
    onGoToEquipmentList: () -> Unit,
    onGoToProfile: () -> Unit,
    onGoToPendingInspections: () -> Unit,
    onGoToIncidents: () -> Unit,
    onGoToSyncStatus: () -> Unit,
    onOpenPriorityEquipment: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val summaryCards = listOf(
        SummaryCardUi(
            title = "Équipements",
            value = "24",
            description = "Liste complète des actifs",
            icon = Icons.Outlined.Build,
            accent = NavyDark,
            iconBackground = Color(0xFFEFF4FB),
            cardBackground = Color(0xFFF7F9FC),
            onClick = onGoToEquipmentList
        ),
        SummaryCardUi(
            title = "Inspections",
            value = "05",
            description = "À traiter aujourd’hui",
            icon = Icons.Outlined.Schedule,
            accent = WarningOrange,
            iconBackground = Color(0xFFFFF6DF),
            cardBackground = Color(0xFFF7F9FC),
            onClick = onGoToPendingInspections
        ),
        SummaryCardUi(
            title = "Incidents",
            value = "02",
            description = "Alertes récentes",
            icon = Icons.Outlined.Notifications,
            accent = DangerRed,
            iconBackground = Color(0xFFFFECEF),
            cardBackground = Color(0xFFF7F9FC),
            onClick = onGoToIncidents
        ),
        SummaryCardUi(
            title = "Synchronisation",
            value = "03",
            description = "Éléments non envoyés",
            icon = Icons.Outlined.Sync,
            accent = OceanBlue,
            iconBackground = Color(0xFFEFF4FB),
            cardBackground = Color(0xFFF7F9FC),
            onClick = onGoToSyncStatus
        )
    )

    val priorityEquipments = listOf(
        PriorityEquipmentUi(
            id = "EQ-001",
            code = "EQ-001",
            name = "Pompe hydraulique",
            location = "Hangar A",
            status = "Avertissement",
            dueText = "Inspection aujourd’hui"
        ),
        PriorityEquipmentUi(
            id = "EQ-002",
            code = "EQ-002",
            name = "Valve carburant",
            location = "Hangar B",
            status = "Critique",
            dueText = "Contrôle immédiat"
        ),
        PriorityEquipmentUi(
            id = "EQ-003",
            code = "EQ-003",
            name = "Unité de refroidissement",
            location = "Hangar C",
            status = "OK",
            dueText = "Suivi programmé"
        ),
        PriorityEquipmentUi(
            id = "EQ-004",
            code = "EQ-004",
            name = "Capteur de pression",
            location = "Hangar D",
            status = "Avertissement",
            dueText = "Avant fin de journée"
        )
    ).filter {
        searchQuery.isBlank() ||
                it.code.contains(searchQuery, ignoreCase = true) ||
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.location.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        containerColor = SurfaceSoft,
        bottomBar = {
            AppBottomBar(
                selectedTab = AppBottomTab.HOME,
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
            contentPadding = PaddingValues(top = 12.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                HomeHeader(
                    username = username,
                    onProfileClick = onGoToProfile
                )
            }

            item {
                SearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it }
                )
            }

            item {
                SectionHeader(
                    title = "Vue d’ensemble",
                    subtitle = "Les informations essentielles de la journée"
                )
            }

            item {
                SummaryCardsGrid(cards = summaryCards)
            }

            item {
                SectionHeader(
                    title = "Équipements prioritaires",
                    subtitle = "Appuyez sur un élément pour voir son détail"
                )
            }

            if (priorityEquipments.isEmpty()) {
                item {
                    EmptySearchResultCard()
                }
            } else {
                items(priorityEquipments) { equipment ->
                    PriorityEquipmentCard(
                        equipment = equipment,
                        onClick = { onOpenPriorityEquipment(equipment.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeHeader(
    username: String,
    onProfileClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Bienvenue,",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextSoft
                )

                Text(
                    text = username,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextDark,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Text(
                    text = "Prêt pour les inspections du jour",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSoft,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }

            Surface(
                modifier = Modifier
                    .size(54.dp)
                    .clickable { onProfileClick() },
                shape = CircleShape,
                color = NavyDark,
                shadowElevation = 4.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Profil",
                        tint = IceBlue
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        placeholder = {
            Text("Rechercher un équipement...")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Recherche",
                tint = TextSoft
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = BorderSoft,
            unfocusedIndicatorColor = BorderSoft,
            focusedTextColor = TextDark,
            unfocusedTextColor = TextDark,
            focusedPlaceholderColor = TextSoft,
            unfocusedPlaceholderColor = TextSoft
        )
    )
}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = TextDark,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSoft,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun SummaryCardsGrid(
    cards: List<SummaryCardUi>
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SummaryCard(
                modifier = Modifier.weight(1f),
                card = cards[0]
            )
            SummaryCard(
                modifier = Modifier.weight(1f),
                card = cards[1]
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SummaryCard(
                modifier = Modifier.weight(1f),
                card = cards[2]
            )
            SummaryCard(
                modifier = Modifier.weight(1f),
                card = cards[3]
            )
        }
    }
}

@Composable
private fun SummaryCard(
    modifier: Modifier = Modifier,
    card: SummaryCardUi
) {
    Card(
        modifier = modifier
            .requiredHeight(160.dp)
            .clickable { card.onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = card.cardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = card.iconBackground,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = card.icon,
                    contentDescription = null,
                    tint = card.accent
                )
            }

            Text(
                text = card.value,
                style = MaterialTheme.typography.headlineMedium,
                color = card.accent,
                fontWeight = FontWeight.ExtraBold
            )

            Column {
                Text(
                    text = card.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextDark,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = card.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSoft,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}

@Composable
private fun PriorityEquipmentCard(
    equipment: PriorityEquipmentUi,
    onClick: () -> Unit
) {
    val statusColor = when (equipment.status) {
        "OK" -> SuccessGreen
        "Avertissement" -> WarningOrange
        "Critique" -> DangerRed
        else -> OceanBlue
    }

    val cardBackground = Color(0xFFF7F9FC)

    val iconBackground = when (equipment.status) {
        "OK" -> Color(0xFFEAF8F1)
        "Avertissement" -> Color(0xFFFFF5D9)
        "Critique" -> Color(0xFFFFE9EE)
        else -> BlueSoft
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = iconBackground,
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

            Spacer(modifier = Modifier.width(12.dp))

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
                    text = "${equipment.code} • ${equipment.location}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSoft,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .background(
                            color = statusColor.copy(alpha = 0.10f),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = equipment.status,
                        style = MaterialTheme.typography.labelMedium,
                        color = statusColor,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = equipment.dueText,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSoft,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = "Ouvrir",
                tint = TextSoft
            )
        }
    }
}

@Composable
private fun EmptySearchResultCard() {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Text(
            text = "Aucun équipement trouvé pour cette recherche.",
            color = TextSoft,
            modifier = Modifier.padding(16.dp)
        )
    }
}