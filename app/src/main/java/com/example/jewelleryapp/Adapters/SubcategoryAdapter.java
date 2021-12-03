package com.example.jewelleryapp.Adapters;

import static com.example.jewelleryapp.Retrofit.ApiUtils.MAIN_URL;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jewelleryapp.Model.Subcategory;
import com.example.jewelleryapp.R;

import java.util.List;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.ViewHolder> {
    private List<Subcategory> subcategoryList;
    private ItemClickListener clickListener;
    private int id;

    public SubcategoryAdapter(List<Subcategory> subcategoryList, ItemClickListener clickListener,int id) {
        this.subcategoryList = subcategoryList;
        this.clickListener = clickListener;
        this.id = id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(subcategoryList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(subcategoryList.get(position),subcategoryList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return subcategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.subcategoryName);
        }
    }

    public interface ItemClickListener{
        public void onItemClick(Subcategory subcategory,int sub_c_id);

    }
}
