package com.example.seats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.seats.Controller.SessionManager;

public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    Button logOut,profile;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        logOut=findViewById(R.id.logOut);
        profile=findViewById(R.id.profile);
    queue= Volley.newRequestQueue(this);
    profile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            startActivity(new Intent(MainActivity.this,ProfilActivity.class));
        }
    });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.getInstance(MainActivity.this).userLogout();
                finish();
                startActivity(new Intent(MainActivity.this, loginActivity.class));
            }
        });
        if(SessionManager.getInstance(this).isLoggedIn()){
            if(SessionManager.getInstance(this).getToken()!=null){
                token=SessionManager.getInstance(this).getToken().getToken();

            }

        }
        else {
            finish();
            startActivity(new Intent(this, loginActivity.class ));
            return;
        }
    }
}