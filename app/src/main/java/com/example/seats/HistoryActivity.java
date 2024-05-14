package com.example.seats;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ArrayList<Seat> seats;
   // MarketoDb database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
//        database=MarketoDb.getInstance(getApplicationContext());
//        List<Product> products = database.getAllProducts();
//        RecyclerView recyclerView = findViewById(R.id.rv_availableTrips);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        AvailableProduct adapter = new AvailableProduct(products, this);
//
//        recyclerView.setAdapter(adapter);
    }
}