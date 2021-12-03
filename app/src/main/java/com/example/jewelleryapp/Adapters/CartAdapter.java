package com.example.jewelleryapp.Adapters;

import static com.example.jewelleryapp.Retrofit.ApiUtils.MAIN_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jewelleryapp.Model.CartProduct;
import com.example.jewelleryapp.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartProduct> cartProductList;

    public CartAdapter(List<CartProduct> cartProductList) {
        this.cartProductList = cartProductList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_design,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext()).load(MAIN_URL+"upload/media/"+cartProductList.get(position).getImgUrl()).into(holder.cartProductImage);

        holder.productName.setText(cartProductList.get(position).getProductName());

    }

    @Override
    public int getItemCount() {
        return cartProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView cartProductImage;
        private TextView productName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartProductImage = itemView.findViewById(R.id.cartImgView);
            productName = itemView.findViewById(R.id.cartProdName);
        }
    }
}
