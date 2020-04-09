package com.example.spyonlineuk.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.spyonlineuk.activities.ProductsActivity;
import com.example.spyonlineuk.activities.ProductsDetailActivity;
import com.example.spyonlineuk.adapters.BestSellingAdapter;
import com.example.spyonlineuk.apiConfig.ApiConfig;
import com.example.spyonlineuk.models.Categories;
import com.example.spyonlineuk.adapters.CategoryHomeAdapter;
import com.example.spyonlineuk.models.Products;
import com.example.spyonlineuk.R;
import com.example.spyonlineuk.adapters.RecentlyAddedAdapter;
import com.example.spyonlineuk.models.Slides;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private NestedScrollView nestedScrollView;
    private Button btnTryAgain;
    private TextView tvViewAll;
    private LinearLayout errorContainer;
    private ArrayList<Slides> slidesList;
    private ArrayList<Categories> categoriesList;
    private ArrayList<Products> recentlyAddedList;
    private ArrayList<Products> bestSellingList;

    private ProgressBar progressBar;
    private SliderLayout sliderLayout;
    private Context mContext;
    private RecyclerView rvCategories;
    private RecyclerView rvRecentlyAdded;
    private RecyclerView rvBestSelling;

    BroadcastReceiver connectivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
                if (errorContainer.isShown()) {
                    fetchDataFromServer();

                }
            }

        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        progressBar = view.findViewById(R.id.progressBar);
        sliderLayout = view.findViewById(R.id.slider);
        errorContainer = view.findViewById(R.id.errorLayout);
        rvCategories = view.findViewById(R.id.rvCategories);
        rvRecentlyAdded = view.findViewById(R.id.rvRecentlyAdded);
        rvBestSelling = view.findViewById(R.id.rvBestSelling);

        btnTryAgain = view.findViewById(R.id.btnTryAgain);
        tvViewAll = view.findViewById(R.id.tvViewAll);


        mContext = getContext();

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Top);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);


        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchDataFromServer();
            }
        });

        fetchDataFromServer();
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.registerReceiver(connectivityReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        mContext.unregisterReceiver(connectivityReceiver);
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderLayout.stopAutoCycle();
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderLayout.startAutoCycle();
    }

    private void fetchDataFromServer() {
        progressBar.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
        nestedScrollView.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(ApiConfig.HOME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray slidesArray = jsonObject.getJSONArray("slides");
                    JSONArray categoryArray = jsonObject.getJSONArray("categories");
                    final JSONArray recentAddedArray = jsonObject.getJSONArray("recentlyAdded");
                   JSONArray bestSellingArray = jsonObject.getJSONArray("bestSellers");

                    Type sliderType = new TypeToken<ArrayList<Slides>>() {}.getType();
                    Type categoryType = new TypeToken<ArrayList<Categories>>() {}.getType();
                    Type recentAddedType = new TypeToken<ArrayList<Products>>() {}.getType();
                    Type bestSellingType=new TypeToken<ArrayList<Products>>(){}.getType();

                    Gson gson = new Gson();
                    slidesList = gson.fromJson(slidesArray.toString(), sliderType);
                    categoriesList = gson.fromJson(categoryArray.toString(), categoryType);
                    recentlyAddedList = gson.fromJson(recentAddedArray.toString(), recentAddedType);
                    bestSellingList=gson.fromJson(bestSellingArray.toString(),bestSellingType);


                    for (Slides slides : slidesList) {
                        TextSliderView sliderView = new TextSliderView(mContext);
                        sliderView.image(slides.getSlideImage());
                        sliderView.description(slides.getSlideCaption());


                        sliderLayout.addSlider(sliderView);
                    }


                    rvCategories.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
                    rvCategories.setAdapter(new CategoryHomeAdapter(categoriesList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent intent = new Intent(mContext, ProductsActivity.class);
                            Categories selectedItem = categoriesList.get(position);
                            intent.putExtra("categories", selectedItem);
                            mContext.startActivity(intent);

                        }
                    }));


                    rvRecentlyAdded.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
                    rvRecentlyAdded.setAdapter(new RecentlyAddedAdapter(recentlyAddedList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent intent=new Intent(mContext, ProductsDetailActivity.class);
                            intent.putExtra("products",recentlyAddedList.get(position));
                            startActivity(intent);
                        }
                    }));

                    rvBestSelling.setAdapter(new BestSellingAdapter(bestSellingList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent intent=new Intent(mContext, ProductsDetailActivity.class);
                            intent.putExtra("products",bestSellingList.get(position));
                            startActivity(intent);
                        }
                    }));
                    rvBestSelling.setLayoutManager(new GridLayoutManager(mContext,2,RecyclerView.VERTICAL,false));


                    rvCategories.setNestedScrollingEnabled(false);
                    rvRecentlyAdded.setNestedScrollingEnabled(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, R.string.process_error, Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                error.printStackTrace();
                errorContainer.setVisibility(View.VISIBLE);
                nestedScrollView.setVisibility(View.GONE);

                Toast.makeText(mContext, R.string.volley_error, Toast.LENGTH_SHORT).show();

            }
        });

        Volley.newRequestQueue(mContext.getApplicationContext()).add(request);
    }

}
