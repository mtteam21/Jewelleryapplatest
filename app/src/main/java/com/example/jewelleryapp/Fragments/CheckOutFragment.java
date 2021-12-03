package com.example.jewelleryapp.Fragments;

import android.location.Address;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jewelleryapp.Adapters.AddressAdapter;
import com.example.jewelleryapp.Model.AddressModel;
import com.example.jewelleryapp.R;

import java.util.ArrayList;
import java.util.List;


public class CheckOutFragment extends Fragment implements AddressAdapter.ItemClickListener{

    public CheckOutFragment() {
        // Required empty public constructor
    }
    private View v;
    private RecyclerView recyclerView;
    private List<AddressModel> addressModelList;
    private String addressId;
    private AddressAdapter.ItemClickListener itemClickListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_check_out, container, false);

        recyclerView = v.findViewById(R.id.addressListRecycler);

        loadAddressList(this);


        return v;
    }

    private void loadAddressList(AddressAdapter.ItemClickListener itemClickListener) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        addressModelList = new ArrayList<>();


        AddressAdapter addressAdapter = new AddressAdapter(addressModelList,itemClickListener);
        recyclerView.setAdapter(addressAdapter);
    }

    @Override
    public void onItemClick(int addressId1) {
       addressId  = String.valueOf(addressId1);
    }
}