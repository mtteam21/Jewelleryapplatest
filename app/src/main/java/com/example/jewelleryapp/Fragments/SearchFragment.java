package com.example.jewelleryapp.Fragments;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jewelleryapp.Adapters.ProductAdapter;
import com.example.jewelleryapp.Model.Product;
import com.example.jewelleryapp.R;
import com.example.jewelleryapp.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchFragment extends Fragment implements ProductAdapter.ItemClickListener{

    public SearchFragment() {
        // Required empty public constructor
    }

    private View v;
    private RecyclerView recyclerView;
    private List<Product> productList;
    private ProductAdapter.ItemClickListener itemClickListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v =  inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = v.findViewById(R.id.productRecyclerView);

            productList = new ArrayList<>();
            GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),2);
            recyclerView.setLayoutManager(linearLayoutManager);
            productList = new ArrayList<>();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Specify your api here
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface api = retrofit.create(ApiInterface.class);

            Call<List<Product>> call = api.getAllProducts();

            Log.e("CALL",call.toString());

            call.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    Product res = new Product();
                    Log.e("|||===||||", res.getCategory() + res.getName() + response.raw().toString() + response.body().toString());
                    if(response.isSuccessful()) {
                        productList = response.body();
                        ProductAdapter adapter = new ProductAdapter(productList,itemClickListener);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            ProductAdapter adapter = new ProductAdapter(productList,null);
            recyclerView.setAdapter(adapter);



        return v;
    }

    @Override
    public void onItemClick(Product product) {

    }
}