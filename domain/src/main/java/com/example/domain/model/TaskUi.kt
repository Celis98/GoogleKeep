package com.example.domain.model

import com.example.data.db.models.TaskEntity
import java.io.Serializable

data class TaskUi(
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val creationDate: Long? = null,
    val dueTime: String? = null,
    val dueDate: Long? = null,
    val photo: String? = null,
    val location: String? = null
): Serializable

internal fun TaskUi.toTaskEntity() : TaskEntity =
    TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        dueDate = this.dueDate,
        creationDate = this.creationDate,
        dueTime = this.dueTime
    )

internal fun TaskEntity.toTaskUi() : TaskUi =
    TaskUi(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        dueDate = this.dueDate,
        creationDate = this.creationDate,
        dueTime = this.dueTime
    )
