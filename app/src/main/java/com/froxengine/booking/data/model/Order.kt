package com.froxengine.booking.data.model

/**
 * Represents an order made by a user for reserving a specific time slot at a sports center.
 *
 * This class stores the details of a reservation, including the selected sports center,
 * the selected time slot, a list of available time slots, and the total cost of the reservation.
 *
 * @property sportCenterSelected The ID of the sport center selected by the user.
 *                               This field is an empty string by default.
 * @property timeSelected The specific time selected for the reservation in a readable format
 *                        (e.g., "2024-11-09"). Defaults to an empty string.
 * @property timeSlots A list of selected time slots (`TimeSlot`) for the selected sports center reservation.
 *                     This is required to provide options to the user for booking.
 * @property total The total amount to be paid for the reservation in string format. It should be
 *                 a formatted value (e.g., "20.00") depending on the currency.
 *
 * Example:
 * ```
 * val order = Order(
 *     sportCenterSelected = "Downtown Gym",
 *     timeSelected = "10:00 AM - 11:00 AM",
 *     timeSlots = listOf(TimeSlot("10:00 AM - 11:00 AM"), TimeSlot("11:00 AM - 12:00 PM")),
 *     total = "$20.00"
 * )
 * ```
 */
data class Order(
    val sportCenterSelected : String = "",
    val timeSelected: String = "",
    val timeSlots: List<TimeSlot> = emptyList(),
    val total: String = "0.00"
)
