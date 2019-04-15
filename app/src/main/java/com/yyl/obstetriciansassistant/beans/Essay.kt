package com.yyl.obstetriciansassistant.beans

import java.io.Serializable

class Essay :Serializable{
    lateinit var title: String
    lateinit var content: String
    lateinit var author: String

    constructor()
    constructor(title: String, author: String, content: String) {
        this.title = title
        this.content = content
        this.author = author
    }

}