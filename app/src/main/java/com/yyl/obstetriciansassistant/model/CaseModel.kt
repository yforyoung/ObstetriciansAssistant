package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.Case

interface CaseModel {
    suspend fun getCases(): List<Case>
    fun deleteCase(id: Int): Boolean
    fun editCase(id: Int): Boolean
}