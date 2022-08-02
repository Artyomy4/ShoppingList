package ru.teempton.shopinglist.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ru.teempton.shopinglist.R

class SettingsFragment:PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)
    }
}