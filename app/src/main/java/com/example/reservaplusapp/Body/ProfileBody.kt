package com.example.reservaplusapp.Body

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reservaplusapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    var showSection by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (showSection == null) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Header with diagonal background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    // Diagonal background
                    Canvas(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val path = Path().apply {
                            moveTo(0f, 0f)
                            lineTo(size.width, 0f)
                            lineTo(size.width, size.height)
                            lineTo(0f, size.height * 0.3f)
                            close()
                        }
                        drawPath(
                            path = path,
                            color = Color(0xFF57BDD3)
                        )
                    }

                    // Profile content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Profile Image
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(2.dp, Color.White, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                tint = Color(0xFF57BDD3)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Martin",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )
                        Text(
                            text = "martindjcg@gmail.com",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Profile Options
                ProfileOption(
                    icon = Icons.Default.Person,
                    text = "Información Personal",
                    onClick = { showSection = "Información Personal" }
                )
                ProfileOption(
                    icon = Icons.Default.Edit,
                    text = "Editar Perfil",
                    onClick = { showSection = "Cambiar Datos" }
                )
                ProfileOption(
                    icon = Icons.Default.Lock,
                    text = "Cambiar Contraseña",
                    onClick = { showSection = "Cambiar contraseña" }
                )
                ProfileOption(
                    icon = Icons.Default.ShoppingCart,
                    text = "Facturación y Pagos",
                    onClick = { showSection = "Facturación y Pagos" }
                )
                ProfileOption(
                    icon = Icons.Default.DateRange,
                    text = "Historial de Reservas",
                    onClick = { showSection = "Historial de Reservas" }
                )

                Spacer(modifier = Modifier.weight(1f))

                // Logout Button
                Button(
                    onClick = { /* Handle logout */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red.copy(alpha = 0.8f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cerrar Sesión")
                }
            }
        } else {
            // Section Content with Back Button
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                // Top Bar with Back Button
                TopAppBar(
                    title = { Text(showSection ?: "") },
                    navigationIcon = {
                        IconButton(onClick = { showSection = null }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Regresar"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF57BDD3),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )

                // Content
                Box(modifier = Modifier.fillMaxSize()) {
                    when (showSection) {
                        "Información Personal" -> PersonalInfoSection()
                        "Cambiar Datos" -> EditProfileSection()
                        "Cambiar contraseña" -> ChangePasswordSection()
                        "Facturación y Pagos" -> BillingSection()
                        "Historial de Reservas" -> ReservationsHistorySection()
                    }
                }
            }
        }
    }
}
@Composable
private fun ProfileOption(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF57BDD3)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

@Composable
private fun NavigationMenu(
    selectedSection: String,
    onSectionSelected: (String) -> Unit
) {
    val sections = listOf(
        "Información Personal" to Icons.Default.Person,
        "Cambiar Datos" to Icons.Default.Edit,
        "Cambiar contraseña" to Icons.Default.Lock,
        "Facturación y Pagos" to Icons.Default.ShoppingCart,
        "Historial de Reservas" to Icons.Default.Info
    )

    Column {
        sections.forEach { (section, icon) ->
            NavigationItem(
                text = section,
                icon = icon,
                selected = selectedSection == section,
                onClick = { onSectionSelected(section) }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        NavigationItem(
            text = "Cerrar Sesión",
            icon = Icons.Default.ExitToApp,
            selected = false,
            onClick = { /* Handle logout */ },
            textColor = Color.Red
        )
    }
}

@Composable
private fun NavigationItem(
    text: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    textColor: Color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

@Composable
private fun PersonalInfoSection() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Información Personal",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Administra tu información personal, incluyendo el número de teléfono y correo electrónico donde podemos contactarte.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Personal Info Fields
        InfoField("Nombre", "Martin")
        InfoField("Correo Electrónico", "martindjcg@gmail.com")
        InfoField("Última Actividad", "27 Nov 2024, 01:19")
        InfoField("Fecha de Registro", "14 Nov 2024")
    }
}

@Composable
private fun InfoField(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Composable
private fun EditProfileSection() {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Editar Información Personal",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Actualiza los datos de tu cuenta.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Handle save changes */ },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("Guardar Cambios")
        }
    }
}

@Composable
private fun ChangePasswordSection() {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Cambiar contraseña",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Cambia la contraseña de tu cuenta.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = currentPassword,
            onValueChange = { currentPassword = it },
            label = { Text("Contraseña actual") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = if (passwordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility),
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("Nueva contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Password requirements
        Text(
            text = "La contraseña debe cumplir con los siguientes criterios:",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Column(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
            Text("• Mínimo 8 caracteres", style = MaterialTheme.typography.bodySmall)
            Text("• Al menos una letra mayúscula", style = MaterialTheme.typography.bodySmall)
            Text("• Al menos un número", style = MaterialTheme.typography.bodySmall)
            Text("• Al menos un carácter especial (como @, #, $)", style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar nueva contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Mamadas para guardar la contra */ },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("Cambiar Contraseña")
        }
    }
}

@Composable
private fun BillingSection() {

    Text("Facturación y Pagos - En desarrollo ")
}

@Composable
private fun ReservationsHistorySection() {

    Text("Historial de Reservas - En desarrollo")
}