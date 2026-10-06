package edu.gonzalezAlvaro.appdummy.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBienvenida(onEntrar: () -> Unit) {
    // R3: Recordar nombre
    var nombreUsuario by rememberSaveable { mutableStateOf("") }

    // R2: Validación nombre
    val esNombreValido = nombreUsuario.length >= 3

    // R4: Estructura con Scaffold y TopAppBar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "AppDummy - Inicio") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // R1: Icono decorativo
            Icon(
                imageVector = Icons.Default.Book,
                contentDescription = "Icono de libro",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            // R1: Título
            Text(
                text = "¡Bienvenido!",
                style = MaterialTheme.typography.headlineMedium
            )

            // R1: Subtítulo
            Text(
                text = "Introduce tu nombre para continuar",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            // R1: OutlinedTextField para el nombre
            OutlinedTextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                label = { Text("Nombre de usuario") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                // R2: Texto de apoyo indicando validación
                supportingText = {
                    Text(
                        text = when {
                            nombreUsuario.isEmpty() -> "Obligatorio (mín. 3 caracteres)"
                            !esNombreValido -> "Faltan caracteres (mín. 3 caracteres)"
                            else -> "Nombre válido "
                        }
                    )
                },
                isError = nombreUsuario.isNotEmpty() && !esNombreValido
            )

            Spacer(modifier = Modifier.height(18.dp))

            // R2: Botón habilitado si se cumple la validación
            Button(
                onClick = onEntrar,
                enabled = esNombreValido,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Entrar")
            }
        }
    }
}