package edu.gonzalezAlvaro.appdummy.ui.models

import edu.gonzalezAlvaro.appdummy.R
import edu.gonzalezAlvaro.appdummy.ui.models.LibroUI
// R5: Modelo LibroUI con ejemplos
data class LibroUI (
    val id: Int,
    val titulo: String,
    val autor: String,
    val year: Int,
    val isbn: String,
    val cover: Int,
    val esFavorito: Boolean,
    val leido: Boolean
)

val libros = listOf(
    LibroUI(1, "Jujutsu Kaisen 01", "Gege Akutami", 2018, "978-84-679-6111-9", R.drawable.jjk, true, true),
    LibroUI(2, "Ataque a los Titanes 01", "Hajime Isayama", 2009, "978-84-679-6092-1", R.drawable.aot, true, false),
    LibroUI(3, "Guardianes de la Noche (Kimetsu no Yaiba) 01", "Koyoharu Gotouge", 2016, "978-84-679-6096-9", R.drawable.kny, false, true),
    LibroUI(4, "JoJo's Bizarre Adventure Part 1: Phantom Blood 01", "Hirohiko Araki", 1987, "978-4-08-851126-9", R.drawable.jjba, true, false),
    LibroUI(5, "Vinland Saga 01", "Makoto Yukimura", 2005, "978-84-16051-81-6", R.drawable.vs, false, false)
)