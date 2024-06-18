package ar.edu.uade.c12024.tpo.trashcan

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.uade.c12024.tpo.R
import ar.edu.uade.c12024.tpo.UI.BanderasViewModel
import ar.edu.uade.c12024.tpo.UI.RecyclerViewPaisesGeneral.PaisGeneralAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: BanderasViewModel
    //TODO() RecyclerView de paises y Adapter
    private lateinit var rvPaises: RecyclerView
    private lateinit var adapter: PaisGeneralAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvPaises = findViewById(R.id.rvBanderas)
        rvPaises.layoutManager = GridLayoutManager(this,3)
        adapter = PaisGeneralAdapter()
        rvPaises.adapter = adapter
        viewModel = ViewModelProvider(this)[BanderasViewModel::class.java]
        viewModel.paises.observe(this){
            adapter.update(it)
        }
        viewModel.init()
    }
}