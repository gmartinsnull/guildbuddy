package com.gomart.guildbuddy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gomart.guildbuddy.vo.Character

/**
 *   Created by gmartins on 2020-08-29
 *   Description:
 */
@Database(entities = [Character::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}