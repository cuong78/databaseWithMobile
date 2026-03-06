package com.example.minitask_roomdatabase.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.minitask_roomdatabase.dao.TaskDao;
import com.example.minitask_roomdatabase.model.Task;

@Database(entities = {Task.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract TaskDao taskDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            // TODO: Tạo database

        }
        return instance;
    }
}
