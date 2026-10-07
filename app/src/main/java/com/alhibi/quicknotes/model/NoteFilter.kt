package com.alhibi.quicknotes.model

/**
 * Filtering / ordering rules for the note list.
 * Free of Android APIs so it is covered by fast JVM unit tests.
 */
object NoteFilter {

    fun apply(notes: List<Note>, query: String): List<Note> {
        val needle = query.trim()
        val matching = if (needle.isEmpty()) {
            notes
        } else {
            notes.filter { note ->
                note.title.contains(needle, ignoreCase = true) ||
                    note.body.contains(needle, ignoreCase = true)
            }
        }
        return matching.sortedWith(
            compareByDescending<Note> { it.pinned }.thenByDescending { it.createdAt }
        )
    }
}
