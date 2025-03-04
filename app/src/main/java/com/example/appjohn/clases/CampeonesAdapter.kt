package com.example.appjohn.clases

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appjohn.R
import com.example.appjohn.databinding.ItemLayoutBinding

// Adapter para RecyclerView
class CampeonesAdapter(private val context: Context, private val items: List<Campeones>) : RecyclerView.Adapter<CampeonesAdapter.PeliculaViewHolder>()
{

    // Infla el layout para cada ítem
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder
    {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PeliculaViewHolder(binding)
    }

    // Asigna los valores de los datos a cada vista
    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int)
    {
        holder.bind(items[position])
    }

    // Devuelve el tamaño de la lista
    override fun getItemCount(): Int
    {
        return items.size
    }

    class PeliculaViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: Campeones)
        {
            // Acceder a las vistas directamente a través del binding
            binding.nombre.text = data.nombre
            binding.posicion.text = data.posicion
            binding.clase.text = data.clase

            //Cambiar la imagen del icono de favorito según el valor de fav
            if (data.fav)
            {
                binding.fabFav.setImageResource(R.drawable.fav_selected) // Icono si es favorito
            }
            else
            {
                binding.fabFav.setImageResource(R.drawable.fav_unselected) // Icono si no es favorito
            }

            /* Evento OnClick */
            binding.fabFav.setOnClickListener{

                if (data.fav)
                    binding.fabFav.setImageResource(R.drawable.fav_unselected)
                else
                    binding.fabFav.setImageResource(R.drawable.fav_selected)

                data.fav = !data.fav
            }
        }
    }
}