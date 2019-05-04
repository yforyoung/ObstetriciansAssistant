package com.yyl.obstetriciansassistant.view.activities

import android.content.Intent
import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import com.yyl.obstetriciansassistant.R
import com.yyl.obstetriciansassistant.model.UserModelImpl

class SettingActivity : PreferenceActivity(), Preference.OnPreferenceClickListener {

    private lateinit var fontSet: ListPreference
    private lateinit var changePwd: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting_preference)
        initView()
        initListener()
    }

    private fun initView() {
        fontSet = findPreference("prf_font_size") as ListPreference
        changePwd = findPreference("change_pwd")
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


    override fun onPreferenceClick(preference: Preference?): Boolean {

        return true
    }
}
