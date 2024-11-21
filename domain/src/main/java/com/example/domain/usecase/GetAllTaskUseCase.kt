package com.example.domain.usecase

import com.example.data.repository.TaskDbRepository
import com.example.domain.model.toTaskUi
import javax.inject.Inject

class GetAllTaskUseCase @Inject constructor(
    private val taskDbRepository: TaskDbRepository
) {
    suspend fun getAllTasks() =
        taskDbRepository.getAllTasks().map {
            it.toTaskUi()
        }
}