package com.alhibi.quicknotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.alhibi.quicknotes.data.NotesRepository
import com.alhibi.quicknotes.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NotesRepository(application)

    private val _notes = MutableStateFlow(repository.load())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    fun setQuery(value: String) {
        _query.value = value
    }

    fun save(note: Note) {
        val current = _notes.value
        val exists = current.any { it.id == note.id }
        update(if (exists) current.map { if (it.id == note.id) note else it } else current + note)
    }

    fun delete(id: Long) {
        update(_notes.value.filterNot { it.id == id })
    }

    fun togglePinned(id: Long) {
        update(_notes.value.map { if (it.id == id) it.copy(pinned = !it.pinned) else it })
    }

    private fun update(next: List<Note>) {
        _notes.value = next
        repository.save(next)
    }
}
