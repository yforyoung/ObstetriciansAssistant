package com.yyl.obstetriciansassistant.view.activities

import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import android.text.Editable
import android.text.TextWatcher
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.model.UserModel
import com.yyl.obstetriciansassistant.utils.HttpUtils
import com.yyl.obstetriciansassistant.utils.UpdateAppUtil
import kotlinx.android.synthetic.main.dialog_change_pwd.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.content.Intent
import android.app.AlertDialog
import com.yyl.obstetriciansassistant.*


class SettingActivity : PreferenceActivity(), Preference.OnPreferenceClickListener{

    private lateinit var fontSet: ListPreference
    private lateinit var changePwd: Preference
    private lateinit var checkUpdate:Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting_preference)
        initView()
        initListener()
    }

    private fun initView() {
        fontSet = findPreference("prf_font_size") as ListPreference
        changePwd = findPreference("change_pwd")
        checkUpdate=findPreference("check_update")

        checkUpdate.summary = "当前版本 ${UpdateAppUtil.getInstance().appVersionName}"
    }

    private fun initListener() {
        changePwd.onPreferenceClickListener = this
        checkUpdate.onPreferenceClickListener = this


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


    private fun changePwd(){
        var pe = false
        var pce = false
        var ne = false
        val v = layoutInflater.inflate(R.layout.dialog_change_pwd, null, false)
        with(v) {
            pwd_new_confirm.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (pwd_new.text.toString() != s.toString()) {
                        pwd_new_confirm_layout.error = "两次密码输入不一致"
                        pce = false
                    } else {
                        pce = true
                        pwd_new_confirm_layout.error = ""
                    }
                }

            })
            pwd_new.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s == null || s.length < 6) {
                        pwd_new_layout.error = "密码小于6位"
                        pe = false
                    } else {
                        pe = true
                        pwd_new_layout.error = ""

                    }
                }

            })
            pwd_old.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s == null || s.length < 6) {
                        pwd_old_layout.error = "密码小于6位"
                        ne = false
                    } else {
                        ne = true
                        pwd_old_layout.error = ""
                    }
                }

            })
        }

        AlertDialog.Builder(this)
            .setTitle("修改密码")
            .setView(v)
            .setPositiveButton("确定") { _, _ ->
                if (pe && ne && pce) {
                    GlobalScope.launch(UI) {
                        val param = HashMap<String, String>()
                        param["name"] = SingleTon.instance.user!!.name
                        with(v) {
                            param["password"] = pwd_old.text.toString()
                            param["newpassword"] = pwd_new.text.toString()
                        }

                        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/updatepassword", param)
                        val re = SingleTon.instance.gson.fromJson(json, ResponseData::class.java)
                        if (re.retcode == 1) {
                            toast("修改成功，请重新登陆")
                            UserModel().logout()
                            val intent = Intent(
                                this@SettingActivity,
                                LoadActivity::class.java
                            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            toast("修改失败 ${re.retmsg}")
                        }
                    }
                }
            }
            .setNegativeButton("取消", null)
            .create().show()
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when (preference!!.key) {
            "check_update"->UpdateAppUtil.getInstance().updateApp(1,this)
            "change_pwd" -> {
                changePwd()
                log("change pwd")
            }
        }
        return true
    }
}
