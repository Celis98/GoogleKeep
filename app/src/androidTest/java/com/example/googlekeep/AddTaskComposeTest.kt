package com.example.googlekeep

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.domain.model.TaskUi
import com.example.googlekeep.ui.screen.ADD_TASK_CARD_DESCRIPTION
import com.example.googlekeep.ui.screen.ADD_TASK_CARD_TITLE
import com.example.googlekeep.ui.screen.ADD_TASK_SAVE_BUTTON
import com.example.googlekeep.ui.screen.ADD_TASK_TEXT_FIELD_DESCRIPTION
import com.example.googlekeep.ui.screen.ADD_TASK_TEXT_FIELD_TITLE
import com.example.googlekeep.ui.screen.AddTask
import com.example.googlekeep.ui.theme.GoogleKeepTheme
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class AddTaskComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testComponentsExistsOnAddTaskComposable() {
        // Iniciar la aplicación
        composeTestRule.setContent {
            // Definir el tema
            GoogleKeepTheme {
                // Setear el contenido
                AddTask {  }
            }
        }
        // Verificar que los elementos existan
        composeTestRule.onNodeWithTag(ADD_TASK_CARD_TITLE).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ADD_TASK_CARD_DESCRIPTION).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ADD_TASK_SAVE_BUTTON).assertIsDisplayed()
    }

    @Test
    fun testAddTaskComposableDoesNotGetCalledWhenTitleOrDescriptionAreEmpty() {
        var taskUi: TaskUi? = null
        composeTestRule.setContent {
            GoogleKeepTheme {
                AddTask { task ->
                    taskUi = task
                }
            }
        }
        composeTestRule.onNodeWithTag(ADD_TASK_SAVE_BUTTON).performClick()
        assert(taskUi == null)
    }

    @Test
    fun testAddTaskComposableReturnsTaskUi() {
        var taskUi: TaskUi? = null
        composeTestRule.setContent {
            GoogleKeepTheme {
                AddTask { task ->
                    taskUi = task
                }
            }
        }

        val title = "Title"
        val description = "Description"

        composeTestRule.onNodeWithTag(ADD_TASK_TEXT_FIELD_TITLE).performTextInput(title)
        composeTestRule.onNodeWithTag(ADD_TASK_TEXT_FIELD_DESCRIPTION).performTextInput(description)

        composeTestRule.onNodeWithTag(ADD_TASK_TEXT_FIELD_TITLE).assert(hasText(title))
        composeTestRule.onNodeWithTag(ADD_TASK_TEXT_FIELD_DESCRIPTION).assert(hasText(description))

        composeTestRule.onNodeWithTag(ADD_TASK_SAVE_BUTTON).performClick()

        assertEquals(taskUi?.title, title)
        assertEquals(taskUi?.description, description)
    }
}