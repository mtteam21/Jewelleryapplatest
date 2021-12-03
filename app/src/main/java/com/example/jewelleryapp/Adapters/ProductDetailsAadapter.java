package com.example.jewelleryapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jewelleryapp.Model.P_Details_A;
import com.example.jewelleryapp.R;

import java.util.ArrayList;

public class ProductDetailsAadapter extends RecyclerView.Adapter<ProductDetailsAadapter.viewHolder> {
    private ArrayList<P_Details_A> pDetailsAArrayList;

    public ProductDetailsAadapter(ArrayList<P_Details_A> pDetailsAArrayList) {
        this.pDetailsAArrayList = pDetailsAArrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_details_a,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        try {
            holder.textView1.setText(pDetailsAArrayList.get(position).getType());
            holder.textView2.setText(pDetailsAArrayList.get(position).getQuantity());
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return pDetailsAArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView textView1,textView2;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.weightTypeTV);
            textView2 = itemView.findViewById(R.id.weightDetailsTv);
        }
    }
}
