package com.example.googlekeep.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.domain.model.TaskUi
import com.example.googlekeep.ui.composable.Task
import kotlinx.serialization.Serializable

@Serializable
object TaskList

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    taskList: List<TaskUi>,
    onAddTask: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 4.dp)
    ) {
        val (taskListRef, emptyListRef, addTaskRef) = createRefs()
        if (taskList.isEmpty()) {
            Text(
                modifier = Modifier
                    .constrainAs(emptyListRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                text = "No hay tareas guardadas"
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(taskListRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .testTag(TASK_LIST_TEST_TAG)
            ) {
                items(taskList) { task ->
                    Task(taskUi = task)
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .constrainAs(addTaskRef) {
                    bottom.linkTo(parent.bottom, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                },
            onClick = onAddTask,
            shape = FloatingActionButtonDefaults.largeShape
        ) {
            Icon(Icons.Outlined.Add, null)
        }
    }
}

const val TASK_LIST_TEST_TAG = "TASK_LIST_TEST_TAG"