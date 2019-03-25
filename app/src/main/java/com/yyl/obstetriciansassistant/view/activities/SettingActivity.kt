package com.yyl.obstetriciansassistant.view.activities

import android.os.Bundle
import android.preference.PreferenceActivity
import com.yyl.obstetriciansassistant.R

class SettingActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting_preference)
    }
}
