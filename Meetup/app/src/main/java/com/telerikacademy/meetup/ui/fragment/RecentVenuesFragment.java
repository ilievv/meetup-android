package com.telerikacademy.meetup.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.base.IApiConstants;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.data.local.base.ILocalData;
import com.telerikacademy.meetup.data.local.base.IRecentVenue;
import com.telerikacademy.meetup.ui.fragment.base.IRecentVenues;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class RecentVenuesFragment extends Fragment implements IRecentVenues {
    @Inject
    ILocalData localData;
    @Inject
    IApiConstants constants;

    @BindView(R.id.rv_button_0)
    Button button0;
    @BindView(R.id.rv_image_0)
    ImageView image0;
    @BindView(R.id.rv_button_1)
    Button button1;
    @BindView(R.id.rv_image_1)
    ImageView image1;
    @BindView(R.id.rv_button_2)
    Button button2;
    @BindView(R.id.rv_image_2)
    ImageView image2;
    @BindView(R.id.rv_button_3)
    Button button3;
    @BindView(R.id.rv_image_3)
    ImageView image3;
    @BindView(R.id.rv_button_4)
    Button button4;
    @BindView(R.id.rv_image_4)
    ImageView image4;
    @BindView(R.id.rv_button_5)
    Button button5;
    @BindView(R.id.rv_image_5)
    ImageView image5;

    private List<Button> buttons = new ArrayList<>();
    private List<ImageView> images = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recent_venues, container, false);
        BaseApplication.bind(this, view);

        this.loadButtons();
        this.loadImages();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        injectDependencies();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void showRecentVenues() {
        List<IRecentVenue> results = this.localData.loadRecentVenues();
        int size = results.size();
        int venuesCountForDisplay = size;
        if (venuesCountForDisplay > this.constants.recentVenuesForDisplayCount()) {
            venuesCountForDisplay = this.constants.recentVenuesForDisplayCount();
        }

        for (int i = 0; i < venuesCountForDisplay; i++) {
            String name = results.get(i).getName();
            Bitmap picture = results.get(i).getPicture();

            this.buttons.get(i).setText(name);
            this.images.get(i).setImageBitmap(picture);
        }
    }

    private void loadButtons() {
        this.buttons.add(button0);
        this.buttons.add(button1);
        this.buttons.add(button2);
        this.buttons.add(button3);
        this.buttons.add(button4);
        this.buttons.add(button5);
    }

    private void loadImages() {
        this.images.add(image0);
        this.images.add(image1);
        this.images.add(image2);
        this.images.add(image3);
        this.images.add(image4);
        this.images.add(image5);
    }

    private void injectDependencies() {
        BaseApplication
                .from(getContext())
                .getComponent()
                .getControllerComponent(new ControllerModule(
                        getActivity(), getFragmentManager()
                ))
                .inject(this);
    }
}
