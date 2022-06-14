package com.example.android.architecture.blueprints.todoapp.tasks.PageObjects

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.android.architecture.blueprints.todoapp.R

class TaskPage {

    private var completeCheckbox = onView(withId(R.id.task_detail_complete_checkbox))
    private var titleText = onView(withId(R.id.task_detail_title_text))
    private var descriptionText = onView(withId(R.id.task_detail_description_text))
    private var deleteTask = onView(withId(R.id.menu_delete))

    fun checkTaskOnTaskPage(taskTitleText:String, taskDescriptionText:String){

        completeCheckbox.check(
            matches(
                isNotChecked()
            )
        )
        titleText.check(
            matches(
                withText(taskTitleText)
            )
        )
        descriptionText.check(
            matches(
                withText(taskDescriptionText)
            )
        )
    }

    fun deleteTask(){
        deleteTask.perform(click())
    }
}