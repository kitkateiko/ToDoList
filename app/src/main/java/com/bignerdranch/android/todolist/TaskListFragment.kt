package com.bignerdranch.android.todolist

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import  androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.TDLDatabase


class TaskListFragment : Fragment(R.layout.fragment_task_list){

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter

    private val taskListViewModel: TaskListViewModel by viewModels {
        TaskFactory(TDLDatabase.getDatabase(requireContext()).taskDao())
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView=view.findViewById(R.id.task_recycler_view)
        recyclerView.layoutManager=LinearLayoutManager(context)

        adapter = TaskAdapter(mutableListOf())
        recyclerView.adapter = adapter

        taskListViewModel.tasks.observe(viewLifecycleOwner){
            tasks->adapter.updateTasks(tasks)
        }

        taskListViewModel.addTask(content = "MAke", priority = 3)
    }

    private inner class TaskHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var task: Task
        private val task_content: TextView = itemView.findViewById(R.id.taskContent)
        private val task_circle: FrameLayout = itemView.findViewById(R.id.priorityCircle)
        private val task_num: TextView = itemView.findViewById(R.id.priorityNumber)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(task: Task) {
            this.task = task
            task_content.text = task.content
            when (task.priority) {
                1 -> task_circle.backgroundTintList = ColorStateList.valueOf(Color.RED)
                2 -> task_circle.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
                3 -> task_circle.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
            }
            task_num.text = task.priority.toString()
        }

        override fun onClick(v: View?) {
            // Обработка клика по задаче
        }
    }

    private inner class TaskAdapter(var tasks: MutableList<Task>) : RecyclerView.Adapter<TaskHolder>() {
        override fun onCreateViewHolder(parent : ViewGroup, viewType: Int) : TaskHolder {
            val view = layoutInflater.inflate(R.layout.list_item_task, parent, false)
            return TaskHolder(view)
        }
        override fun getItemCount() = tasks.size
        override fun onBindViewHolder(holder : TaskHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)
        }
        fun updateTasks(newTasks: List<Task>) {
            tasks.clear()
            tasks.addAll(newTasks)
            notifyDataSetChanged()
        }
    }

    companion object
    {
        fun newInstance()=TaskListFragment()
    }
}