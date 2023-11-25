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
import com.example.fitflow.dominio.modelos.Actividad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActividadItem(
    actividad: Actividad,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier
            .clickable { onClick() },
        headlineText = {
            Text(text = actividad.nombre)
        },
        overlineText = {
            Text(text = "${actividad.duracion} segundos")
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