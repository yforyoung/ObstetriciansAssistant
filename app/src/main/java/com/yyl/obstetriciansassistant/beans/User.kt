package com.yyl.obstetriciansassistant.beans

class User {
    lateinit var id:String
    lateinit var name:String
    lateinit var hospital:String
    lateinit var position:String
    lateinit var password:String
    lateinit var department:String
    lateinit var administrators:String
    lateinit var createTime:String


    constructor(
        id: String,
        name: String,
        hospital: String,
        position: String,
        password: String,
        department: String,
        administrators: String,
        createTime: String
    ) {
        this.id = id
        this.name = name
        this.hospital = hospital
        this.position = position
        this.password = password
        this.department = department
        this.administrators = administrators
        this.createTime = createTime
    }

    constructor()
}