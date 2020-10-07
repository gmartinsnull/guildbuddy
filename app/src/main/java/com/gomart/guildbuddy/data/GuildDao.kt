package com.gomart.guildbuddy.data

import androidx.room.*
import com.gomart.guildbuddy.vo.Guild
import com.gomart.guildbuddy.vo.Character

/**
 *   Created by gmartins on 2020-08-31
 *   Description:
 */
@Dao
interface GuildDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuild(guild: Guild)

    @Query("SELECT * FROM Guild")
    suspend fun getGuild(): Guild?

    @Query("DELETE FROM Guild")
    suspend fun deleteGuild()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoster(guildCharacters: List<Character>)

    @Transaction
    @Query("SELECT * FROM Character")
    fun getRoster(): List<Character>

    @Query("DELETE FROM Character")
    suspend fun deleteAll()
}