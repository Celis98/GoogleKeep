package com.example.domain.model

import com.example.data.db.models.TaskEntity
import java.util.Date

data class TaskUi(
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val creationDate: Date? = null,
    val dueDate: Date? = null,
    val photo: String? = null,
    val location: String? = null
)

internal fun TaskUi.toTaskEntity() : TaskEntity =
    TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted
    )

internal fun TaskEntity.toTaskUi() : TaskUi =
    TaskUi(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted
    )
