package com.example.jewelleryapp.AllActivities;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jewelleryapp.Model.UserLogin;
import com.example.jewelleryapp.R;
import com.example.jewelleryapp.Retrofit.ApiInterface;
import com.example.jewelleryapp.Retrofit.Res;
import com.example.jewelleryapp.TinyDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private String name,email,phone,pass;
    private EditText userNameEditText,emailEditText,moEditText,passEdittext;
    private Button signUpBtn;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full scree
        setContentView(R.layout.activity_register);
        tinyDB = new TinyDB(this);
        findViewByIds();
        onClickListeners();
    }

    private void onClickListeners() {

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = userNameEditText.getText().toString();
                email    = emailEditText.getText().toString();
                phone = moEditText.getText().toString();
                pass = passEdittext.getText().toString();

                if(validateRegistration(name,email,phone,pass)) {
                    registerUser(name,email,phone,pass);
                }
            }
        });
    }

    private void findViewByIds() {
        userNameEditText = findViewById(R.id.fullNameSignUp);
        emailEditText    = findViewById(R.id.emailSignUp);
        moEditText       = findViewById(R.id.moNumberSignUp);
        passEdittext = findViewById(R.id.passSignUp);
        signUpBtn        = findViewById(R.id.signUpBtn);
    }



    private void registerUser(String name,String email,String phone, String pass) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);



            UserLogin userLogin = new UserLogin(name,email,phone,pass,0,"","","","","");
            Call<Res> call = apiInterface.register_User(userLogin);

            call.enqueue(new Callback<Res>() {
                @Override
                public void onResponse(Call<Res> call, Response<Res> response) {
                    Res res = response.body();

                    Log.d("RESP", String.valueOf(userLogin));
                    if (response.isSuccessful()) {

                        assert response.body() != null;
                        if(response.body().getResult().equals("true")) {
                            Log.e("Success", new Gson().toJson(response.body()));
                            tinyDB.putBoolean("login",false);
                            Toast.makeText(RegisterActivity.this, "Sign up done", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Res> call, Throwable t) {
                    Log.d("RESPT", t.getMessage().toString());
                }
            });
    }

    private boolean validateRegistration(String username,String email,String mo_number, String pass){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(email == null || email.trim().length() == 0){
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mo_number == null || mo_number.trim().length() == 0){
            Toast.makeText(this, "phone Number is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(pass == null || pass.trim().length() == 0){
            Toast.makeText(this, "pass is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}