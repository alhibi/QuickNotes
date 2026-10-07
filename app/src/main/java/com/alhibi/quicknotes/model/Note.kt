package com.alhibi.quicknotes.model

/**
 * A single note. Pure Kotlin on purpose so it stays testable on the JVM
 * without the Android framework.
 */
data class Note(
    val id: Long,
    val title: String,
    val body: String,
    val createdAt: Long,
    val pinned: Boolean = false,
)
