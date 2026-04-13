package com.olfa.aircraftmaintenance.presentation.screens.incident

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olfa.aircraftmaintenance.ui.theme.DangerRed
import com.olfa.aircraftmaintenance.ui.theme.NavyDark
import com.olfa.aircraftmaintenance.ui.theme.SurfaceSoft
import com.olfa.aircraftmaintenance.ui.theme.TextDark
import com.olfa.aircraftmaintenance.ui.theme.TextSoft
import com.olfa.aircraftmaintenance.ui.theme.WarningOrange

data class IncidentUi(
    val title: String,
    val equipmentCode: String,
    val severity: String,
    val date: String
)

@Composable
fun IncidentListScreen(
    onBack: () -> Unit
) {
    val incidents = listOf(
        IncidentUi("Abnormal pressure detected", "EQ-002", "High", "2026-04-10"),
        IncidentUi("Connector wear observed", "EQ-005", "Medium", "2026-04-09"),
        IncidentUi("Cooling fluctuation", "EQ-003", "Low", "2026-04-08")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceSoft)
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Incidents récents",
            style = MaterialTheme.typography.headlineSmall,
            color = TextDark,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Suivi des incidents déclarés.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSoft
        )

        incidents.forEach { incident ->
            val severityColor = when (incident.severity) {
                "High" -> DangerRed
                "Medium" -> WarningOrange
                else -> TextSoft
            }

            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = incident.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextDark,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Équipement : ${incident.equipmentCode}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSoft
                    )

                    Text(
                        text = "Criticité : ${incident.severity}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = severityColor,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Date : ${incident.date}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSoft
                    )
                }
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
        ) {
            Text("Retour", color = Color.White)
        }
    }
}