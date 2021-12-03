package com.example.jewelleryapp.Fragments;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jewelleryapp.Adapters.CartAdapter;
import com.example.jewelleryapp.Adapters.CategoryAdapter;
import com.example.jewelleryapp.Adapters.ProductAdapter;
import com.example.jewelleryapp.Adapters.SliderAdapter;
import com.example.jewelleryapp.Model.CartProduct;
import com.example.jewelleryapp.Model.CategoryData;
import com.example.jewelleryapp.Model.Product;
import com.example.jewelleryapp.Model.ProductCategory;
import com.example.jewelleryapp.Model.SliderItem;
import com.example.jewelleryapp.R;
import com.example.jewelleryapp.Retrofit.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryFragment extends Fragment implements CategoryAdapter.ItemClickListener{

    private View v;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private List<CategoryData> categoriesList;
    private int idc;
    private ArrayList<SliderItem> sliderItems = new ArrayList<>();
    private Handler sliderHandler = new Handler();
    private ViewPager2 viewPager2;
    private Activity mActivity;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         v = inflater.inflate(R.layout.fragment_category, container, false);
         recyclerView = v.findViewById(R.id.productCategoryRecyclerView);
        viewPager2 = v.findViewById(R.id.viewpager);
        viewPager2.setVisibility(View.GONE);
        nestedScrollView = v.findViewById(R.id.scrollViewPage);
        progressBar = v.findViewById(R.id.progressBar);
        nestedScrollView.setVisibility(View.GONE);

         fetchProductsCategories(this);
         loadSlider();
        categoryAdapter = new CategoryAdapter(categoriesList,this,idc);
        recyclerView.setAdapter(categoryAdapter);
        // Inflate the layout for this fragment
        return v;
    }

    private void fetchProductsCategories(CategoryAdapter.ItemClickListener clickListener) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(v.getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        categoriesList = new ArrayList<>();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Specify your api here
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface api = retrofit.create(ApiInterface.class);

            Call<List<CategoryData>> call = api.getCategoriesData();

            call.enqueue(new Callback<List<CategoryData>>() {
                @Override
                public void onResponse(Call<List<CategoryData>> call, Response<List<CategoryData>> response) {
                    progressBar.setVisibility(View.GONE);
                    nestedScrollView.setVisibility(View.VISIBLE);
                    if(response.isSuccessful()) {
                        categoriesList = response.body();

                        categoryAdapter = new CategoryAdapter(categoriesList,clickListener,idc);
                        categoryAdapter.notifyDataSetChanged();
                        recyclerView.scheduleLayoutAnimation();
                        recyclerView.setAdapter(categoryAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<CategoryData>> call, Throwable t) {

                    Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    @Override
    public void OnItemClick(CategoryData productCategory,int id) {
        idc = id;
        Bundle bundle = new Bundle();
        bundle.putInt("cat_id", id);
        bundle.putString("img",productCategory.getImgPath());
        bundle.putString("name",productCategory.getName());
        SubcategoryFragment bookFragment = new SubcategoryFragment();
        bookFragment.setArguments(bundle);
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
                    progressBar.setVisibility(View.GONE);
                    nestedScrollView.setVisibility(View.VISIBLE);
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
