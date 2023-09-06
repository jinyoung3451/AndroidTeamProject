package com.ecw.foodrankpr.user

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// RealTime Database 유저 DB 값 수정 및 저장
class UserDao {
    private var databaseReference: DatabaseReference? = null

    init {
        val userDb = FirebaseDatabase.getInstance()
        databaseReference = userDb.getReference("user")

    }

    //수정함수
    fun userUpdate(key:String,hashMap:HashMap<String,Any>): Task<Void>{
        return databaseReference!!.child(key).updateChildren(hashMap)
    }

    //삭제함수
    fun userDelete(key: String): Task<Void>{
        return databaseReference!!.child(key).removeValue()
    }

}