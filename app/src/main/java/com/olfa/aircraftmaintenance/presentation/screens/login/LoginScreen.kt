package com.olfa.aircraftmaintenance.presentation.screens.login

import android.util.Patterns
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private enum class AuthMode {
    LOGIN, REGISTER
}

private val AviationDark = Color(0xFF0B1F33)
private val AviationBlue = Color(0xFF163A5F)
private val AviationSteel = Color(0xFF607D8B)
private val AviationCyan = Color(0xFF4FC3F7)
private val AviationBg = Color(0xFFF4F7FA)
private val CardBg = Color(0xFFFFFFFF)

private val SuccessBg = Color(0xFFE8F5E9)
private val SuccessText = Color(0xFF1B5E20)
private val ErrorBg = Color(0xFFFFEBEE)
private val ErrorText = Color(0xFFB71C1C)

private val usernameRegex = Regex("^[A-Za-z0-9_.-]{3,20}$")
private val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,64}$")

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit
) {
    var mode by rememberSaveable { mutableStateOf(AuthMode.LOGIN) }

    var loginIdentifier by rememberSaveable { mutableStateOf("") }
    var loginPassword by rememberSaveable { mutableStateOf("") }

    var registerUsername by rememberSaveable { mutableStateOf("") }
    var registerEmail by rememberSaveable { mutableStateOf("") }
    var registerPassword by rememberSaveable { mutableStateOf("") }
    var registerConfirmPassword by rememberSaveable { mutableStateOf("") }

    var showLoginPassword by rememberSaveable { mutableStateOf(false) }
    var showRegisterPassword by rememberSaveable { mutableStateOf(false) }
    var showRegisterConfirmPassword by rememberSaveable { mutableStateOf(false) }

    var bannerMessage by rememberSaveable { mutableStateOf<String?>(null) }
    var bannerIsError by rememberSaveable { mutableStateOf(false) }

    var storedUsername by rememberSaveable { mutableStateOf("") }
    var storedEmail by rememberSaveable { mutableStateOf("") }
    var storedPassword by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AviationBg)
            .statusBarsPadding()
            .imePadding()
    ) {
        AircraftBackgroundDecor()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                color = CardBg,
                shadowElevation = 12.dp,
                tonalElevation = 2.dp
            ) {
                Column {
                    AircraftHeader()

                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AuthModeSwitcher(
                            selected = mode,
                            onSelect = {
                                mode = it
                                bannerMessage = null
                            }
                        )

                        bannerMessage?.let {
                            MessageBanner(
                                message = it,
                                isError = bannerIsError
                            )
                        }

                        if (mode == AuthMode.LOGIN) {
                            AuthTextField(
                                value = loginIdentifier,
                                onValueChange = {
                                    loginIdentifier = it
                                    bannerMessage = null
                                },
                                label = "Nom d'utilisateur ou email",
                                keyboardType = KeyboardType.Email
                            )

                            PasswordField(
                                value = loginPassword,
                                onValueChange = {
                                    loginPassword = it
                                    bannerMessage = null
                                },
                                label = "Mot de passe",
                                visible = showLoginPassword,
                                onToggleVisibility = {
                                    showLoginPassword = !showLoginPassword
                                }
                            )

                            Text(
                                text = "Accès technicien aux inspections et rapports de maintenance.",
                                style = MaterialTheme.typography.bodySmall,
                                color = AviationSteel
                            )

                            Button(
                                onClick = {
                                    val error = validateLogin(
                                        identifier = loginIdentifier,
                                        password = loginPassword
                                    )

                                    val cleanIdentifier = loginIdentifier.trim()

                                    when {
                                        error != null -> {
                                            bannerMessage = error
                                            bannerIsError = true
                                        }

                                        storedUsername.isBlank() && storedEmail.isBlank() -> {
                                            bannerMessage = "Aucun compte n'existe encore. Veuillez d'abord vous inscrire."
                                            bannerIsError = true
                                        }

                                        isCredentialMatch(
                                            identifier = loginIdentifier,
                                            password = loginPassword,
                                            storedUsername = storedUsername,
                                            storedEmail = storedEmail,
                                            storedPassword = storedPassword
                                        ) -> {
                                            bannerMessage = null
                                            onLoginSuccess(
                                                storedUsername.ifBlank { cleanIdentifier }
                                            )
                                        }

                                        else -> {
                                            bannerMessage = "Identifiants invalides."
                                            bannerIsError = true
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp),
                                shape = RoundedCornerShape(18.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AviationBlue
                                )
                            ) {
                                Text(
                                    text = "SE CONNECTER",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Pas encore de compte ? ",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "S'inscrire",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = AviationBlue,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.clickable {
                                        mode = AuthMode.REGISTER
                                        bannerMessage = null
                                    }
                                )
                            }
                        } else {
                            AuthTextField(
                                value = registerUsername,
                                onValueChange = {
                                    registerUsername = it
                                    bannerMessage = null
                                },
                                label = "Nom d'utilisateur",
                                keyboardType = KeyboardType.Text,
                                capitalization = KeyboardCapitalization.None
                            )

                            AuthTextField(
                                value = registerEmail,
                                onValueChange = {
                                    registerEmail = it
                                    bannerMessage = null
                                },
                                label = "Email",
                                keyboardType = KeyboardType.Email
                            )

                            PasswordField(
                                value = registerPassword,
                                onValueChange = {
                                    registerPassword = it
                                    bannerMessage = null
                                },
                                label = "Mot de passe",
                                visible = showRegisterPassword,
                                onToggleVisibility = {
                                    showRegisterPassword = !showRegisterPassword
                                }
                            )

                            PasswordField(
                                value = registerConfirmPassword,
                                onValueChange = {
                                    registerConfirmPassword = it
                                    bannerMessage = null
                                },
                                label = "Confirmer le mot de passe",
                                visible = showRegisterConfirmPassword,
                                onToggleVisibility = {
                                    showRegisterConfirmPassword = !showRegisterConfirmPassword
                                }
                            )

                            Text(
                                text = "8 caractères minimum, 1 majuscule, 1 minuscule et 1 chiffre.",
                                style = MaterialTheme.typography.bodySmall,
                                color = AviationSteel
                            )

                            Button(
                                onClick = {
                                    val error = validateRegister(
                                        username = registerUsername,
                                        email = registerEmail,
                                        password = registerPassword,
                                        confirmPassword = registerConfirmPassword
                                    )

                                    if (error != null) {
                                        bannerMessage = error
                                        bannerIsError = true
                                    } else {
                                        storedUsername = registerUsername.trim()
                                        storedEmail = registerEmail.trim().lowercase()
                                        storedPassword = registerPassword

                                        loginIdentifier = storedEmail
                                        loginPassword = ""

                                        registerPassword = ""
                                        registerConfirmPassword = ""

                                        bannerMessage = "Compte créé avec succès. Vous pouvez maintenant vous connecter."
                                        bannerIsError = false
                                        mode = AuthMode.LOGIN
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp),
                                shape = RoundedCornerShape(18.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AviationBlue
                                )
                            ) {
                                Text(
                                    text = "CRÉER MON COMPTE",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Vous avez déjà un compte ? ",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Se connecter",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = AviationBlue,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.clickable {
                                        mode = AuthMode.LOGIN
                                        bannerMessage = null
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun AircraftHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 28.dp,
                    topEnd = 28.dp,
                    bottomStart = 44.dp,
                    bottomEnd = 44.dp
                )
            )
            .background(
                Brush.linearGradient(
                    colors = listOf(AviationDark, AviationBlue, AviationSteel)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Aircraft Maintenance\nCompanion",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AuthModeSwitcher(
    selected: AuthMode,
    onSelect: (AuthMode) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFEAF1F7)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            SwitchButton(
                text = "Connexion",
                selected = selected == AuthMode.LOGIN,
                onClick = { onSelect(AuthMode.LOGIN) },
                modifier = Modifier.weight(1f)
            )

            SwitchButton(
                text = "Inscription",
                selected = selected == AuthMode.REGISTER,
                onClick = { onSelect(AuthMode.REGISTER) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SwitchButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(2.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        color = if (selected) AviationBlue else Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = if (selected) Color.White else AviationBlue,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it.trimStart()) },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(18.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization
        )
    )
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    visible: Boolean,
    onToggleVisibility: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(18.dp),
        visualTransformation = if (visible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            Text(
                text = if (visible) "Masquer" else "Voir",
                color = AviationBlue,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.clickable { onToggleVisibility() }
            )
        }
    )
}

@Composable
private fun MessageBanner(
    message: String,
    isError: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (isError) ErrorBg else SuccessBg)
            .padding(12.dp)
    ) {
        Text(
            text = message,
            color = if (isError) ErrorText else SuccessText,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun AircraftBackgroundDecor() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(260.dp)
                .offset(x = (-80).dp, y = (-40).dp)
                .background(Color(0x11163A5F), CircleShape)
        )

        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.TopEnd)
                .offset(x = 90.dp, y = (-70).dp)
                .background(Color(0x140B1F33), CircleShape)
        )

        Box(
            modifier = Modifier
                .size(12.dp)
                .align(Alignment.TopStart)
                .offset(x = 52.dp, y = 145.dp)
                .background(AviationCyan, CircleShape)
        )

        Box(
            modifier = Modifier
                .size(10.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-34).dp, y = 120.dp)
                .background(AviationSteel, CircleShape)
        )

        Box(
            modifier = Modifier
                .size(8.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (-40).dp, y = 30.dp)
                .background(AviationSteel, CircleShape)
        )
    }
}

