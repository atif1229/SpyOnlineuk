package com.example.spyonlineuk.activities;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.spyonlineuk.apiConfig.ApiConfig;
import com.example.spyonlineuk.helpers.SessionHelper;
import com.example.spyonlineuk.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spyonlineuk.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private TextInputLayout tilFullName;
    private EditText etFullName;
    private TextInputLayout tilEmail;
    private EditText etEmail;
    private TextInputLayout tilPassword;
    private EditText etPassword;
    private Button btnSignUp;
    private TextInputLayout tilAddress;
    private EditText etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tilFullName = findViewById(R.id.tilFullName);

        etFullName = findViewById(R.id.etFullName);
        tilEmail = findViewById(R.id.tilEmail);
        etEmail = findViewById(R.id.etEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tilAddress = findViewById(R.id.tilFullAddress);
        etAddress = findViewById(R.id.etFullAddress);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tilFullName.setErrorEnabled(false);
                tilEmail.setErrorEnabled(false);
                tilPassword.setErrorEnabled(false);

                String email = etEmail.getText().toString().trim();
                String fullName = etFullName.getText().toString().trim();
                String password = etPassword.getText().toString();
                String address = etAddress.getText().toString().trim();


                if (fullName.isEmpty()) {
                    tilEmail.setError("Email is required");
                    tilEmail.setErrorEnabled(true);
                } else if (email.isEmpty()) {
                    tilFullName.setError("Name is required");
                    tilFullName.setErrorEnabled(true);
                } else if (password.isEmpty()) {
                    tilPassword.setError("Password is required");
                    tilPassword.setErrorEnabled(true);
                } else if (address.isEmpty()) {
                    tilAddress.setError("Address is required");
                    tilAddress.setErrorEnabled(true);
                } else {

                    signUp(fullName, email, password, address);
                }
            }

        });

    }

    private void signUp(final String fullName, final String email, final String password, final String address) {
        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, ApiConfig.SIGN_UP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("mytag", response);
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 0) {
                        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                    JSONObject userObject = jsonObject.getJSONObject("user");

                    User user = new Gson().fromJson(userObject.toString(), User.class);
                    SessionHelper.createSession(SignUpActivity.this, user);
                    finish();

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, R.string.process_error, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(SignUpActivity.this, R.string.volley_error, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("fullName", fullName);
                param.put("email", email);
                param.put("password", password);
                param.put("fullAddress", address);
                return param;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

}
