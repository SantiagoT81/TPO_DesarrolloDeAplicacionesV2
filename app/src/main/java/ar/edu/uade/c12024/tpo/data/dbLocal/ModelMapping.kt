package ar.edu.uade.c12024.tpo.data.dbLocal
import ar.edu.uade.c12024.tpo.domain.model.PaisDetalles

fun PaisDetallesLocal.toPaisDetalles() = PaisDetalles(
    flags, name, cca3, currencies, capital, region, subregion, languages, borders, population, timezones, coatOfArms, idd
)

fun List<PaisDetallesLocal>.toPaisDetallesList() = map(PaisDetallesLocal::toPaisDetalles)

fun PaisDetalles.toPaisDetallesLocal(PK: String) = PaisDetallesLocal(
    id = PK, flags, name, cca3, currencies, capital, region, subregion, languages, borders, population, timezones, coatOfArms, idd
)

