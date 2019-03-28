package com.yyl.obstetriciansassistant.model

import com.yyl.obstetriciansassistant.beans.Medicine
import com.yyl.obstetriciansassistant.beans.Risk

class RiskMedicineModelImpl :RiskMedicineModel {
    override fun getInitial(): List<String> {
        return arrayListOf("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")
    }

    override fun getRiskMedicineListFromInitail(s: String): List<Medicine> {
        val r1=Medicine("medicine test1","medicine content")
        val r2=Medicine("medicine test2","medicine content")
        val r3=Medicine("medicine test","medicine content")
        val r4=Medicine("medicine test","medicine content")
        val r5=Medicine("medicine test","medicine content")
        val r6=Medicine("medicine test","medicine content")

        return arrayListOf(r1,r2,r3,r4,r5,r6)
    }
    /* val A=Risk("A")
     val B=Risk("B")
     val C=Risk("C")
     val D=Risk(0,"D")
     val E=Risk(0,"E")
     val F=Risk(0,"F")
     val G=Risk(0,"G")
     val H=Risk(0,"H")
     val I=Risk(0,"I")
     val J=Risk(0,"J")
     val K=Risk(0,"K")
     val L=Risk(0,"L")
     val M=Risk(0,"M")
     val N=Risk(0,"N")
     val O=Risk(0,"O")
     val P=Risk(0,"P")
     val Q=Risk(0,"Q")
     val R=Risk(0,"R")
     val S=Risk(0,"S")
     val T=Risk(0,"T")
     val U=Risk(0,"U")
     val V=Risk(0,"V")
     val W=Risk(0,"W")
     val X=Risk(0,"X")
     val Y=Risk(0,"Y")
     val Z=Risk(0,"Z")

     return arrayListOf(A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z)*/

    override fun getRiskMedicine(): List<Risk> {
        val medicine=Medicine("medicine test","medicine content")
        val r1=Risk("A",medicine)
        val r2=Risk("A",medicine)
        val r3=Risk("B",medicine)
        val r4=Risk("C",medicine)
        val r5=Risk("D",medicine)
        val r6=Risk("E",medicine)

        return arrayListOf(r1,r2,r3,r4,r5,r6)
    }

    override fun getHotRiskMedicine(): List<Medicine> {
        val medicine=Medicine("test medicinemedicinemedicinemedicine","test medicine content")
        val medicine2=Medicine("test medicine2","test medicine content2medicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicine")

        return arrayListOf(medicine,medicine2)
    }
}