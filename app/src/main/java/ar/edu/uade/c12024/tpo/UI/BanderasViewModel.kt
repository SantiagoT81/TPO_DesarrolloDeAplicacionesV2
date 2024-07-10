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


    val listaFiltrada : MutableLiveData<ArrayList<PaisGeneral>> = MutableLiveData<ArrayList<PaisGeneral>>()
    private val coroutineContext: CoroutineContext = newSingleThreadContext("countries")
    private val scope = CoroutineScope(coroutineContext)

    fun init (){
        //Único llamado al inicio (paises está vacío)
        if(paises.value.isNullOrEmpty()){
            scope.launch {
                kotlin.runCatching {
                    paisRepo.getPaises()
                }.onSuccess {
                    //Si fue exitoso, se carga la lista en 2 lugares: la de países y la de los mismos pero filtrada
                    Log.d("API", "CARGA DE PAÍSES GENERALES: EXITO")
                    paises.postValue(it)
                    //La lista filtrada es, en un inicio, igual a la lista de países
                    listaFiltrada.postValue(it)
                    Log.d("API", "PAISES: $it")
                }.onFailure {
                    //Caso contrario ambas variables reciben una lista vacia
                    Log.e("API", "CARGA DE PAÍSES GENERALES: ERROR: $it")
                    paises.postValue(ArrayList<PaisGeneral>())
                    listaFiltrada.postValue(ArrayList<PaisGeneral>())
                }
            }
        }
    }

    //Filtrado de la lista filtrada observada a partir del nombre de un país.
    //La búsqueda trae aproximaciones (es decir, si se busca "ua" muestra "Paraguay", "Guatemala", etc)
    fun filtrarLista(query: String?){
        val original = paises.value ?: return
        //Si la lista filtrada está vacía, no se realizan cambios.
        listaFiltrada.value = if (query.isNullOrEmpty()){
            //Consulta vacía, lista filtrada toma el valor de la original
            original
        }else{
            //Caso contrario toma el valor de la lista original filtrada en base al nombre común pasado como argumento
            original.filter {
                it.name.common.contains(query, true)
            } as ArrayList<PaisGeneral>
        }
    }
}