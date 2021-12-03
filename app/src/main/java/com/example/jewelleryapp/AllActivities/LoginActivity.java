package com.example.jewelleryapp.AllActivities;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;
import static com.example.jewelleryapp.Retrofit.ApiUtils.MAIN_URL;
import static com.example.jewelleryapp.Retrofit.ApiUtils.M_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jewelleryapp.Model.User;
import com.example.jewelleryapp.Model.UserLogin;
import com.example.jewelleryapp.R;
import com.example.jewelleryapp.Retrofit.ApiInterface;
import com.example.jewelleryapp.Retrofit.Res;
import com.example.jewelleryapp.TinyDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Button signUpBtn;
    private Button logInBtn;
    private ApiInterface apiInterface;
    private EditText email_EditText,passEditText;
    private String userName,pass;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_login);

        tinyDB = new TinyDB(this);
        signUpBtn = findViewById(R.id.signUpBtn);
        logInBtn  = findViewById(R.id.signInBtn);
        email_EditText = findViewById(R.id.emailEditTextLogin);
        passEditText = findViewById(R.id.passEdittextLogin);

        if(tinyDB.getBoolean("login")){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.putExtra("username", userName);
//                intent.putExtra("login", true);
//                startActivity(intent);
                userName = email_EditText.getText().toString().trim();
                pass = passEditText.getText().toString().trim();
                if(validateLogin(userName,pass)) {
                    doLogin(userName, pass);
                }
            }
        });




    }
    private void doLogin(String userName,String pass) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(M_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        UserLogin userLogin = new UserLogin();
            User user = new User(userName, pass,userLogin);
            Log.e("USER", new Gson().toJson(user));

            Call<Res> call = apiInterface.login(user);
                        call.enqueue(new Callback<Res>() {
                            @Override
                            public void onResponse(Call<Res> call, Response<Res> response) {
                                Res res = response.body();
                                Log.e("|||===||||", res.getResult() + res.getResponseMsg() + response.raw().toString() + response.body().toString());

                                if (response.isSuccessful()) {
                                    Log.e("Success", new Gson().toJson(response.body()));
                                    Log.e("Success", new Gson().toJson(res.getResponseMsg()+" "+res.getResponseCode()+" "+res.getResult()));


                                    if(response.body().getResult().equals("true")) {
                                   Log.e("UserLogin",new Gson().toJson(response.body().getUserLogin()));


                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        tinyDB.putBoolean("login",true);
                                        tinyDB.putString("username",res.getUserLogin().getName());
                                        tinyDB.putInt("id",res.getUserLogin().getId());
                                        tinyDB.putString("email",res.getUserLogin().getEmail());
                                        tinyDB.putString("password",res.getUserLogin().getPass());
                                        tinyDB.putString("mobile",res.getUserLogin().getPhone());
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

    private boolean validateLogin(String username, String pass){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(pass == null || pass.trim().length() == 0){
            Toast.makeText(this, "pass is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}