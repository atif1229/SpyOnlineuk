package com.example.spyonlineuk.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.spyonlineuk.helpers.CartHelper;
import com.example.spyonlineuk.adapters.SizesAdapter;
import com.example.spyonlineuk.models.Products;
import com.example.spyonlineuk.models.Sizes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spyonlineuk.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsDetailActivity extends AppCompatActivity {
    private ImageView ivProductsImage;
    private TextView tvProductName;
    private TextView tvProductDesc;
    private TextView tvProductDetailPrice;
    private ArrayList<Sizes> sizesList;
    private RecyclerView rvSizes;
    private SizesAdapter sizesAdapter;
    private Button btnAddToCart;
    private ImageButton btnAdd;
    private ImageButton btnRemove;
    private TextView tvQuantity;
    Products selectedProducts;
    private CartHelper cartHelper;
    Sizes sizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            int flags = getWindow().getDecorView().getSystemUiVisibility();
            flags = flags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            getWindow().getDecorView().setSystemUiVisibility(flags);

            getWindow().setStatusBarColor(Color.RED);

        } else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorMine));
        }


        setContentView(R.layout.activity_products_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        ivProductsImage = findViewById(R.id.ivProdImage);
        tvProductName = findViewById(R.id.tvProductDetailName);
        tvProductDesc = findViewById(R.id.tvDetailDesc);
        tvProductDetailPrice = findViewById(R.id.tvProductsDetailPrice);
        rvSizes = findViewById(R.id.rvSizes);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);
        tvQuantity = findViewById(R.id.tvQuantity);

        selectedProducts = (Products) getIntent().getSerializableExtra("products");
        sizesList = selectedProducts.getProductSizes();

        Picasso.with(ProductsDetailActivity.this).load(selectedProducts.getProductImage()).into(ivProductsImage);
        tvProductName.setText(selectedProducts.getProductName());
        tvProductDesc.setText(selectedProducts.getProductDetail());
        tvProductDetailPrice.setText("$ " + selectedProducts.getProductPrice() + ".00");

        cartHelper = new CartHelper(ProductsDetailActivity.this);


        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sizesAdapter.getSelectedPosition() == -1) {
                    Toast.makeText(ProductsDetailActivity.this, "plz select a size", Toast.LENGTH_SHORT).show();
                    return;
                }


                Sizes selectedSize = sizesList.get(sizesAdapter.getSelectedPosition());

                boolean flag = cartHelper.isAlreadyAdded(selectedProducts.getProductId(), selectedSize.getSizeId());

                if (flag) {
                    Toast.makeText(ProductsDetailActivity.this, "Product is already exist!!", Toast.LENGTH_SHORT).show();
                    return;
                }


                int quantity = Integer.parseInt(tvQuantity.getText().toString());
                long cartId = cartHelper.addToCart(selectedProducts, selectedSize, quantity);
                if (cartId == -1) {
                    Toast.makeText(ProductsDetailActivity.this, "unable to add products", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductsDetailActivity.this, "product addedd successfully", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(ProductsDetailActivity.this, CartActivity.class);
                //startActivity(intent);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oldQuantity = Integer.parseInt(tvQuantity.getText().toString());
                if (oldQuantity < 10) {
                    int newQuantity = oldQuantity + 1;
                    tvQuantity.setText(String.valueOf(newQuantity));
                }
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oldQty = Integer.parseInt(tvQuantity.getText().toString());
                if (oldQty > 1) {
                    int newQty = oldQty - 1;
                    tvQuantity.setText(String.valueOf(newQty));
                }

            }
        });

        sizesAdapter = new SizesAdapter(sizesList);

        rvSizes.setAdapter(sizesAdapter);
        rvSizes.setLayoutManager(new LinearLayoutManager(ProductsDetailActivity.this, RecyclerView.HORIZONTAL, false));

    }

}
