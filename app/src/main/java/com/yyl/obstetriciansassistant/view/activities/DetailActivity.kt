package com.yyl.obstetriciansassistant.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.view.fragments.*
import kotlinx.android.synthetic.main.layout_toolbar_search.*

class DetailActivity : AppCompatActivity() {
    private val caseDetailFragment = CaseDetailFragment()
    private val essayDetailFragment = EssayDetailFragment()
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

        val temp = when (intent.getStringExtra(TYPE)) {
            CASE -> caseDetailFragment
            ESSAY -> essayDetailFragment
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
        others_search_bt.setOnClickListener {
            jump2Activity(this, SearchActivity::class.java)
        }

        others_toolbar_back_bt.setOnClickListener {
            finish()
        }
    }
}
