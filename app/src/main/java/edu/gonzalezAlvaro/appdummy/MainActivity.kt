package edu.gonzalezAlvaro.appdummy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import edu.gonzalezAlvaro.appdummy.ui.screens.PantallaBienvenida

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaBienvenida(
                onEntrar = {
                    // Acción por desempeñar...
                }
            )
        }
    }
}