package com.example.jewelleryapp.Fragments;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jewelleryapp.Adapters.ProductAdapter;
import com.example.jewelleryapp.Adapters.ProductSubCategoriwiseAdapter;
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

public class ProductFragment extends Fragment implements ProductSubCategoriwiseAdapter.ItemClickListener{


    public ProductFragment() {
        // Required empty public constructor
    }
    private View v;
    private RecyclerView recyclerView;
    private List<Product> productList;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = v.findViewById(R.id.productswithCategoryList);
        progressBar = v.findViewById(R.id.progressBar);

        fetchProducts(this);
        return v;
    }
    private void fetchProducts(ProductSubCategoriwiseAdapter.ItemClickListener itemClickListener) {

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
               progressBar.setVisibility(View.GONE);
                Log.e("|||===||||", res.getCategory() + res.getName() + response.raw().toString() + response.body().toString());
                if(response.isSuccessful()) {
                    Log.e("|||===||||", res.getCategory() + res.getName()+ response.raw().toString() + response.body().toString());
                    productList = response.body();
                    ProductSubCategoriwiseAdapter adapter = new ProductSubCategoriwiseAdapter(productList,itemClickListener);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ProductAdapter adapter = new ProductAdapter(productList,null);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(Product product) {
        FullProductDetailsFragment bookFragment = new FullProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("product_name",product.getName());
        bundle.putString("product_price",String.valueOf(product.getPrice()));
        if(product.getProductImagesList().size()!=0) {
            bundle.putString("product_img", product.getProductImagesList().get(0).getImgpath());
        }
        bundle.putDouble("product_discounted_price",product.getDiscount());
        bundle.putInt("product_id",product.getId());
        bookFragment.setArguments(bundle);
        AppCompatActivity activity = (AppCompatActivity) v.getContext();

        activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, bookFragment).addToBackStack(null).commit();

    }
}