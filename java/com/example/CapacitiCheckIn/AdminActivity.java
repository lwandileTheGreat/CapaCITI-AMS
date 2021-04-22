package com.example.CapacitiCheckIn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class AdminActivity extends AppCompatActivity {
    private static final String TAG = "AdminActivity";
    private Button viewAttendance, logOutBtn, viewSkills, viewLearnership, download, addEmailBtn, delete;
    private DatabaseSupporter2 db;
    private DatabaseSupporter3 database;
    private DatabaseSupporter datab;
    private EditText addEmailTxt;
    private ImageView emailBtn, deleteEmails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    initialViews();
    addEmailBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            emailAdd();
        }
    });
    viewAttendance.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        view();
    }
});
    viewSkills.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            skills();
        }
    });
    viewLearnership.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            learnership();
        }
    });
    delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            db.delete();
            Intent intentBack = new Intent(AdminActivity.this, AdminActivity.class);
            startActivity(intentBack);
            Toast.makeText(getApplicationContext(), "Information deleted successfully", Toast.LENGTH_SHORT).show();
        }
    });

    logOutBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentBackToLogin = new Intent(AdminActivity.this, MainActivity.class);
            startActivity(intentBackToLogin);
            Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
        }
    });

    emailBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            viewEmail();
        }
    });

    deleteEmails.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = addEmailTxt.getText().toString().trim();
            if (email.equals("")){
            emailsDelete();}
            else {
                specificDelete();
            }

        }
    });
//    download.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//        downloadData();
//    }
//});
}

private void emailsDelete(){
    Log.d(TAG, "Deleting Email: ");
    database.deleteEmails();
    datab.deleteEmails();
    Intent intentBack = new Intent(AdminActivity.this, AdminActivity.class);
    startActivity(intentBack);
    Toast.makeText(getApplicationContext(), "Emails deleted successfully", Toast.LENGTH_SHORT).show();
}

    private void specificDelete(){
        Log.d(TAG, "Deleting Email: ");
        String email = addEmailTxt.getText().toString().trim();

       Boolean res = database.validateEmail(email);
       if (res){
        database.deleteSpecificEmails(email);
        datab.deleteSpecificEmails(email);
        Intent intentBack = new Intent(AdminActivity.this, AdminActivity.class);
        startActivity(intentBack);
        Toast.makeText(getApplicationContext(), "Email deleted successfully: " + email, Toast.LENGTH_SHORT).show();}
       else{
           Toast.makeText(this, "The email doesn't exist", Toast.LENGTH_SHORT).show();
       }
    }
private void viewEmail(){
    Log.d(TAG, "Viewing Emails: started");

    Cursor res = database.getEmail();


    if (res.getCount() == 0){
        showMessage("Error", "No email has been added yet");
        return;
    }

    StringBuffer uffer = new StringBuffer();
    while (res.moveToNext()){

        uffer.append("Email: "+ res.getString(1)+ "\n");
    }
    showMessage("Added Emails", uffer.toString());
}

    private void emailAdd(){
        Log.d(TAG, "Adding Email: ");

        String email = addEmailTxt.getText().toString().trim();

        boolean res = database.validateEmail(email);
            if (email.equals("")){
                Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
        }  else if (res == true){
                Toast.makeText(getApplicationContext(), "The Email already exist", Toast.LENGTH_SHORT).show();
            }
            else{ if (res == false) {
            long value = database.addEmail(email);
            if (value > 0) {
                Intent intentBackToLogin = new Intent(AdminActivity.this, AdminActivity.class);
                startActivity(intentBackToLogin);
                Toast.makeText(getApplicationContext(), "Email successfully added", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "Registration Error", Toast.LENGTH_SHORT).show();
            }


     }}
    }
    private void learnership(){
        Log.d(TAG, "Viewing Learnership programmes attendance: started");

        Cursor res = db.getLearnership();


        if (res.getCount() == 0){
            showMessage("Error", "Nobody has checked in yet");
            return;
        }

        StringBuffer oldbuffer = new StringBuffer();
        while (res.moveToNext()){
            oldbuffer.append("Name: "+ res.getString(1) + "\n");
            oldbuffer.append("Surname: "+ res.getString(2) + "\n");
            oldbuffer.append("Time of attendance: "+ res.getString(3)+ "\n\n");
        }
        showMessage("Learnership Attendance", oldbuffer.toString());
    }
    private void skills(){
        Log.d(TAG, "Viewing skills programmes attendance: started");

        Cursor res = db.getSkills();


        if (res.getCount() == 0){
            showMessage("Error", "Nobody has checked in yet");
            return;
        }

        StringBuffer newbuffer = new StringBuffer();
        while (res.moveToNext()){
            newbuffer.append("Name: "+ res.getString(1) + "\n");
            newbuffer.append("Surname: "+ res.getString(2) + "\n");
            newbuffer.append("Time of attendance: "+ res.getString(3)+ "\n\n");
        }
        showMessage("Skills Attendance", newbuffer.toString());
    }
    private void view(){
        Log.d(TAG, "Viewing attendance: started");

        Cursor res = db.getAttendance();


       if (res.getCount() == 0){
        showMessage("Error", "Nobody has checked in yet");
           return;
       }

       StringBuffer buffer = new StringBuffer();
       while (res.moveToNext()){
//           buffer.append("Id: "+ res.getString(0) + "\n");
           buffer.append("Name: "+ res.getString(1) + "\n");
           buffer.append("Surname: "+ res.getString(2) + "\n");
           buffer.append("Programme: "+ res.getString(4) + "\n");
           buffer.append("Time of attendance: "+ res.getString(3)+ "\n\n");
       }
       showMessage("Attendance", buffer.toString());
    }

    public void export(){

    }

    private void showMessage(String title, String message){
        Log.d(TAG, "Showing Alert dialog: started");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void initialViews() {
        Log.d(TAG, "Admin page: ");

        db = new DatabaseSupporter2(this);
        database = new DatabaseSupporter3(this);
        datab = new DatabaseSupporter(this);

        viewAttendance = findViewById(R.id.viewAttedenceBtn);
        viewSkills = findViewById(R.id.viewSkillsAttendance);
        viewLearnership = findViewById(R.id.viewLearneshipBtn);
        addEmailBtn = findViewById(R.id.addEmailBtn);
        delete = findViewById(R.id.reset);
//        download = findViewById(R.id.downloadBtn);
        logOutBtn = findViewById(R.id.logOutBtn);

        addEmailTxt = findViewById(R.id.addEmailTxt);
        emailBtn = findViewById(R.id.emailBtn);
        deleteEmails = findViewById(R.id.deleteEmails);


    }
}