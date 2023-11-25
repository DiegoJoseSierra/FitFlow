package com.example.fitflow.vistas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.fitflow.componentes.AgregarActividad
import com.example.fitflow.viewmodels.actividades.ActividadEvento
import com.example.fitflow.viewmodels.actividades.ActividadViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividades(
    navController: NavController,
    actividadViewModel: ActividadViewModel = hiltViewModel()
) {
    val actividades by actividadViewModel.actividades
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Rutinas",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(20.dp)
            )
            TextButton(
                onClick = {
                    // Crear actividad
                    actividadViewModel.onEvento(ActividadEvento.AbrirDialogo)
                },
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Volver",
                    modifier = Modifier
                        .width(30.dp)
                        .size(20.dp)
                )
                Text(text = "Crear Actividad")
            }
        }
        if (actividades.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(actividades) { actividad ->
                    ListItem(
                        headlineText = {
                            Text(text = actividad.nombre)
                        },
                        overlineText = {
                            // Mostrar la duracion en minutos y segundos
                            Text(text = "(m:s): ${actividad.duracion / 60}:${actividad.duracion % 60}")
                        },
                        supportingText = {
                            // Limitar la cantidad de caracteres a 50
                            Text(text = actividad.descripcion)
                        },
                        tonalElevation = 2.dp,
                    )
                }
            }
        }
        else {
            Text(
                text = "No hay actividades creadas",
                modifier = Modifier.padding(20.dp)
            )
        }

        when {
            actividadViewModel.dialogoVisible.value.visible -> {
                AgregarActividad(actividadViewModel = actividadViewModel)
            }
        }
    }
}