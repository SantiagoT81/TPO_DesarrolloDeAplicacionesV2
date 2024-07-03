package ar.edu.uade.c12024.tpo.data.dbLocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PaisDetallesDAO {

    @Query("SELECT * FROM paises")
    fun getAll(): List<PaisDetallesLocal>
    @Query("SELECT * FROM paises WHERE id = :clave LIMIT 1")
    fun getByPK(clave: String): PaisDetallesLocal
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg paisDetallesLocal: PaisDetallesLocal)
    @Delete
    fun delete (paisDetallesLocal: PaisDetallesLocal)
}