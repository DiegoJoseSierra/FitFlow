package com.example.fitflow.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.fitflow.viewmodels.actividades.ActividadEvento
import com.example.fitflow.viewmodels.actividades.ActividadViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarActividad(actividadViewModel: ActividadViewModel) {
    val contexto = LocalContext.current
    if (actividadViewModel.dialogoVisible.value.visible) {
        Dialog(
            onDismissRequest = { actividadViewModel.onEvento(ActividadEvento.CerrarDialogo) },
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
                        onClick = { actividadViewModel.onEvento(ActividadEvento.CerrarDialogo) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar ventana de agregar actividad"
                        )
                    }
                    Text(
                        text = "Agregar Actividad",
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
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                        .clip(MaterialTheme.shapes.large),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = actividadViewModel.nombre.value.valor,
                        onValueChange = { nuevoValor ->
                            actividadViewModel.onEvento(ActividadEvento.IngresarNombre(nuevoValor))
                        },
                        label = { Text("Nombre de la actividad") },
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        shape = MaterialTheme.shapes.large
                    )
                    TextField(
                        value = actividadViewModel.duracion.value.valor,
                        onValueChange = { nuevoValor ->
                            if (nuevoValor.isNotEmpty() && nuevoValor.all { it.isDigit() }) {
                                actividadViewModel.onEvento(ActividadEvento.IngresarDuracion(nuevoValor.toInt()))
                            } else {
                                actividadViewModel.onEvento(ActividadEvento.IngresarDuracion(0))
                            }
                        },
                        label = { Text("Duración de la actividad") },
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        shape = MaterialTheme.shapes.large,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        value = actividadViewModel.descripcion.value.valor,
                        onValueChange = { nuevoValor ->
                            actividadViewModel.onEvento(ActividadEvento.IngresarDescripcion(nuevoValor))
                        },
                        label = { Text("Descripción de la actividad") },
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        shape = MaterialTheme.shapes.large
                    )
                    Button(
                        onClick = {
                            actividadViewModel.onEvento(
                                ActividadEvento.GuardarActividad(
                                    contexto
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)
                            .fillMaxWidth(),
                    ) {
                        Text("Guardar Actividad")
                    }
                }
            }
        }
    }
}