package com.alhibi.quicknotes.model

import org.junit.Assert.assertEquals
import org.junit.Test

class NoteFilterTest {

    private val older = Note(id = 1, title = "تقرير", body = "ملاحظات الاجتماع", createdAt = 100)
    private val newer = Note(id = 2, title = "قائمة التسوق", body = "حليب، خبز", createdAt = 200)
    private val pinnedOld = Note(id = 3, title = "مثبتة", body = "مهمة", createdAt = 50, pinned = true)

    @Test
    fun emptyQueryReturnsPinnedFirstThenNewest() {
        val result = NoteFilter.apply(listOf(older, newer, pinnedOld), "")
        assertEquals(listOf(3L, 2L, 1L), result.map { it.id })
    }

    @Test
    fun queryMatchesTitleIgnoringCase() {
        val result = NoteFilter.apply(listOf(older, newer, pinnedOld), "قائمة")
        assertEquals(listOf(2L), result.map { it.id })
    }

    @Test
    fun queryMatchesBodyAndTrimsWhitespace() {
        val result = NoteFilter.apply(listOf(older, newer, pinnedOld), "  الاجتماع ")
        assertEquals(listOf(1L), result.map { it.id })
    }

    @Test
    fun unmatchedQueryYieldsEmptyList() {
        assertEquals(emptyList<Note>(), NoteFilter.apply(listOf(older, newer), "zzz"))
    }
}
