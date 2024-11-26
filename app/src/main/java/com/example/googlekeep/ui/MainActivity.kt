package com.example.googlekeep.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import com.example.domain.model.TaskUi
import com.example.googlekeep.ui.notification.HandlePostNotificationPermission
import com.example.googlekeep.ui.notification.ReminderReceiver.Companion.NOTIFICATION_DATA_KEY
import com.example.googlekeep.ui.notification.ScheduleNotification
import com.example.googlekeep.ui.screen.AddTask
import com.example.googlekeep.ui.screen.TaskList
import com.example.googlekeep.ui.theme.GoogleKeepTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewmodel: TaskViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleKeepTheme {
                HandlePostNotificationPermission()
                MainScreenNavigation(viewmodel)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        intent.getSerializableExtra(NOTIFICATION_DATA_KEY, TaskUi::class.java)?.let {
            Toast.makeText(this, "La tarea ${it.title} ya se venció", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun MainScreenNavigation(viewmodel: TaskViewModel) {
    val context = LocalContext.current
    val taskUiState by viewmodel.uiState.collectAsState()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TaskList
    ) {
        composable<TaskList> {
            TaskList(
                taskList = taskUiState.taskList,
                onDeleteTask = { id ->
                    viewmodel.deleteTask(id)
                }
            ) {
                navController.navigate(AddTask)
            }
        }
        composable<AddTask> {
            AddTask { taskToSave ->
                viewmodel.saveNewTask(taskToSave)
                ScheduleNotification().scheduleNotification(context, taskToSave)
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