package com.example.seats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.seats.Controller.SessionManager;
import com.example.seats.Model.User;

public class ProfilActivity extends AppCompatActivity {
Button logOut;
TextView phone,email,name,id;
String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);
        email = findViewById(R.id.p_email);
        phone = findViewById(R.id.p_phone);
        name = findViewById(R.id.p_name);
        id = findViewById(R.id.p_id);
        logOut=findViewById(R.id.p_log_out);
        User user=SessionManager.getInstance(this).getUserInfo();
        if (user != null) {
            email.setText(user.getUserEmail());
            phone.setText(user.getUserMobileNumber());
            name.setText(user.getUsername());
            id.setText(String.valueOf(user.getId()));
        }
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.getInstance(ProfilActivity.this).userLogout();
                finish();
                startActivity(new Intent(ProfilActivity.this, loginActivity.class));
            }
        });

    }
}