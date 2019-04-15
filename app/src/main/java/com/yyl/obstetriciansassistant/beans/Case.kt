package com.yyl.obstetriciansassistant.beans

import java.io.Serializable

class Case : Serializable {
    var id:Int=-1
    lateinit var hospital:Hospital
    lateinit var createDate: String
    lateinit var updateDate: String
    lateinit var creater:User
    lateinit var content:String
    lateinit var name:String
    lateinit var reason:String
    lateinit var treatment:String



    constructor(
        id: Int,
        createDate: String,
        updateDate: String,
        name: String,
        reason: String,
        treatment: String
    ) {
        this.id = id
        this.createDate = createDate
        this.updateDate = updateDate
        this.name = name
        this.reason = reason
        this.treatment = treatment
    }

    constructor()
}