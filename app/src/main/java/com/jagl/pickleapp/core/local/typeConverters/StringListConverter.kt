package com.jagl.pickleapp.core.local.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toStringListOfString(data: String?): List<String> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun fromLongList(list: List<Long>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toStringListOfLong(data: String?): List<Long> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Long>>() {}.type
        return Gson().fromJson(data, listType)
    }
}