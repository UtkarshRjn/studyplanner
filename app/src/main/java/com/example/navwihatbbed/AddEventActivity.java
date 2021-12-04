package com.example.navwihatbbed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddEventActivity extends AppCompatActivity {

    TextInputLayout til_types;
    AutoCompleteTextView act_types;
    List<String> arrayList_types;
    ArrayAdapter<String> arrayAdapter_types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        til_types = (TextInputLayout)findViewById(R.id.til_types);
        act_types = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        arrayList_types = Arrays.asList(getResources().getStringArray(R.array.types));
        arrayAdapter_types = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arrayList_types);
        act_types.setAdapter(arrayAdapter_types);

        act_types.setThreshold(1);

    }
}