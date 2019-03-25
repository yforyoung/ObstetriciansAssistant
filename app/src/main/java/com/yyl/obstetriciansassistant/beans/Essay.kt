package com.yyl.obstetriciansassistant.beans

class Essay {
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