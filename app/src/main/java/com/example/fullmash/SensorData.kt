package com.example.fullmash

data class SensorData(
    val sensorIndex: Double,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val pm25Atm: Double
)

data class SensorsResponse(
    val data: List<List<Any>>
)
