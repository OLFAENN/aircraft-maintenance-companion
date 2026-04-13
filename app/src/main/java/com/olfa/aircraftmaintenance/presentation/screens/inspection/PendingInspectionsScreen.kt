package com.olfa.aircraftmaintenance.presentation.screens.inspection

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
import com.olfa.aircraftmaintenance.ui.theme.NavyDark
import com.olfa.aircraftmaintenance.ui.theme.SurfaceSoft
import com.olfa.aircraftmaintenance.ui.theme.TextDark
import com.olfa.aircraftmaintenance.ui.theme.TextSoft
import com.olfa.aircraftmaintenance.ui.theme.WarningOrange

data class PendingInspectionUi(
    val equipmentCode: String,
    val equipmentName: String,
    val dueDate: String,
    val priority: String
)

@Composable
fun PendingInspectionsScreen(
    onBack: () -> Unit
) {
    val pendingInspections = listOf(
        PendingInspectionUi("EQ-002", "Fuel Valve", "2026-04-11", "High"),
        PendingInspectionUi("EQ-005", "Hydraulic Circuit", "2026-04-12", "Medium"),
        PendingInspectionUi("EQ-009", "Cooling Module", "2026-04-13", "High")
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
            text = "Inspections en attente",
            style = MaterialTheme.typography.headlineSmall,
            color = TextDark,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Les inspections à traiter rapidement.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSoft
        )

        pendingInspections.forEach { inspection ->
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "${inspection.equipmentCode} - ${inspection.equipmentName}",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextDark,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Date prévue : ${inspection.dueDate}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSoft
                    )

                    Text(
                        text = "Priorité : ${inspection.priority}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = WarningOrange,
                        fontWeight = FontWeight.SemiBold
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