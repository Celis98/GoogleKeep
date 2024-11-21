package com.example.googlekeep

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.*
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.googlekeep.ui.screen.AddTask
import com.example.googlekeep.ui.screen.TaskList
import com.example.googlekeep.ui.theme.GoogleKeepTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewmodel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleKeepTheme {
                MainScreenNavigation(viewmodel)
            }
        }
    }
}

@Composable
fun MainScreenNavigation(viewmodel: TaskViewModel) {
    val taskUiState by viewmodel.uiState.collectAsState()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TaskList
    ) {
        composable<TaskList> {
            TaskList(
                taskList = taskUiState.taskList
            ) {
                navController.navigate(AddTask)
            }
        }
        composable<AddTask> {
            AddTask { taskToSave ->
                viewmodel.saveNewTask(taskToSave)
                navController.popBackStack(route = TaskList, inclusive = false)
            }
        }
    }
    HandleAppLifecycle { state ->
        Log.d("TEST--", "$state")
        when(state) {
            RESUMED -> {
                viewmodel.loadAllTasks()
            }
            else -> Unit
        }
    }
}

@Composable
fun HandleAppLifecycle(
    onLifecycleChanged:(Lifecycle.State) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentLifecycleStateFlow by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    onLifecycleChanged(currentLifecycleStateFlow)

    val context = LocalContext.current
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
//        Toast.makeText(context, "LifecycleEventEffect ON_RESUME", Toast.LENGTH_SHORT).show()
    }
    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
//        Toast.makeText(context, "LifecycleEventEffect ON_PAUSE", Toast.LENGTH_SHORT).show()
    }
    LifecycleEventEffect(Lifecycle.Event.ON_START) {
//        Toast.makeText(context, "LifecycleEventEffect ON_START", Toast.LENGTH_SHORT).show()
    }
}