package com.example.navwihatbbed;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Button button;
    MaterialCalendarView calendarView;
    String selectedDate;
    private mySQLiteDBHandler dbHandler;
    TextView num_lecture, num_assignment, num_exam , num_study_plan;
    EventDecorator eventDecorator;
    TodayDecorator todayDecorator;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("Calendar");
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

        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(1900, 1, 1))
                .setMaximumDate(CalendarDay.from(2100, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

//        Collection<CalendarDay> today = new ArrayList<>();
//        today.add(CalendarDay.today());
//        todayDecorator = new TodayDecorator(today);
//        calendarView.addDecorators(todayDecorator);
        calendarView.setDateSelected(CalendarDay.today(), true);

        ArrayList<String> eventDays = dbHandler.findMarkedDays(this);
        Collection<CalendarDay> event_col = new ArrayList<>();
        for(String date: eventDays){
            int day = Integer.parseInt(date.split("/")[0]);
            int month = Integer.parseInt(date.split("/")[1]);
            int year = Integer.parseInt(date.split("/")[2]);

            CalendarDay mydate = CalendarDay.from(year,  month-1, day);
            event_col.add(mydate);
        }

        eventDecorator = new EventDecorator(Color.parseColor("#E91E63"),event_col);
        calendarView.addDecorators(eventDecorator);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                String OnlyDate = date.toString().substring(date.toString().indexOf("{")+1,date.toString().indexOf("}"));
                selectedDate = OnlyDate.split("-")[2] + "/" + (Integer.parseInt(OnlyDate.split("-")[1]) + 1 )+ "/" + OnlyDate.split("-")[0];

                try{
                    List<Integer> count = dbHandler.getCount(getApplicationContext(),selectedDate);
                    num_study_plan.setText(count.get(0).toString());
                    num_assignment.setText(count.get(1).toString());
                    num_exam.setText(count.get(2).toString());
                    num_lecture.setText(count.get(3).toString());
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();
                }
            }
        });

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

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

    public class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
        }
    }

    public class TodayDecorator implements DayViewDecorator {

        private final HashSet<CalendarDay> dates;

        public TodayDecorator(Collection<CalendarDay> dates) {
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
        }
    }
}