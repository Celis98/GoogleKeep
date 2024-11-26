package com.example.googlekeep.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.TaskUi
import com.example.domain.usecase.AddTaskUseCase
import com.example.domain.usecase.DeleteTaskUseCase
import com.example.domain.usecase.GetAllTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getAllTaskUseCase: GetAllTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        loadAllTasks()
    }

    fun loadAllTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                taskList = getAllTaskUseCase.getAllTasks()
            )
        }
    }

    fun saveNewTask(taskUi: TaskUi) {
        viewModelScope.launch {
            addTaskUseCase.addTask(taskUi)
            loadAllTasks()
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            _uiState.value.taskList.find { it.id == id }?.let { taskToDelete ->
                if (deleteTaskUseCase.deleteTaskById(taskToDelete)) {
                    _uiState.value = _uiState.value.copy(
                        taskList = _uiState.value.taskList.filter { it.id != id }
                    )
                }
            }
        }
    }
}

data class TaskUiState(
    val taskList: List<TaskUi> = emptyList()
)