package com.example.fitflow.vistas

import android.annotation.SuppressLint
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
import androidx.compose.material3.Icon
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
import com.example.fitflow.componentes.AgregarRutina
import com.example.fitflow.componentes.ManejarRutina
import com.example.fitflow.componentes.RutinaItem
import com.example.fitflow.viewmodels.rutina.RutinaEvento
import com.example.fitflow.viewmodels.rutina.RutinaViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun Rutinas(
    navController: NavController,
    rutinaViewModel: RutinaViewModel = hiltViewModel()
) {
    val rutinas by rutinaViewModel.rutinas
    val dialogoVerRutinaVisible = remember { mutableStateOf(false) }
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
                    // Crear rutina
                    rutinaViewModel.onEvento(RutinaEvento.AbrirDialogo)
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
                Text(text = "Crear Rutina")
            }
        }
        if (rutinas.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(rutinas) { rutina ->
                    // Controlar el estado del dialogo de ver rutina con mutable state
                    when {
                        dialogoVerRutinaVisible.value -> {
                            // Mostrar el dialogo de ver rutina
                            ManejarRutina(
                                rutina = rutina,
                                actividades = rutina.actividades,
                                estado = dialogoVerRutinaVisible,
                                onEvento = { evento ->
                                    rutinaViewModel.onEvento(evento)
                                }
                            )
                        }
                    }
                    RutinaItem(
                        rutina = rutina,
                        onClick = {
                            // Mostrar la pantalla de manejar rutina
                            dialogoVerRutinaVisible.value = true
                        }
                    )
                }
            }
        }
        else {
            Text(
                text = "No hay rutinas",
                modifier = Modifier.padding(20.dp)
            )
        }
    }
    when {
        rutinaViewModel.dialogoVisible.value.visible -> {
            // Mostrar el dialogo de ver rutina
            AgregarRutina(
                rutinaViewModel = rutinaViewModel,
            )
        }
    }
}