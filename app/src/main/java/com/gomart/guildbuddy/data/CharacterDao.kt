package com.gomart.guildbuddy.data

import androidx.room.*
import com.gomart.guildbuddy.testing.OpenForTesting
import com.gomart.guildbuddy.vo.Character

/**
 *   Created by gmartins on 2020-08-29
 *   Description:
 */
@Dao
@OpenForTesting
interface CharacterDao {
        @Query("SELECT * FROM character")
        suspend fun getAll(): List<Character>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(characters: List<Character>)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertCharacter(character: Character)

        @Update
        suspend fun update(characters: List<Character>)

        @Query("DELETE FROM character")
        suspend fun deleteAll()
}