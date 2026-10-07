package com.alhibi.quicknotes.data

import android.content.Context
import com.alhibi.quicknotes.model.Note
import org.json.JSONArray
import org.json.JSONObject

/**
 * Dead simple persistence: the note list is stored as a JSON array inside
 * SharedPreferences. No database, no network, no extra dependency.
 */
class NotesRepository(context: Context) {

    private val prefs =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun load(): List<Note> {
        val raw = prefs.getString(KEY_NOTES, null) ?: return emptyList()
        return runCatching { parse(raw) }.getOrDefault(emptyList())
    }

    fun save(notes: List<Note>) {
        prefs.edit().putString(KEY_NOTES, serialize(notes).toString()).apply()
    }

    private fun serialize(notes: List<Note>): JSONArray {
        val array = JSONArray()
        notes.forEach { note ->
            array.put(
                JSONObject().apply {
                    put("id", note.id)
                    put("title", note.title)
                    put("body", note.body)
                    put("createdAt", note.createdAt)
                    put("pinned", note.pinned)
                }
            )
        }
        return array
    }

    private fun parse(raw: String): List<Note> {
        val array = JSONArray(raw)
        return (0 until array.length()).map { index ->
            val json = array.getJSONObject(index)
            Note(
                id = json.optLong("id", index.toLong()),
                title = json.optString("title"),
                body = json.optString("body"),
                createdAt = json.optLong("createdAt", 0L),
                pinned = json.optBoolean("pinned", false),
            )
        }
    }

    private companion object {
        const val PREFS_NAME = "quicknotes_store"
        const val KEY_NOTES = "notes"
    }
}
