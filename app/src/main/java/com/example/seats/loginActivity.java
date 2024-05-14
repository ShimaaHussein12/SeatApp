package com.example.seats;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.seats.Controller.SessionManager;
import com.example.seats.Controller.VollySingletone;
import com.example.seats.Model.User;
import com.example.seats.SERVER.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {
    EditText email, pass;
    Button log_btn;
    TextView log_to_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.log_email);
        pass = findViewById(R.id.log_pass);
        log_btn = findViewById(R.id.log_btn);
        log_to_reg = findViewById(R.id.logtoreg);
        log_to_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String LOGINemail = email.getText().toString().trim();
                String LOGINpass = pass.getText().toString().trim();
                if (TextUtils.isEmpty(LOGINemail)) {
                    Toast.makeText(loginActivity.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    email.setError("Email Is Required");
                    email.requestFocus();
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(LOGINemail).matches()) {
                    Toast.makeText(loginActivity.this, "Please enter valid email", Toast.LENGTH_LONG).show();
                    email.setError("enter valid email");
                    email.requestFocus();
                }
                if (TextUtils.isEmpty(LOGINpass)) {
                    Toast.makeText(loginActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    pass.setError("Passward Is Required");
                    pass.requestFocus();
                }
                loginUser(LOGINemail, LOGINpass);
            }
        });
    }
    private void loginUser( String email, String password) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("UserPassword", password);
            jsonBody.put("UserEmail", email);

            String requestBody = jsonBody.toString();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.login, new JSONObject(requestBody),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String message = response.getString("message");
                                if (message.equals("success")) {
                                    Toast.makeText(loginActivity.this, "login successful", Toast.LENGTH_LONG).show();

                                    JSONObject userData = response.getJSONObject("data");
                                    String token = response.getString("token");

                                    //JSONObject userJason=response.getJSONObject();
                                  User user=new User(userData.getString("BusinessUserID"),userData.getString("Username"),userData.getString("UserEmail"),token,userData.getString("UserMobileNumber"),userData.getString("UserPassword"));
                                   SessionManager.getInstance(getApplicationContext()).userLogin(user);
                                    finish();
                                    startActivity(new Intent(loginActivity.this, MainActivity.class));

                                } else {
                                    Toast.makeText(loginActivity.this, "login failed: " + message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(loginActivity.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String errorMessage = "Registration failed: ";
                            if (error != null && error.networkResponse != null) {
                                errorMessage += "HTTP Status Code: " + error.networkResponse.statusCode;
                            } else {
                                errorMessage += "Unknown error";
                            }
                            Toast.makeText(loginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                            Log.e("RegistrationError", "Error: " + errorMessage, error);
                        }
                    })

//            {
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("Content-Type", "application/json");
//                    params.put("UserEmail", LOGINemail);
//                    params.put("UserPassword", LOGINpass);
//                    return params;
//                }
//            }


                    ;

            // Add the request to the RequestQueue
            VollySingletone.getInstance(loginActivity.this).AddToequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(loginActivity.this, "JSON creation error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}