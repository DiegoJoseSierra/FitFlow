package com.example.fitflow.vistas

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
import com.example.fitflow.componentes.HistorialItem
import com.example.fitflow.componentes.ManejarRutina
import com.example.fitflow.componentes.RutinaItem
import com.example.fitflow.viewmodels.historial.HistorialViewModel
import com.example.fitflow.viewmodels.rutina.RutinaEvento
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun Historial(
    navController: NavController,
    historialViewModel: HistorialViewModel = hiltViewModel()
) {
    val historial by historialViewModel.historial
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
                text = "Historial de Rutinas",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(20.dp)
            )
        }
        if (historial.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(historial) { rutinaHistorial ->
                    HistorialItem(historial = rutinaHistorial)
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
}