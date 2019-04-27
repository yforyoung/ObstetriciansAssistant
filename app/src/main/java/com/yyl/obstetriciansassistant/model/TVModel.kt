package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.beans.Video

interface TVModel{
    suspend fun getTVList():ResponseData<List<Video>>
}