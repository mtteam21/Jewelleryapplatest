package com.example.jewelleryapp.AllActivities;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droidnet.DroidListener;
import com.example.jewelleryapp.Adapters.ProductAdapter;
import com.example.jewelleryapp.Fragments.CartFragment;
import com.example.jewelleryapp.Fragments.CategoryFragment;
import com.example.jewelleryapp.Fragments.FullProductDetailsFragment;
import com.example.jewelleryapp.Fragments.HomeFragment;
import com.example.jewelleryapp.Fragments.ProfileFragment;
import com.example.jewelleryapp.Fragments.SearchFragment;
import com.example.jewelleryapp.Model.Product;
import com.example.jewelleryapp.R;
import com.example.jewelleryapp.Retrofit.ApiInterface;
import com.example.jewelleryapp.TinyDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, DroidListener,ProductAdapter.ItemClickListener {
    private BottomNavigationView bottomNavigationView;
    private View v;
    private ImageView menuIcon;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View navView;
    private TinyDB tinyDB;
    private EditText searchBar;
    private FrameLayout frameLayout;
    private RecyclerView productRecyclerView;
    private List<Product> productList;
    private ProductAdapter.ItemClickListener itemClickListener;
    private LinearLayoutCompat linearLayoutCompat;
    private ProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         tinyDB = new TinyDB(getApplicationContext());
         initView();

         clickListeners();

         checkNetworkStatus();

         navItemClicks();

         fetchProducts(this);


    }

    private void fetchProducts(ProductAdapter.ItemClickListener onItemClickListener){
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,2);
        productRecyclerView.setLayoutManager(linearLayoutManager);
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
                Log.e("|||===||||", res.getCategory()+ res.getName() + response.raw().toString() + response.body().toString());
                if(response.isSuccessful()) {
                    productList = response.body();
                    adapter = new ProductAdapter(productList,onItemClickListener);
                    productRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new ProductAdapter(productList,onItemClickListener);
        productRecyclerView.setAdapter(adapter);


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


    }

    private void filter(String text) {

        List<Product> filterlist1 = new ArrayList<>();

        for(Product product: productList){
            if(product.getName().toLowerCase().contains(text.toString())){
                filterlist1.add(product);
            }
        }
        adapter.filterList(filterlist1);

    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        v = findViewById(R.id.toolBView);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuIcon = v.findViewById(R.id.menuIcon);
        searchBar = v.findViewById(R.id.searchEditText);
        frameLayout = findViewById(R.id.flFragment);
        productRecyclerView = findViewById(R.id.productRecyclerView);
        linearLayoutCompat = findViewById(R.id.llcompat);

    }


    private void navItemClicks() {

        TextView logOutTextView,tearmsAndConditionsTv,aboutUsTv,contactUsText;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);

        logOutTextView = headerview.findViewById(R.id.textVLogOut);
        tearmsAndConditionsTv = headerview.findViewById(R.id.textView_TandC);
        contactUsText = headerview.findViewById(R.id.textView_ContactUs);
        aboutUsTv = headerview.findViewById(R.id.textViewaboutShop);

        contactUsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DetailsMenuItemActivity.class);
                intent.putExtra("contactUs","0");
                startActivity(intent);
            }
        });
        tearmsAndConditionsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DetailsMenuItemActivity.class);
                intent.putExtra("tc","0");
                startActivity(intent);
            }
        });
        aboutUsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DetailsMenuItemActivity.class);
                intent.putExtra("aboutUs","0");
                startActivity(intent);
            }
        });

        logOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tinyDB!=null) {
                    tinyDB.putBoolean("login", false);
                    tinyDB.putString("username","");
                    tinyDB.putString("email","");
                    tinyDB.putString("password","");
                    tinyDB.putString("mobile","");
                    tinyDB.putBoolean("login",false);
                    Toast.makeText(getApplicationContext(), "LogOut Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }
            }
        });

        linearLayoutCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.setVisibility(View.GONE);
                productRecyclerView.setVisibility(View.VISIBLE);
                searchBar.setFocusable(true);
            }
        });
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 frameLayout.setVisibility(View.GONE);
                 productRecyclerView.setVisibility(View.VISIBLE);
                searchBar.setFocusable(true);

            }
        });


    }

    private void checkNetworkStatus() {


    }

    private void clickListeners() {
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!drawerLayout.isDrawerOpen(navigationView)){
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }


    HomeFragment homeFragment = new HomeFragment();
    CategoryFragment productFragment = new CategoryFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    CartFragment cartFragment = new CartFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                return true;

            case R.id.products:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, productFragment).commit();
                return true;

            case R.id.userProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, profileFragment).commit();
                return true;

            case R.id.cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, cartFragment).commit();
                return true;
        }
        return false;
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if(!isConnected) {
            Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Internet Available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Product product) {
        FullProductDetailsFragment bookFragment = new FullProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("product_name",product.getName());
        bundle.putString("product_price",String.valueOf(product.getPrice()));
//        bundle.putString("product_img",product.getImgpath());
        bundle.putDouble("product_discounted_price",product.getDiscount());
        bundle.putInt("product_id",product.getId());
        bookFragment.setArguments(bundle);
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, bookFragment).addToBackStack(null).commit();
        productRecyclerView.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
    }
}