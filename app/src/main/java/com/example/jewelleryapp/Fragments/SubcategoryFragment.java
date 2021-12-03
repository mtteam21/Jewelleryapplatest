package com.example.jewelleryapp.Fragments;

import static android.view.View.GONE;
import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;
import static com.example.jewelleryapp.Retrofit.ApiUtils.MAIN_URL;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jewelleryapp.Adapters.SliderAdapter;
import com.example.jewelleryapp.Adapters.SubcategoryAdapter;
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


public class SubcategoryFragment extends Fragment implements SubcategoryAdapter.ItemClickListener{

    public SubcategoryFragment() {
        // Required empty public constructor
    }

    private View v;
    private RecyclerView recyclerView;
    private List<Subcategory> subcategoryList;
    private SubcategoryAdapter subcategoryAdapter;
    private int cat_id,subcategory_id;
    private ArrayList<SliderItem> sliderItems = new ArrayList<>();
    private Handler sliderHandler = new Handler();
    private ViewPager2 viewPager2;
    private Activity mActivity;
    private ProgressBar progressBar;
    private String imgPath,cName;
    private ImageView imageView;
    private TextView categoryName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_subcategory, container, false);

        recyclerView = v.findViewById(R.id.subcategoryItemList);
        viewPager2 = v.findViewById(R.id.viewpager);
        viewPager2.setVisibility(GONE);

        progressBar = v.findViewById(R.id.progressBar);
        imageView = v.findViewById(R.id.categoryImage);
        categoryName = v.findViewById(R.id.subCTitle);

        cat_id = getArguments().getInt("cat_id");
        imgPath = getArguments().getString("img");
        cName   = getArguments().getString("name");


        Log.e("IP",MAIN_URL+imgPath+" "+cName);

        categoryName.setText(cName);
        Glide.with(getContext()).load(MAIN_URL+imgPath).into(imageView);

        loadSubCategories(cat_id,this);

        subcategoryAdapter = new SubcategoryAdapter(subcategoryList,this,subcategory_id);
        recyclerView.setAdapter(subcategoryAdapter);

        loadSlider();

        return v;
    }

    private void loadSubCategories(int id, SubcategoryAdapter.ItemClickListener clickListener) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        subcategoryList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("cid",""+String.valueOf(cat_id)+"");

        Log.d("ID",paramObject.toString());
        Call<List<Subcategory>> call = api.getSubcategories(cat_id);

        Log.e("CALL",call.toString());
        call.enqueue(new Callback<List<Subcategory>>() {
            @Override
            public void onResponse(Call<List<Subcategory>> call, Response<List<Subcategory>> response) {
                Log.d("RESULT", new Gson().toJson(response.body()));
                if(response.isSuccessful()) {
                    progressBar.setVisibility(GONE);
                    Log.d("Under_SUCCESS", new Gson().toJson(response.body()));


                    subcategoryList = response.body();
                    subcategoryAdapter = new SubcategoryAdapter(subcategoryList,clickListener,subcategory_id);
                    subcategoryAdapter.notifyDataSetChanged();
                    recyclerView.scheduleLayoutAnimation();
                    recyclerView.setAdapter(subcategoryAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Subcategory>> call, Throwable t) {
                progressBar.setVisibility(GONE);
                Log.e("CALL_RESULT",t.getMessage()) ;
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(Subcategory subcategory,int id) {


        Bundle bundle = new Bundle();
        bundle.putInt("cat_id", id);
        CategorywiseProductsListFragment bookFragment = new CategorywiseProductsListFragment();
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