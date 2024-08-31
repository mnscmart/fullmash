package com.example.fullmash
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fullmash.SensorData
import com.example.fullmash.fetchSensors
import kotlinx.coroutines.launch

class SensorsViewModel : ViewModel() {
    val sensorsLiveData = MutableLiveData<List<SensorData>?>()

    fun loadSensors(apiKey: String, nwLng: Double, nwLat: Double, seLng: Double, seLat: Double) {
        viewModelScope.launch {
            val sensors = fetchSensors(apiKey, nwLng, nwLat, seLng, seLat)
            sensorsLiveData.postValue(sensors)
        }
    }
}