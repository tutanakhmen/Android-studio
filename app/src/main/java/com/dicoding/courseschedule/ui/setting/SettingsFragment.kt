package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var dailyReminder: DailyReminder
    private var listPreference: ListPreference? = null
    private var switchPreference: SwitchPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        listPreference = findPreference(resources.getString(R.string.pref_key_dark))
        listPreference?.setOnPreferenceChangeListener { _, newValue ->
            val selectedMode = NightMode.valueOf(newValue.toString().uppercase(Locale.US))
            updateTheme(selectedMode.value)
            true
        } ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        dailyReminder = DailyReminder()
        switchPreference = findPreference(resources.getString(R.string.pref_key_notify))
        switchPreference?.setOnPreferenceChangeListener { _, newValue ->
            val isEnable = newValue as Boolean
            if (isEnable) {
                dailyReminder.setDailyReminder(requireContext())
            } else {
                dailyReminder.cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}