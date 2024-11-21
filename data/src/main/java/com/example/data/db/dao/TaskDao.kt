package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.db.models.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM TASKS")
    suspend fun getAllTasks(): List<TaskEntity>

    @Insert
    suspend fun insertTask(taskEntity: TaskEntity)

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)
}