package com.example.plataformasfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebase()

        btn_ingresar.setOnClickListener(){
            val saltargps:Intent=Intent(this,AuthActivity::class.java)
            startActivity(saltargps)
        }
        btn_gps.setOnClickListener(){
            val saltargps:Intent=Intent(this,GpsActivity::class.java)
            startActivity(saltargps)
        }
    }
    fun firebase(){
        val analitycs:FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message","Integracion con frirebase completa")
        analitycs.logEvent("InitScreen", bundle)
    }


}