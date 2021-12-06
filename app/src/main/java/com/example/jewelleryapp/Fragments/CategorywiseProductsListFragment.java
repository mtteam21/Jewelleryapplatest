package com.example.jewelleryapp.Fragments;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jewelleryapp.Adapters.ChildCategoryAdapter;
import com.example.jewelleryapp.Adapters.SliderAdapter;
import com.example.jewelleryapp.Adapters.SubcategoryAdapter;
import com.example.jewelleryapp.Model.ChildCategoryList;
import com.example.jewelleryapp.Model.SliderItem;
import com.example.jewelleryapp.Model.Subcategory;
import com.example.jewelleryapp.R;
import com.example.jewelleryapp.Retrofit.ApiInterface;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CategorywiseProductsListFragment extends Fragment implements ChildCategoryAdapter.ItemClickListener{

    private View v;
    private RecyclerView recyclerView;
    private List<ChildCategoryList> childCategoryLists;
    private ArrayList<SliderItem> sliderItems = new ArrayList<>();
    private Handler sliderHandler = new Handler();
    private ViewPager2 viewPager2;
    private Activity mActivity;
    private int c;
    private TextView subcNameTv;
    public CategorywiseProductsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_categorywise_product_list, container, false);

        recyclerView = v.findViewById(R.id.categoriwiseitemsList);
        viewPager2 = v.findViewById(R.id.viewpager);
        viewPager2.setVisibility(View.GONE);
        subcNameTv = v.findViewById(R.id.subcNameViewTextV);



        fetchProductsList(this);

        loadSlider();
        // Inflate the layout for this fragment
        return v;
    }

    private void fetchProductsList(ChildCategoryAdapter.ItemClickListener onItemClickListener) {
        c = getArguments().getInt("cat_id");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        childCategoryLists = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

            Call<List<ChildCategoryList>> call = api.fetchChildCategories(c);

            Log.e("CALL",call.toString());
            call.enqueue(new Callback<List<ChildCategoryList>>() {
                @Override
                public void onResponse(Call<List<ChildCategoryList>> call, Response<List<ChildCategoryList>> response) {
                    Log.d("RESULT", new Gson().toJson(response.body()));
                    if(response.isSuccessful()) {
                        Log.d("Under_SUCCESS", new Gson().toJson(response.body()));
                        childCategoryLists = response.body();
                        ChildCategoryAdapter productNamesAdapter = new ChildCategoryAdapter(childCategoryLists,onItemClickListener);
                        recyclerView.setAdapter(productNamesAdapter);
                    }
                }
                @Override
                public void onFailure(Call<List<ChildCategoryList>> call, Throwable t) {
                    Log.e("CALL_RESULT",t.getMessage()) ;
                    Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public void onItemClick(ChildCategoryList productsList) {
        ProductFragment bookFragment = new ProductFragment();
        subcNameTv.setText(productsList.getChildccame());
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, bookFragment).addToBackStack(null).commit();
    }

    private void loadSlider() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<ArrayList<SliderItem>> call = api.getAllSliders();

        call.enqueue(new Callback<ArrayList<SliderItem>>() {
            @Override
            public void onResponse(Call<ArrayList<SliderItem>> call, Response<ArrayList<SliderItem>> response) {
                if (response.isSuccessful()) {
                    viewPager2.setVisibility(View.VISIBLE);
                    ArrayList<SliderItem> sliderItems = response.body();
                    Log.e("TAG", response.message().toString());
                    viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
                    viewPager2.setClipToPadding(false);
                    viewPager2.setClipChildren(false);
                    viewPager2.setOffscreenPageLimit(3);
                    viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                    CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                    compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                    compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                        @Override
                        public void transformPage(@NonNull View page, float position) {
                            float r = 1 - Math.abs(position);
                            page.setScaleY(0.85f + r * 0.15f);
                        }
                    });

                    viewPager2.setPageTransformer(compositePageTransformer);

                    viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            sliderHandler.removeCallbacks(sliderRunnable);
                            sliderHandler.postDelayed(sliderRunnable, 4000); // slide duration 2 seconds
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SliderItem>> call, Throwable t) {

            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 1000);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
}