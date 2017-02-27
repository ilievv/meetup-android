package com.telerikacademy.meetup.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.ui.adapter.GalleryAdapter;
import com.telerikacademy.meetup.ui.fragment.base.IGallery;

public class GalleryFragment extends Fragment
        implements IGallery {

    private ViewPager viewPager;
    private GalleryAdapter galleryAdapter;

    public GalleryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.vp_gallery);
        galleryAdapter = new GalleryAdapter(view.getContext());
        viewPager.setAdapter(galleryAdapter);

        return view;
    }

    @Override
    public void addPhoto(Bitmap photo) {
        galleryAdapter.addPhoto(photo);
    }

    @Override
    public void removePhoto(Bitmap photo) {
        galleryAdapter.removePhoto(photo);
    }
}
