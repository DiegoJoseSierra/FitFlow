package com.example.fitflow.componentes

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitflow.dominio.modelos.Rutina

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinaItem(
    rutina: Rutina,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier
            .clickable { onClick() },
        headlineText = {
            Text(text = rutina.nombre)
        },
        overlineText = {
            Text(text = "${rutina.duracion} minutos")
        },
        supportingText = {
            // Limitar la cantidad de caracteres a 50
            Text(text = rutina.actividades.joinToString(separator = ", ") { it.nombre }
                .take(50))
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Ejecutar Rutina"
            )
        },
        tonalElevation = 2.dp,
    )
}