package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDataBase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.TaskViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DaoTest {

    // For data
    private TodocDataBase dataBase;;

    private static long PROJECT_ID = 1;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "test", 0xFFEADAD1);
    private static Task TASK_DEMO = new Task(PROJECT_ID, "test", 1593545213);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.dataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDataBase.class).allowMainThreadQueries().build();
    }

    @After
    public void closeDb() throws Exception {
        dataBase.close();
    }

    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.tasksDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException {
        this.dataBase.projectDao().createProject(PROJECT_DEMO);
        this.dataBase.tasksDao().insertTask(TASK_DEMO);

        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.tasksDao().getTasks());
        assertTrue(tasks.size() == 1);
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        this.dataBase.projectDao().createProject(PROJECT_DEMO);
        this.dataBase.tasksDao().insertTask(TASK_DEMO);

        Task tasksAdded = LiveDataTestUtil.getValue(this.dataBase.tasksDao().getTasks()).get(0);
        this.dataBase.tasksDao().deleteTask(tasksAdded);

        List<Task> tasks = LiveDataTestUtil.getValue(this.dataBase.tasksDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        this.dataBase.projectDao().createProject(PROJECT_DEMO);

        List<Project> projects = LiveDataTestUtil.getValue(this.dataBase.projectDao().getProjects());
        assertTrue(projects.size() == 1);
    }


}
