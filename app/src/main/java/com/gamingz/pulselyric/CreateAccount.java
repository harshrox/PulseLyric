package com.gamingz.pulselyric;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccount extends AppCompatActivity {
    TextView alreadyAccount;
    EditText email, password, confirmPassword;
    Button login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        alreadyAccount=findViewById(R.id.alreadyAccount);

        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAccount.this, LoginAccount.class));
            }
        });

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirmPassword);
        login = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perforAuth();
            }
        });
    }

    private void perforAuth() {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String ConfirmPassword = confirmPassword.getText().toString();

        if (!Email.matches(emailPattern)) {
            email.setError("Enter correct email");
        } else if (Password.isEmpty() || Password.length() < 6) {
            password.setError("Enter proper password");
        } else if (!Password.equals(ConfirmPassword)) {
            confirmPassword.setError("Password did not match");
        } else {
            progressDialog.setMessage("Registering...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(CreateAccount.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(CreateAccount.this, "User already exits", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    private void sendUserToNextActivity(){
        Intent intent=new Intent(CreateAccount.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}