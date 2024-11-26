package com.example.googlekeep.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.TaskUi

@Composable
fun Task(
    modifier: Modifier = Modifier,
    onDeleteTask: (id: Int) -> Unit,
    taskUi: TaskUi
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                        .testTag(TASK_CARD_TITLE),
                    text = taskUi.title
                )
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                        .testTag(TASK_CARD_DESCRIPTION),
                    text = taskUi.description
                )
                taskUi.dueDate?.let {
                    Text(
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp)
                            .testTag(TASK_CARD_DESCRIPTION),
                        color = if (it < System.currentTimeMillis()) Color.Red else Color.Blue,
                        text = "${it.convertMillisToDate()}"
                    )
                }
                taskUi.dueTime?.let {
                    Text(
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp)
                            .testTag(TASK_CARD_DESCRIPTION),
                        text = it
                    )
                }
            }
            Button(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                onClick = {
                    onDeleteTask(taskUi.id)
                }
            ) {
                Image(Icons.Outlined.Delete, "")
            }
        }
    }
}

const val TASK_CARD_TITLE = "TASK_CARD_TITLE"
const val TASK_CARD_DESCRIPTION = "TASK_CARD_DESCRIPTION"

@Preview
@Composable
fun PreviewTaskCard() {
    Task(
        taskUi = TaskUi(0, "Titulo", "Descripcion"),
        onDeleteTask = {}
    )
}