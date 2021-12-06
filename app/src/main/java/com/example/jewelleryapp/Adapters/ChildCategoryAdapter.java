package com.example.jewelleryapp.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jewelleryapp.Model.ChildCategoryList;
import com.example.jewelleryapp.R;

import java.util.List;

public class ChildCategoryAdapter extends RecyclerView.Adapter<ChildCategoryAdapter.ViewHolder>{

    private List<ChildCategoryList> productsLists;
    private ItemClickListener itemClickListener;

    public ChildCategoryAdapter(List<ChildCategoryList> productsLists, ItemClickListener itemClickListener) {
        this.productsLists = productsLists;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.childName.setText(productsLists.get(position).getChildccame());

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               itemClickListener.onItemClick(productsLists.get(position));
           }
       });
    }

    @Override
    public int getItemCount() {
        return productsLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView childName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childName = itemView.findViewById(R.id.childCName);
        }
    }

    public interface ItemClickListener{
        void onItemClick(ChildCategoryList productsList);
    }
}
