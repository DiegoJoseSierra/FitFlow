package com.example.fitflow.componentes

import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fitflow.dominio.modelos.Actividad
import com.example.fitflow.dominio.modelos.Rutina
import com.example.fitflow.dominio.modelos.RutinaCompletada
import com.example.fitflow.viewmodels.rutina.ManejarRutinaViewModel
import com.example.fitflow.viewmodels.rutina.RutinaEvento
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.compose.OnParticleSystemUpdateListener
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.PartySystem
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@Composable
fun ManejarRutina(
    rutina: Rutina,
    actividades: List<Actividad>,
    estado: MutableState<Boolean>,
    onEvento: (RutinaEvento) -> Unit,
    manejarRutinaViewModel: ManejarRutinaViewModel = hiltViewModel()
) {
    // Crear estado del tiempo de duracion
    var duracion by rememberSaveable {
        mutableStateOf(actividades.first().duracion)
    }
    // Crear estado para el nombre de la actividad
    var nombreActividad by rememberSaveable {
        mutableStateOf(actividades.first().nombre)
    }
    // Crear estado para la descripcion de la actividad
    var descripcionActividad by rememberSaveable {
        mutableStateOf(actividades.first().descripcion)
    }
    // Crear estado para el índice de la actividad actual
    var indiceActividad by rememberSaveable {
        mutableStateOf(0)
    }
    // Crear estado para el temporizador
    var temporizador by rememberSaveable {
        mutableStateOf<CountDownTimer?>(null)
    }
    // Crear estado para el diálogo de finalización
    var mostrarDialogoFinal by rememberSaveable {
        mutableStateOf(false)
    }
    // Crear estado para el contador general
    var contadorGeneral by rememberSaveable {
        mutableStateOf(0)
    }
    // Crear estado para la duración real de cada actividad
    var duracionesReales by rememberSaveable {
        mutableStateOf(actividades.map { it.duracion }.toMutableList())
    }
    // Crear estado para el confeti
    var mostrarConfeti by rememberSaveable {
        mutableStateOf(false)
    }
    Dialog(
        onDismissRequest = {
            estado.value = false
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        estado.value = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
                Text(
                    text = "Manejar Rutina",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(48.dp)
                )
            }
            Text(
                text = "Ejecutar Rutina",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Descripcion:",
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = descripcionActividad,
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Duracion:", fontSize = 20.sp, modifier = Modifier.padding(top = 20.dp))
                // Crear un cronometro para la duracion de la rutina
                Text(
                    // Formatear el tiempo de duracion en minutos y segundos 00:00
                    text = "${duracion / 60}:${duracion % 60}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .padding(20.dp)
                )
                // Crear un boton para iniciar la rutina
                Button(
                    onClick = {
                        // Iniciar la rutina
                        temporizador?.cancel() // Cancelar el temporizador anterior si existe
                        temporizador = object : CountDownTimer(duracion * 1000L, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                duracion = (millisUntilFinished / 1000).toInt()
                            }

                            override fun onFinish() {
                                // Pasar a la siguiente actividad o mostrar el diálogo de finalización
                                duracionesReales[indiceActividad] = actividades[indiceActividad].duracion - duracion // Guardar la duración real de la actividad actual
                                contadorGeneral += duracionesReales[indiceActividad] // Sumar la duración real de la actividad actual al contador general
                                if (indiceActividad + 1 < actividades.size) {
                                    indiceActividad++
                                    duracion = actividades[indiceActividad].duracion
                                    nombreActividad = actividades[indiceActividad].nombre
                                } else {
                                    mostrarDialogoFinal = true
                                }
                            }
                        }.start()
                    },
                    modifier = Modifier
                        .padding(20.dp)
                        .size(150.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Ejecutar Rutina",
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
                // Crear un boton para omitir la actividad
                TextButton(
                    onClick = {
                        // Omitir la actividad
                        temporizador?.cancel() // Cancelar el temporizador actual
                        duracionesReales[indiceActividad] =
                            actividades[indiceActividad].duracion - duracion // Guardar la duración real de la actividad actual
                        contadorGeneral += duracionesReales[indiceActividad] // Sumar la duración real de la actividad actual al contador general
                        if (indiceActividad + 1 < actividades.size) {
                            indiceActividad++
                            duracion = actividades[indiceActividad].duracion
                            nombreActividad = actividades[indiceActividad].nombre
                            // Reiniciar el temporizador con la nueva duración
                            temporizador = object : CountDownTimer(duracion * 1000L, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    duracion = (millisUntilFinished / 1000).toInt()
                                }

                                override fun onFinish() {
                                    // Pasar a la siguiente actividad o mostrar el diálogo de finalización
                                    duracionesReales[indiceActividad] =
                                        actividades[indiceActividad].duracion - duracion // Guardar la duración real de la actividad actual
                                    contadorGeneral += duracionesReales[indiceActividad] // Sumar la duración real de la actividad actual al contador general
                                    if (indiceActividad + 1 < actividades.size) {
                                        indiceActividad++
                                        duracion = actividades[indiceActividad].duracion
                                        nombreActividad = actividades[indiceActividad].nombre
                                    } else {
                                        mostrarDialogoFinal = true
                                    }
                                }
                            }.start()
                        } else {
                            mostrarConfeti = true
                            mostrarDialogoFinal = true
                        }
                    },
                    modifier = Modifier
                        .padding(20.dp)
                        .size(150.dp)
                        .clip(CircleShape)
                ) {
                    Text(text = "Omitir")
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Omitir Actividad",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
                // Mostrar el diálogo de finalización
                if (mostrarDialogoFinal) {
                    Dialog(
                        onDismissRequest = {
                            estado.value = false
                        },
                        properties = DialogProperties(usePlatformDefaultWidth = false)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "¡Felicidades!",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Has completado todas las actividades.",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Duracion total de la rutina: ${contadorGeneral / 60}:${contadorGeneral % 60}",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Actividades realizadas:",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                            LazyColumn {
                                items(actividades) { actividad ->
                                    Text(
                                        text = "- ${actividad.nombre} (${
                                            duracionesReales[actividades.indexOf(
                                                actividad
                                            )] / 60
                                        }:${
                                            duracionesReales[actividades.indexOf(
                                                actividad
                                            )] % 60
                                        })",
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                            Button(
                                onClick = {
                                    estado.value = false
                                    manejarRutinaViewModel.finalizarRutina(
                                        RutinaCompletada(
                                            id = rutina.id,
                                            nombre = nombreActividad,
                                            duracion = contadorGeneral,
                                            actividades = actividades
                                        ),
                                        contadorGeneral
                                    )
                                    // Limimpiar los estados
                                    duracion = actividades.first().duracion
                                    nombreActividad = actividades.first().nombre
                                    descripcionActividad = actividades.first().descripcion
                                    indiceActividad = 0
                                    temporizador = null
                                    mostrarDialogoFinal = false
                                    contadorGeneral = actividades.sumOf { it.duracion }
                                    duracionesReales = actividades.map { it.duracion }.toMutableList()
                                },
                                modifier = Modifier
                                    .padding(20.dp)
                                    .align(Alignment.End)
                            ) {
                                Text("OK")
                            }
                        }
                        KonfettiView(
                            modifier = Modifier
                                .fillMaxSize(),
                            parties = listOf(
                                Party(
                                    speed = 0f,
                                    maxSpeed = 30f,
                                    damping = 0.9f,
                                    spread = 360,
                                    colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                                    position = Position.Relative(0.5, 0.3),
                                    emitter = Emitter(
                                        duration = 100,
                                        TimeUnit.MILLISECONDS
                                    ).max(100)
                                ),
                            ),
                            updateListener = object : OnParticleSystemUpdateListener {
                                override fun onParticleSystemEnded(
                                    system: PartySystem,
                                    activeSystems: Int
                                ) {
                                    if (system.enabled && activeSystems == 0) {
                                        system.enabled = false
                                        mostrarConfeti = false
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}