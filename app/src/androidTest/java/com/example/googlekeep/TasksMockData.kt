package com.example.googlekeep

import com.example.domain.model.TaskUi

fun getMockTasks(
    counter: Int = 3
): ArrayList<TaskUi> {
    val tasks = arrayListOf<TaskUi>()
    for (i in 0 until counter) {
        tasks.add(
            TaskUi(i, "t: $i", "d: $i")
        )
    }
    return tasks
}

val mockTaskUi = TaskUi(0, "Titulo", "Descripcion")