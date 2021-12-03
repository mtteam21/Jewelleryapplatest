package com.example.jewelleryapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jewelleryapp.R;


public class ProductBuyFragment extends Fragment {

    public ProductBuyFragment() {
        // Required empty public constructor
    }

    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_product_buy, container, false);
        return v;
    }
}