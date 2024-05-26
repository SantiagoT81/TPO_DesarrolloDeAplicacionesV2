package ar.edu.uade.c12024.tpo.data

import ar.edu.uade.c12024.tpo.domain.model.PaisGeneral

class PaisGeneralRepository {
    suspend fun getPaises(): ArrayList<PaisGeneral> {
        return PaisGeneralDataSource.Companion.getPaises()
    }
}