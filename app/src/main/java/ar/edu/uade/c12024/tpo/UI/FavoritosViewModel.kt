package ar.edu.uade.c12024.tpo.UI

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.edu.uade.c12024.tpo.data.PaisGeneralRepository
import ar.edu.uade.c12024.tpo.domain.model.PaisGeneral
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class FavoritosViewModel: ViewModel() {
    var paisRepo: PaisGeneralRepository = PaisGeneralRepository()
    var paises: MutableLiveData<List<Any>> = MutableLiveData<List<Any>>()
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser

    private val coroutineContext: CoroutineContext = newSingleThreadContext("countries")
    private val scope = CoroutineScope(coroutineContext)

    fun init (){
        if(paises.value.isNullOrEmpty()){
            scope.launch {
                Log.d("API", currentUser!!.uid)
                val userId = currentUser!!.uid
                val ids = paisRepo.getFavs(userId) ?: emptyList()

                val listaFavoritos: MutableList<PaisGeneral> = mutableListOf()

                for (id in ids){
                    val paisArray = paisRepo.getPaisGeneralFavorito(id.toString())
                    val pais = paisArray[0]
                    listaFavoritos.add(pais)
                }
                paises.postValue(listaFavoritos)

            }

        }

    }
}