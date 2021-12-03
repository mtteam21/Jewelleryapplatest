package com.example.jewelleryapp.AllActivities;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.jewelleryapp.Adapters.ProductAdapter;
import com.example.jewelleryapp.Model.DataModel;
import com.example.jewelleryapp.Model.Product;
import com.example.jewelleryapp.R;
import com.example.jewelleryapp.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsMenuItemActivity extends AppCompatActivity {
    private WebView webView;
    private List<DataModel> dataModelslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_menu_item);

        webView = findViewById(R.id.webView);

        loadData();

    }

    private void loadData() {

        dataModelslist = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<List<DataModel>> call = api.getData();

        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                dataModelslist = response.body();

                if(response.isSuccessful()){
                    Log.e("Response",response.body().toString());
                    Log.e("|||===||||", response.raw().toString() + response.body().get(0).getPrivacy().toString());

                    webView.loadDataWithBaseURL(null, response.body().get(0).getPrivacy(), "text/html", "UTF-8", null);
                }

            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {

            }
        });

    }
}