package com.example.jewelleryapp.Fragments;

import static com.example.jewelleryapp.BottomCustomDesignFragment.listItem;
import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.jewelleryapp.Adapters.AddressAdapter;
import com.example.jewelleryapp.Adapters.CartAdapter;
import com.example.jewelleryapp.Adapters.CategoryAdapter;
import com.example.jewelleryapp.AllActivities.MainActivity;
import com.example.jewelleryapp.Model.AddressModel;
import com.example.jewelleryapp.Model.CartProduct;
import com.example.jewelleryapp.Model.ProductCategory;
import com.example.jewelleryapp.Model.StoreOrder;
import com.example.jewelleryapp.Model.StoreVariantsDetails;
import com.example.jewelleryapp.R;
import com.example.jewelleryapp.Retrofit.ApiInterface;
import com.example.jewelleryapp.Retrofit.Res;
import com.example.jewelleryapp.TinyDB;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CartFragment extends Fragment {
    private View v;
    private RecyclerView recyclerView;
    private CartAdapter categoryAdapter;
    private List<CartProduct> categoriesList;
    private CardView checkOutCard,changeAddress;
    private int productId;
    private String qty;
    private String productName;
    private String productImg;
    private String addressId="0";
    private ArrayList<AddressModel> addressModelsList;
    private AddressModel addressModel;
    private TinyDB tinyDB;
    private TextView placeOrderTextView;
    private int uId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_cart, container, false);

        findViewByIds();

        fetchProductsCartProduct();

        clickListeners();

        tinyDB = new TinyDB(v.getContext());

        return v;
    }

    private void findViewByIds() {
        checkOutCard = v.findViewById(R.id.checkOutCardView);
        changeAddress = v.findViewById(R.id.changeAddressCard);
        recyclerView = v.findViewById(R.id.cartProductRecyclerView);
        placeOrderTextView = v.findViewById(R.id.placeOrderTextView);

        if(getArguments()!=null){
            productId = getArguments().getInt("product_id");
            addressId = getArguments().getString("aid");
            productName = getArguments().getString("product_name");
            productImg = getArguments().getString("product_img");

                qty = getArguments().getString("qty");

        }

        try {
            if (!addressId.equals("0")) {
                placeOrderTextView.setText("Place Order");
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void clickListeners() {



        checkOutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        if (placeOrderTextView.getText().toString().equals("Next") && listItem.size()!=00) {
                            AddressFragment bookFragment = new AddressFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("product_id", productId);
                            bundle.putString("product_name", productName);
                            bundle.putString("product_img", productImg);
                            bundle.putString("qty", String.valueOf(qty));
                            bookFragment.setArguments(bundle);
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, bookFragment).addToBackStack(null).commit();
                        } else {
                            if (listItem.size() != 0) {

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(BASE_URL) // Specify your api here
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                ApiInterface api1 = retrofit.create(ApiInterface.class);

                                uId = tinyDB.getInt("id");
                                qty = getArguments().getString("qty");

                                Call<ArrayList<AddressModel>> getAddressesList = api1.getAddressList(uId);

                                getAddressesList.enqueue(new Callback<ArrayList<AddressModel>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<AddressModel>> call, Response<ArrayList<AddressModel>> response) {
                                        if (response.isSuccessful()) {
                                            addressModelsList = response.body();

                                            if (addressId == null) {
                                                if (addressModelsList.size() > 1) {
                                                    Toast.makeText(v.getContext(), "Please Select Address", Toast.LENGTH_SHORT).show();
                                                    AddressFragment bookFragment = new AddressFragment();
                                                    Bundle bundle = new Bundle();
                                                    bundle.putInt("product_id", productId);
                                                    bundle.putString("product_name", productName);
                                                    bundle.putString("product_img", productImg);
                                                    bundle.putString("qty", String.valueOf(qty));
                                                    bookFragment.setArguments(bundle);
                                                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, bookFragment).addToBackStack(null).commit();
                                                }

                                            }

                                            if (addressModelsList.size() == 0 && addressId.equals("0") && addressId == null) {
                                                Toast.makeText(v.getContext(), "Please Add Address", Toast.LENGTH_SHORT).show();
                                                AddAddressFragment bookFragment = new AddAddressFragment();
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("product_id", productId);
                                                bundle.putString("product_name", productName);
                                                bundle.putString("product_img", productImg);
                                                bundle.putString("qty", String.valueOf(qty));
                                                bookFragment.setArguments(bundle);
                                                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                                activity.getSupportFragmentManager().beginTransaction().add(R.id.flFragment, bookFragment).addToBackStack(null).commit();
                                            } else if
                                            (addressModelsList.size() > 1 && addressId == null) {
                                                Toast.makeText(v.getContext(), "Please Select Address", Toast.LENGTH_SHORT).show();
                                                AddressFragment bookFragment = new AddressFragment();
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("product_id", productId);
                                                bundle.putString("product_name", productName);
                                                bundle.putString("product_img", productImg);
                                                bundle.putString("qty", String.valueOf(qty));
                                                bookFragment.setArguments(bundle);
                                                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, bookFragment).addToBackStack(null).commit();
                                            } else {
                                                startOrder();
                                            }

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<AddressModel>> call, Throwable t) {

                                    }
                                });
                            }
                            if (listItem.size() == 0) {
                                Toast.makeText(v.getContext(), "Don't Have Any Product In Your Cart", Toast.LENGTH_SHORT).show();
                            }
                        }

                }
            });
        if(listItem.size()!=0) {
            changeAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listItem.size() != 0) {
                        AddressFragment bookFragment = new AddressFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("product_id", productId);
                        bundle.putString("product_name", productName);
                        bundle.putString("product_img", productImg);
                        bundle.putString("qty", String.valueOf(qty));
                        bookFragment.setArguments(bundle);
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, bookFragment).addToBackStack(null).commit();
                    } else {
                        Toast.makeText(v.getContext(), "Don't Have Any Product In Your Cart", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void startOrder() {


        for (int i = 0; i <= listItem.size(); i++) {



                Retrofit retrofit1 = new Retrofit.Builder()
                        .baseUrl(BASE_URL) // Specify your api here
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiInterface api = retrofit1.create(ApiInterface.class);

                try {
                    showDialog(getActivity(),"");
                    StoreVariantsDetails storeVariantsDetails = new StoreVariantsDetails(String.valueOf(productId),"0",String.valueOf(uId),String.valueOf(listItem.get(i).getVariants()),String.valueOf(qty));
                    Call<Res> call1 = api.storeVariantsList(storeVariantsDetails);

//                  Log.e("||RES||",String.valueOf(productId)+"0"+String.valueOf(uId)+String.valueOf(listItem.get(i).getVariants())+addressId+String.valueOf(qty));

                    Log.e("---BB---",new Gson().toJson(storeVariantsDetails));
                    call1.enqueue(new Callback<Res>() {
                        @Override
                        public void onResponse(Call<Res> call, Response<Res> response) {
                            if(response.isSuccessful()){
                                                 Log.e("---ST DETAILS---",new Gson().toJson(storeVariantsDetails));


                                                 Log.e("SUCCESS",response.raw().toString() + response.body().toString());


                            }

                        }
                        @Override
                        public void onFailure(Call<Res> call, Throwable t) {

                        }
                    });
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
        }

        storeProductOrders();
    }

    private void storeProductOrders() {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api1 = retrofit1.create(ApiInterface.class);
        StoreOrder storeOrder = new StoreOrder(addressId,String.valueOf(qty),String.valueOf(productId),"0",String.valueOf(uId));
        Call<Res> call1 = api1.storeProductOrders(storeOrder);
        Log.e("---DATA---",new Gson().toJson(storeOrder));
        call1.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {
                Log.e("---BB---",new Gson().toJson(storeOrder));
                if(response.isSuccessful()){
                    Log.e("PRODUCT STORED",response.raw().toString() + response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<Res> call, Throwable t) {
                Log.e("PRODUCT STORED",t.getMessage().toString());
            }
        });
    }

    private void fetchProductsCartProduct() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(gridLayoutManager);

        categoriesList = new ArrayList<>();

        if(getArguments()!=null) {
            categoriesList.add(new CartProduct(getArguments().getString("product_img"), getArguments().getString("product_name"), "", ""));
        }else{
        }
        categoryAdapter = new CartAdapter(categoriesList);
        recyclerView.setAdapter(categoryAdapter);
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