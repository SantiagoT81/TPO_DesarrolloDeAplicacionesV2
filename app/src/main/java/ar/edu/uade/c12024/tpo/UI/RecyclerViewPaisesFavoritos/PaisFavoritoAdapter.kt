package ar.edu.uade.c12024.tpo.UI.RecyclerViewPaisesFavoritos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.uade.c12024.tpo.R
import ar.edu.uade.c12024.tpo.domain.model.PaisDetalles
import ar.edu.uade.c12024.tpo.domain.model.PaisGeneral

class PaisFavoritoAdapter: RecyclerView.Adapter<PaisFavoritoViewHolder>(){
    var favoritos: MutableList<PaisGeneral> = ArrayList<PaisGeneral>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisFavoritoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.paisgeneral_item, parent, false)
        return PaisFavoritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaisFavoritoViewHolder, position: Int) {
        TODO("Not yet implemented")

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}