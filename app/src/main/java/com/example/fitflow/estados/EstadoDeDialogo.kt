package com.example.fitflow.estados

data class EstadoDeDialogo (
    val visible : Boolean = false,
    val texto : String = "Quieres descartar esta operacion?",
)