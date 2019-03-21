package com.rubin.rxlearning.utils

import com.rubin.rxlearning.model.Task

class DataSource {

    companion object {
        fun createTaskList(): List<Task> {
            val tasks = mutableListOf<Task>()
            tasks.add(Task("Take out the trask", true, 3))
            tasks.add(Task("Walk the dog", false, 2))
            tasks.add(Task("Make my bed", true, 1))
            tasks.add(Task("Unload the dishwasher", false, 0))
            tasks.add(Task("Make dinner", true, 5))

            return tasks
        }
    }
}