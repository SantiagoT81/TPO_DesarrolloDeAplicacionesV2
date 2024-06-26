package ar.edu.uade.c12024.tpo.UI

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.edu.uade.c12024.tpo.data.PaisGeneralRepository
import ar.edu.uade.c12024.tpo.domain.model.PaisGeneral
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class BanderasViewModel: ViewModel() {
    var paisRepo: PaisGeneralRepository = PaisGeneralRepository()
    var paises: MutableLiveData<ArrayList<PaisGeneral>> = MutableLiveData<ArrayList<PaisGeneral>>()

    private val coroutineContext: CoroutineContext = newSingleThreadContext("countries")
    private val scope = CoroutineScope(coroutineContext)

    fun init (){
        if(paises.value.isNullOrEmpty()){
            scope.launch {
                kotlin.runCatching {
                    paisRepo.getPaises()
                }.onSuccess {
                    Log.d("API", "VISTA GENERAL DE PAISES: EXITO")
                    paises.postValue(it)
                    Log.d("API", "PAISES: $it")
                }.onFailure {
                    Log.e("API", "VISTA GENERAL DE PAISES: ERROR: $it")
                    paises.postValue(ArrayList<PaisGeneral>())
                }
            }
        }
    }
}