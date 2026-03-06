package com.example.minitask_roomdatabase.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minitask_roomdatabase.R;
import com.example.minitask_roomdatabase.database.AppDatabase;
import com.example.minitask_roomdatabase.model.Task;

public class AddEditTaskActivity extends AppCompatActivity {

    EditText edtTitle, edtDescription;
    CheckBox checkStatus;
    Button btnSave;
    TextView tvHeader;
    AppDatabase database;
    Task existingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        tvHeader = findViewById(R.id.tvHeader);
        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        checkStatus = findViewById(R.id.checkStatus);
        btnSave = findViewById(R.id.btnSave);

        database = AppDatabase.getInstance(this);

        existingTask = (Task) getIntent().getSerializableExtra("task_data");

        if (existingTask != null) {

            tvHeader.setText("Update Task");
            btnSave.setText("Update Task");
            edtTitle.setText(existingTask.getTitle());
            edtDescription.setText(existingTask.getDescription());
            checkStatus.setVisibility(View.VISIBLE);
            checkStatus.setChecked(existingTask.isCompleted());
        }

        btnSave.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (existingTask != null) {
                // Update
                existingTask.setTitle(title);
                existingTask.setDescription(description);
                existingTask.setCompleted(checkStatus.isChecked());
                database.taskDao().update(existingTask);
            } else {
                // Add New
                // TODO: Insert task vào database

            }
            finish();
        });
    }
}
