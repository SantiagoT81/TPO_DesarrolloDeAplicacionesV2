package ar.edu.uade.c12024.tpo.UI

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.uade.c12024.tpo.R
import ar.edu.uade.c12024.tpo.R.layout
import ar.edu.uade.c12024.tpo.UI.RecyclerViewPaisesGeneral.PaisGeneralAdapter
import ar.edu.uade.c12024.tpo.domain.model.PaisGeneral
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BanderasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BanderasFragment : Fragment() {
    private lateinit var viewModel: BanderasViewModel
    private lateinit var adapter: PaisGeneralAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var botonOrdenar: Button
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout.fragment_banderas, container, false)
        //bind único
        val rvPaises: RecyclerView = view.findViewById(R.id.rvFlags)
        botonOrdenar = view.findViewById(R.id.btnOrdenar)
        searchView = view.findViewById(R.id.scvBusqueda)
        progressBar = view.findViewById(R.id.pbCargar)
        progressBar.visibility = View.VISIBLE
        val animator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        animator.duration = 1000
        animator.start()

        rvPaises.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = PaisGeneralAdapter()
        rvPaises.adapter = adapter

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        viewModel = ViewModelProvider(requireActivity())[BanderasViewModel::class.java]

        //observe único
        viewModel.listaFiltrada.observe(viewLifecycleOwner, Observer {
                lista -> adapter.update(lista)
            progressBar.visibility = View.INVISIBLE
        })
        //adapter.update(it)

        viewModel.init()

        botonOrdenar.setOnClickListener {
            ordenar()
        }

        configurarSearchView(searchView)

        //COLOR SEARCHVIEW
        val id = searchView.context.resources.getIdentifier("android:id/search_src_text", null, null)
        val texto = searchView.findViewById<EditText>(id)
        texto.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
        texto.setHintTextColor(Color.GRAY)
        texto.setHint("Argentina...")

        return view
    }


    private fun checkUser(){
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
    }

    private fun ordenar(){
        val comparador = Comparator<PaisGeneral>{o1,o2 ->
            o1.name.toString().compareTo(o2.name.toString())
        }
        adapter.ordenar(comparador)
    }
    //Listener para que se actualice la lista filtrada según el string que esté en el SearchView
    private fun configurarSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //No hace el filtrado al presionar enter, sino por cada cambio de texto en el campo.
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(texto: String?): Boolean {
                viewModel.filtrarLista(texto)
                return true
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BanderasFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BanderasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}