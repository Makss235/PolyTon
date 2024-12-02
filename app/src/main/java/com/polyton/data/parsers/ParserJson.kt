package com.polyton.data.parsers

import android.content.Context
import com.google.gson.Gson
import java.io.IOException

class ParserJson {

    fun <T> parseJson(context: Context, fileName: String, className: Class<T>): T? {
        try {
            context.assets.open(fileName).bufferedReader().use {
                val gson = Gson()
                val dataItems = gson.fromJson(it, className)
                return dataItems
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }
}