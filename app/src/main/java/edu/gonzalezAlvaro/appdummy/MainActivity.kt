package edu.gonzalezAlvaro.appdummy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import edu.gonzalezAlvaro.appdummy.ui.screens.PantallaBienvenida
import edu.gonzalezAlvaro.appdummy.ui.screens.PantallaGestionPermisos
import edu.gonzalezAlvaro.appdummy.ui.screens.PantallaListado

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var mostrarListado by rememberSaveable { mutableStateOf(false) }

//            if (!mostrarListado) {
//                PantallaBienvenida(
//                    onEntrar = {
//                        mostrarListado = true
//                    }
//                )
//            } else {
//                PantallaListado()
//            }
            PantallaGestionPermisos()
        }
    }
}