package org.queryquill.app.core.datastore

import androidx.datastore.preferences.core.mutablePreferencesOf
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CurrentTransactionIdDataSourceTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: CurrentTransactionIdDataSource

    @Before
    fun setup() {
        subject = CurrentTransactionIdDataSource(InMemoryDataStore(mutablePreferencesOf()))
    }

    @Test
    fun `getId initially returns null`() = testScope.runTest {
        val result = subject.getId().first()
        assertNull(result)
    }

    @Test
    fun `saveId stores and retrieves new value`() = testScope.runTest {
        val testId = 123L
        subject.saveId(testId)
        val result = subject.getId().first()
        assertEquals(testId, result)
    }

    @Test
    fun `saveId with null clears value`() = testScope.runTest {
        subject.saveId(123L)
        subject.saveId(null)
        val result = subject.getId().first()
        assertNull(result)
    }

    @Test
    fun `multiple saveId calls retain last value`() = testScope.runTest {
        subject.saveId(123L)
        subject.saveId(456L)
        val result = subject.getId().first()
        assertEquals(456L, result)
    }
}