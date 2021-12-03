package com.example.jewelleryapp.CustmizedProjects.Models;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jewelleryapp.R;

import java.util.List;

public class CustomDesItemsAdapter extends RecyclerView.Adapter<CustomDesItemsAdapter.vholder> {

    private List<P_SpecificationModel> p_specificationModelList;
    private int id;
    private int  selectedItemPos = -1;
    private int lastItemSelectedPos = -1;
    private int idsList;
    private ItemClickListener clickListener;
    private int i=-1;

    public CustomDesItemsAdapter(List<P_SpecificationModel> p_specificationModelList,int id,int idsList,ItemClickListener clickListener) {
        this.p_specificationModelList = p_specificationModelList;
        this.id = id;
        this.idsList = idsList;
        this.clickListener = clickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_model_item, parent, false);
        return new vholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull vholder holder, final @SuppressLint("RecyclerView") int position) {


        holder.textView.setText(p_specificationModelList.get(position).getProductSpecificationName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                selectedItemPos = position;
                if (lastItemSelectedPos == -1) {
                    notifyItemChanged(lastItemSelectedPos);
                    lastItemSelectedPos = selectedItemPos;
                } else {
                    notifyItemChanged(lastItemSelectedPos);
                    lastItemSelectedPos = selectedItemPos;
                }
                notifyItemChanged(selectedItemPos);

                          clickListener.OnItemClick(idsList,selectedItemPos);



            }
        });


        if (position != selectedItemPos) {
            holder.linearLayout.setBackground(holder.itemView.getResources().getDrawable(R.drawable.round_border));
            holder.textView.setTextColor(holder.itemView.getResources().getColor(R.color.redTextColor));
        }
        if (position == selectedItemPos) {
            holder.linearLayout.setBackgroundTintList(ColorStateList.valueOf(holder.itemView.getResources().getColor(R.color.redTextColor)));
            holder.textView.setTextColor(holder.itemView.getResources().getColor(R.color.white));
        } else {
            holder.linearLayout.setBackground(holder.itemView.getResources().getDrawable(R.drawable.round_border));
            holder.textView.setTextColor(holder.itemView.getResources().getColor(R.color.redTextColor));
        }
    }



    @Override
    public int getItemCount() {
        return p_specificationModelList.size();
    }

    public class vholder extends RecyclerView.ViewHolder {
        private TextView textView;
        private LinearLayout linearLayout;

        public vholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.pro_specificationName);
            linearLayout = itemView.findViewById(R.id.changeThisColorLayout);
        }





    }

    public interface ItemClickListener {
        public void OnItemClick(int idslist,int selectedItemPos);
    }
}
