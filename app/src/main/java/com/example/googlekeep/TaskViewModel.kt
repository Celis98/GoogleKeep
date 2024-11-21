package com.example.googlekeep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.TaskUi
import com.example.domain.usecase.AddTaskUseCase
import com.example.domain.usecase.GetAllTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.StringFormat
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getAllTaskUseCase: GetAllTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase
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
}

data class TaskUiState(
    val taskList: List<TaskUi> = emptyList()
)