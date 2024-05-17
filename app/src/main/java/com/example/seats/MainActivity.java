package com.example.seats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.seats.Controller.SessionManager;

public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;

    private Seat_history adapter;
    Button logOut,profile,history;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        logOut=findViewById(R.id.logOut);
        profile=findViewById(R.id.profile);
        history=findViewById(R.id.his_btn);

    queue= Volley.newRequestQueue(this);
    history.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            startActivity(new Intent(MainActivity.this,HistoryActivity.class));
        }
    });
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