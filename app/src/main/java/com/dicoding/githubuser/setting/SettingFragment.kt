package com.dicoding.githubuser.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.dicoding.githubuser.R
import com.dicoding.githubuser.reminder.ReminderReceiver

class SettingFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var reminder: String
    private lateinit var reminderReceiver: ReminderReceiver
    private lateinit var LANGUAGE: String
    private lateinit var languagePreference: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
    }

    private fun init() {
        reminder = resources.getString(R.string.key_reminder)
        reminderReceiver = ReminderReceiver()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        LANGUAGE = resources.getString(R.string.key_language)
        languagePreference = findPreference<Preference>(LANGUAGE) as Preference
        languagePreference.setOnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == reminder) {
            val sh = preferenceManager.sharedPreferences
            val status = sh.getBoolean(reminder, false)
            if (status) reminderReceiver.setReminder((activity as SettingActivity).applicationContext)
            else reminderReceiver.cancelReminder((activity as SettingActivity).applicationContext)
        }
    }
}