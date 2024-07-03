package ar.edu.uade.c12024.tpo.UI

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.edu.uade.c12024.tpo.data.PaisGeneralRepository
import ar.edu.uade.c12024.tpo.domain.model.PaisDetalles
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class DetallesPaisViewModel: ViewModel() {
    val paisRepo: PaisGeneralRepository = PaisGeneralRepository()
    var pais: MutableLiveData<PaisDetalles> = MutableLiveData<PaisDetalles>()
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser

    val agregado = MutableLiveData<Boolean>()
    val borrado = MutableLiveData<Boolean>()

    private val coroutineContext: CoroutineContext = newSingleThreadContext("countries")
    private val scope = CoroutineScope(coroutineContext)
    private val viewModel: FavoritosViewModel = FavoritosViewModel()

    fun init(name: String, context: Context){
        scope.launch {
            kotlin.runCatching {
                //Búsqueda de un único país por API
                paisRepo.getPais(name, context)
            }.onSuccess {
                pais.postValue(it ?: PaisDetalles())
            }.onFailure {
                pais.postValue(PaisDetalles())
            }
        }
    }

    private val coroutineContext2: CoroutineContext = newSingleThreadContext("agregarFavorito")
    private val scope2 = CoroutineScope(coroutineContext2)
    fun agregarRemoverFavorito(idPais: String){
        scope2.launch {
            val existePaisFavorito = paisRepo.existePaisFavorito(idPais, currentUser!!.uid)
            kotlin.runCatching {
                if(existePaisFavorito){
                    paisRepo.removeFavorite(idPais, currentUser!!.uid)
                }else{
                    paisRepo.addFavorite(idPais, currentUser!!.uid)
                }
            }.onSuccess {
                //SI SE EFECTUÓ EL CAMBIO EN FIRESTORE
                //Si fue de borrado
                if(existePaisFavorito){
                    Log.d("API", "REMOVER FAVORITO: EXITO")
                    borrado.postValue(true)
                    agregado.postValue(false)

                    //Si fue de agregado
                }else{
                    Log.d("API", "AGREGAR FAVORITO: EXITO")
                    agregado.postValue(true)
                    borrado.postValue(false)
                }
            }.onFailure {
                if(existePaisFavorito){
                    Log.e("API", "REMOVER FAVORITO: ERROR: $it")
                    borrado.postValue(false)
                }else{
                    Log.e("API", "AGREGAR FAVORITO: ERROR: $it")
                    agregado.postValue(false)
                }
            }
        }
    }
}