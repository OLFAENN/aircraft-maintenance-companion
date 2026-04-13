package com.olfa.aircraftmaintenance.presentation.screens.profile

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.olfa.aircraftmaintenance.ui.theme.NavyDark
import com.olfa.aircraftmaintenance.ui.theme.SurfaceSoft
import com.olfa.aircraftmaintenance.ui.theme.TextDark
import com.olfa.aircraftmaintenance.ui.theme.TextSoft

@Composable
fun ProfileScreen(
    onBack: () -> Unit
) {
    var username by remember { mutableStateOf("nom_utilisateur") }
    var email by remember { mutableStateOf("olfa@example.com") }
    var role by remember { mutableStateOf("Maintenance Technician") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceSoft)
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Profil",
            style = MaterialTheme.typography.headlineSmall,
            color = TextDark,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Modifiez vos informations personnelles.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSoft
        )

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Nom d'utilisateur") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Rôle") },
                    singleLine = true
                )
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
        ) {
            Text("Enregistrer et retourner", color = Color.White)
        }
    }
}