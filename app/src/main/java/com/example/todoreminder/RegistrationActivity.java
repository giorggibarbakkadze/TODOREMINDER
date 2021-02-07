package com.example.todoreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Sign Up" ;
    private EditText registerEmail;
    private EditText registerPassword;
    private EditText confirmPassword;
    private Button signupBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPass);
        confirmPassword = findViewById(R.id.registerConfPass);
        signupBtn = findViewById(R.id.signupBtn);
        mAuth = FirebaseAuth.getInstance();
        signupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email =  registerEmail.getText().toString();
        String password = registerPassword.getText().toString();
        String password2 = confirmPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty() || password2.isEmpty()){
            Toast.makeText(this,"Please fill in all fields", Toast.LENGTH_LONG).show();
        } else if (!password.equals(password2)){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
        } else if (password.length() < 6){
            Toast.makeText(this,"Password must contain more than 6 characters", Toast.LENGTH_LONG).show();

        }
        else {
            Intent i = new Intent(this, MainActivity.class);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(i);
                                Toast.makeText(RegistrationActivity.this, "Authenticated successfully", Toast.LENGTH_LONG).show();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }




    }
    private void updateUI(FirebaseUser user) {
        if(user == null){
            Log.d("Acc", "is not signed in");
        } else {
            Log.d("acc", "is signed in " + user.getEmail() );
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.back_menu, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.backToLoginScreen){
//            Intent intent = new Intent(this, LogInAtctivity.class);
//            startActivity(intent);
//
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
}