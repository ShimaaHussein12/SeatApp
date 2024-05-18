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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.seats.Controller.VollySingletone;
import com.example.seats.SERVER.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    EditText emailEditText, nameEditText, passwordEditText, confirmPasswordEditText, phoneEditText, idEditText, nationalIdEditText;
    Button registerButton;
    TextView goToLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Initialize views
        emailEditText = findViewById(R.id.reg_email);
        nameEditText = findViewById(R.id.reg_name);
        passwordEditText = findViewById(R.id.reg_pass);
        confirmPasswordEditText = findViewById(R.id.reg_confirm_pass);
        phoneEditText = findViewById(R.id.reg_phone);
        //idEditText = findViewById(R.id.reg_id);
        nationalIdEditText = findViewById(R.id.reg_id); // Add a new EditText for National ID
        registerButton = findViewById(R.id.reg_btn);
        goToLoginTextView = findViewById(R.id.reg_to_log);

        // Handle click on "Go to Login" text
        goToLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, loginActivity.class));
                finish();
            }
        });

        // Handle register button click
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                //String id = idEditText.getText().toString().trim();
                String nationalId = nationalIdEditText.getText().toString().trim();

                // Validate input fields
                if (TextUtils.isEmpty(name)  || TextUtils.isEmpty(email) ||
                        TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(nationalId)) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Enter a valid email");
                    emailEditText.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    confirmPasswordEditText.setError("Passwords do not match");
                    confirmPasswordEditText.requestFocus();
                    return;
                }
                if (nationalId.length() != 14) {
                    nationalIdEditText.setError("National ID must be 14 digits");
                    nationalIdEditText.requestFocus();
                    return;
                }

                // Perform registration request
                registerUser(name, email, password, phone,  nationalId);
            }
        });
    }

    private void registerUser(String name, String email, String password, String phone, String nationalId) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("UserStatus", true);
            ; // Ensure BusinessUserID is an integer
            jsonBody.put("Username", name);
            jsonBody.put("UserPassword", password);
            jsonBody.put("UserMobileNumber", phone);
            jsonBody.put("UserEmail", email);
            jsonBody.put("UserNationalID", nationalId); // Add National ID to the JSON body

            String requestBody = jsonBody.toString();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.register, new JSONObject(requestBody),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String message = response.getString("message");
                                if (message.equals("success")) {
                                    // Optionally, handle additional data from the response
                                    JSONObject data = response.getJSONObject("data");
                                    String organizationId = data.getString("OrganizationID");
                                    String orgAdminId = data.getString("OrgAdminID");
                                    String permissionId = data.getString("PermissionID");

                                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RegisterActivity.this, loginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration failed: " + message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(RegisterActivity.this, "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                            Log.e("RegistrationError", "Error: " + errorMessage, error);
                        }
                    });

            // Add the request to the RequestQueue
            VollySingletone.getInstance(RegisterActivity.this).AddToequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "JSON creation error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "ID must be a number: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
