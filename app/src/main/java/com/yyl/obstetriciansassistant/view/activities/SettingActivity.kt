package com.yyl.obstetriciansassistant.view.activities

import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import com.yyl.obstetriciansassistant.R

class SettingActivity : PreferenceActivity() {
    private lateinit var fontSet: ListPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting_preference)
        initView()
        initListener()
    }

    private fun initView() {
        fontSet = findPreference("prf_font_size") as ListPreference
    }

    private fun initListener() {
        fontSet.onPreferenceChangeListener = object : Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                fontSet.summary = when (fontSet.findIndexOfValue(newValue as String?)) {
                    0 -> "大"
                    1 -> "中"
                    2 -> "小"
                    else -> ""
                }

                return true
            }

        }
    }
}
