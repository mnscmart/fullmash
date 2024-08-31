package com.example.fullmash
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem
import java.util.*


class MainActivity : AppCompatActivity() {

    private val sensorsViewModel: SensorsViewModel by viewModels()

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private lateinit var map: MapView;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        setContentView(R.layout.activity_main);

        map = findViewById<MapView>(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK);
        val mapController = map.controller
        mapController.setZoom(13.5)
        val startPoint = GeoPoint(-3.10000, -60.0000);
        mapController.setCenter(startPoint);

        map.setBuiltInZoomControls(true)
        map.setMultiTouchControls(true)

        sensorsViewModel.sensorsLiveData.observe(this, androidx.lifecycle.Observer { sensors ->
            sensors?.let {
                map.overlays.clear()

                for (sensor in sensors) {

                    val location = GeoPoint(sensor.latitude, sensor.longitude)
                    val marker = Marker(map)
                    marker.position = location

                    val textDrawable = createTextMarkerDrawable(this, sensor.pm25Atm)
                    marker.icon = textDrawable

                    marker.title = "Estação: " + sensor.name + "\nLongitude: " + sensor.longitude + "\nLatitude: " + sensor.latitude + "\nQualidade do ar: " + sensor.pm25Atm.toString() + " PM2.5 (ug/m3)" + "\nFonte: Purpleair"

                    marker.setOnMarkerClickListener { m, mapView ->
                        if (m.isInfoWindowShown) {
                            m.closeInfoWindow()
                        } else {
                            m.showInfoWindow()
                        }
                        true
                    }

                    map.overlays.add(marker)
                }
                map.invalidate()
            }
        })

        sensorsViewModel.loadSensors(
            apiKey = "E7C27A40-653E-11EF-95CB-42010A80000E",
            nwLng = -60.1261,
            nwLat = -3.0072,
            seLng = -59.9261,
            seLat = -3.2072
        )
    }

    fun createTextMarkerDrawable(context: Context, airQuality: Double): BitmapDrawable {
        val textView = TextView(context)
        textView.text = airQuality.toString()
        textView.setTextColor(Color.BLACK)

        if (airQuality <= 25.0) {
            textView.setBackgroundColor(Color.GREEN)
        } else if (airQuality > 25.0 && airQuality <= 50) {
            textView.setBackgroundColor(Color.YELLOW)
        } else if (airQuality > 50.0 && airQuality <= 75) {
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.coral))
        } else if (airQuality > 75.0 && airQuality <= 125) {
            textView.setBackgroundColor(Color.RED)
        } else {
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.roxo))
        }

        textView.setPadding(10, 10, 10, 10)
        textView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        textView.layout(0, 0, textView.measuredWidth, textView.measuredHeight)

        val bitmap = Bitmap.createBitmap(textView.measuredWidth, textView.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        textView.draw(canvas)

        return BitmapDrawable(context.resources, bitmap)
    }



    override fun onResume() {
        super.onResume();
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause();
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsToRequest = ArrayList<String>();
        var i = 0;
        while (i < grantResults.size) {
            permissionsToRequest.add(permissions[i]);
            i++;
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE
            );
        }

    }


}
