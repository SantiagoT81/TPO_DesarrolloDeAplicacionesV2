package ar.edu.uade.c12024.tpo.domain.model

data class PaisDetalles(
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

    ){
    constructor(): this(
        Flags("", "", ""),
        Name("", "", emptyMap()),
        "",
        emptyMap(),
        arrayListOf(),
        "",
        "",
        emptyMap(),
        arrayListOf(),
        0,
        arrayListOf(),
        CoatOfArms("", ""),
        Idd("", arrayListOf()))
}