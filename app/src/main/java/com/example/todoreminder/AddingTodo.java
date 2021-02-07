package com.example.todoreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddingTodo extends AppCompatActivity implements View.OnClickListener {
    private EditText todoTitle;
    private EditText thisngtodo;
    private FirebaseFirestore firebasefirestore;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_todo);
        todoTitle = findViewById(R.id.todoTitle);
        thisngtodo = findViewById(R.id.todo);
        dateButton = findViewById(R.id.date);

        firebasefirestore = FirebaseFirestore.getInstance();
        dateButton.setOnClickListener(this);

        initDatePicker();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.backItem){
            Log.d("GB", "Go Back");
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);

        }else if ((item.getItemId() == R.id.addItem)) {
            String title = todoTitle.getText().toString();
            String todo = thisngtodo.getText().toString();
            String date = dateButton.getText().toString();
            TODO pr = new TODO(title, todo, date);
            if (title.isEmpty() || todo.isEmpty() || date.equals("Click here to Set Date")) {
                Toast.makeText(this,"Please Fill All Fields", Toast.LENGTH_LONG).show();

            } else {
                firebasefirestore.collection("TODO")
                        .add(pr)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.w("TODO", "Todo has been added successfully");
                                Toast.makeText(AddingTodo.this, "Todo added successfully", Toast.LENGTH_LONG).show();




                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddingTodo.this, "Failed to add todo", Toast.LENGTH_SHORT).show();

                            }
                        });


            }
        }


        return super.onOptionsItemSelected(item);
    }


    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";
    }



    @Override
    public void onClick(View v) {
        datePickerDialog.show();

    }
}