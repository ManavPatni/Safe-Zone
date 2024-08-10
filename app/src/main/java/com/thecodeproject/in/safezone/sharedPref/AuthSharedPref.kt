package com.thecodeproject.`in`.safezone.sharedPref

import android.content.Context
import android.content.SharedPreferences

class AuthSharedPref (private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AuthSharedPref", Context.MODE_PRIVATE)

    /*Sign In Status of user*/
    fun setAuthStatus(isSignedIn: Boolean) {
        sharedPreferences.edit().putBoolean("isSignedIn", isSignedIn).apply()
    }

    fun isSignedIn(): Boolean {
        return sharedPreferences.getBoolean("isSignedIn", false)
    }


    /*User Details*/
    fun setUID(uid: String) {
        sharedPreferences.edit().putString("uid", uid).apply()
    }

    fun uid(): String? {
        return sharedPreferences.getString("uid", null)
    }

    fun setUserName(userName: String) {
        sharedPreferences.edit().putString("userName", userName).apply()
    }

    fun userName(): String? {
        return sharedPreferences.getString("userName", null)
    }

    fun setCountry(country: String) {
        sharedPreferences.edit().putString("country", country).apply()
    }

    fun country(): String? {
        return sharedPreferences.getString("country", null)
    }

    fun setCountryCode(countryCode: String) {
        sharedPreferences.edit().putString("countryCode", countryCode).apply()
    }

    fun countryCode(): String? {
        return sharedPreferences.getString("countryCode", null)
    }

}