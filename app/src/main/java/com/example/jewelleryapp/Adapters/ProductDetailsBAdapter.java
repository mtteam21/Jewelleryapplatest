package com.example.jewelleryapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jewelleryapp.Model.P_Details_B;
import com.example.jewelleryapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductDetailsBAdapter extends RecyclerView.Adapter<ProductDetailsBAdapter.viewholder> {
    private ArrayList<P_Details_B> p_details_bArrayList;

    public ProductDetailsBAdapter(ArrayList<P_Details_B> p_details_bArrayList) {
        this.p_details_bArrayList = p_details_bArrayList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_details_b,parent,false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.textViewB.setText(String.valueOf(p_details_bArrayList.get(position).getCost()));
        holder.textViewA.setText(String.valueOf(p_details_bArrayList.get(position).getTypes()));
    }

    @Override
    public int getItemCount() {
        return p_details_bArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        private TextView textViewA,textViewB;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            textViewA = itemView.findViewById(R.id.weightTypeTV);
            textViewB = itemView.findViewById(R.id.weightDetailsTv);
        }
    }
}
