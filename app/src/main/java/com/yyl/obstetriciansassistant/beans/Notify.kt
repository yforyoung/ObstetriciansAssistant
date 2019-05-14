package com.yyl.obstetriciansassistant.beans

class Notify {
    var notifyId:Int=-1
    lateinit var name:String
    lateinit var content:String
    lateinit var id:String
    var type:String="回答"
    var read:Boolean=false



    constructor(notifyId:Int,name: String, content: String, id: String, read: Boolean,type:String) {
        this.notifyId=notifyId
        this.name = name
        this.content = content
        this.id = id
        this.read = read
        this.type=type
    }

    constructor()

    override fun toString(): String {
        return "$notifyId  $name  $content  $id   read:$read"
    }
}