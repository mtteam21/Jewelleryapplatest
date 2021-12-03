package com.example.jewelleryapp.Fragments;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jewelleryapp.Model.User;
import com.example.jewelleryapp.Model.UserLogin;
import com.example.jewelleryapp.R;
import com.example.jewelleryapp.Retrofit.ApiInterface;
import com.example.jewelleryapp.Retrofit.Res;
import com.example.jewelleryapp.TinyDB;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    private TextInputEditText nameEditText,phoneNumEditText,emailEditText,addressEditText,pincodeEditText,
    landMarkEdittext,stateEditText,cityEditText;
    private View v;
    private TinyDB tinyDB;
    private String userName,userId,userEmail,userNumber;
    private Button updateProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);


        findViewByIds();
        setStoredData();
        clickListener();
        return v;

    }

    private void clickListener() {
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL) // Specify your api here
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                userId = String.valueOf(tinyDB.getInt("id"));

                ApiInterface api1 = retrofit.create(ApiInterface.class);

                UserLogin userLogin = new UserLogin(userId,userName,userEmail,userNumber,0,"","","","","");

                Call<Res> call = api1.update_Profile(userLogin);
                Log.e("UserUpdate",new Gson().toJson(userLogin));
                call.enqueue(new Callback<Res>() {
                    @Override
                    public void onResponse(Call<Res> call, Response<Res> response) {
                        Log.e("UserLogin1",new Gson().toJson(response.body().getResponseMsg()));
                        Log.e("|||===|||", String.valueOf(userId) + response.body().toString());
                        if(response.isSuccessful()){
                            Toast.makeText(v.getContext(), response.body().getResponseMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Res> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void setStoredData() {
        tinyDB = new TinyDB(getContext());
        userName = tinyDB.getString("username");
        userEmail = tinyDB.getString("email");
        userNumber = tinyDB.getString("mobile");
        nameEditText.setText(userName);
        emailEditText.setText(userEmail);
        phoneNumEditText.setText(userNumber);
    }

    private void findViewByIds() {
        nameEditText = v.findViewById(R.id.fullname_ed);
        phoneNumEditText = v.findViewById(R.id.phone_ed);
        emailEditText = v.findViewById(R.id.email_edittetxt);
        addressEditText = v.findViewById(R.id.address1edittext);
        pincodeEditText = v.findViewById(R.id.pincode_edittext);
        landMarkEdittext = v.findViewById(R.id.landmark_edittext);
        cityEditText = v.findViewById(R.id.city_edittext);
        stateEditText = v.findViewById(R.id.state_edittext);
        updateProfile = v.findViewById(R.id.updatePbtn);
    }


}