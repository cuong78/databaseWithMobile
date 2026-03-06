package com.example.minitask_roomdatabase.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minitask_roomdatabase.R;
import com.example.minitask_roomdatabase.adapter.TaskAdapter;
import com.example.minitask_roomdatabase.database.AppDatabase;
import com.example.minitask_roomdatabase.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton btnAdd;

    TaskAdapter adapter;
    List<Task> taskList = new ArrayList<>();

    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);

        database = AppDatabase.getInstance(this);


        adapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskActionListener() {
            @Override
            public void onUpdate(Task task) {
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                intent.putExtra("task_data", task);
                startActivity(intent);
            }

            @Override
            public void onDelete(Task task) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Task")
                        .setMessage("Are you sure you want to delete this task?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            database.taskDao().delete(task);
                            loadData();
                            Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }

            @Override
            public void onStatusChange(Task task) {
                database.taskDao().update(task);

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
            startActivity(intent);
        });
        

        checkAndAddSampleData();
    }

    private void checkAndAddSampleData() {
    // TODO: Load danh sách task từ database


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        List<Task> newData = database.taskDao().getAllTasks();
        if (newData != null) {
            taskList.clear();
            taskList.addAll(newData);
            adapter.notifyDataSetChanged();
        }
    }
}
