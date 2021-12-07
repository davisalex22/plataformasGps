package com.example.plataformasfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_ingresar.setOnClickListener(){
            startActivity(Intent(this,AuthActivity::class.java))
        }
        btn_gps.setOnClickListener(){
            startActivity(Intent(this,GpsActivity::class.java))
        }
    }
}