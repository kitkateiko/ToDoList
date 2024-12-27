package com.bignerdranch.android.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
                 var content: String,
                 var priority: Int)