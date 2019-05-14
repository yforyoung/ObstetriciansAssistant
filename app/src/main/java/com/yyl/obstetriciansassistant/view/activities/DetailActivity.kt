package com.yyl.obstetriciansassistant.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.MenuItem
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.view.fragments.*
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private val caseDetailFragment = CaseDetailFragment()
    private val medicineDetailFragment = MedicineDetailFragment()
    private val qaDetailFragment = QADetailFragment()
    private lateinit var fragmentManager: FragmentManager
    private lateinit var transaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()

        fragmentManager = supportFragmentManager

        initFragment()
    }

    private fun initFragment() {

        transaction = fragmentManager.beginTransaction()
        supportActionBar!!.title = when (intent.getStringExtra(TYPE)) {
            CASE -> "病例详情"
            ESSAY -> "文章"
            MEDICINE -> "药物"
            QA -> "问题详情"
            else -> ""
        }

        val temp = when (intent.getStringExtra(TYPE)) {
            CASE -> caseDetailFragment
            MEDICINE -> medicineDetailFragment
            QA -> qaDetailFragment
            //TV_VIDEO -> tvDetailFragment
            else -> Fragment()
        }
        if (intent.getBooleanExtra(IS_CHANGE, false)) {
            val bundle = Bundle()
            bundle.putBoolean(IS_CHANGE, CHANGE)
            temp.arguments = bundle
        }
        transaction.replace(R.id.detail_fragment_layout, temp)
        transaction.commit()

    }

    private fun initToolbar() {
        detail_toolbar.title = ""
        setSupportActionBar(detail_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}
