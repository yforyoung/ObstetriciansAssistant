package com.yyl.obstetriciansassistant.beans

class SearchResult {
    lateinit var title:String
    var type:Int=-1


    constructor(title: String, type: Int) {
        this.title = title
        this.type = type
    }

    constructor()
}