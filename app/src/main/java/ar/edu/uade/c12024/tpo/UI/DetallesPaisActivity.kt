package ar.edu.uade.c12024.tpo.UI

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import ar.edu.uade.c12024.tpo.R
import com.bumptech.glide.Glide

class DetallesPaisActivity : AppCompatActivity() {

    lateinit var vm: DetallesPaisViewModel
    lateinit var pb: ProgressBar
    lateinit var imagen: ImageView
    lateinit var emblema: ImageView
    lateinit var pais: TextView
    lateinit var region: TextView
    lateinit var capital: TextView
    lateinit var divisa: TextView
    lateinit var zonaHoraria: TextView
    lateinit var prefijo: TextView

    lateinit var botonFavorito: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles_pais)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        vm = ViewModelProvider(this)[DetallesPaisViewModel::class.java]


        bind()
        pb.visibility = View.VISIBLE

        val name = intent.getStringExtra("name")!!
        vm.init(name)
        observe()

}
    private fun bind(){
        pais = findViewById(R.id.txtNombrePais)
        region = findViewById(R.id.txtRegion)
        capital = findViewById(R.id.txtCapital)
        divisa = findViewById(R.id.txtDivisa)
        zonaHoraria = findViewById(R.id.txtZonaHoraria)
        prefijo = findViewById(R.id.txtPrefijo)
        imagen = findViewById(R.id.imgBanderaDetallada)
        emblema = findViewById(R.id.imgHeraldica)
        pb = findViewById(R.id.pbProgreso)

        botonFavorito = findViewById(R.id.imgFavorito)
    }

    private fun observe(){
        vm.pais.observe(this) {
            val name = intent.getStringExtra("name")!!
            pais.text = it.name.common
            region.text = it.region
            capital.text = it.capital.firstOrNull() ?: "N/A"
            divisa.text = it.currencies.values.firstOrNull()?.name ?: "N/A"
            zonaHoraria.text = it.timezones.firstOrNull() ?: "N/A"
            prefijo.text = "${it.idd.root}${it.idd.suffixes.firstOrNull() ?: ""}"
            botonFavorito.setOnClickListener {
                vm.agregarRemoverFavorito(name)
            }
            pb.visibility = View.INVISIBLE

            Glide.with(this)
                .load(it.flags.png)
                .override(302, 171)
                .into(imagen)

            Glide.with(this)
                .load(it.coatOfArms.png)
                .override(76, 68)
                .into(emblema)
        }

        vm.agregado.observe(this) {agregado ->
            if(agregado){
                Toast.makeText(this, "Agregado a favoritos", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Error al agregar el favorito", Toast.LENGTH_SHORT).show()
            }
        }
        vm.borrado.observe(this){borrado ->
            if(borrado){
                Toast.makeText(this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Error al eliminar el favorito", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
