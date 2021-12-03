package com.example.jewelleryapp.CustmizedProjects.Models;

import static com.example.jewelleryapp.Retrofit.ApiUtils.BASE_URL;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jewelleryapp.R;
import com.example.jewelleryapp.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CustomDesignAdapter extends RecyclerView.Adapter<CustomDesignAdapter.viewHolder>{

    private List<CustomD_Model> customD_modelList;
    private List<P_SpecificationModel> p_specificationModels;
    private int id;
    private CustomDesItemsAdapter.ItemClickListener itemClickListener;


    public CustomDesignAdapter(List<CustomD_Model> customD_modelList, List<P_SpecificationModel> p_specificationModels,int id,CustomDesItemsAdapter.ItemClickListener itemClickListener) {
        this.customD_modelList = customD_modelList;
        this.p_specificationModels = p_specificationModels;
        this.id = id;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_design_model_item,parent,false);
       return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        p_specificationModels = new ArrayList<>();

        int idsList = customD_modelList.get(position).getId();
        holder.textView.setText(customD_modelList.get(position).getDesignName());
        int cu_id = customD_modelList.get(position).getId();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.productSpecificationDesignRecycler.setLayoutManager(linearLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        p_specificationModels = new ArrayList<>();

        Call<ArrayList<P_SpecificationModel>> listCall = api.getProductVariantsOptions(idsList);

        listCall.enqueue(new Callback<ArrayList<P_SpecificationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<P_SpecificationModel>> call, Response<ArrayList<P_SpecificationModel>> response) {
                if(response.isSuccessful()){
                    p_specificationModels = response.body();

                    CustomDesItemsAdapter  customDesignAdapter = new CustomDesItemsAdapter(p_specificationModels,0,idsList,itemClickListener);
                    holder.productSpecificationDesignRecycler.setAdapter(customDesignAdapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<P_SpecificationModel>> call, Throwable t) {

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Log.e("CUID",String.valueOf(cu_id));
    }

    @Override
    public int getItemCount() {
        return customD_modelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private RecyclerView productSpecificationDesignRecycler;
        LinearLayout linearLayout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.customDText);
            productSpecificationDesignRecycler = itemView.findViewById(R.id.productSpecificationRecyclerView);
            linearLayout = itemView.findViewById(R.id.mainLayout);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            productSpecificationDesignRecycler.setLayoutManager(linearLayoutManager);
        }
    }
    public interface ItemClickListener {
         void onItemClick(int idslist,int subItemId);
    }
}
