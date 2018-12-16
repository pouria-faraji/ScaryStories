package com.blacksite.scarystories.model.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Music (@PrimaryKey val uid: Int,val resourceID: String){
}