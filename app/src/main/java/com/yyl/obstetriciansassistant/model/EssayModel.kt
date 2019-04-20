package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.Essay

interface EssayModel {
    fun getEssay():List<Essay>
    suspend fun getHotEssay(json:String):List<Essay>
    fun setHotEssay(json:String)

}