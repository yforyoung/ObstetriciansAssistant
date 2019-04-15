package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.Case
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class CaseModelImpl : CaseModel {
    override fun getCases(): ArrayList<Case> {
        val date=Date(119, 4, 1)
        val s=DateFormat.getDateInstance().format(date)
        val c1 = Case(1, s, s, "超长的中文名有十个字", "resonresonresonresonresonresonresonresonresonresonreson", "treatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatment")
        val c2 = Case(1, s, s, "test naem", "reson", "treatment")
        val c3 = Case(1, s, s, "test naem", "resonresonresonresonresonresonresonresonresonresonreson", "treatment")
        val c4 = Case(1, s, s, "test naem", "reson", "treatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatment")
        val c5 = Case(1, s, s, "test naem", "resonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonreson", "treatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatmenttreatment")
        val c6 = Case(1, s, s, "test naem", "resonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonresonreson", "treatment")

        return arrayListOf(c1, c2, c3, c4, c5, c6)
    }

    override fun deleteCase(id: Int): Boolean {
        return id%2==0
    }

    override fun editCase(id: Int): Boolean {
        return true
    }
}