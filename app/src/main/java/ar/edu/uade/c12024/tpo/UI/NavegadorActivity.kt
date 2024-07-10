package ar.edu.uade.c12024.tpo.UI

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import ar.edu.uade.c12024.tpo.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavegadorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_navegador)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //bind único
        val navegadorView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        navegadorView.setOnNavigationItemSelectedListener { item ->
            var fragmento : Fragment? = null
            when (item.itemId){
                R.id.navegador_banderas -> fragmento = BanderasFragment()
                R.id.navegador_favoritos -> fragmento = FavoritosFragment()
                R.id.navegador_usuario -> fragmento = UsuarioFragment()
            }
            if(fragmento != null){
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmento).commit()
                item.icon?.setTint(ContextCompat.getColor(this, R.color.inicio_gradiente))
            }
            true



        }
        navegadorView.selectedItemId = R.id.navegador_banderas
    }
}