package ar.edu.uade.c12024.tpo.UI.RecyclerViewPaisesGeneral

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.uade.c12024.tpo.R
import ar.edu.uade.c12024.tpo.UI.DetallesPaisActivity
import ar.edu.uade.c12024.tpo.domain.model.PaisGeneral
import com.bumptech.glide.Glide

class PaisGeneralAdapter: RecyclerView.Adapter<PaisGeneralViewHolder>() {
    var banderas: MutableList<PaisGeneral> = ArrayList<PaisGeneral>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisGeneralViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.paisgeneral_item, parent, false )
        return PaisGeneralViewHolder(view)

    }

    override fun getItemCount(): Int {
        //Log.d("VISTA_BANDERAS", "Size: ${banderas.size}")
        return banderas.size
    }

    override fun onBindViewHolder(holder: PaisGeneralViewHolder, position: Int) {
        //Log.d("VISTA_BANDERAS", "Position: $position")
        holder.nombre.text = banderas[position].name.common
        Glide.with(holder.itemView.context)
            .load(banderas[position].flags.png)
            .override(320, 160)
            .centerCrop()
            .into(holder.bandera)

        //Listener en la imagen de cada bandera para redireccionar a los detalles de dicho país
        holder.bandera.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetallesPaisActivity::class.java).apply {
                putExtra("name", banderas[position].name.common)
            }
            holder.itemView.context.startActivity(intent)
        }
    }


    //Actualizar array con favoritos
    fun update(lista: List<Any>){
        banderas = lista as MutableList<PaisGeneral>
        this.notifyDataSetChanged()
    }

    //Ordenamiento de banderas en base al nombre (descendente)
    fun ordenar(comparador: Comparator<PaisGeneral>){
        banderas.sortWith(comparador)
        notifyDataSetChanged()
    }
}