private fun validateLogin(
    identifier: String,
    password: String
): String? {
    val cleanIdentifier = identifier.trim()

    if (cleanIdentifier.isBlank() || password.isBlank()) {
        return "Veuillez remplir tous les champs."
    }

    if (cleanIdentifier.contains("@") &&
        !Patterns.EMAIL_ADDRESS.matcher(cleanIdentifier).matches()
    ) {
        return "Adresse email invalide."
    }

    if (password.length < 8) {
        return "Le mot de passe doit contenir au moins 8 caractères."
    }

    return null
}

private fun validateRegister(
    username: String,
    email: String,
    password: String,
    confirmPassword: String
): String? {
    val cleanUsername = username.trim()
    val cleanEmail = email.trim()

    if (
        cleanUsername.isBlank() ||
        cleanEmail.isBlank() ||
        password.isBlank() ||
        confirmPassword.isBlank()
    ) {
        return "Veuillez remplir tous les champs."
    }

    if (!usernameRegex.matches(cleanUsername)) {
        return "Le nom d'utilisateur doit contenir 3 à 20 caractères, sans espaces, avec lettres, chiffres, _ . ou -."
    }

    if (!Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches()) {
        return "Adresse email invalide."
    }

    if (!passwordRegex.matches(password)) {
        return "Mot de passe trop faible. Il faut au moins 8 caractères, 1 majuscule, 1 minuscule et 1 chiffre."
    }

    if (password != confirmPassword) {
        return "La confirmation du mot de passe ne correspond pas."
    }

    return null
}

private fun isCredentialMatch(
    identifier: String,
    password: String,
    storedUsername: String,
    storedEmail: String,
    storedPassword: String
): Boolean {
    val cleanIdentifier = identifier.trim()

    val matchesUsername = cleanIdentifier.equals(storedUsername, ignoreCase = true)
    val matchesEmail = cleanIdentifier.equals(storedEmail, ignoreCase = true)

    return (matchesUsername || matchesEmail) && password == storedPassword
}