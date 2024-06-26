package ar.edu.uade.c12024.tpo.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.uade.c12024.tpo.R
import ar.edu.uade.c12024.tpo.UI.RecyclerViewPaisesGeneral.PaisGeneralAdapter
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritosFragment : Fragment() {
    private lateinit var viewModel: FavoritosViewModel
    private lateinit var adapter: PaisGeneralAdapter
    private lateinit var firebaseAuth: FirebaseAuth
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)

        val rvFavs: RecyclerView = view.findViewById(R.id.rvFavoritos)
        rvFavs.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = PaisGeneralAdapter()
        rvFavs.adapter = adapter

        viewModel = ViewModelProvider(requireActivity())[FavoritosViewModel::class.java]
        viewModel.paises.observe(viewLifecycleOwner){
            adapter.update(it)
        }
        viewModel.init()

        return view
    }

    override fun onResume() {
        super.onResume()
        Log.d("API", "Volviendo a la pantalla")
        viewModel.init()


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoritosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoritosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}