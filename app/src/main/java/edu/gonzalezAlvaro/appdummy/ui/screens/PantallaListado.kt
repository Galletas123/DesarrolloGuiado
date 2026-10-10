package edu.gonzalezAlvaro.appdummy.ui.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import edu.gonzalezAlvaro.appdummy.ui.models.LibroUI
import edu.gonzalezAlvaro.appdummy.ui.models.libros

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListado() {
    var listaLibros by remember { mutableStateOf(libros) }

    // R8: Estado del campo de búsqueda por título
    var textoBusqueda by remember { mutableStateOf("") }

    // R9: Estado del filtro por autor seleccionado (null si no hay filtro)
    var autorSeleccionado by remember { mutableStateOf<String?>(null) }

    val autoresDisponibles = remember { listaLibros.map { it.autor }.distinct() }

    // R9: Combinación reactiva de búsqueda por título y filtro por autor
    val librosFiltrados = remember(listaLibros, textoBusqueda, autorSeleccionado) {
        listaLibros.filter { libro ->
            val coincideTitulo = libro.titulo.contains(textoBusqueda, ignoreCase = true)
            val coincideAutor = autorSeleccionado == null || libro.autor == autorSeleccionado
            coincideTitulo && coincideAutor
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Biblioteca Digital", style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // R8: Campo de búsqueda con AnimatedVisibility en el botón de borrado
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                label = { Text("Buscar por título...") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    AnimatedVisibility(visible = textoBusqueda.isNotEmpty()) {
                        IconButton(onClick = { textoBusqueda = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Limpiar búsqueda",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // R9: Filtros por autor mediante FilterChip en una LazyRow horizontal
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    FilterChip(
                        selected = autorSeleccionado == null,
                        onClick = { autorSeleccionado = null },
                        label = { Text("Todos") }
                    )
                }
                items(autoresDisponibles) { autor ->
                    FilterChip(
                        selected = autorSeleccionado == autor,
                        onClick = { autorSeleccionado = if (autorSeleccionado == autor) null else autor },
                        label = { Text(autor) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // R10: Control de estado vacío si la búsqueda/filtro no arroja resultados
            if (librosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = "Sin resultados",
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No se encontraron libros",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                // R6: LazyVerticalGrid de dos columnas para mostrar la rejilla
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(librosFiltrados, key = { it.id }) { libro ->
                        ItemLibro(
                            libro = libro,
                            onFavoritoClick = {
                                listaLibros = listaLibros.map {
                                    if (it.id == libro.id) it.copy(esFavorito = !it.esFavorito) else it
                                }
                            },
                            onLeidoClick = {
                                listaLibros = listaLibros.map {
                                    if (it.id == libro.id) it.copy(leido = !it.leido) else it
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

// R6 y R7: Celda individual ItemLibro con Coil y Botones de acción dinámicos
@Composable
fun ItemLibro(
    libro: LibroUI,
    onFavoritoClick: () -> Unit,
    onLeidoClick: () -> Unit
) {
    // R16: Obtener contexto actual para  lanzar Intent implícito
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // R6: Imagen de portada con AsyncImage de Coil
            AsyncImage(
                model = libro.cover,
                contentDescription = "Portada de ${libro.titulo}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // R11: Uso estricto de MaterialTheme.typography y colorScheme
            Text(
                text = libro.titulo,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            Text(
                text = libro.autor,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
            Text(
                text = "Año: ${libro.year}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(4.dp))

            // R7: Dos IconButton cuyos iconos cambian dinámicamente según el estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                // R16: Cambio a SpaceAround para acomodar botones uniformemente
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = onFavoritoClick) {
                    Icon(
                        imageVector = if (libro.esFavorito) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Marcar como favorito",
                        tint = if (libro.esFavorito) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onLeidoClick) {
                    Icon(
                        imageVector = if (libro.leido) Icons.Default.CheckCircle else Icons.Outlined.CheckCircle,
                        contentDescription = "Marcar como leído",
                        tint = if (libro.leido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                // R16: Botón para compartir el libro
                IconButton(onClick = {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "¡Mira este libro '${libro.titulo}' de ${libro.autor}!")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, "Compartir libro")
                    context.startActivity(shareIntent)
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartir libro",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// R17 Añadido de @Preview
@Preview(showBackground = true)
@Composable
fun PantallaListadoPreview() {
    MaterialTheme {
        PantallaListado()
    }
}

// R17 Añadido de @Preview
@Preview(showBackground = true)
@Composable
fun ItemLibroPreview() {
    MaterialTheme {
        ItemLibro(
            libro = libros.first(),
            onFavoritoClick = {},
            onLeidoClick = {}
        )
    }
}