package com.yyl.obstetriciansassistant.beans

class SearchResult {
    lateinit var title:String
    lateinit var type:String
    lateinit var id:String


    constructor(title: String, type: String, id: String) {
        this.title = title
        this.type = type
        this.id = id
    }

    constructor()
}