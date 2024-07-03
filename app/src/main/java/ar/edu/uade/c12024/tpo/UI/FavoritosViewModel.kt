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
    val USER = currentUser!!.uid

    private val coroutineContext: CoroutineContext = newSingleThreadContext("countries")
    private val scope = CoroutineScope(coroutineContext)

    fun init (){
            scope.launch {
                Log.d("API", currentUser!!.uid)
                val userId = currentUser!!.uid

                //Array de strings con las IDs de cada país favorito del usuario
                val ids = paisRepo.getFavs(userId) ?: emptyList()

                val listaFavoritos: MutableList<PaisGeneral> = mutableListOf()

                //Por cada ID de país favorito, se realiza una llamada individual a la API.
                //Dicha llamada trae un único país dentro de un array
                for (id in ids){
                    val paisArray = paisRepo.getPaisGeneralFavorito(id.toString())
                    //Devuelve un array con un único país.
                    val pais = paisArray[0]
                    listaFavoritos.add(pais)
                }
                paises.postValue(listaFavoritos)

            }
    }

    //Se elimina

}