package com.example.googlekeep

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
import com.example.googlekeep.ui.screen.TASK_LIST_TEST_TAG
import com.example.googlekeep.ui.screen.TaskList
import org.junit.Rule
import org.junit.Test

class TaskListComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testTaskListIsLoaded() {
        val taskList = getMockTasks(100)
        composeTestRule.setContent {
            TaskList(
                taskList = taskList
            ) { }
        }
        composeTestRule.onNodeWithTag(TASK_LIST_TEST_TAG)
            .onChildren().assertCountEquals(taskList.size)
    }

    @Test
    fun testTaskListCanScrollToMiddle() {
        val taskList = getMockTasks(100)
        composeTestRule.setContent {
            TaskList(
                taskList = taskList
            ) { }
        }
        composeTestRule.onNodeWithTag(TASK_LIST_TEST_TAG)
            .performScrollToIndex(50).assertExists()
    }

    @Test
    fun testTaskListDataIsConsistentWithIndex() {
        val taskList = getMockTasks(5)
        val indexToValidate = 2
        composeTestRule.setContent {
            TaskList(
                taskList = taskList
            ) { }
        }
        val textToValidate = taskList[indexToValidate].title
        composeTestRule.onNodeWithTag(TASK_LIST_TEST_TAG)
            .onChildAt(indexToValidate)
            .onChildAt(0)
            .assert(hasText(textToValidate))
    }

    @Test
    fun testTaskListDataIsConsistentWithTestTag() {
        val taskList = getMockTasks(5)
        val indexToValidate = 2
        composeTestRule.setContent {
            TaskList(
                taskList = taskList
            ) { }
        }
        val titleExpected = taskList[indexToValidate].title
        val descriptionExpected = taskList[indexToValidate].description
        composeTestRule.onNodeWithTag(TASK_LIST_TEST_TAG)
            .onChildAt(indexToValidate)
            .onChild()
            .fetchSemanticsNode()
    }
}