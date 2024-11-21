package com.example.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val creationDate: String? = null,
    val dueDate: String? = null,
    val photo: String? = null,
    val location: String? = null
)
