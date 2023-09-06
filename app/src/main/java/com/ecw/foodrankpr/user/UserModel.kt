package com.ecw.foodrankpr.user


// Realtime Database 유저 모델
data class UserModel(

    var email:String,   // 유저 이메일
    var name:String,    // 유저 이름
    var tel:String,     // 유저 전화번호
    var adr:String,     // 유저 주소
    var uId:String      // 유저 고유id값

){
    constructor():this("","","","","")
    fun toMap():Map<String,Any?>{
        return mapOf(
            "email" to email,
            "name" to name,
            "tel" to tel,
            "adr" to adr,
            "uid" to uId
        )
    }
}
