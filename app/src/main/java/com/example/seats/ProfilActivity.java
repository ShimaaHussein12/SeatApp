package com.example.seats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seats.Controller.SessionManager;
import com.example.seats.Model.User;

public class ProfilActivity extends AppCompatActivity {
    Button logOut;
    TextView phone, email, name, id;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);

        // Initialize views
        email = findViewById(R.id.p_email);
        phone = findViewById(R.id.p_phone);
        name = findViewById(R.id.p_name);
        id = findViewById(R.id.p_id);
        logOut = findViewById(R.id.p_log_out);

        // Retrieve and display user information
        User user = SessionManager.getInstance(this).getUserInfo();
        if (user != null) {
            email.setText(user.getUserEmail());
            phone.setText(user.getUserMobileNumber());
            name.setText(user.getUsername());
            id.setText(String.valueOf(user.getId()));
        }

        // Handle log out button click
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.getInstance(ProfilActivity.this).userLogout();
                Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear all previous activities
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Go back to MainActivity when the back button is pressed
        super.onBackPressed();
        Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
