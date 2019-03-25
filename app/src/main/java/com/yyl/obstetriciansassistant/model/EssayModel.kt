package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.Essay

interface EssayModel {
    fun getEssay():List<Essay>
    fun getHotEssay():List<Essay>
}