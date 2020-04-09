package com.example.spyonlineuk.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.spyonlineuk.helpers.CartHelper;
import com.example.spyonlineuk.adapters.CartAdapter;
import com.example.spyonlineuk.helpers.SessionHelper;
import com.example.spyonlineuk.models.CartItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spyonlineuk.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ArrayList<CartItem> cartItemsList;
    private CartAdapter cartAdapter;
    private RecyclerView rvCartItem;
    private CartHelper cartHelper;
    private LinearLayout cartErrorLayout;
    private TextView tvTotalBill;
    private RelativeLayout checkoutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=23){
            int flags=getWindow().getDecorView().getSystemUiVisibility();
            flags = flags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            getWindow().getDecorView().setSystemUiVisibility(flags);

            getWindow().setStatusBarColor(Color.WHITE);

        }

        else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorMine));
        }


        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        cartHelper = new CartHelper(CartActivity.this);
        rvCartItem = findViewById(R.id.rvCartItem);
        cartErrorLayout = findViewById(R.id.cartErrorLayout);
        checkoutLayout = findViewById(R.id.checkoutLayout);
        tvTotalBill = findViewById(R.id.tvTotalBill);
        checkoutLayout = findViewById(R.id.checkoutLayout);

        cartItemsList = cartHelper.getAllCartItem();
        displayTotalBill();

        checkoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SessionHelper.isUserLoggedIn(CartActivity.this)) {
                    Intent intent=new Intent(CartActivity.this,CheckoutActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent=new Intent(CartActivity.this,SignInActivity.class);
                    startActivity(intent);
                }
            }

        });
        rvCartItem.setLayoutManager(new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false));
        cartAdapter = new CartAdapter(cartItemsList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                CartItem cartItem = cartItemsList.get(position);
                int oldQty = cartItem.getQuantity();
                if (oldQty < 10) {
                    int newQty = oldQty + 1;
                    int affectedRows = cartHelper.updateItemIntoCart(cartItem.getCartId(), newQty);
                    if (affectedRows > 0) {
                        cartItemsList.clear();
                        cartItemsList.addAll(cartHelper.getAllCartItem());
                        cartAdapter.notifyItemChanged(position);
                    }
                    displayTotalBill();
                }
            }
        }, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CartItem cartItem = cartItemsList.get(position);
                int cartId = cartItem.getCartId();
                int oldQty = cartItem.getQuantity();
                if (oldQty > 1) {
                    int newQty = oldQty - 1;
                    int affectedRows = cartHelper.updateItemIntoCart(cartId, newQty);
                    if (affectedRows > 0) {
                        cartItemsList.clear();
                        cartItemsList.addAll(cartHelper.getAllCartItem());
                        cartAdapter.notifyItemChanged(position);
                    }
                    displayTotalBill();
                }
            }
        }, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == -1) {
                    return;
                }
                int cartId = cartItemsList.get(position).getCartId();
                int numberOfDeletedRows = cartHelper.deleteItemFromCart(cartId);
                if (numberOfDeletedRows > 0) {
                    cartItemsList.clear();
                    cartItemsList.addAll(cartHelper.getAllCartItem());
                    cartAdapter.notifyItemRemoved(position);

                    if (cartItemsList.size() == 0) {
                        cartErrorLayout.setVisibility(View.VISIBLE);
                        checkoutLayout.setVisibility(View.GONE);
                    }
                }
                displayTotalBill();

            }
        });
        rvCartItem.setAdapter(cartAdapter);


    }


    public void displayTotalBill() {
        int totalBill = cartHelper.getTotalBill();
        NumberFormat nf = new DecimalFormat("#,###");
        tvTotalBill.setText("$ " + nf.format(totalBill));
    }


}
