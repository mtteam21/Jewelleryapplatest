package com.example.jewelleryapp.Fragments;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jewelleryapp.Adapters.AddressAdapter;
import com.example.jewelleryapp.Model.AddressModel;
import com.example.jewelleryapp.R;
import com.example.jewelleryapp.Retrofit.ApiInterface;
import com.example.jewelleryapp.TinyDB;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddressFragment extends Fragment implements AddressAdapter.ItemClickListener{

    private View v;
    private RecyclerView recyclerView;
    private List<AddressModel> addressModelList;
    private CardView addAddressCard;
    private TinyDB tinyDB;
    private Button saveBtn;
    private int addressId = 0 ;
    private int productId;
    private String productName;
    private String productImg;
    private AddressAdapter.ItemClickListener itemClickListener;
    public AddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_address, container, false);
        addAddressCard = v.findViewById(R.id.openaddAddress);
        saveBtn = v.findViewById(R.id.changeAddressBtnSuccess);
        if(getArguments()!=null){
            productId = getArguments().getInt("product_id");
//            addressId = Integer.parseInt(getArguments().getString("aid"));
            productName = getArguments().getString("product_name");
            productImg = getArguments().getString("product_img");

        }

        addAddressCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAddressFragment bookFragment = new AddAddressFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, bookFragment).addToBackStack(null).commit();
            }
        });



            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CartFragment bookFragment = new CartFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("aid", String.valueOf(addressId));
                    bundle.putInt("product_id",productId);
                    bundle.putString("product_name",productName);
                    bundle.putString("product_img",productImg);
                    bookFragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, bookFragment).addToBackStack(null).commit();
                }
            });


        loadAddressList(this);

        return v;
    }
    private void loadAddressList(AddressAdapter.ItemClickListener itemClickListener) {

        tinyDB = new TinyDB(getContext());
        recyclerView = v.findViewById(R.id.addressListRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addressModelList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        int uid = tinyDB.getInt("id");
        Call<ArrayList<AddressModel>> call = api.getAddressList(uid);

        call.enqueue(new Callback<ArrayList<AddressModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AddressModel>> call, Response<ArrayList<AddressModel>> response) {
                Log.e("ArrayList", response.raw().toString() + response.body().toString());

                if(response.isSuccessful()){
                    addressModelList = response.body();
                    AddressAdapter addressAdapter = new AddressAdapter(addressModelList,itemClickListener);
                    recyclerView.setAdapter(addressAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AddressModel>> call, Throwable t) {
                Log.e("ArrayList", t.getMessage().toString());

            }
        });




        AddressAdapter addressAdapter = new AddressAdapter(addressModelList,itemClickListener);
        recyclerView.setAdapter(addressAdapter);
    }

    @Override
    public void onItemClick(int addressId1) {
        addressId = addressId1;
        saveBtn.setVisibility(View.VISIBLE);
    }

}