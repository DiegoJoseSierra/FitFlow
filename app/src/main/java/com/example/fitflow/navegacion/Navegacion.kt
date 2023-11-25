package com.example.fitflow.navegacion

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.LineWeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitflow.vistas.Actividades
import com.example.fitflow.vistas.Historial
import com.example.fitflow.vistas.Rutinas

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navegacion() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    val pantallas = listOf(
        Pantallas.Rutinas to Icons.Outlined.DirectionsRun,
        Pantallas.Actividades to Icons.Outlined.LineWeight,
        Pantallas.Historial to Icons.Outlined.History,
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                pantallas.forEach { (pantalla, icono) ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = icono, contentDescription = null) },
                        label = { Text(pantalla.nombre) },
                        selected = currentRoute?.hierarchy?.any { it.route == pantalla.ruta } == true,
                        onClick = {
                            navController.navigate(pantalla.ruta) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Pantallas.Rutinas.ruta,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Pantallas.Rutinas.ruta) {
                Rutinas(navController)
            }

            composable(Pantallas.Actividades.ruta) {
                Actividades(navController)
            }

            composable(Pantallas.Historial.ruta) {
                Historial(navController)
            }
        }
    }
}