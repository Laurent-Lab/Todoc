package com.cleanup.todoc.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

//Regroupe toutes les actions CRUD de la table Task
@Dao
public interface TasksDao {

    /**
     *
     * @return
     */
    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getTasks();

    /**
     *
     * @param task
     * @return new task
     */
    @Insert
    long insertTask(Task task);

    /**
     *
     * @param task
     * @return
     */
    @Delete
    int deleteTask(Task task);

}
