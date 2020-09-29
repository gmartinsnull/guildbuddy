package com.gomart.guildbuddy.util

import com.gomart.guildbuddy.CoroutinesContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 *   Created by gmartins on 2020-09-28
 *   Description:
 */
class TestCoroutinesContextProvider : CoroutinesContextProvider() {
    override val Main: CoroutineContext
        get() = Dispatchers.Unconfined
    override val IO: CoroutineContext
        get() = Dispatchers.Unconfined
}