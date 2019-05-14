package com.yyl.obstetriciansassistant.beans

class SearchHistory {
    var id: Int=-1
    lateinit var title:String
    lateinit var type:String

    constructor(id: Int, title: String, type: String) {
        this.id = id
        this.title = title
        this.type = type
    }
}