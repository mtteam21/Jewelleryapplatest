package com.example.jewelleryapp.Adapters;

import static com.example.jewelleryapp.Retrofit.ApiUtils.MAIN_URL;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jewelleryapp.Model.Product;
import com.example.jewelleryapp.R;

import java.util.List;

public class ProductSubCategoriwiseAdapter extends RecyclerView.Adapter<ProductSubCategoriwiseAdapter.ViewHolder> {
        private List<Product> productList;
        private ItemClickListener itemClickListener;

public ProductSubCategoriwiseAdapter(List<Product> productList,ItemClickListener itemClickListener) {
        this.productList = productList;
        this.itemClickListener = itemClickListener;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_product_design,parent,false);
        return new ViewHolder(v);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
    if(productList.get(position).getProductImagesList().size()!=0) {
        Glide.with(holder.itemView.getContext()).load(MAIN_URL + "upload/media/" + productList.get(position).getProductImagesList().get(0).getImgpath()).into(holder.imageView);
    }

    double main_price = productList.get(position).getPrice();
    double discount   = productList.get(position).getDiscount();
    double finalPrice = (main_price/discount)*100;

    holder.productPrice.setText("MRP: "+String.valueOf(finalPrice));
    holder.productMrp.setText("M.R.P.:\t₹ "+productList.get(position).getPrice());
    holder.productNameText.setText(productList.get(position).getName());
    holder.productPrice.setText(String.valueOf(productList.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        itemClickListener.onItemClick(productList.get(position));

        }
        });
        }

@Override
public int getItemCount() {
        return productList.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView productNameText,productPrice,productMrp;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.homeProductIMG);
        productNameText = itemView.findViewById(R.id.h_productnameTv);
        productMrp = itemView.findViewById(R.id.mainPriceTv);
        productPrice = itemView.findViewById(R.id.originalPrice);


    }
}


public interface ItemClickListener {
    public void onItemClick(Product product);
}
}
