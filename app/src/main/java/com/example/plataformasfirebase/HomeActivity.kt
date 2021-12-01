package com.example.plataformasfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plataformasfirebase.adapters.GridAdapter
import com.example.plataformasfirebase.model.GridItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

enum class ProviderType{
    BASIC
}
class HomeActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var gridItem: ArrayList<GridItem>? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var gridAdapter: GridAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        /*
        * RecyclerView solicita esas vistas y las vincula a sus datos mediante llamadas a los
        *  métodos en el adaptador.
        */

        recyclerView = findViewById(R.id.recycler_view_item)
        gridLayoutManager =
            GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)

        gridItem = ArrayList()
        gridItem = setGrids()
        gridAdapter = GridAdapter(applicationContext, gridItem!!)
        recyclerView?.adapter = gridAdapter


        val bundle:Bundle?=intent.extras
        val email:String? = bundle?.getString("email")
        val provider:String? = bundle?.getString("provider")
        setup(email?:"", provider?:"")
    }
    private fun setup(email:String, provider:String){
        title ="Inicio"
        tv_correo.text="Usuario: "+email
        //tv_provider.text=provider

        btn_salir.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
    private fun setGrids(): ArrayList<GridItem> {

        var arrayList: ArrayList<GridItem> = ArrayList()

        // Añadir grids
        arrayList.add(GridItem(R.drawable.yogur, "Yogurt"))
        arrayList.add(GridItem(R.drawable.avena, "Avena"))
        arrayList.add(GridItem(R.drawable.pan, "Pan"))
        arrayList.add(GridItem(R.drawable.soda, "Soda"))
        arrayList.add(GridItem(R.drawable.galletas, "Galletas"))
        arrayList.add(GridItem(R.drawable.azucar, "Azúcar"))
        arrayList.add(GridItem(R.drawable.pizza, "Pizza"))
        arrayList.add(GridItem(R.drawable.cafe, "Café"))
        arrayList.add(GridItem(R.drawable.helado, "Helado"))


        return arrayList
    }
}