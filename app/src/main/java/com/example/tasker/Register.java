package com.example.tasker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {

    private static final String TAG = "RegisterActivity"; // Define TAG here

    private FirebaseAuth mAuth;
    private EditText txtEmail, txtPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Register.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
            return; // Return without attempting to register
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        Toast.makeText(Register.this, "Registration successful.",
                                Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthException) {
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            switch (errorCode) {
                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    Toast.makeText(Register.this, "Email is already in use.",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case "ERROR_INVALID_EMAIL":
                                    Toast.makeText(Register.this, "Invalid email address.",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Register.this, "Registration failed. Please try again later.",
                                            Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
