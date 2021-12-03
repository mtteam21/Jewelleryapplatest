package com.example.jewelleryapp.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.jewelleryapp.Adapters.ProductAdapter;
import com.example.jewelleryapp.Adapters.ProductSubCategoriwiseAdapter;
import com.example.jewelleryapp.Fragments.FullProductDetailsFragment;
import com.example.jewelleryapp.Model.Product;
import com.example.jewelleryapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductsbycategoryActivity extends AppCompatActivity implements ProductSubCategoriwiseAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        recyclerView = findViewById(R.id.productswithCategoryList);
        fetchProducts(this);

    }

    private void fetchProducts(ProductSubCategoriwiseAdapter.ItemClickListener itemClickListener) {

        productList = new ArrayList<>();
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(linearLayoutManager);

        ProductSubCategoriwiseAdapter productAdapter = new ProductSubCategoriwiseAdapter(productList,this);
        recyclerView.setAdapter(productAdapter);

    }

    @Override
    public void onItemClick(Product product) {
        FullProductDetailsFragment bookFragment = new FullProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("product_name",product.getName());
        bundle.putString("product_price",String.valueOf(product.getPrice()));
        if(productList.get(0).getProductImagesList().size()!=0) {
            bundle.putString("product_img", product.getProductImagesList().get(0).getImgpath());
        }
        bundle.putDouble("product_discounted_price",product.getDiscount());
        bundle.putInt("product_id",product.getId());
        bookFragment.setArguments(bundle);

        AppCompatActivity activity = (AppCompatActivity) getApplicationContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, bookFragment).addToBackStack(null).commit();

    }
}