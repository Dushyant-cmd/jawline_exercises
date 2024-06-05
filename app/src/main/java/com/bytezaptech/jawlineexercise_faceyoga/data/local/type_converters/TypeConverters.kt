package com.bytezaptech.jawlineexercise_faceyoga.data.local.type_converters

import androidx.room.TypeConverter
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.util.Arrays

class TypeConverters {
    @TypeConverter
    fun fromListToString(list: List<EachDayExerciseModel>): String {
        return Gson().toJson(list).toString()
    }
    @TypeConverter
    fun fromStringToList(str: String): List<EachDayExerciseModel> {
        val arr = JSONArray(str)
        return Gson().fromJson(arr.toString(), Array<EachDayExerciseModel>::class.java).toList()
    }
}