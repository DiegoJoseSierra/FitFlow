package com.example.fitflow.componentes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.fitflow.dominio.modelos.Actividad
import com.example.fitflow.viewmodels.rutina.RutinaEvento
import com.example.fitflow.viewmodels.rutina.RutinaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarRutina(rutinaViewModel: RutinaViewModel) {
    val actividades = rutinaViewModel.obtenerActividades().collectAsState(
        initial = listOf()
    )
    val actividadesSeleccionadas = mutableListOf<Actividad>()
    val contexto = LocalContext.current
    if (rutinaViewModel.dialogoVisible.value.visible) {
        Dialog(
            onDismissRequest = { rutinaViewModel.onEvento(RutinaEvento.CerrarDialogo) },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier.padding(bottom = 8.dp),
                        onClick = { rutinaViewModel.onEvento(RutinaEvento.CerrarDialogo) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar ventana de agregar rutina"
                        )
                    }
                    Text(
                        text = "Agregar Rutina",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .width(48.dp)
                    )
                }
                if (actividades.value.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .padding(16.dp)
                            .clip(MaterialTheme.shapes.extraLarge),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            value = rutinaViewModel.nombre.value.valor,
                            onValueChange = { nuevoValor ->
                                rutinaViewModel.onEvento(RutinaEvento.IngresarNombre(nuevoValor))
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            label = { Text("Nombre de la rutina") },
                            shape = MaterialTheme.shapes.large
                        )
                        TextField(
                            enabled = false,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            value = rutinaViewModel.duracion.value.valor,
                            onValueChange = { },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ),
                            label = { Text("Duración de la rutina") },
                            shape = MaterialTheme.shapes.large
                        )
                        Text("Actividades de la rutina", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            columns = GridCells.Adaptive(100.dp),
                        ) {
                            actividades.value.forEach { actividad ->
                                item {
                                    FilterChip(
                                        label = { Text(actividad.nombre ) },
                                        selected = actividadesSeleccionadas.contains(actividad),
                                        onClick = {
                                            if (actividadesSeleccionadas.contains(actividad)) {
                                                actividadesSeleccionadas.remove(actividad)
                                            } else {
                                                actividadesSeleccionadas.add(actividad)
                                            }
                                            rutinaViewModel.onEvento(
                                                RutinaEvento.IngresarActividades(
                                                    actividadesSeleccionadas
                                                )
                                            )
                                            val duracionTotal = actividadesSeleccionadas.sumOf { it.duracion }
                                            rutinaViewModel.onEvento(
                                                RutinaEvento.IngresarDuracion(
                                                    duracionTotal
                                                )
                                            )
                                        },
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .width(100.dp)
                                            .height(25.dp)
                                    )
                                }
                            }
                        }
                        Button(
                            onClick = {
                                rutinaViewModel.onEvento(RutinaEvento.GuardarRutina(contexto))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text("Guardar Rutina")
                        }
                    }
                } else {
                    Text(
                        text = "Necesitas crear actividades primero antes de crear una rutina",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}