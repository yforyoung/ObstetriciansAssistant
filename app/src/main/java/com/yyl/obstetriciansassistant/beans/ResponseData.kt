package com.yyl.obstetriciansassistant.beans

class ResponseData<T>{
    var data:T ?= null
    lateinit var retmsg:String
    var retcode:Int=-1



    constructor(data: T, retmsg: String, retcode: Int) {
        this.data = data
        this.retmsg = retmsg
        this.retcode = retcode
    }

    constructor()
}