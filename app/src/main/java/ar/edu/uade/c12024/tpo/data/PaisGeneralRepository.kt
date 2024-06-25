package ar.edu.uade.c12024.tpo.data

import ar.edu.uade.c12024.tpo.domain.model.PaisDetalles
import ar.edu.uade.c12024.tpo.domain.model.PaisGeneral
import ar.edu.uade.c12024.tpo.domain.model.Usuario

class PaisGeneralRepository {
    suspend fun getPaises(): ArrayList<PaisGeneral> {
        return PaisGeneralDataSource.Companion.getPaises()
    }

    suspend fun getPais(name: String): PaisDetalles?{
        return PaisGeneralDataSource.Companion.getPais(name)
    }

    suspend fun getFavs(userId: String): List<Any>?{
        return PaisGeneralDataSource.Companion.getFavs(userId)
    }

    suspend fun getPaisGeneralFavorito(name: String): ArrayList<PaisGeneral> {
        return PaisGeneralDataSource.Companion.getPaisGeneralFavorito(name)
    }
}