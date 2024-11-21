package com.example.googlekeep.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.TaskUi

@Composable
fun Task(
    modifier: Modifier = Modifier,
    taskUi: TaskUi
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 2.dp)
                    .testTag(TASK_CARD_TITLE),
                text = taskUi.title
            )
            Text(
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 4.dp)
                    .testTag(TASK_CARD_DESCRIPTION),
                text = taskUi.description
            )
        }
    }
}

const val TASK_CARD_TITLE = "TASK_CARD_TITLE"
const val TASK_CARD_DESCRIPTION = "TASK_CARD_DESCRIPTION"

@Preview
@Composable
fun PreviewTaskCard() {
    Task(
        taskUi = TaskUi(0, "Titulo", "Descripcion")
    )
}