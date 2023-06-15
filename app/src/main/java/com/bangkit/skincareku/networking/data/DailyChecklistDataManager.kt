package com.bangkit.skincareku.networking.data

import android.content.Context

class DailyChecklistDataManager(context: Context) {
    private val preferences = context.getSharedPreferences("daily_checklist", Context.MODE_PRIVATE)

    fun saveGetup(getup: String?) {
        preferences.edit().putString("getup", getup).apply()
    }
    fun saveCleanserMorning(cleanserMorning: Boolean) {
        preferences.edit().putBoolean("cleanserMorning", cleanserMorning).apply()
    }
    fun saveTonerMorning(tonerMorning: Boolean) {
        preferences.edit().putBoolean("tonerMorning", tonerMorning).apply()
    }
    fun saveMoisturizerMorning(moisturizerMorning: Boolean) {
        preferences.edit().putBoolean("moisturizerMorning", moisturizerMorning).apply()
    }

    fun saveCleanserNight(cleanserNight: Boolean) {
        preferences.edit().putBoolean("cleanserNight", cleanserNight).apply()
    }
    fun saveTonerNight(tonerNight: Boolean) {
        preferences.edit().putBoolean("tonerNight", tonerNight).apply()
    }
    fun saveSerumNight(serumNight: Boolean) {
        preferences.edit().putBoolean("serumNight", serumNight).apply()
    }
    fun saveSleep(sleep: String) {
        preferences.edit().putString("sleep", sleep).apply()
    }
    fun saveDate(date: String) {
        preferences.edit().putString("date", date).apply()
    }


    fun getGetup(): String? {
        return preferences.getString("getup", null)
    }
    fun getCleanserMorning(): Boolean {
        return preferences.getBoolean("cleanserMorning", false)
    }
    fun getTonerMorning(): Boolean {
        return preferences.getBoolean("tonerMorning", false)
    }
    fun getMoisturizerMorning(): Boolean {
        return preferences.getBoolean("moisturizerMorning", false)
    }
    fun getCleanserNight(): Boolean {
        return preferences.getBoolean("cleanserNight", false)
    }
    fun getTonerNight(): Boolean {
        return preferences.getBoolean("tonerNight", false)
    }
    fun getSerumNight(): Boolean {
        return preferences.getBoolean("serumNight", false)
    }
    fun getSleep(): String? {
        return preferences.getString("sleep", null)
    }
    fun getDate(): String? {
        return preferences.getString("date", null)
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

}