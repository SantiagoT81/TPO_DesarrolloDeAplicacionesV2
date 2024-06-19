package ar.edu.uade.c12024.tpo.data

import ar.edu.uade.c12024.tpo.domain.model.PaisDetalles
import ar.edu.uade.c12024.tpo.domain.model.PaisGeneral
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PaisAPI {
    //Obtener todos los países con detalles mínimos.
    @GET("all?fields=name,flags")
    fun getPaises(): Call<ArrayList<PaisGeneral>>

    //TODO: Obtener los detalles de un poís a partir de su nombre (GET).

    @GET("name/{name}")
    fun getPais(@Path("name") code: String): Call<ArrayList<PaisDetalles>>
}