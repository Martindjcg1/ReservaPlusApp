package com.example.reservaplusapp.Body

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reservaplusapp.Clases.UserProfile
import com.example.reservaplusapp.R
import com.example.reservaplusapp.Apis.RetrofitInstance
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reservaplusapp.Clases.PasswordChangeRequest
import com.example.reservaplusapp.Clases.PasswordChangeResponse
import com.example.reservaplusapp.Clases.UpdateProfileRequest
import com.example.reservaplusapp.Clases.UpdateProfileResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    onLogout: () -> Unit
) {
    var showSection by remember { mutableStateOf<String?>(null) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    val viewModel: UserProfileViewModel = viewModel()
    val viewModel2:  LogoutViewModel= viewModel()
    val context = LocalContext.current

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

                Spacer(modifier = Modifier.weight(1f))

                // Logout Button
                Button(
                    onClick = { showLogoutDialog = true },
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
                        "Cambiar Datos" -> EditProfileSection(viewModel = viewModel)
                        "Cambiar contraseña" -> ChangePasswordSection()
                    }
                }
            }
        }
    }

    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Cerrar Sesión") },
            text = { Text("¿Estás seguro que deseas cerrar sesión?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false // Cierra el diálogo inmediatamente
                        viewModel2.logout(context, onLogout)

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Sí, cerrar sesión")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showLogoutDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

class LogoutViewModel : ViewModel() {
    fun logout(context: Context, onLogout: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.logout()
                if (response.isSuccessful) {
                    // Borra el token de SharedPreferences
                    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().clear().apply()
                    Log.e("Borrado",sharedPreferences.toString())
                    // Navega fuera del perfil
                    withContext(Dispatchers.Main) {
                        onLogout()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error al cerrar sesión.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error de red: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
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
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

class UserProfileViewModel : ViewModel() {
    fun fetchUserProfile(onResult: (UserProfile?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUserProfile()
                val userProfile = response.user
                onResult(userProfile)
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }

    fun updateUserProfile(
        request: UpdateProfileRequest,
        onSuccess: (UpdateProfileResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response: Response<UpdateProfileResponse> =
                    RetrofitInstance.api.updateUserProfile(request)
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError("Error al actualizar el perfil: ${response.errorBody()?.string()}")
                }
            } catch (e: HttpException) {
                onError("Error al conectar con el servidor: ${e.message()}")
            } catch (e: Exception) {
                onError("Error inesperado: ${e.message}")
            }
        }
    }


}

@Composable
fun PersonalInfoSection() {
    val viewModel: UserProfileViewModel = viewModel()
    var userProfile by remember { mutableStateOf<UserProfile?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile { profile ->
            userProfile = profile
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        userProfile?.let { profile ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                item {
                    Text(
                        text = "Información Personal",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Administra tu información personal.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }

                item { InfoField("Nombre", profile.first_name ?: "N/A") }
                item { InfoField("Apellido", profile.last_name ?: "N/A") }
                item { InfoField("Nombre de usuario", profile.username ?: "N/A") }
                item { InfoField("Correo Electrónico", profile.email ?: "N/A") }
                item { InfoField("Primer registro", profile.date_joined.toString()) }
            }
        }
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
fun EditProfileSection(viewModel: UserProfileViewModel) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Cargar los datos actuales del usuario
    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile { profile ->
            if (profile != null) {
                nombre = profile.first_name ?: ""
                apellido = profile.last_name ?: ""
                usuario = profile.username ?: ""
                email = profile.email ?: ""
            }
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
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

            successMessage?.let {
                Text(
                    text = it,
                    color = Color.Green,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Button(
                onClick = {
                    isLoading = true
                    successMessage = null
                    errorMessage = null

                    val request = UpdateProfileRequest(
                        username = usuario,
                        first_name = nombre,
                        last_name = apellido,
                        email = email
                    )

                    viewModel.updateUserProfile(request,
                        onSuccess = { response ->
                            isLoading = false
                            successMessage = response.message
                        },
                        onError = { error ->
                            isLoading = false
                            errorMessage = error
                        }
                    )
                },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Guardar Cambios")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
            onClick = { if (newPassword == confirmPassword) {
                // Llamar a la función para cambiar la contraseña
                changePassword(currentPassword, newPassword)
            } else {
                // Mostrar mensaje de error si las contraseñas no coinciden
                //Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                Log.e("Cambio de Contraseña","No coincideen las contraseñas")
            } },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("Cambiar Contraseña")
        }
    }
}

fun changePassword(currentPassword: String, newPassword: String) {
    // Crear el objeto de solicitud
    val passwordChangeRequest = PasswordChangeRequest(
        old_password = currentPassword,
        new_password = newPassword
    )

    // Hacer la solicitud a la API utilizando Retrofit
    val call = RetrofitInstance.api.changePassword(passwordChangeRequest)

    call.enqueue(object : Callback<PasswordChangeResponse> {
        override fun onResponse(
            call: Call<PasswordChangeResponse>,
            response: Response<PasswordChangeResponse>
        ) {
            if (response.isSuccessful) {
                // Mostrar mensaje de éxito
                Log.e("Cambio de Contraseña","Awebo si Jalo")
            } else {
                // Mostrar error
                Log.e("Cambio de Contraseña","Chale")

            }
        }

        override fun onFailure(call: Call<PasswordChangeResponse>, t: Throwable) {
            // Mostrar error de conexión
            Log.e("Cambio de Contraseña","Error dee conexion")
        }
    })
}


