package com.example.googlekeep

import com.example.domain.usecase.AddTaskUseCase
import com.example.domain.usecase.GetAllTaskUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class TaskViewModelTest {

    private val getAllTaskUseCase: GetAllTaskUseCase = mockk {
        // Definir valores esperados
        coEvery { getAllTasks() } returns getMockTasks()
    }
    private val addTaskUseCase: AddTaskUseCase = mockk(relaxed = true)
    private lateinit var viewModel: TaskViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        val dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        viewModel = TaskViewModel(getAllTaskUseCase, addTaskUseCase)
    }

    @Test
    fun `loadAllTasks function should fill uiState when result is not empty`() = runTest {
        // Valores esperados
        val expectedTasks = getMockTasks()
        // Llamar la funcion del viewmodel
        viewModel.loadAllTasks()
        // Assert
        coVerify(exactly = 1) {
            getAllTaskUseCase.getAllTasks()
        }
        assertEquals(expectedTasks, viewModel.uiState.value.taskList)
    }

    @Test
    fun `saveNewTask function calls only once addTask from the addTaskUseCase`() = runTest {
        viewModel.saveNewTask(mockTaskUi)
        coVerify() {
            addTaskUseCase.addTask(any())
        }
    }
}