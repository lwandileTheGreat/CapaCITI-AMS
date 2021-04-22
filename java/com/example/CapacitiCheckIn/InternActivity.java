package com.example.CapacitiCheckIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class InternActivity extends AppCompatActivity {
    private static final String TAG = "InternActivity";
    private DatabaseSupporter2 db;
    private TextView theTime;
    private EditText name, surname, program, attendance;
    private Button attendanceBtn, logOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intern);

        initialViews();

        attendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAttendance();
            }
        });
        logOutBtn.setOnClickListener(new View.OnClickListener() {
          @Override
              public void onClick(View v) {
        logout();
    }
});

        currentTime();
    }


    private void currentTime(){
        Log.d(TAG, "Current time: ");

        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate=dateFormat.format(date);

        theTime.setText(formattedDate);
    }

    private void  addAttendance() {
        String firstname = name.getText().toString().trim();
        String lastname = surname.getText().toString().trim();
        String programme = program.getText().toString().trim();
        String setAttendance = theTime.getText().toString().trim();

        if (firstname.equals("") || lastname.equals("") || programme.equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill in the empty space/(s)", Toast.LENGTH_SHORT).show();
        }
        else {
            boolean isInserted = db.insertData(firstname, lastname, setAttendance, programme);

            if (isInserted == true) {
                Intent intentBackToLogin = new Intent(InternActivity.this, MainActivity.class);
                startActivity(intentBackToLogin);
                Toast.makeText(this, "You have checked in successfully", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this, "Checked in failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void logout(){
        Intent intent = new Intent(InternActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Log out successfully", Toast.LENGTH_SHORT).show();
    }

    private void initialViews() {

        Log.d(TAG, "Intern page: ");

        db = new DatabaseSupporter2(this);

        theTime = findViewById(R.id.time);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        program = findViewById(R.id.program);


        attendanceBtn = findViewById(R.id.takeAttedenceBtn);
        logOutBtn = findViewById(R.id.logOutBtn);

    }

}