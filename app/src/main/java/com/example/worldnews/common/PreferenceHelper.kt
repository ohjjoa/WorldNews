package com.example.worldnews.common

import android.content.Context
import android.content.SharedPreferences
import com.example.worldnews.model.CountryCode

object PreferenceHelper {
    private const val NAME = "SharedPreferenceName"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val COUNTRY_CODE_KEY
            = Pair("COUNTRY_CODE_KEY", CountryCode.SOUTH_KOREA.code)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var countryCode: String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(COUNTRY_CODE_KEY.first, COUNTRY_CODE_KEY.second)!!

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(COUNTRY_CODE_KEY.first, value)
        }
}