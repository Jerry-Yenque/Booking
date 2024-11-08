package com.froxengine.booking.presentation.screens.detail

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.froxengine.booking.data.model.toFormattedTimeSlotList
import com.froxengine.booking.presentation.components.AvailableDatePickerDialog
import com.froxengine.booking.presentation.navigation.AppScreen
import com.froxengine.booking.presentation.screens.home.HomeViewModel
import com.froxengine.booking.presentation.screens.home.components.Base64Image
import java.math.BigDecimal
import java.time.LocalDate

@Composable
fun SportCenterReservationScreen(navController: NavHostController, homeViewModel: HomeViewModel) { // sportCenterId: String
    val orderState by homeViewModel.orderState.collectAsState()


    val sportCenter = homeViewModel.sportCenterSelected
    val context = LocalContext.current
    LaunchedEffect(sportCenter) {
        if (sportCenter != null) {
            homeViewModel.getSchedulesForSelectedSportCenter()
        }
    }

//    LaunchedEffect(homeViewModel.schedules) {
//        if (homeViewModel.schedules != null) {
//            Toast.makeText(context, "Obtained: ${homeViewModel.schedules}", Toast.LENGTH_LONG).show()
//        }
//    }
//    val scheduleDates = homeViewModel.getScheduleDates()
    val scheduleDates by remember(homeViewModel.schedules) {
        derivedStateOf { homeViewModel.getScheduleDates() }
    }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimeSlots by remember { mutableStateOf(false) }
    val timeSlots = homeViewModel.timeSlots.toFormattedTimeSlotList()
    val selectedTimeSlots = remember { mutableStateListOf<String>() }
    val costPerHour: BigDecimal = homeViewModel.sportCenterSelected?.price ?: BigDecimal(50.00)

    LaunchedEffect(selectedTimeSlots) {
        snapshotFlow { selectedTimeSlots.toList() }
            .collect { newList ->
                Log.v("Booking","showTimeSlots changed to $newList")
                homeViewModel.calculateTotal(newList.size)
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "CENTRO DEPORTIVO 1",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Base64Image(sportCenter = homeViewModel.sportCenterSelected!!)
        Spacer(modifier = Modifier.height(16.dp))
        Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Descripción: ${ homeViewModel.sportCenterSelected?.description ?: "" }", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Lugar: ${homeViewModel.sportCenterSelected?.address ?: ""}")
        Spacer(modifier = Modifier.height(16.dp))

        // Fecha
        OutlinedTextField(
            value = orderState.timeSelected,
            onValueChange = {},
            label = { Text("Fecha") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (showDatePicker) {
            AvailableDatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                onDateSelected = { date ->
                    homeViewModel.setSlotsBySelectedDate(date.toString())
//                    Toast.makeText(context, "Date selected perrooo ${homeViewModel.timeSelected}", Toast.LENGTH_SHORT).show()
                    showDatePicker = false
                },
                availableDates = scheduleDates
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Horario Disponible
        Button(
            enabled= orderState.timeSelected.isNotEmpty(),
            onClick = { showTimeSlots = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Horario disponible")
        }
//        String.format("%,.2f", payable.crossTotal)
        if (showTimeSlots) {
            AlertDialog(
                onDismissRequest = { showTimeSlots = false },
                title = { Text("Selecciona los horarios") },
                text = {
                    Column {
                        timeSlots.forEach { slot ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Checkbox(
                                    checked = selectedTimeSlots.contains(slot),
                                    onCheckedChange = { isChecked ->
                                        if (isChecked) {
                                            selectedTimeSlots.add(slot)
                                        } else {
                                            selectedTimeSlots.remove(slot)
                                        }
                                    }
                                )
                                Text(text = slot)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showTimeSlots = false }) {
                        Text(text = "Aceptar")
                    }
                },
//                dismissButton = {
//                    TextButton(onClick = { showTimeSlots = false }) {
//                        Text(text = "Cancelar", color = Color.Red)
//                    }
//                }
            )
        }

        Spacer(modifier = Modifier.height(26.dp))
        Text(text = "Costo de reserva: S/$costPerHour por hora")
        Spacer(modifier = Modifier.height(26.dp))
        Text(text = "Prohibiciones: ${homeViewModel.sportCenterSelected?.constraint}", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Reservar
        Button(
            onClick = { navController.navigate(AppScreen.RegisterClientScreen.route) },
            enabled = selectedTimeSlots.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "RESERVAR")
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Total de costo
        Text(text = "Total: S/${String.format("%.2f", orderState.total.toDouble())}")
    }
}

// Composable auxiliar para el DatePicker


fun getAvailableDates(): List<LocalDate> {
    val availableDates = listOf(
        LocalDate.of(2024, 10, 25),
        LocalDate.of(2024, 10, 27),
        LocalDate.of(2024, 10, 30),
        LocalDate.of(2024, 10, 2),
        LocalDate.of(2024, 10, 5),
        LocalDate.of(2024, 10, 8)
    )
    return availableDates
}



//@Preview(showSystemUi = true)
//@Composable
//fun PreviewReservation() {
//    BooKingTheme {
//        SportCenterReservationScreen()
//    }
//}