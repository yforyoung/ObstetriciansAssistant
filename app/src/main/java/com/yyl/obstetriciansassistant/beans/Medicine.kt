package com.yyl.obstetriciansassistant.beans

class Medicine {
    lateinit var id:String
    lateinit var medicienName:String
    lateinit var hospitalName:String
    lateinit var level:String
    lateinit var EnName:String
    lateinit var medicineIntroduce:String
    var searchTime:Int=0

    constructor(medicienName: String, medicineIntroduce: String) {
        this.medicienName = medicienName
        this.medicineIntroduce = medicineIntroduce
    }
}