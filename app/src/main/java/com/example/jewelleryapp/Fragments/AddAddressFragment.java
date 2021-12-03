package com.example.jewelleryapp.Fragments;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jewelleryapp.Model.AddressModel;
import com.example.jewelleryapp.R;
import com.example.jewelleryapp.Retrofit.ApiInterface;
import com.example.jewelleryapp.Retrofit.Res;
import com.example.jewelleryapp.TinyDB;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddAddressFragment extends Fragment {


    public AddAddressFragment() {
        // Required empty public constructor
    }
    private View v;
    private TextInputEditText houseNumEditText,landMarkEditText,pinCodeEditText,addressEditText;
    private String houseNum,landMark,picCode,address;
    private Button saveBtn;
    private TinyDB tinyDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_address, container, false);

        tinyDB = new TinyDB(getContext());
        findViewByIds();

        setOnClick();

        return v;
    }

    private void findViewByIds() {
        addressEditText  = v.findViewById(R.id.addressEditText);
        pinCodeEditText  = v.findViewById(R.id.pincode_edittext);
        houseNumEditText = v.findViewById(R.id.houseNumedittext);
        landMarkEditText = v.findViewById(R.id.landmark_edittetxt);


        saveBtn          = v.findViewById(R.id.saveBtn);

    }


    private void setOnClick() {

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int uid = tinyDB.getInt("id");

               houseNum = houseNumEditText.getText().toString().trim();
               landMark = landMarkEditText.getText().toString().trim();
               picCode  = pinCodeEditText.getText().toString().trim();
               address  = addressEditText.getText().toString().trim();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL) // Specify your api here
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiInterface api = retrofit.create(ApiInterface.class);

                AddressModel addressModel = new AddressModel(address,uid,houseNum,landMark,picCode,"0");

                Call<Res> call = api.storeAddress(addressModel);

                call.enqueue(new Callback<Res>() {
                    @Override
                    public void onResponse(Call<Res> call, Response<Res> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(v.getContext(), "Address Stored Successfully.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Res> call, Throwable t) {

                    }
                });

            }
        });
    }
}