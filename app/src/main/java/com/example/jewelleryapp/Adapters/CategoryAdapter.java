package com.example.jewelleryapp.Adapters;


import static com.example.jewelleryapp.Retrofit.ApiUtils.MAIN_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLDebugHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jewelleryapp.Fragments.SubcategoryFragment;
import com.example.jewelleryapp.Model.CategoryData;
import com.example.jewelleryapp.Model.ProductCategory;
import com.example.jewelleryapp.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryData> productCategoryList;
    private Context c;
    private ItemClickListener clickListener;
    private int id;

    public CategoryAdapter(List<CategoryData> productCategoryList,ItemClickListener clickListener,int id) {
        this.productCategoryList = productCategoryList;
        this.clickListener = clickListener;
        this.id = id;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_design,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.categoryName.setText(productCategoryList.get(position).getName());
        Glide.with(holder.itemView.getContext()).load(MAIN_URL+productCategoryList.get(position).getImgPath()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubcategoryFragment subcategoryFragment = new SubcategoryFragment();
                Bundle bundle = new Bundle();
                bundle.putString("img",productCategoryList.get(position).getImgPath());
                bundle.putString("name",productCategoryList.get(position).getName());
                subcategoryFragment.setArguments(bundle);

                clickListener.OnItemClick(productCategoryList.get(position),productCategoryList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final Intent intent;

        }
    }

    public interface ItemClickListener{
        public void OnItemClick(CategoryData productCategory,int id);

    }
}
