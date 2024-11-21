package com.example.googlekeep.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.TaskUi
import kotlinx.serialization.Serializable

@Serializable
object AddTask

@Composable
fun AddTask(
    onSaveTask: (TaskUi) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val context = LocalContext.current
        var taskTitle by remember { mutableStateOf("") }
        AddTaskTextField(
            modifier = Modifier
                .padding(bottom = 4.dp),
            value = taskTitle,
            label = "Titulo",
            cardTestTag = ADD_TASK_CARD_TITLE,
            textFieldTestTag = ADD_TASK_TEXT_FIELD_TITLE
        ) {
            taskTitle = it
        }
        var taskDescription by remember { mutableStateOf("") }
        AddTaskTextField(
            modifier = Modifier
                .padding(top = 4.dp),
            value = taskDescription,
            label = "Descripcion",
            cardTestTag = ADD_TASK_CARD_DESCRIPTION,
            textFieldTestTag = ADD_TASK_TEXT_FIELD_DESCRIPTION
        ) {
            taskDescription = it
        }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .testTag(ADD_TASK_SAVE_BUTTON),
            onClick = {
                if (taskTitle.isNotEmpty() && taskDescription.isNotEmpty()) {
                    onSaveTask(
                        TaskUi(
                            title = taskTitle,
                            description = taskDescription
                        )
                    )
                } else {
                    Toast.makeText(context, "Campos vacios", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Guardar tarea")
        }
    }
}

@Composable
fun AddTaskTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    value: String,
    cardTestTag: String = "",
    textFieldTestTag: String = "",
    onValueChange: (String) -> Unit,
) {
    OutlinedCard(
        modifier = modifier
            .testTag(cardTestTag)
    ) {
        TextField(
            label = {
                Text(label)
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(textFieldTestTag),
            value = value,
            onValueChange = {
                onValueChange(it)
            }
        )
    }
}

const val ADD_TASK_CARD_TITLE = "ADD_TASK_CARD_TITLE"
const val ADD_TASK_TEXT_FIELD_TITLE = "ADD_TASK_TEXT_FIELD_TITLE"
const val ADD_TASK_TEXT_FIELD_DESCRIPTION = "ADD_TASK_TEXT_FIELD_DESCRIPTION"
const val ADD_TASK_CARD_DESCRIPTION = "ADD_TASK_CARD_DESCRIPTION"
const val ADD_TASK_SAVE_BUTTON = "ADD_TASK_SAVE_BUTTON"

@Preview
@Composable
fun PreviewAddTaskTextField() {
    AddTaskTextField(
        value = "Prueba",
        label = "Hint aqui"
    ) { }
}