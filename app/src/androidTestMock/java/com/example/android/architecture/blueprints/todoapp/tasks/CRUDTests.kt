package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.tasks.PageObjects.AddTaskPage
import com.example.android.architecture.blueprints.todoapp.tasks.PageObjects.MainPage
import com.example.android.architecture.blueprints.todoapp.tasks.PageObjects.TaskPage
import com.example.android.architecture.blueprints.todoapp.util.DataBindingIdlingResource
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource
import com.example.android.architecture.blueprints.todoapp.util.deleteAllTasksBlocking
import com.example.android.architecture.blueprints.todoapp.util.monitorActivity
import com.example.android.architecture.blueprints.todoapp.util.saveTaskBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CRUDTests {

    private lateinit var repository: TasksRepository

    private val taskTitleText = "My Task Title"
    private val taskDescriptionText = "Task description"
    private val newTaskTitleText = "New Task Title"
    private val newTaskDescriptionText = "New Task description"

    private var addTaskPage = AddTaskPage()
    private var mainPage = MainPage()
    private var taskPage = TaskPage()

    private var taskItem = onView(withText(taskTitleText))
    private var newTaskItem = onView(withText(newTaskTitleText))

    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()


    @Before
    fun init() {
        // Run on UI thread to make sure the same instance of the SL is used.
        runOnUiThread {
            ServiceLocator.createDataBase(getApplicationContext(), inMemory = true)
            repository = ServiceLocator.provideTasksRepository(getApplicationContext())
            repository.deleteAllTasksBlocking()
        }
    }

    @After
    fun reset() {
        runOnUiThread {
            ServiceLocator.resetRepository()
        }
    }

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun addTaskTest() {
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        addTaskPage.addTask(taskTitleText, taskDescriptionText)
        mainPage.checkTaskMainPage(taskTitleText)
        taskPage.checkTaskOnTaskPage(taskTitleText,taskDescriptionText)
    }

    @Test
    fun editTaskTest(){
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        repository.saveTaskBlocking(Task(taskTitleText, taskDescriptionText))
        dataBindingIdlingResource.monitorActivity(activityScenario)

        taskItem.perform(click())

        addTaskPage.editTask(newTaskTitleText, newTaskDescriptionText)
        mainPage.checkTaskMainPage(newTaskTitleText)

        newTaskItem.perform(click())
        taskPage.checkTaskOnTaskPage(newTaskTitleText, newTaskDescriptionText)
    }

    @Test
    fun deleteTaskTest(){
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        repository.saveTaskBlocking(Task(taskTitleText, taskDescriptionText))
        dataBindingIdlingResource.monitorActivity(activityScenario)

        taskItem.perform(click())
        taskPage.deleteTask()
        taskItem.check(doesNotExist())
    }
}