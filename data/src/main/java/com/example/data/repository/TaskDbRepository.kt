package com.example.data.repository

import com.example.data.db.dao.TaskDao
import com.example.data.db.models.TaskEntity
import javax.inject.Inject

class TaskDbRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    suspend fun getAllTasks() =
        taskDao.getAllTasks()

    suspend fun insertTask(taskEntity: TaskEntity) =
        taskDao.insertTask(taskEntity)

    suspend fun updateTask(taskEntity: TaskEntity) =
        taskDao.updateTask(taskEntity)

    suspend fun deleteTask(taskEntity: TaskEntity) =
        taskDao.deleteTask(taskEntity)
}