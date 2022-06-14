package com.example.android.architecture.blueprints.todoapp.tasks.PageObjects

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.android.architecture.blueprints.todoapp.R

class AddTaskPage {

    private var addTaskBtn = onView(withId(R.id.add_task_fab))
    private var editTaskBtn = onView(withId(R.id.edit_task_fab))
    private var taskTitle = onView(withId(R.id.add_task_title_edit_text))
    private var taskDescription = onView(withId(R.id.add_task_description_edit_text))
    private var saveTaskBtn = onView(withId(R.id.save_task_fab))

    fun addTask(taskTitleText:String, taskDescriptionText:String){
        addTaskBtn.perform(click())
        taskTitle.perform(click())
        taskTitle.perform(
            typeText(taskTitleText)
        )
        taskDescription.perform(click())
        taskDescription.perform(
            typeText(taskDescriptionText)
        )
        saveTaskBtn.perform(click())
    }

    fun editTask(taskTitleText:String, taskDescriptionText:String){
        editTaskBtn.perform(click())
        taskTitle.perform(click())
        taskTitle.perform(clearText())
        taskTitle.perform(
            typeText(taskTitleText)
        )
        taskDescription.perform(click())
        taskDescription.perform(clearText())
        taskDescription.perform(
            typeText(taskDescriptionText)
        )
        saveTaskBtn.perform(click())
    }
}