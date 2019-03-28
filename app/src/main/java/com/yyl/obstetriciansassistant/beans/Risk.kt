package com.yyl.obstetriciansassistant.beans

class Risk {
    lateinit var initial:String
    lateinit var medicine: Medicine


    constructor(initial: String, medicine: Medicine) {
        this.initial = initial
        this.medicine = medicine
    }

    constructor()
}