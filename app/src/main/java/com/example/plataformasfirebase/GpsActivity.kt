package com.example.plataformasfirebase

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_gps.*
import com.google.android.gms.location.*
import java.util.*
import android.location.Geocoder

class GpsActivity : AppCompatActivity() {
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val PERMISSION_ID = 42
    private val REQUIRED_PERMISSIONS_GPS= arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)
     
        if (permisosConcedidos()) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            localizar()
        } else {     
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID
            )
        }
        btndetectar.setOnClickListener {
            localizar()
        }
    }

    private fun permisosConcedidos() = REQUIRED_PERMISSIONS_GPS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    private fun localizar(){
        if (permisos()){
            if (ubicacionActiva()){
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.lastLocation.addOnCompleteListener(this){ task ->
                        val location: Location? = task.result
                        if (location == null){
                            generarNuevaLocalizacion()
                        } else {
                            txt_latitud.text = "Latitud = " + location.latitude.toString()
                            txt_longitud.text = "Longitud = " + location.longitude.toString()
                            txt_altitud.text = "Altitud = " + location.altitude.toString()
                            val geocoder = Geocoder(this, Locale.getDefault())
                            val list: List<Address> = geocoder.getFromLocation(
                                location.latitude, location.longitude,1)
                            val dirCalle: Address = list[0]
                            direcccion.text = "Dirección: " + dirCalle.getAddressLine(0)
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Por favor activa la  ubicación de tu dispositivo", Toast.LENGTH_SHORT).show()
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                this.finish()
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
        }
    }

    @SuppressLint("MissingPermission")
    private fun generarNuevaLocalizacion(){
        val request = LocationRequest()
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        request.interval = 0
        request.fastestInterval = 0
        request.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(request, obtenerLocalizacion, Looper.myLooper())
    }

    private val obtenerLocalizacion = object : LocationCallback(){
        override fun onLocationResult(result: LocationResult) {
            val ubicacionObtenida : Location = result.lastLocation
            txt_latitud.text = "LATITUD = " + ubicacionObtenida.latitude.toString()
            txt_longitud.text = "LONGITUD = "+ ubicacionObtenida.longitude.toString()
            txt_altitud.text = "ALTITUD = " + ubicacionObtenida.altitude.toString()
        }
    }

    // Verificacion de permisos y ubicacion activa
    private fun ubicacionActiva(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun permisos(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
}


