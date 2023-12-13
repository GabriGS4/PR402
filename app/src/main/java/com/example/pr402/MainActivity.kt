package com.example.pr402

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pr402.ui.theme.PR402Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PR402Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    concesionario()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun concesionario() {
    val topAppBarHeight = 56.dp // Puedes ajustar este valor según tus necesidades
    var showListadoVehiculos by remember { mutableStateOf(true) }
    var showCrearVehiculo by remember { mutableStateOf(false) }
    var showElegirNuevoVehiculo by remember { mutableStateOf(false) }
    val tipoVehiculos = arrayOf("Furgoneta", "Moto", "Patinete", "Trailer")
    var selectedVehiculo by remember { mutableStateOf(tipoVehiculos[0]) }
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "PR402",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        },
    ) {
        Box(Modifier.fillMaxSize()) {

            // Contenido de la pantalla
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = topAppBarHeight + 16.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.Center)
            ) {
                if (showListadoVehiculos) {
                    // Título de la lista
                    Text(
                        text = "LISTADO DE VEHÍCULOS",
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ElevatedButton(
                            onClick = {
                                showElegirNuevoVehiculo = true
                            },
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text("Crear Vehículo")
                        }

                        Spacer(modifier = Modifier.width(8.dp))


                        ElevatedButton(
                            onClick = {
                            },
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text("Consultar Vehículos")
                        }
                    }

                    listadoVehiculos(showListadoVehiculos)
                }
                if (showElegirNuevoVehiculo) {
                    AlertDialog(
                        title = {
                            Text(text = "Selecciona el tipo de vehículo")
                        },
                        text = {
                            var expanded by remember { mutableStateOf(false) }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            ) {
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = {
                                        expanded = !expanded
                                    }
                                ) {
                                    TextField(
                                        value = selectedVehiculo,
                                        onValueChange = {},
                                        readOnly = true,
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                        modifier = Modifier.menuAnchor()
                                    )

                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        tipoVehiculos.forEach { item ->
                                            DropdownMenuItem(
                                                text = { Text(text = item) },
                                                onClick = {
                                                    selectedVehiculo = item
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                        },
                        onDismissRequest = {
                            showElegirNuevoVehiculo = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showElegirNuevoVehiculo = false
                                    showListadoVehiculos = false
                                    showCrearVehiculo = true
                                }
                            ) {
                                Text("Aceptar")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    showElegirNuevoVehiculo = false
                                }
                            ) {
                                Text("Cancelar")
                            }
                        }

                        )
                }
                if (showCrearVehiculo) {
                    crearVehiculo(showCrearVehiculo, selectedVehiculo)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun listadoVehiculos(showListadoVehiculos: Boolean) {
    if (showListadoVehiculos) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            var busquedaText by remember { mutableStateOf("") }
            var vehiculos = arrayOf<Vehiculo>(
                Vehiculo(4, 2000, 5, "Rojo", "Seat Ibiza"),
                Vehiculo(4, 2000, 5, "Azul", "Seat León"),
                Vehiculo(4, 2000, 5, "Verde", "Seat Arona"),
                Vehiculo(4, 2000, 5, "Blanco", "Seat Ateca"),
                Vehiculo(2, 9000, 2, "Verde", "Yamaha R6"),
            )
            // Función para realizar la búsqueda
            fun buscarVehiculos(): Array<Vehiculo> {
                val busquedaLowerCase = busquedaText.lowercase()

                return if (busquedaLowerCase.isBlank()) {
                    // Si la búsqueda está en blanco, mostrar todos los vehiculos
                    vehiculos
                } else {
                    // Si la búsqueda no está en blanco, mostrar los vehiculos que coincidan con la búsqueda
                    vehiculos.filter {
                        it.modelo.lowercase().contains(busquedaLowerCase)
                    }.toTypedArray()
                }
            }

            // Campo de búsqueda
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = busquedaText,
                    onValueChange = { busquedaText = it },
                    label = { Text("Buscar vehículo") },
                    maxLines = 1,
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Icono de búsqueda"
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                )
            }
            // Lista de alumnos
            val resultadosBusqueda by remember(busquedaText) {
                derivedStateOf {
                    buscarVehiculos()
                }
            }

            // Lista de vehículos
            LazyColumn {
                if (resultadosBusqueda.isEmpty()) {
                    // Mostrar un mensaje si no hay resultados
                    item {
                        Text(
                            text = "No hay resultados",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                } else {
                    items(resultadosBusqueda) { vehiculo ->
                        // Elemento de la lista para cada alumno
                        Card(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            onClick = {
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                        Text(
                                            text = "Modelo: ${vehiculo.modelo}\n" +
                                                    "Color: ${vehiculo.color}\n" +
                                                    "Motor: ${vehiculo.motor}\n" +
                                                    "Asientos: ${vehiculo.asientos}\n" +
                                                    "Ruedas: ${vehiculo.ruedas}\n",
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(4.dp)
                                        )

                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun crearVehiculo(showCrearVehiculo: Boolean, selectedVehiculo: String ) {
    // - En el caso de los patinetes se ha de controlar que no se ponga asiento.
    //- En el de los tráileres que al menos tengan 6 ruedas, además de introducir la
    //carga máxima.
    //- En las furgonetas que tengan como máximo 6 ruedas y se necesita saber la
    //carga máxima.
    //- Las motos no pueden tener más de dos asientos.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            var modeloText by remember { mutableStateOf("") }
            var colorText by remember { mutableStateOf("") }
            var motorText by remember { mutableStateOf("") }
            var asientosText by remember { mutableStateOf("") }
            var ruedasText by remember { mutableStateOf("") }
            var errorText by remember { mutableStateOf("") }
            var error by remember { mutableStateOf(false) }

    }
}