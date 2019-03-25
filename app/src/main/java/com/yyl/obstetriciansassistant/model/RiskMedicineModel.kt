package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.Medicine

interface RiskMedicineModel {
    fun getRiskMedicine():List<Medicine>
    fun getHotRiskMedicine():List<Medicine>
}