package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.Case


interface CaseModel {
    suspend fun deleteCase(id: String): Boolean
    suspend fun editCase(id: String, case:Case): Boolean
}