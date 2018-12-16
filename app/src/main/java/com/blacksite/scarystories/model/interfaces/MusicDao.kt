package com.blacksite.scarystories.model.interfaces

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.blacksite.scarystories.model.database.Music

@Dao
interface MusicDao {
    @Query("SELECT * FROM music ORDER BY uid ASC")
    fun getAll(): List<Music>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(music: Music)
}