package com.froxengine.booking.presentation.screens.home

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.froxengine.booking.BooKing
import com.froxengine.booking.data.model.ContactDto
import com.froxengine.booking.data.model.DNIDto
import com.froxengine.booking.data.model.DniClient
import com.froxengine.booking.data.model.IdentificationType
import com.froxengine.booking.data.model.RUCDto
import com.froxengine.booking.data.model.Schedule
import com.froxengine.booking.data.model.SportCenter
import com.froxengine.booking.data.model.TimeSlot
import com.froxengine.booking.data.repository.ContactService
import com.froxengine.booking.domain.repository.ScheduleRepository
import com.froxengine.booking.domain.repository.SportCenterRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("StaticFieldLeak")
class HomeViewModel(private val sportCenterRepository: SportCenterRepository, private val scheduleRepository: ScheduleRepository): ViewModel() {

    private var contactDto: ContactDto? = null
    private var dniDto: DNIDto? = null
    private var rucDto: RUCDto? = null

    private lateinit var contactService: ContactService

    val contactServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            serviceBinder: IBinder
        ) {
            contactService = (serviceBinder as ContactService.ServiceBinder).service
            Log.v("Booking", "ContactService connected")
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            TODO("Not yet implemented")
        }
    }

    var sportCenter: List<SportCenter> by mutableStateOf(emptyList())
        private set

    var sportCenterSelected: SportCenter? by mutableStateOf(null)
        private set

    var schedules: Schedule? by mutableStateOf(null)
        private set

    var timeSelected: String? by mutableStateOf(null)
        private set

    var timeSlots: List<TimeSlot> by mutableStateOf(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var clientName by mutableStateOf("")
        private set

    init {
        getSportCenters()
    }

    private fun getSportCenters() {
        viewModelScope.launch {
            try {
                sportCenter = sportCenterRepository.getSportCenters()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setSlotsBySelectedDate(seletedDate: String) {
        timeSelected = seletedDate
        timeSlots = schedules?.getTimeSlotsForDate(timeSelected.toString()) ?: emptyList()
    }

    fun sportCenterSelected(sportCenter: SportCenter) {
        sportCenterSelected = sportCenter;
    }

    fun getSchedulesForSelectedSportCenter() {
        sportCenterSelected?.let {
            viewModelScope.launch {
                try {
                    schedules = scheduleRepository.getSchedulesByCenterId(sportCenterSelected!!.id)
                    Log.d("Logger", "obtained $schedules")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun findContactByType(contactDtos: List<ContactDto>, typeDoc: IdentificationType): List<ContactDto?> {
        return contactDtos.filter { it.idType == typeDoc }
    }

    fun search(term: String, typeDoc: IdentificationType) {
        isLoading = true
        try {
            contactService.findContacts(term) { contactDtos: List<ContactDto> ->
                val contact = findContactByType(contactDtos, typeDoc)
                if (contact.size > 1) {
                    val firstIdNumber = contact.first()!!.idNumber
                    if (contact.all { it!!.idNumber == firstIdNumber }) {
                        Log.v("Booking", "Posible contacto duplicado, comunique al administrador")
                    }
                }
                if (contact.isEmpty()) {
                    handleNotFound(typeDoc, term)
                } else {
                    this.contactDto = contact[0]
                    clientName = this.contactDto?.name ?: ""
                    isLoading = false
                }
            }
        } catch (e: Exception) {
            Log.e("Booking", "Error: ${e.printStackTrace()}")
        }
    }

    private fun handleNotFound(docType: IdentificationType, term: String) {
        when (docType) {
            IdentificationType.DNI -> {
                contactService.getDNI(term) { dniDto: DNIDto? ->
                    if (dniDto == null) {
                        Log.d("Booking", "nothing found")
                    } else {
                        this.rucDto = null
                        this.dniDto = dniDto
                        clientName = this.dniDto?.name ?: ""
                    }
                    isLoading = false
                }
            }
            IdentificationType.RUC -> {
                contactService.getRUC(term) { rucDto: RUCDto? ->
                    if (rucDto == null) {
                        Log.d("Booking", "nothing found")
                    } else {
                        this.rucDto = rucDto
                        this.dniDto = null
                        clientName = this.rucDto?.name ?: ""
                    }
                    isLoading = false
                }
            }
            else -> {
//                binding.lpiLoading.visibility = View.INVISIBLE
//                if (showBtnOmitir) {binding.btnSiguiente.visibility = Visibility.VISIBLE.asInt}
//                binding.btnSiguiente.isEnabled = false
//                binding.etEmail.visibility = View.VISIBLE
                isLoading = false
            }
        }
    }

    fun getScheduleDates(): List<LocalDate> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return schedules?.schedules?.mapNotNull { schedule ->
            try {
                LocalDate.parse(schedule.date, formatter)
            } catch (e: Exception) {
                null // Ignora las fechas no válidas
            }
        } ?:  emptyList()
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BooKing)
                val sportCenterRepository = application.container.sportCenterRepository
                val scheduleRepository = application.container.scheduleRepository
                HomeViewModel(sportCenterRepository = sportCenterRepository, scheduleRepository= scheduleRepository)
            }
        }
    }
}