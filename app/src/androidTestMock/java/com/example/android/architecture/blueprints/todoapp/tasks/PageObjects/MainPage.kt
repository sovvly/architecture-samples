package com.example.android.architecture.blueprints.todoapp.tasks.PageObjects

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.android.architecture.blueprints.todoapp.R

class MainPage {

    private var completeCheckbox =  onView(withId(R.id.complete_checkbox))
    private var titleText =  onView(withId(R.id.title_text))

    fun checkTaskMainPage(taskTitleText:String){

        completeCheckbox.check(
            matches(
                isNotChecked()
            )
        )
        titleText.check(
            matches(
                withText(
                    taskTitleText
                )
            )
        )
        titleText.perform(click())
    }
}