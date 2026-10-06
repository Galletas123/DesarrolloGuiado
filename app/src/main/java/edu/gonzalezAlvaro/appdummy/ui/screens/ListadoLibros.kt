package edu.gonzalezAlvaro.appdummy.ui.screens

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable

data class LibroUI (
    val id: Int,
    val titulo: String,
    val autor: String,
    val year: Int,
    val isbn: String,
    val cover: String,
    val esFavorite: Boolean,
    val leido: Boolean
)

val l1 = LibroUI(id = 1, titulo = "Jujutsu Kaisen 01", autor = "Gege Akutami", year = 2018, isbn = "978-84-679-6111-9", cover = "jujutsu_kaisen_01.jpg", esFavorite = true, leido = true)
val l2 = LibroUI(id = 2, titulo = "Ataque a los Titanes 01", autor = "Hajime Isayama", year = 2009, isbn = "978-84-679-6092-1", cover = "shingeki_01.jpg", esFavorite = true, leido = false)
val l3 = LibroUI(id = 3, titulo = "Guardianes de la Noche (Kimetsu no Yaiba) 01", autor = "Koyoharu Gotouge", year = 2016, isbn = "978-84-679-6096-9", cover = "kimetsu_01.jpg", esFavorite = false, leido = true)
val l4 = LibroUI(id = 4, titulo = "JoJo's Bizarre Adventure Part 1: Phantom Blood 01", autor = "Hirohiko Araki", year = 1987, isbn = "978-4-08-851126-9",cover = "jojos_phantom_01.jpg", esFavorite = true, leido = false)
val l5 = LibroUI(id = 5, titulo = "Vinland Saga 01", autor = "Makoto Yukimura", year = 2005, isbn = "978-84-16051-81-6", cover = "vinland_saga_01.jpg", esFavorite = false, leido = false)

@Composable
fun PantallaListado(){
    LazyVerticalGrid{

    }
}

fun LazyVerticalGrid(content: LazyGridScope.() -> Unit) {}
