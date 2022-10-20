package com.company.akimov

class User(val phone:String?="",val pass:String?="") {
    companion object{
        var currentUser:User?=null
    }
}