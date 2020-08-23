package github.tinkzhang.readkeeper.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import github.tinkzhang.readkeeper.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}