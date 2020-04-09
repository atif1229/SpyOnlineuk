package com.example.spyonlineuk.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.spyonlineuk.activities.ProductsActivity;
import com.example.spyonlineuk.models.Categories;
import com.example.spyonlineuk.adapters.CategoryAdapter;
import com.example.spyonlineuk.R;
import com.example.spyonlineuk.models.Products;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    private RecyclerView rvCategories;
    private ArrayList<Categories> categoriesList;
    private String link = "http://atif.androidlabz.com/spyshop/category.php";
    private Context mContext;


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCategories = view.findViewById(R.id.rvCategories);
        mContext = getContext();
        StringRequest request = new StringRequest(link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray categoryArray = jsonObject.getJSONArray("categories");

                    Type catListType = new TypeToken<ArrayList<Categories>>() {
                    }.getType();

                    Gson gson = new Gson();

                    categoriesList = gson.fromJson(categoryArray.toString(), catListType);

                    rvCategories.setAdapter(new CategoryAdapter(categoriesList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                            Intent intent =new Intent(mContext, ProductsActivity.class);
                            //Categories catList=categoriesList.get(position);
                            intent.putExtra("categories",categoriesList.get(position));
                            mContext.startActivity(intent);
                        }
                    }));
                    rvCategories.setLayoutManager(new GridLayoutManager(mContext, 2, RecyclerView.VERTICAL, false));


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "unable to process data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(mContext, "unable to fetch data from server ", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(mContext).add(request);

    }


}

