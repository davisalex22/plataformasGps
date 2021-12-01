package com.example.plataformasfirebase.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.plataformasfirebase.R
import com.example.plataformasfirebase.model.GridItem

class GridAdapter(var context: Context, var arrayList: ArrayList<GridItem>) :
    RecyclerView.Adapter<GridAdapter.ItemHolder>() {

    /* El método crea el ViewHolder y su View asociada, y los inicializa, pero no completa el
       contenido de la vista
    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_layout, parent, false)
        return ItemHolder(viewHolder)
    }
    // RecyclerView llama a este método a fin de obtener el tamaño del conjunto de datos

    override fun getItemCount(): Int {
        return arrayList.size
    }

    // Llama a este método para asociar una ViewHolder con los datos.

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val gridItem: GridItem = arrayList.get(position)

        holder.image.setImageResource(gridItem.image!!)
        holder.description.text = gridItem.description

        holder.description.setOnClickListener {
            Toast.makeText(context, gridItem.description, Toast.LENGTH_LONG).show()
        }

    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image = itemView.findViewById<ImageView>(R.id.icons_image)
        var description = itemView.findViewById<TextView>(R.id.text_image)

    }
}