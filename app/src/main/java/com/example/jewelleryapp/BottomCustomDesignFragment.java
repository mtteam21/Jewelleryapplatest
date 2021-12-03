package com.example.jewelleryapp;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.Notification;
import android.app.admin.FactoryResetProtectionPolicy;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.jewelleryapp.AllActivities.MainActivity;
import com.example.jewelleryapp.CustmizedProjects.Models.CustomD_Model;
import com.example.jewelleryapp.CustmizedProjects.Models.CustomDesItemsAdapter;
import com.example.jewelleryapp.CustmizedProjects.Models.CustomDesignAdapter;
import com.example.jewelleryapp.CustmizedProjects.Models.P_SpecificationModel;
import com.example.jewelleryapp.Fragments.CartFragment;
import com.example.jewelleryapp.Fragments.FullProductDetailsFragment;
import com.example.jewelleryapp.Fragments.SubcategoryFragment;
import com.example.jewelleryapp.Model.StoreVariantsDetails;
import com.example.jewelleryapp.Retrofit.ApiInterface;
import com.example.jewelleryapp.Retrofit.Res;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BottomCustomDesignFragment extends BottomSheetDialogFragment implements CustomDesItemsAdapter.ItemClickListener{

    public static BottomCustomDesignFragment newInstance() {
        return new BottomCustomDesignFragment();
    }

    public static final ArrayList<StoreVariantsDetails> listItem = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private List<P_SpecificationModel> p_specificationModelsList;
    private List<CustomD_Model> customD_modelsList;
    private RecyclerView recyclerView;
    private View v;
    private CardView increaseBtn,decreaseBtn,applyCardView,cancelCardView;
    private TextView quantityTextView,productNameTextView;
    private CustomDesItemsAdapter.ItemClickListener itemClickListener;
    private int productQuantity,productId,mainVid,selectedOptionsId;
    private String productName, productImg;
    private TinyDB tinyDB;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_bottom_custom_design, container, false);
        initIds();
        loadItems();
        productQuantity();
        onClickListeners();
        itemClickListener = this;
        tinyDB = new TinyDB(getContext());

        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
        if(getArguments()!=null) {

            productName  = getArguments().getString("product_name");
            productImg =  getArguments().getString("product_img");
            productNameTextView.setText(productName);

            Log.e("pimg",
                    String.valueOf(getArguments().getInt("product_id")
                    +"\n"+getArguments().getString("product_name")+
                    "\n"+getArguments().getString("product_img")));
        }

        return v;
    }

    private void onClickListeners() {

        applyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartFragment cartFragment = new CartFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("product_id",productId);
                bundle.putString("product_name",productName);
                bundle.putString("product_img",productImg);
                bundle.putString("qty",quantityTextView.getText().toString());

                cartFragment.setArguments(bundle);


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,cartFragment).addToBackStack(null).commit();



                getActivity().getSupportFragmentManager().beginTransaction().remove(BottomCustomDesignFragment.this).commit();

            }
        });

        cancelCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(BottomCustomDesignFragment.this).commit();
            }
        });
    }

    private void loadItems() {

        if(getArguments()!=null){
            productId = getArguments().getInt("product_id");
            Log.e("LLL",String.valueOf(productId));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        customD_modelsList = new ArrayList<>();

        Call<ArrayList<CustomD_Model>> call = api.getProductVariants(productId);

        call.enqueue(new Callback<ArrayList<CustomD_Model>>() {
            @Override
            public void onResponse(Call<ArrayList<CustomD_Model>> call, Response<ArrayList<CustomD_Model>> response) {
                if(response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    customD_modelsList = response.body();
                    CustomDesignAdapter customDesItemsAdapter = new CustomDesignAdapter(customD_modelsList,p_specificationModelsList,0,itemClickListener);
                    recyclerView.setAdapter(customDesItemsAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CustomD_Model>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initIds() {
        increaseBtn      = v.findViewById(R.id.increaseQuantity);
        decreaseBtn      = v.findViewById(R.id.decreaseQuantity);
        quantityTextView = v.findViewById(R.id.quantityTextView);
        recyclerView     = v.findViewById(R.id.changePrecycler);
        applyCardView = v.findViewById(R.id.applyCardView);
        cancelCardView = v.findViewById(R.id.cancelCardView);
        productNameTextView = v.findViewById(R.id.productNameTextView);
        quantityTextView.setText("1");
        linearLayout = v.findViewById(R.id.bottomsItem);
        progressBar =  v.findViewById(R.id.progressBar);
    }

    private void productQuantity() {
        productQuantity = 1;
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(quantityTextView.getText().toString()) <= 12) {
                    productQuantity++;
                    quantityTextView.setText(String.valueOf(productQuantity));
                }
            }
        });

        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(quantityTextView.getText().toString()) >= 1) {
                    productQuantity--;
                    quantityTextView.setText(String.valueOf(productQuantity));
                }
            }
        });
    }

    @Override
    public void OnItemClick(int idslist, int selectedItemPos) {
        mainVid = idslist;
        selectedOptionsId = selectedItemPos;

        int UID = tinyDB.getInt("id");

        listItem.add(new StoreVariantsDetails(String.valueOf(productId)
                ,"0",String.valueOf(UID),String.valueOf(idslist)+","+String.valueOf(selectedItemPos)));

        Log.e("DatId",String.valueOf(idslist+","+selectedItemPos));
    }



        public void showDialog(Activity activity, String msg){

            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialogbox);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            LottieAnimationView animationView = dialog.findViewById(R.id.animationView);

            animationView.setAnimation(R.raw.success);

                    TextView text = (TextView) dialog.findViewById(R.id.messageTextView);
            text.setText(msg);

            dialog.show();
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    startActivity(new Intent(v.getContext(), MainActivity.class));
                    //Do something after 100ms
                }
            }, 3000);
        }
}