package com.example.spyonlineuk.activities;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.spyonlineuk.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignIn;
    private TextView tvSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);

        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp=findViewById(R.id.tvSignUp);



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tilEmail.setErrorEnabled(false);
                tilPassword.setErrorEnabled(false);
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString();
                if (email.isEmpty()) {

                    tilEmail.setError("Email is required");
                    tilEmail.setErrorEnabled(true);
                } else if (password.isEmpty()) {
                    tilPassword.setError("Password is required");
                    tilPassword.setErrorEnabled(true);
                } else if (etPassword.length() < 4) {
                    etPassword.setError("Password must be at least 4 characters ");
                    tilPassword.setErrorEnabled(true);
                } else {
                    signIn(email, password);
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void signIn(final String email, final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, ApiConfig.SIGN_IN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("mytag",response);
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);

                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    Log.i("mytag",message);
                    if (status == 0) {
                        Toast.makeText(SignInActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                     JSONObject userObject=jsonObject.getJSONObject("user");
                     User user=new Gson().fromJson(userObject.toString(),User.class);
                     SessionHelper.createSession(SignInActivity.this,user);
                     finish();
                    }


                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(SignInActivity.this, R.string.process_error, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(SignInActivity.this, R.string.volley_error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("email", email);
                param.put("password", password);
                return param;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

}
