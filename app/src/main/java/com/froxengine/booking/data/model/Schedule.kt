package com.froxengine.booking.data.model

data class Schedule(
    val schedules: List<ScheduleResponse>
) {
    fun getTimeSlotsForDate(date: String): List<TimeSlot> {
        // Busca el ScheduleResponse correspondiente a la fecha dada
        val scheduleForDate = schedules.find { it.date == date }
        // Si existe un ScheduleResponse para esa fecha, devuelve los timeSlots, si no, devuelve una lista vacía
        return scheduleForDate?.timeSlots ?: emptyList()
    }
}

data class ScheduleResponse(
    val _id: String,
    val sportCenterId: String,
    val date: String,
    val timeSlots: List<TimeSlot>,
    val __v: Int
)

data class TimeSlot(
    val _id: String,
    val startTime: String,
    val endTime: String,
    val isAvailable: Boolean
)

fun List<TimeSlot>.toFormattedTimeSlotList(): List<String> {
    return this.map { timeSlot ->
        "${timeSlot.startTime} Hrs - ${timeSlot.endTime} Hrs"
    }
}
