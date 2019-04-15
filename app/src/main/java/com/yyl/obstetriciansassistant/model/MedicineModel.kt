package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.Medicine
import com.yyl.obstetriciansassistant.beans.Risk

interface MedicineModel {
    fun getRiskMedicine():List<Risk>
    fun getRiskMedicineListFromInitail(s:String):List<Medicine>
    fun getHotRiskMedicine():List<Medicine>
    fun getInitial():List<String>
}