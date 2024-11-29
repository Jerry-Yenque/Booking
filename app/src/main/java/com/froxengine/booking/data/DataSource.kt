package com.froxengine.booking.data

import com.froxengine.booking.data.model.SportCenter

import java.math.BigDecimal

fun getSportCenters(): List<SportCenter> {
    return listOf(
        SportCenter(
            id = "1",
            name = "Centro Deportivo Central",
            image = "R.drawable.center_1",
            description = "Centro deportivo completo con instalaciones para diversas disciplinas.",
            address = "Av. Siempre Viva 123, Springfield",
            price = BigDecimal(25.50),
            constraint = "Reservación previa",
            version = 1
        ),
        SportCenter(
            id = "2",
            name = "Gimnasio PowerFit",
            image = "R.drawable.center_1",
            description = "Gimnasio con equipamiento de última generación para entrenamiento de fuerza.",
            address = "Calle Fuerte 456, Capital City",
            price = BigDecimal(30.00),
            constraint = "Acceso sólo mayores de 18 años",
            version = 1
        ),
        SportCenter(
            id = "3",
            name = "Club de Tenis Las Palmas",
            image = "R.drawable.center_1",
            description = "Complejo de canchas de tenis con entrenadores profesionales disponibles.",
            address = "Calle Palma Real 789, Shelbyville",
            price = BigDecimal(15.75),
            constraint = "Reservación de cancha requerida",
            version = 1
        ),
        SportCenter(
            id = "4",
            name = "Piscina Olímpica Municipal",
            image = "R.drawable.center_1",
            description = "Piscina olímpica con áreas para nado recreativo y clases de natación.",
            address = "Av. del Agua 321, Springfield",
            price = BigDecimal(20.00),
            constraint = "Acceso con gorra de natación obligatoria",
            version = 1
        ),
        SportCenter(
            id = "5",
            name = "Campo de Fútbol Los Amigos",
            image = "R.drawable.center_1",
            description = "Campo de fútbol de césped natural para ligas amateur y torneos.",
            address = "Av. del Deporte 654, Capital City",
            price = BigDecimal(10.00),
            constraint = "Solo fines de semana",
            version = 1
        )
    )
}