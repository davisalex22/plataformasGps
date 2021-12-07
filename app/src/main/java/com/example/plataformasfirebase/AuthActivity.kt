package com.example.plataformasfirebase


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        title = "Autentication"
        btn_registar.setOnClickListener{
            if (et_email.text.isNotEmpty() && et_clave.text.isNotEmpty()){

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(et_email.text.toString(),
                        et_clave.text.toString()).addOnCompleteListener{
                        if (it.isSuccessful){
                            alerta("Registo", "Usuario registrado")
                            ir(it.result?.user?.email?:"", ProviderType.BASIC)
                        }else{
                            alerta("Registo", "No se pudo registrar el error")
                        }
                    }
            }

        }
        btn_acceder.setOnClickListener(){
            if (et_email.text.isNotEmpty() && et_clave.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(et_email.text.toString(),
                    et_clave.text.toString()).addOnCompleteListener(){
                    if (it.isSuccessful){
                        ir(it.result?.user?.email?:"", ProviderType.BASIC)
                    }else{
                        alerta("Inicio de sesión", "No se pudo iniciar sesión")
                    }
                }
            }
        }
    }

    private fun alerta(title:String, msg:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
    private fun ir(email:String, provider: ProviderType){
        startActivity(Intent(this,HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        })
    }
}