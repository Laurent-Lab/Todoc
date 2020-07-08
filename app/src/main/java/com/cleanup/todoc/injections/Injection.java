package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.database.TodocDataBase;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskDataRepository provideTasksDataSource(Context context){
        TodocDataBase dataBase = TodocDataBase.getInstance(context);
        return new TaskDataRepository(dataBase.tasksDao());
    }

    public static ProjectDataRepository provideProjectSource(Context context){
        TodocDataBase dataBase = TodocDataBase.getInstance(context);
        return new ProjectDataRepository(dataBase.projectDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static  ViewModelFactory provideViewModelFactory(Context context){
        TaskDataRepository dataSourceTask = provideTasksDataSource(context);
        ProjectDataRepository dataSourceProject = provideProjectSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }
}
