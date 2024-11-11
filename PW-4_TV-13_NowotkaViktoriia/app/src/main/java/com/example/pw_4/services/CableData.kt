package com.example.pw_4.services

import kotlinx.serialization.*
import android.content.Context
import org.json.JSONArray
import java.io.FileNotFoundException

fun loadCableData(context: Context): JSONArray {
    try {
        val inputStream = context.assets.open("cable_data.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        return JSONArray(json)
    } catch (e: Exception) {
        e.printStackTrace()
        throw FileNotFoundException("The file cable_data.json was not found in the assets folder.")
    }
}