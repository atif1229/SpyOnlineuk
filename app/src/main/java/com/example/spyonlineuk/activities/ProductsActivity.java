package com.example.spyonlineuk.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.spyonlineuk.adapters.ProductsAdapter;
import com.example.spyonlineuk.apiConfig.ApiConfig;
import com.example.spyonlineuk.fragments.HomeFragment;
import com.example.spyonlineuk.models.Categories;
import com.example.spyonlineuk.models.Products;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.accessibility.AccessibilityManagerCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.style.IconMarginSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spyonlineuk.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    private ArrayList<Products> productsList;
    private ProductsAdapter productsAdapter;
    private ProgressBar progressBar;
    private RecyclerView rvProducts;
    Categories selectedItem;

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


        setContentView(R.layout.activity_products);
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


        rvProducts = findViewById(R.id.rvProducts);
        progressBar = findViewById(R.id.progressBar);

        selectedItem = (Categories) getIntent().getSerializableExtra("categories");
        setTitle(selectedItem.getCatName());
        fetchProductDataFromServer();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.activity_product_action_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.action_camera){
            Intent intent=new Intent(ProductsActivity.this,MainActivity.class);
            item.setIntent(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchProductDataFromServer() {

        StringRequest request = new StringRequest(ApiConfig.PRODUCTS_URL + "?cat_Id=" + selectedItem.getCatId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("mytSizesList", response);
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status == 0) {
                        String message = jsonObject.getString("message");
                        Toast.makeText(ProductsActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray productsArray = jsonObject.getJSONArray("products");

                        Type productType = new TypeToken<ArrayList<Products>>() {
                        }.getType();

                        Gson gson = new Gson();
                        productsList = gson.fromJson(productsArray.toString(), productType);

                        rvProducts.setLayoutManager(new GridLayoutManager(ProductsActivity.this, 2, RecyclerView.VERTICAL, false));
                        rvProducts.setAdapter(new ProductsAdapter(productsList, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Intent intent=new Intent(ProductsActivity.this,ProductsDetailActivity.class);
                                intent.putExtra("products",productsList.get(position));
                                startActivity(intent);
                            }
                        }, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }));

                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                    Toast.makeText(ProductsActivity.this, R.string.process_error, Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ProductsActivity.this, R.string.volley_error, Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }


}
