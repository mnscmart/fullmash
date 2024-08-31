package com.example.fullmash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import android.util.Log

suspend fun fetchSensors(
    apiKey: String,
    nwLng: Double,
    nwLat: Double,
    seLng: Double,
    seLat: Double
): List<SensorData>? {
    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.purpleAirService.getSensors(
                fields = "name,latitude,longitude,pm2.5_atm",
                locationType = 0,
                nwLng = nwLng,
                nwLat = nwLat,
                seLng = seLng,
                seLat = seLat,
                apiKey = apiKey
            )

            // Log a resposta da API
            Log.d("API_RESPONSE", "Resposta da API: $response")

            // Transformar a resposta bruta em uma lista de SensorData
            response.data.map { item ->
                SensorData(
                    sensorIndex = item[0] as Double, // Atualizado para Double
                    name = item[1] as String,
                    latitude = item[2] as Double,
                    longitude = item[3] as Double,
                    pm25Atm = item[4] as Double
                )
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Erro ao buscar sensores: ${e.message}")

            null
        }
    }
}