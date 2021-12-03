package com.example.jewelleryapp.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.jewelleryapp.R;
import com.example.jewelleryapp.ZoomView;
import com.ortiz.touchview.TouchImageView;

public class ViewfullscreenImagesFragment extends Fragment implements ZoomView.ZoomViewListener {
    public ViewfullscreenImagesFragment() {
        // Required empty public constructor
    }

    private View v;
    private String img;
    private TouchImageView imageView;
    private ImageView imageViewCancel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_viewfullscreen_images, container, false);

       imageView = v.findViewById(R.id.myZoomageView);
       imageViewCancel = v.findViewById(R.id.cancel_button);

        if(getArguments()!=null){
            img = getArguments().getString("img");

        }

        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        Glide.with(v.getContext())
                .load(img)
                .fitCenter()
                .into(imageView);

        imageView.setImageDrawable(v.getResources().getDrawable(R.drawable.ringimg));

        Log.e("URL2",img);

        return v;
    }

    @Override
    public void onZoomStarted(float zoom, float zoomx, float zoomy) {

    }

    @Override
    public void onZooming(float zoom, float zoomx, float zoomy) {

    }

    @Override
    public void onZoomEnded(float zoom, float zoomx, float zoomy) {

    }
}