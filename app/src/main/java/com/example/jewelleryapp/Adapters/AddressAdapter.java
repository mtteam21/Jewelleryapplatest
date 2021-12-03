package com.example.jewelleryapp.Adapters;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jewelleryapp.Model.AddressModel;
import com.example.jewelleryapp.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressModel> addressList;
    private ItemClickListener clickListener;
    private int  selectedItemPos = -1;
    private int lastItemSelectedPos = -1;

    public AddressAdapter(List<AddressModel> addressList, ItemClickListener clickListener) {
        this.addressList = addressList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.textView.setText(addressList.get(position).getAddress()+" "+addressList.get(position).getPincode()+" "+addressList.get(position).getLandmark()
        +" "+addressList.get(position).getLandmark());


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
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
                clickListener.onItemClick(addressList.get(selectedItemPos).getId());
            }
        });

        if(position == selectedItemPos){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.addressLine);
            checkBox = itemView.findViewById(R.id.selectOneFromList);
        }
    }

    public interface ItemClickListener{
        public void onItemClick(int addressId);
    }
}
