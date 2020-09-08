package com.gomart.guildbuddy.data

import android.content.SharedPreferences
import com.gomart.guildbuddy.testing.OpenForTesting
import javax.inject.Inject

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@OpenForTesting
class SharedPrefs @Inject constructor(
        private var sharedPreferences: SharedPreferences
) {
    /**
     * gets shared preference data via key
     */
    fun getSharedPrefsByKey(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    /**
     * sets shared preference data via key
     */
    fun setSharedPrefsByKey(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}