package edu.gonzalezAlvaro.appdummy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import edu.gonzalezAlvaro.appdummy.ui.models.LibroUI
import edu.gonzalezAlvaro.appdummy.ui.models.libros

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListado() {
    var listaLibros by remember { mutableStateOf(libros) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Biblioteca Digital", style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { innerPadding ->
        // R6: LazyVerticalGrid de dos columnas para mostrar la rejilla de libros
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaLibros, key = { it.id }) { libro ->
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

// R6 y R7: Celda individual ItemLibro con Coil y Botones de acción dinámicos
@Composable
fun ItemLibro(
    libro: LibroUI,
    onFavoritoClick: () -> Unit,
    onLeidoClick: () -> Unit
) {
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
                horizontalArrangement = Arrangement.SpaceBetween
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
            }
        }
    }
}