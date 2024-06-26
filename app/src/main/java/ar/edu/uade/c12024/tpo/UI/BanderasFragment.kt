package ar.edu.uade.c12024.tpo.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.uade.c12024.tpo.R
import ar.edu.uade.c12024.tpo.R.layout
import ar.edu.uade.c12024.tpo.UI.RecyclerViewPaisesGeneral.PaisGeneralAdapter
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

        val rvPaises: RecyclerView = view.findViewById(R.id.rvFlags)
        rvPaises.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = PaisGeneralAdapter()
        rvPaises.adapter = adapter

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        viewModel = ViewModelProvider(requireActivity())[BanderasViewModel::class.java]
        viewModel.paises.observe(viewLifecycleOwner) {
            adapter.update(it)
        }
        viewModel.init()

        //viewModel.agregado.observe(viewLifecycleOwner) {agregado ->
         //   if(agregado){
         //       Toast.makeText(requireContext(), "Favorito agregado exitosamente", Toast.LENGTH_SHORT).show()
         //   }else{
        //        Toast.makeText(requireContext(), "Error al agregar el favorito", Toast.LENGTH_SHORT).show()
         //   }
        //}

        return view
    }


    private fun checkUser(){
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
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

/*
class BanderasFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PaisGeneralAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_banderas, container, false)

        val rvPaises: RecyclerView = view.findViewById(R.id.rvBanderas)
        rvPaises.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = PaisGeneralAdapter()
        rvPaises.adapter = adapter

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.paises.observe(viewLifecycleOwner) {
            adapter.update(it)
        }
        viewModel.init()

        return view
    }
}




 */