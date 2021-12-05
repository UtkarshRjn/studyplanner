package com.example.navwihatbbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    Button save_button;
    private mySQLiteDBHandler dbHandler;
    private EditText editTextTitleName,editTextDate,editTextTime2,editTextTextMultiLine;
    private String type;


    List<String> arrayList_types;
    ArrayAdapter<String> arrayAdapter_types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("Add Events");
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        spinner = (Spinner)findViewById(R.id.spinner);
        arrayList_types = Arrays.asList(getResources().getStringArray(R.array.types));
        arrayAdapter_types = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arrayList_types);
        arrayAdapter_types.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter_types);
        spinner.setOnItemSelectedListener(this);

        save_button = (Button) findViewById(R.id.save_button);

        editTextTitleName = findViewById(R.id.editTextTitleName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime2 = findViewById(R.id.editTextTime2);
        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);
        dbHandler = new mySQLiteDBHandler(this);

        Intent intent = getIntent();
        String date = intent.getStringExtra("Date");

        editTextDate.setText(date);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventModel eventModel;
                try {
                    eventModel = new EventModel(-1,editTextTitleName.getText().toString(),editTextDate.getText().toString(),editTextTime2.getText().toString(),type,editTextTextMultiLine.getText().toString());
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Error creating Event",Toast.LENGTH_SHORT).show();
                    eventModel = new EventModel(-1,"error_title","error_date","error_time","error_type","error_description");
                }

                boolean success = dbHandler.addOne(eventModel);
                Intent intent = new Intent(AddEventActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), type, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}