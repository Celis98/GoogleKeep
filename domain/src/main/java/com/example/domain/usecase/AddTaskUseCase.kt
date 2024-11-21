package com.example.domain.usecase

import com.example.data.repository.TaskDbRepository
import com.example.domain.model.TaskUi
import com.example.domain.model.toTaskEntity
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val taskDbRepository: TaskDbRepository
) {
    suspend fun addTask(taskUi: TaskUi) =
        taskDbRepository.insertTask(
            taskUi.toTaskEntity()
        )
}