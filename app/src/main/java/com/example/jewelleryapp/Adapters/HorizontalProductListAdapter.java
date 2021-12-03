package com.example.jewelleryapp.Adapters;

import static com.example.jewelleryapp.Retrofit.ApiUtils.MAIN_URL;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jewelleryapp.Model.CategoryData;
import com.example.jewelleryapp.Model.HorizontalProductImage;
import com.example.jewelleryapp.R;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.List;

public class HorizontalProductListAdapter extends RecyclerView.Adapter<HorizontalProductListAdapter.viewholder> {

    private List<HorizontalProductImage> productImagesList;
    private HorizontalProductListAdapter.ItemClickListener clickListener;

    public HorizontalProductListAdapter(List<HorizontalProductImage> productImagesList,ItemClickListener itemClickListener) {
        this.productImagesList = productImagesList;
        this.clickListener = itemClickListener;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_product_image,parent,false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(holder.itemView.getContext()).load(MAIN_URL+"upload/media/"+productImagesList.get(position).getImgpath()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.OnItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productImagesList.size();
    }

    public interface ItemClickListener {
        public void OnItemClick(int position);
    }

    public class viewholder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.horizontal_ProductImageView);
        }
    }
}
