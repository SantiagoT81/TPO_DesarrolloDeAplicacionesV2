package ar.edu.uade.c12024.tpo.data.dbLocal

import androidx.room.TypeConverter
import ar.edu.uade.c12024.tpo.domain.model.CoatOfArms
import ar.edu.uade.c12024.tpo.domain.model.Currency
import ar.edu.uade.c12024.tpo.domain.model.Flags
import ar.edu.uade.c12024.tpo.domain.model.Idd
import ar.edu.uade.c12024.tpo.domain.model.Name
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromFlags(flags: Flags): String {
        return gson.toJson(flags)
    }

    @TypeConverter
    fun toFlags(flagsString: String): Flags {
        return gson.fromJson(flagsString, Flags::class.java)
    }

    @TypeConverter
    fun fromName(name: Name): String {
        return gson.toJson(name)
    }

    @TypeConverter
    fun toName(nameString: String): Name {
        return gson.fromJson(nameString, Name::class.java)
    }

    @TypeConverter
    fun fromMap(map: Map<String, Currency>): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun toMap(mapString: String): Map<String, Currency> {
        val type = object : TypeToken<Map<String, Currency>>() {}.type
        return gson.fromJson(mapString, type)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(listString: String): ArrayList<String> {
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(listString, type)
    }

    @TypeConverter
    fun fromCoatOfArms(coatOfArms: CoatOfArms): String {
        return gson.toJson(coatOfArms)
    }

    @TypeConverter
    fun toCoatOfArms(coatOfArmsString: String): CoatOfArms {
        return gson.fromJson(coatOfArmsString, CoatOfArms::class.java)
    }

    @TypeConverter
    fun fromIdd(idd: Idd): String {
        return gson.toJson(idd)
    }

    @TypeConverter
    fun toIdd(iddString: String): Idd {
        return gson.fromJson(iddString, Idd::class.java)
    }

    @TypeConverter
    fun fromStringToMap(value: String): Map<String, String> {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromMapToString(map: Map<String, String>): String {
        return Gson().toJson(map)
    }
}