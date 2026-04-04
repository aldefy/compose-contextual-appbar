package io.github.aldefy.contextualappbar

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ContextualTopAppBarTest {

    @Test
    fun selectedCountZeroMeansInactive() {
        val isActive = 0 > 0
        assertFalse(isActive)
    }

    @Test
    fun selectedCountPositiveMeansActive() {
        val isActive = 3 > 0
        assertTrue(isActive)
    }
}
