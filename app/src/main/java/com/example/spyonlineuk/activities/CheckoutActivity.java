package com.example.spyonlineuk.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.spyonlineuk.adapters.AddressAdapter;
import com.example.spyonlineuk.apiConfig.ApiConfig;
import com.example.spyonlineuk.helpers.CartHelper;
import com.example.spyonlineuk.helpers.SessionHelper;
import com.example.spyonlineuk.models.Addresses;
import com.example.spyonlineuk.models.CartItem;
import com.example.spyonlineuk.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.spyonlineuk.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    private AddressAdapter addressAdapter;
    private ArrayList<Addresses> addressesList;
    private RecyclerView rvAddresses;
    private User currentUser;
    private RadioButton rdoCash;
    private RadioButton rdoCard;
    private RadioButton rdojazzCash;
    private RadioButton rdoEasyPaisa;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            int flags = getWindow().getDecorView().getSystemUiVisibility();
            flags = flags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            getWindow().getDecorView().setSystemUiVisibility(flags);

            getWindow().setStatusBarColor(Color.WHITE);

        } else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorMine));
        }




        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rdoCard = findViewById(R.id.rdoCard);
        rdoCash = findViewById(R.id.rdoCash);
        rdojazzCash = findViewById(R.id.rdoJazzCash);
        rdoEasyPaisa = findViewById(R.id.rdoEasyPaisa);
        btnConfirm = findViewById(R.id.btnConfirm);


        rvAddresses = findViewById(R.id.rvAddresses);
        currentUser = SessionHelper.getCurrentUser(CheckoutActivity.this);

        rdoCash.setChecked(true);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOrderToServer();
            }
        });
        addressesList = currentUser.getAddresses();
        //aisy issue ki umeed nhi hoti ap say :/
        addressAdapter = new AddressAdapter(addressesList);

        rvAddresses.setAdapter(addressAdapter);
        rvAddresses.setLayoutManager(new LinearLayoutManager(CheckoutActivity.this, RecyclerView.VERTICAL, false));

    }

    private void submitOrderToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(CheckoutActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, ApiConfig.SUBMIT_ORDER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("mytagorderResponse", response);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    //String title = jsonObject.getString("title");
                    if (status == 0) {
                        Toast.makeText(CheckoutActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        new AlertDialog.Builder(CheckoutActivity.this)
                                .setTitle(message)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(CheckoutActivity.this, OrderSubmitted.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        CartHelper cartHelper = new CartHelper(CheckoutActivity.this);
                                        cartHelper.clearCart();
                                    }
                                })
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(CheckoutActivity.this, R.string.process_error, Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(CheckoutActivity.this, R.string.volley_error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("userId", currentUser.getUserId());
                params.put("auth_token", currentUser.getAuthToken());
                Addresses selectedAddress = addressesList.get(addressAdapter.getSelectedAddress());

                String fullAddress = selectedAddress.getFullAddress();
                String nearByLandMark = selectedAddress.getNearByLandMark();
                params.put("full_address", fullAddress + nearByLandMark);

                String paymentMethod = "";

                if (rdoCash.isChecked()) {
                    paymentMethod = "CASH";
                } else if (rdoCard.isChecked()) {
                    paymentMethod = "CARD";
                } else if (rdoEasyPaisa.isChecked()) {
                    paymentMethod = "EASYPAISA";
                } else if (rdojazzCash.isChecked()) {
                    paymentMethod = "JAZZCASH";
                }
                params.put("payment_method", paymentMethod);
                CartHelper cartHelper = new CartHelper(CheckoutActivity.this);
                ArrayList<CartItem> cartItems = cartHelper.getAllCartItem();
                int totalBill = cartHelper.getTotalBill();
                params.put("total_bill", String.valueOf(totalBill));
                params.put("cart", new Gson().toJson(cartItems));
                return params;

            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);

    }


}


