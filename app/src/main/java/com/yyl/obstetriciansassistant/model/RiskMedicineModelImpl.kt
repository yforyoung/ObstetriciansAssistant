package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.Medicine

class RiskMedicineModelImpl :RiskMedicineModel {
    override fun getRiskMedicine(): List<Medicine> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getHotRiskMedicine(): List<Medicine> {
        val medicine=Medicine("test medicinemedicinemedicinemedicine","test medicine content")
        val medicine2=Medicine("test medicine2","test medicine content2medicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicine")

        return arrayListOf(medicine,medicine2)
    }
}