package com.example.domain.usecase

import com.example.data.repository.TaskDbRepository
import com.example.domain.model.TaskUi
import com.example.domain.model.toTaskEntity
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskDbRepository: TaskDbRepository
) {
    suspend fun deleteTaskById(taskUi: TaskUi): Boolean {
        return taskDbRepository.deleteTask(taskUi.toTaskEntity()) == 1
    }
}