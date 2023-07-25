package com.gomart.guildbuddy.ui.roster

import com.gomart.guildbuddy.vo.Character

/**
 *   Created by gmartins on 2023-07-25
 *   Description:
 */
sealed class RosterState {
    object Loading: RosterState()
    data class Loaded(val data: List<Character>): RosterState()
    data class Error(val errorMessage: String): RosterState()
}
