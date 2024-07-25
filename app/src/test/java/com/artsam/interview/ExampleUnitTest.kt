package com.artsam.interview

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun coroutine_test() {
        val scope = CoroutineScope(Dispatchers.IO)
        val job = flowOf(1, 2, 3)
            .onEach { println("test item $it") }
            .onCompletion { println("test onCompletion") }
            .launchIn(scope)

        assertEquals(true, job.isActive)
    }

    private fun pt() = println("test pt")

    data class Tmp(private val tmp: Int)

    @Test
    fun tmp() {

        val tmp = Tmp(321)
        pt()
        fun otherTmp() = println("test other $tmp")

        otherTmp()
    }

}
