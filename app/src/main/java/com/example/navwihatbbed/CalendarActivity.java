package com.example.navwihatbbed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Button button;
    CalendarView calendarView;
    String selectedDate;
    private mySQLiteDBHandler dbHandler;
    TextView num_lecture, num_assignment, num_exam , num_study_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("Study Planner");
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=(DrawerLayout) findViewById(R.id.mydrawer);
        navigationView = (NavigationView) findViewById(R.id.cnav);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        dbHandler = new mySQLiteDBHandler(this);
        num_lecture = findViewById(R.id.num_lecture);
        num_assignment = findViewById(R.id.num_assignment);
        num_exam = findViewById(R.id.num_exam);
        num_study_plan = findViewById(R.id.num_study_plan);

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                selectedDate = dayOfMonth + "/" + (month+1) + '/' + year;

                try{
                    List<Integer> count = dbHandler.getCount(getApplicationContext(),selectedDate);
//                    Toast.makeText(getApplicationContext(), count.get(0).toString(), Toast.LENGTH_LONG).show();
                    num_study_plan.setText(count.get(0).toString());
                    num_assignment.setText(count.get(1).toString());
                    num_exam.setText(count.get(2).toString());
                    num_lecture.setText(count.get(3).toString());
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();
                }

            }
        });

        button = (Button) findViewById(R.id.addevent_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, AddEventActivity.class);
                intent.putExtra("Date",selectedDate);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    Toast.makeText(getApplicationContext(), "Home is clicked", Toast.LENGTH_LONG).show();
                    Intent myintent = new Intent(CalendarActivity.this, MainActivity.class);
                    startActivity(myintent);
                    return false;
                }

                if (item.getItemId() == R.id.calendar) {
                    Toast.makeText(getApplicationContext(), "Calendar is clicked", Toast.LENGTH_LONG).show();

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }
}