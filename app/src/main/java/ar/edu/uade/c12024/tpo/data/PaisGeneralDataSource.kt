package ar.edu.uade.c12024.tpo.data

import android.util.Log
import ar.edu.uade.c12024.tpo.domain.model.CoatOfArms
import ar.edu.uade.c12024.tpo.domain.model.Flags
import ar.edu.uade.c12024.tpo.domain.model.Idd
import ar.edu.uade.c12024.tpo.domain.model.Name
import ar.edu.uade.c12024.tpo.domain.model.PaisDetalles
import ar.edu.uade.c12024.tpo.domain.model.PaisFavorito
import ar.edu.uade.c12024.tpo.domain.model.PaisGeneral
import ar.edu.uade.c12024.tpo.domain.model.Usuario
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.suspendCoroutine

class PaisGeneralDataSource {
    companion object {
        private val API_URL = "https://restcountries.com/v3.1/"
        private val api: PaisAPI
        private val db = FirebaseFirestore.getInstance()
        private val COLECCION_USUARIOS = "Usuarios"
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

        suspend fun getFavs(userId: String): List<Any>? {
            Log.d("API", "FIREBASE: FAVORITOS LLAMADO")

            try {
                val favoritos = db.collection(COLECCION_USUARIOS).document(userId).get().await()

                if (favoritos.exists()) {
                    val usuario = favoritos.toObject(Usuario::class.java)

                    return usuario?.favoritos ?: emptyList()
                } else {
                    return emptyList()
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener favoritos: $e")
                return null
            }
        }

        suspend fun getPaisGeneralFavorito(name: String): ArrayList<PaisGeneral>{
            Log.d("API", "getPaisGeneralFavorito() llamado" )
            val api = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PaisAPI::class.java)

            var result = api.getPaisGeneral(name).execute()

            return if(result.isSuccessful){
                Log.d("API", "getPaises(): EXITO")
                result.body() ?: ArrayList<PaisGeneral>()
            }else{
                Log.e("API", "getPaises(): ERROR")
                ArrayList<PaisGeneral>()
            }
        }

        suspend fun existePaisFavorito(idPais: String, userId: String): Boolean{
            val usuario = db.collection(COLECCION_USUARIOS).document(userId).get().await()

            if(usuario.exists()){
                val favorito = usuario.get("favoritos") as? List<String>
                return favorito?.contains(idPais) == true
            }
            return false;

        }

        //Remover favorito

        //Agregar favorito
        suspend fun addFavorite(idPais: String, userId: String): Boolean {
            Log.d("API", "FIREBASE: AGREGAR A FAVORITOS LLAMADO")
            val usuario = db.collection(COLECCION_USUARIOS).document(userId)

            try{
                usuario.update("favoritos", FieldValue.arrayUnion(idPais)).await()
                return true

            }catch (e: Exception){
                Log.e("API", "FIREBASE: ERROR AL AGREGAR A FAVORITOS: $e")
                return false
            }

        }

        suspend fun removeFavorite(idPais: String, userId: String): Boolean {
            Log.d("API", "FIREBASE: REMOVER DE FAVORITOS LLAMADO")
            val usuario = db.collection(COLECCION_USUARIOS).document(userId)

            try{
                usuario.update("favoritos", FieldValue.arrayRemove(idPais)).await()
                return true
            }catch (e: Exception){
                Log.e("API", "FIREBASE: ERROR AL REMOVER DE FAVORITOS: $e")
                return false
            }
        }

    }
}