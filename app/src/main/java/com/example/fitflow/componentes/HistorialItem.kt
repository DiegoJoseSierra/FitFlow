package com.example.fitflow.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitflow.dominio.modelos.Historial

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialItem(
    historial: Historial
) {
    ListItem(
        headlineText = {
            Text(text = historial.rutina.nombre)
        },
        overlineText = {
            // Formatear la duración de la rutina a mm:ss
            Text(text = "${historial.rutina.duracion / 60}:${historial.rutina.duracion % 60}")
        },
        supportingText = {
            // Limitar la cantidad de caracteres a 50
            Text(text = historial.rutina.actividades.joinToString(separator = ", ") { it.nombre }
                .take(50))
        },
        trailingContent = {
                          Column (
                              modifier = Modifier
                                  .fillMaxHeight(),
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.Center
                          ) {
                                Text(text = "Fecha: ${historial.fecha}")
                                Text(text = "Duración: ${historial.duracion / 60}:${historial.duracion % 60}")
                          }
        },
        tonalElevation = 2.dp,
    )
}