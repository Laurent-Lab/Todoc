package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.TasksDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TasksDao taskDao;

    public TaskDataRepository(TasksDao taskDao){
        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getTasks(){
        return this.taskDao.getTasks();
    }

    public void createTask(Task task){ taskDao.insertTask(task); }

    public void deleteTask(Task task){
        taskDao.deleteTask(task);
    }
}
