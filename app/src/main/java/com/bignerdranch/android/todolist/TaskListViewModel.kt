package com.bignerdranch.android.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import database.TaskDao
import kotlinx.coroutines.launch

class TaskListViewModel (private val taskDao: TaskDao):ViewModel() {
    private val ts=MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = ts

    fun addTask(content:String,priority:Int)
    {
        val task=Task(content=content,priority=priority)
        viewModelScope.launch{
            taskDao.addTask(task)
            getTasks()
        }

    }

    fun getTasks()
    {
        viewModelScope.launch{
            val tasks_list=taskDao.getTask()
            ts.postValue(tasks_list)
        }
    }

    fun delTask(task:Task)
    {
        viewModelScope.launch{
            taskDao.delTask(task)
            getTasks()
        }
    }

}