package ar.edu.uade.c12024.tpo.UI

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.edu.uade.c12024.tpo.data.PaisGeneralRepository
import ar.edu.uade.c12024.tpo.domain.model.PaisDetalles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class DetallesPaisViewModel: ViewModel() {
    val paisRepo: PaisGeneralRepository = PaisGeneralRepository()
    var pais: MutableLiveData<PaisDetalles> = MutableLiveData<PaisDetalles>()

    private val coroutineContext: CoroutineContext = newSingleThreadContext("countries")
    private val scope = CoroutineScope(coroutineContext)

    fun init(name: String){
        scope.launch {
            kotlin.runCatching {
                paisRepo.getPais(name)
            }.onSuccess {
                pais.postValue(it ?: PaisDetalles())
            }.onFailure {
                pais.postValue(PaisDetalles())
            }
        }
    }
}