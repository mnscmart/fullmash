package com.example.fullmash
import retrofit2.http.GET
import retrofit2.http.Query

interface PurpleAirService {
    @GET("v1/sensors")
    suspend fun getSensors(
        @Query("fields") fields: String = "name,latitude,longitude,pm2.5_atm",
        @Query("location_type") locationType: Int = 0,
        @Query("nwlng") nwLng: Double,
        @Query("nwlat") nwLat: Double,
        @Query("selng") seLng: Double,
        @Query("selat") seLat: Double,
        @Query("api_key") apiKey: String
    ): SensorsResponse
}