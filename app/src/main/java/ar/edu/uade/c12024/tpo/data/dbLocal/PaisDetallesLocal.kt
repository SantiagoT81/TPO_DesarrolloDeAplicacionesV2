package ar.edu.uade.c12024.tpo.data.dbLocal

import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.uade.c12024.tpo.domain.model.CoatOfArms
import ar.edu.uade.c12024.tpo.domain.model.Currency
import ar.edu.uade.c12024.tpo.domain.model.Flags
import ar.edu.uade.c12024.tpo.domain.model.Idd
import ar.edu.uade.c12024.tpo.domain.model.Name
@Entity(tableName = "paises")
class PaisDetallesLocal (
    @PrimaryKey val id: String,
    val flags: Flags,
    val name: Name,
    val cca3: String,
    val currencies: Map<String, Currency>,
    val capital: ArrayList<String>,
    val region: String,
    val subregion: String,
    val languages: Map<String, String>,
    val borders: ArrayList<String>,
    val population: Int,
    val timezones: ArrayList<String>,
    val coatOfArms: CoatOfArms,
    val idd: Idd
)