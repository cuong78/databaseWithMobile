package com.example.roomdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.room.Room;

import com.example.roomdatabase.constants.Constants;
import com.example.roomdatabase.db.AppDatabase;
import com.example.roomdatabase.model.Person;

public class EditPersonActivity extends AppCompatActivity {
    private EditText etFistName;
    private EditText etLastName;
    private Button btnSave;
    private int mPersonId;
    private Intent intent;
    private AppDatabase mDb;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_edit);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViews();
        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();

        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            btnSave.setText("Update");
            mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id, -1);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Person person = mDb.personDao().loadPersonById(mPersonId);
                    populateUI(person);
                }
            });
        }
    }

    private void populateUI(Person person) {
        if (person == null) {
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etFistName.setText(person.getFirstName());
                etLastName.setText(person.getLastName());
            }
        });
    }

    private void initViews() {
        etFistName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {
        final Person person = new Person(
                etFistName.getText().toString(),
                etLastName.getText().toString());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                boolean isUpdate = intent != null && intent.hasExtra(Constants.UPDATE_Person_Id);
                if (!isUpdate) {
                    mDb.personDao().insert(person);
                } else {
                    person.setUid(mPersonId);
                    mDb.personDao().update(person);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
