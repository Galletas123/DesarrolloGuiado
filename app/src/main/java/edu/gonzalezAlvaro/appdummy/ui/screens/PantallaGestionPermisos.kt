package edu.gonzalezAlvaro.appdummy.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.gonzalezAlvaro.appdummy.ui.permissions.EstadoPermiso

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaGestionPermisos() {
    val context = LocalContext.current
    val activity = context as? Activity

    fun calcularEstadoActual(): EstadoPermiso {
        val concedido = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        return when {
            concedido -> EstadoPermiso.Concedido
            activity != null && !ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA) -> {
                EstadoPermiso.Pendiente
            }
            else -> EstadoPermiso.Pendiente
        }
    }

    var estadoPermiso by remember { mutableStateOf<EstadoPermiso>(EstadoPermiso.Pendiente) }

    // R14: Lanzador para solicitar el permiso de la cámara
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        estadoPermiso = when {
            isGranted -> EstadoPermiso.Concedido
            activity != null && !ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA) -> {
                EstadoPermiso.DenegadoPermanentemente
            }
            else -> EstadoPermiso.Denegado
        }
    }

    LaunchedEffect(Unit) {
        val check = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        if (check == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            estadoPermiso = EstadoPermiso.Concedido
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gestión de Permiso de Cámara") }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            // R13: Interfaz totalmente distinta para cada estado de la sealed class
            when (estadoPermiso) {
                is EstadoPermiso.Concedido -> {
                    ColumnaEstado(
                        icon = Icons.Default.CheckCircle,
                        titulo = "¡Permiso Concedido!",
                        descripcion = "La aplicación ya tiene acceso a la cámara del dispositivo.",
                        colorIcono = MaterialTheme.colorScheme.primary
                    ) {}
                }
                is EstadoPermiso.Pendiente, is EstadoPermiso.Denegado -> {
                    ColumnaEstado(
                        icon = Icons.Default.CameraAlt,
                        titulo = "Se necesita acceso a la Cámara",
                        descripcion = "Para poder utilizar esta funcionalidad, por favor concede el permiso de la cámara.",
                        colorIcono = MaterialTheme.colorScheme.secondary,
                        textoBoton = "Solicitar Permiso",
                        onBotonClick = {
                            launcher.launch(Manifest.permission.CAMERA)
                        }
                    )
                }
                is EstadoPermiso.DenegadoPermanentemente -> {
                    ColumnaEstado(
                        icon = Icons.Default.Warning,
                        titulo = "Permiso Denegado Permanentemente",
                        descripcion = "Has denegado el permiso de la cámara de forma permanente. Debes habilitarlo manualmente desde los ajustes de la aplicación.",
                        colorIcono = MaterialTheme.colorScheme.error,
                        textoBoton = "Abrir Ajustes",
                        onBotonClick = {
                            // R15: Intent implícito para abrir los ajustes de la app
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun ColumnaEstado(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    titulo: String,
    descripcion: String,
    colorIcono: androidx.compose.ui.graphics.Color,
    textoBoton: String? = null,
    onBotonClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = colorIcono
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = descripcion,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        if (textoBoton != null) {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onBotonClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = textoBoton)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaGestionPermisosPreview() {
    MaterialTheme {
        PantallaGestionPermisos()
    }
}