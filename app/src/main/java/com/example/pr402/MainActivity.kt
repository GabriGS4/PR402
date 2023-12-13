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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
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
    var showListadoVehiculos = remember { mutableStateOf(true) }
    var showCrearVehiculo = remember { mutableStateOf(false) }
    var showElegirNuevoVehiculo by remember { mutableStateOf(false) }
    var showConsultarVehiculos by remember { mutableStateOf(false) }
    var showPreguntarConsultarVehiculos by remember { mutableStateOf(false) }
    val tipoVehiculos = arrayOf("Coche", "Furgoneta", "Moto", "Patinete", "Trailer")
    var selectedVehiculo by remember { mutableStateOf(tipoVehiculos[0]) }

    var vehiculos = remember { mutableStateOf(arrayOf<Vehiculo>()) }



    Scaffold(
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
                if (showListadoVehiculos.value) {
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
                                showPreguntarConsultarVehiculos = true
                            },
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text("Consultar Vehículos")
                        }
                    }

                    listadoVehiculos(showListadoVehiculos, vehiculos)
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
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded
                                            )
                                        },
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
                                    showListadoVehiculos.value = false
                                    showCrearVehiculo.value = true
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
                if (showCrearVehiculo.value) {
                    crearVehiculo(
                        showCrearVehiculo,
                        selectedVehiculo,
                        showListadoVehiculos,
                        vehiculos
                    )
                }
                if (showPreguntarConsultarVehiculos) {
                    AlertDialog(
                        title = {
                            Text(text = "Consultar número de vehículos")
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
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded
                                            )
                                        },
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
                            showPreguntarConsultarVehiculos = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showPreguntarConsultarVehiculos = false
                                    showConsultarVehiculos = true
                                }
                            ) {
                                Text("Aceptar")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    showPreguntarConsultarVehiculos = false
                                }
                            ) {
                                Text("Cancelar")
                            }
                        }

                    )
                }
                if (showConsultarVehiculos) {
                    AlertDialog(
                        title = {
                            when (selectedVehiculo) {
                                "Coche" -> {
                                    Text(
                                        text = "Hay ${vehiculos.value.filter { it.tipo == "Coche" }.size} coches"
                                    )
                                }
                                "Furgoneta" -> {
                                    Text(
                                        text = "Hay ${vehiculos.value.filter { it.tipo == "Furgoneta" }.size} furgonetas"
                                    )
                                }
                                "Moto" -> {
                                    Text(
                                        text = "Hay ${vehiculos.value.filter { it.tipo == "Moto" }.size} motos"
                                    )
                                }
                                "Patinete" -> {
                                    Text(
                                        text = "Hay ${vehiculos.value.filter { it.tipo == "Patinete" }.size} patinetes"
                                    )
                                }
                                else -> {
                                    Text(
                                        text = "Hay ${vehiculos.value.filter { it.tipo == "Trailer" }.size} trailers"
                                    )
                                }
                            }
                        },

                        onDismissRequest = {
                            showConsultarVehiculos = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showConsultarVehiculos = false
                                }
                            ) {
                                Text("Aceptar")
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun listadoVehiculos(
    showListadoVehiculos: MutableState<Boolean>,
    vehiculos: MutableState<Array<Vehiculo>>
) {
    if (showListadoVehiculos.value) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            var busquedaText by remember { mutableStateOf("") }


            // Función para realizar la búsqueda
            fun buscarVehiculos(): Array<Vehiculo> {
                val busquedaLowerCase = busquedaText.lowercase()

                return if (busquedaLowerCase.isBlank()) {
                    // Si la búsqueda está en blanco, mostrar todos los vehiculos
                    vehiculos.value
                } else {
                    // Si la búsqueda no está en blanco, mostrar los vehiculos que coincidan con la búsqueda
                    vehiculos.value.filter {
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
                                        text = "Tipo: ${vehiculo.tipo}\n" +
                                                "Modelo: ${vehiculo.modelo}\n" +
                                                "Color: ${vehiculo.color}\n" +
                                                "Motor: ${vehiculo.motor}\n" +
                                                "Asientos: ${vehiculo.asientos}\n" +
                                                "Ruedas: ${vehiculo.ruedas}\n",
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(4.dp)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))
                                    when (vehiculo.tipo) {
                                        "Coche" -> {
                                            Icon(
                                                painter = painterResource(id = R.drawable.car),
                                                contentDescription = null // decorative element
                                            )
                                        }
                                        "Furgoneta" -> {
                                            Icon(
                                                painter = painterResource(id = R.drawable.van),
                                                contentDescription = null // decorative element
                                            )
                                        }
                                        "Moto" -> {
                                            Icon(
                                                painter = painterResource(id = R.drawable.motorcycle),
                                                contentDescription = null // decorative element
                                            )
                                        }
                                        "Patinete" -> {
                                            Icon(
                                                painter = painterResource(id = R.drawable.scooter),
                                                contentDescription = null // decorative element
                                            )
                                        }
                                        else -> {
                                            Icon(
                                                painter = painterResource(id = R.drawable.truck),
                                                contentDescription = null // decorative element
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
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun crearVehiculo(
    showCrearVehiculo: MutableState<Boolean>,
    selectedVehiculo: String,
    showListadoVehiculos: MutableState<Boolean>,
    vehiculos: MutableState<Array<Vehiculo>>
) {
    var modeloText by remember { mutableStateOf("") }
    var colorText by remember { mutableStateOf("") }
    var motorText by remember { mutableStateOf("") }
    var asientosText by remember { mutableStateOf("") }
    var ruedasText by remember { mutableStateOf("") }
    var cargaText by remember { mutableStateOf("") }
    var errorText = remember { mutableListOf<String>() }
    var error by remember { mutableStateOf(false) }

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
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "CREAR VEHÍCULO $selectedVehiculo",
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )

        OutlinedTextField(
            value = modeloText,
            onValueChange = { modeloText = it },
            label = { Text("Modelo") },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = colorText,
            onValueChange = { colorText = it },
            label = { Text("Color") },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = motorText,
            onValueChange = {
                // Solo actualiza el valor si es un número
                if (it.toIntOrNull() != null) {
                    motorText = it
                }

            },
            label = { Text("Motor") },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
        if (selectedVehiculo != "Patinete") {
            OutlinedTextField(
                value = asientosText,
                onValueChange = {
                    // Solo actualiza el valor si es un número
                    if (it.toIntOrNull() != null) {
                        asientosText = it
                    }
                },
                label = { Text("Asientos") },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        OutlinedTextField(
            value = ruedasText,
            onValueChange = {
                // Solo actualiza el valor si es un número
                if (it.toIntOrNull() != null) {
                    ruedasText = it
                }
            },
            label = { Text("Ruedas") },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        if (selectedVehiculo == "Furgoneta" || selectedVehiculo == "Trailer") {
            OutlinedTextField(
                value = cargaText,
                onValueChange = {
                    // Solo actualiza el valor si es un número
                    if (it.toIntOrNull() != null) {
                        cargaText = it
                    }
                },
                label = { Text("Carga máxima") },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        if (error) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)

            ) {
                // Recorremos los errores de errorText
                errorText.forEach {
                    // Mostramos un mensaje de error por cada error
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                errorText.clear()
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    showCrearVehiculo.value = false
                    showListadoVehiculos.value = true
                },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text("Cancelar")
            }
            Spacer(modifier = Modifier.width(8.dp))

            ElevatedButton(
                onClick = {
                    error = false
                    if (modeloText == "") {
                        error = true
                        errorText.add("El modelo no puede estar vacío")
                    }
                    if (colorText == "") {
                        error = true
                        errorText.add("El color no puede estar vacío")
                    }
                    if (motorText == "") {
                        error = true
                        errorText.add("El motor no puede estar vacío")
                    }
                    if (asientosText == "" && selectedVehiculo != "Patinete") {
                        error = true
                        errorText.add("Los asientos no pueden estar vacíos")
                    }
                    if (ruedasText == "") {
                        error = true
                        errorText.add("Las ruedas no pueden estar vacías")
                    }
                    if (cargaText == "" && selectedVehiculo == "Furgoneta") {
                        error = true
                        errorText.add("La carga no puede estar vacía")
                    }
                    if (selectedVehiculo == "Patinete") {
                        if (asientosText != "") {
                            error = true
                            errorText.add("Los patinetes no tienen asientos")
                        }
                    }
                    if (selectedVehiculo == "Trailer") {
                        if (ruedasText != "" && ruedasText.toInt() < 6) {
                            error = true
                            errorText.add("Los trailers tienen que tener al menos 6 ruedas")
                        }
                    }
                    if (selectedVehiculo == "Furgoneta") {
                        if (ruedasText != "" && ruedasText.toInt() > 6) {
                            error = true
                            errorText.add("Las furgonetas no pueden tener más de 6 ruedas")
                        }
                    }
                    if (selectedVehiculo == "Moto") {
                        if (asientosText != "" && asientosText.toInt() > 2) {
                            error = true
                            errorText.add("Las motos no pueden tener más de 2 asientos")
                        }
                    }
                    if (!error) {
                        // Añadimos el nuevo vehículo al array de nuevos vehiculos
                        val newVehicle = when (selectedVehiculo) {
                            "Coche" -> Coche(
                                ruedasText.toInt(),
                                motorText.toInt(),
                                asientosText.toInt(),
                                colorText,
                                modeloText,
                                selectedVehiculo
                            )

                            "Furgoneta" -> Furgoneta(
                                ruedasText.toInt(),
                                motorText.toInt(),
                                asientosText.toInt(),
                                colorText,
                                modeloText,
                                selectedVehiculo,
                                cargaText.toInt()
                            )

                            "Moto" -> Moto(
                                ruedasText.toInt(),
                                motorText.toInt(),
                                asientosText.toInt(),
                                colorText,
                                modeloText,
                                selectedVehiculo
                            )

                            "Patinete" -> Patinete(
                                ruedasText.toInt(),
                                motorText.toInt(),
                                asientosText.toInt(),
                                colorText,
                                modeloText,
                                selectedVehiculo
                            )

                            else -> Trailer(
                                ruedasText.toInt(),
                                motorText.toInt(),
                                asientosText.toInt(),
                                colorText,
                                modeloText,
                                selectedVehiculo
                            )
                        }
                        // Actualizamos el array de vehículos
                        vehiculos.value = vehiculos.value.plus(newVehicle)
                        showCrearVehiculo.value = false
                        showListadoVehiculos.value = true

                    }
                },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text("Crear Vehículo")
            }

        }
    }
}
