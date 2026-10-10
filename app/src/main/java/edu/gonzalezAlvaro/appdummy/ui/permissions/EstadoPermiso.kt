package edu.gonzalezAlvaro.appdummy.ui.permissions

// 13: Creación de "sealed class"
sealed class EstadoPermiso {
    object Pendiente : EstadoPermiso()
    object Concedido : EstadoPermiso()
    object Denegado : EstadoPermiso()
    object DenegadoPermanentemente : EstadoPermiso()
}