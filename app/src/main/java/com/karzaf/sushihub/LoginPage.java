package com.karzaf.sushihub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        dbHelper = DatabaseHelper.getInstance(this);

        etUsername = findViewById(R.id.LoginUsername);
        etPassword = findViewById(R.id.LoginPassword);

        Button backButton = findViewById(R.id.ButtonBackPage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button adminButton = findViewById(R.id.ButtonAdminPage);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginPage.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (dbHelper.checkLogin(username, password)) {
                    Toast.makeText(LoginPage.this, "Welcome Admin!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginPage.this, AdminPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginPage.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button guestButton = findViewById(R.id.ButtonGuestPage);
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guestIntent = new Intent(LoginPage.this, GuestPage.class);
                startActivity(guestIntent);
            }
        });
    }
}
