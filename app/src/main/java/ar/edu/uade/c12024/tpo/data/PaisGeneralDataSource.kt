package ar.edu.uade.c12024.tpo.data

import android.util.Log
import ar.edu.uade.c12024.tpo.domain.model.CoatOfArms
import ar.edu.uade.c12024.tpo.domain.model.Flags
import ar.edu.uade.c12024.tpo.domain.model.Idd
import ar.edu.uade.c12024.tpo.domain.model.Name
import ar.edu.uade.c12024.tpo.domain.model.PaisDetalles
import ar.edu.uade.c12024.tpo.domain.model.PaisGeneral
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PaisGeneralDataSource {
    companion object {
        private val API_URL = "https://restcountries.com/v3.1/"
        private val api: PaisAPI
        //Inicializar API
        init {
            //Requerido para esta API: Ultimamente se toma un poco más de tiempo para responder, resultando en: "java.net.SocketTimeoutException: Read timed out"
            val client = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            api = Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PaisAPI::class.java)
        }

        suspend fun getPaises(): ArrayList<PaisGeneral>{
            Log.d("API", "getPaises() llamado" )
            val api = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PaisAPI::class.java)

            var result = api.getPaises().execute()

            return if(result.isSuccessful){
                Log.d("API", "getPaises(): EXITO")
                result.body() ?: ArrayList<PaisGeneral>()
            }else{
                Log.e("API", "getPaises(): ERROR")
                ArrayList<PaisGeneral>()
            }
        }

        suspend fun getPais(name: String): PaisDetalles? {
            Log.d("API", "getPais() llamado")
            Log.d("API", name)

            try {
                val result = api.getPais(name).execute()

                if (result.isSuccessful) {
                    Log.d("API", "getPais(): EXITO")
                    val paisesDetalles = result.body() ?: return null
                    return paisesDetalles.firstOrNull()
                } else {
                    Log.e("API", "getPais(): ERROR - ${result.code()}")
                    return null
                }
            } catch (e: Exception) {
                Log.e("API", "getPais(): Exception - ${e.message}")
                return null
            }
        }


    }
}