//package com.example.fullmash
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.fullmash.R
//
//class SensorAdapter(private val sensors: List<SensorData>) :
//    RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_sensor, parent, false)
//        return SensorViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
//        val sensor = sensors[position]
//        holder.bind(sensor)
//    }
//
//    override fun getItemCount(): Int = sensors.size
//
//    inner class SensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val sensorName: TextView = itemView.findViewById(R.id.sensorName)
//        private val sensorLocation: TextView = itemView.findViewById(R.id.sensorLocation)
//        private val sensorPm25: TextView = itemView.findViewById(R.id.sensorPm25)
//
//        fun bind(sensor: SensorData) {
//            sensorName.text = sensor.name
//            sensorLocation.text = "Lat: ${sensor.latitude}, Lng: ${sensor.longitude}"
//            sensorPm25.text = "PM2.5: ${sensor.pm25Atm}"
//        }
//    }
//}