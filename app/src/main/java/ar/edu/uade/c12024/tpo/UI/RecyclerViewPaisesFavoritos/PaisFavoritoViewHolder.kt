package ar.edu.uade.c12024.tpo.UI.RecyclerViewPaisesFavoritos

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.uade.c12024.tpo.R

class PaisFavoritoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
    val bandera: ImageView = itemView.findViewById(R.id.imgBandera)
    val nombre: TextView = itemView.findViewById(R.id.txtPais)
}