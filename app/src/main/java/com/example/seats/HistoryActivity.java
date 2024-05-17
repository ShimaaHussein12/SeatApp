package com.example.seats;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.seats.Controller.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Seat_history tripAdapter;
    private List<Seat> tripList;

    private SessionManager sessionManager;

   // MarketoDb database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.rv_seatHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripList = new ArrayList<>();
        tripAdapter = new Seat_history(tripList);
        recyclerView.setAdapter(tripAdapter);
        sessionManager = SessionManager.getInstance(this);
        String url = "https://seated-booking.onrender.com/ticket/history";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String token = sessionManager.getToken().getToken();
        //Toast.makeText(HistoryActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("data");
                            tripList.clear();

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject tripObject = dataArray.getJSONObject(i);
                                String ticketId = tripObject.getString("ticket_id");
                                String tripId = tripObject.getString("trip_id");
                                String boardingStation = tripObject.getString("boarding_station");
                                String destinationStation = tripObject.getString("destination_station");
                                String tripStartDate = tripObject.getString("trip_start_date");
                                String tripEndDate = tripObject.getString("trip_end_date");
                                int ticketsCount = tripObject.getInt("tickets_count");
                                int totalPrice = tripObject.getInt("total_price");

                                Seat trip = new Seat( boardingStation, destinationStation, tripStartDate, tripEndDate, ticketsCount, totalPrice);
                                tripList.add(trip);
                            }

                            tripAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
//        adapter = new Seat_history(tripList);
//        recyclerView.setAdapter(adapter);
//        database=MarketoDb.getInstance(getApplicationContext());
//        List<Product> products = database.getAllProducts();
//        RecyclerView recyclerView = findViewById(R.id.rv_availableTrips);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        AvailableProduct adapter = new AvailableProduct(products, this);
//
//        recyclerView.setAdapter(adapter);
    }
