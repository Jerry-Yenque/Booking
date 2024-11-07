package com.froxengine.booking.data.repository

import android.app.Service
import android.preference.PreferenceManager
import com.google.gson.Gson

abstract class BaseService : Service() {
    protected fun isCloud(): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        return preferences.getBoolean("switch_cloud", false)
    }

    protected fun getWorkspaceOid(): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        return preferences.getString("local_workspaceOid", "MISSING")!!
    }

    protected fun getOrderId(): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        return preferences.getString("order_id", null)
    }

    fun setOrderId(orderId: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.edit().putString("order_id", orderId).apply()
    }

    fun removeOrderId() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.edit().remove("order_id").apply()
    }

    /**
     * Stores the given object inside defaultSharedPreferences in a Json format
     * @param key the object will be store in this key, use it to retrieve also the object but
     * with another method
     */
    fun storeObject(key: String, anyObject: Any) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val json = Gson().toJson(anyObject)
        preferences.edit().putString(key, json).apply()
    }

    /**
     * Complementary method to storeObject.
     *
     * It returns in object form, the object in Json format stored in DefaultSharedPreferences
     * @param classOfT the class instance that will be return, it must match the object json format stored
     */
    fun <T>retrieveObject(key: String, classOfT: Class<T>): T? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val json = preferences.getString(key, null) ?: return null
        return Gson().fromJson(json, classOfT)
    }

    fun removeObject(key: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.edit().remove(key).apply()
    }

    fun getString(key: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        return preferences.getString(key, null)
    }

    companion object {
        const val CACHE_PAYABLE_KEY: String = "payable"
    }
}
