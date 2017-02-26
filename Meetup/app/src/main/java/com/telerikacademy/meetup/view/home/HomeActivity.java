package com.telerikacademy.meetup.view.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;

import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.data.local.base.ILocalData;
import com.telerikacademy.meetup.data.local.base.IRecentVenue;
import com.telerikacademy.meetup.data.local.realm.RealmRecentVenue;
import com.telerikacademy.meetup.model.base.ILocation;
import com.telerikacademy.meetup.provider.base.ILocationAware;
import com.telerikacademy.meetup.view.home.base.IHomeContentContract;
import com.telerikacademy.meetup.view.home.base.IHomeHeaderContract;
import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity
        implements ILocationAware {

    @Inject
    IHomeContentContract.Presenter contentPresenter;
    @Inject
    IHomeHeaderContract.Presenter headerPresenter;
    @Inject
    FragmentManager fragmentManager;
    @Inject
    ILocalData localData;

    private HomeContentFragment content;
    private HomeHeaderFragment header;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        injectDependencies();
        initialize();
        setup();
    }

    @Override
    public void onStart() {
        super.onStart();
        header.setNavigationDrawer(R.layout.activity_home);
        //showRecentVenues();
    }

    @Override
    public ILocation getLocation() {
        ILocation currentLocation = headerPresenter.getLocation();
        return currentLocation;
    }

    @OnClick(R.id.btn_update_location)
    void updateLocation() {
        header.updateLocation();
    }

    private void initialize() {
        content = (HomeContentFragment) fragmentManager
                .findFragmentById(R.id.fragment_home_content);

        header = (HomeHeaderFragment) fragmentManager
                .findFragmentById(R.id.fragment_home_header);
    }

    private void setup() {
        contentPresenter.setView(content);
        content.setPresenter(contentPresenter);
        headerPresenter.setView(header);
        header.setPresenter(headerPresenter);
    }

    private void injectDependencies() {
        BaseApplication
                .bind(this)
                .from(this)
                .getComponent()
                .getControllerComponent(new ControllerModule(
                        this, getSupportFragmentManager()
                ))
                .inject(this);
    }

    private void showRecentVenues(){
        List<IRecentVenue> results = this.localData.getRecentVenues();

        int len = results.size();
        if(len > 2){ len = 2; }


        for (int i = 0; i < len; i++) {
            IRecentVenue rv = results.get(i);
            int buttonId = -1;
            int imageId = -1;

            try {
                buttonId = R.id.class.getField("rv_button_" + i).getInt(0);
                imageId = R.id.class.getField("rv_image_" + i).getInt(0);
            } catch (Exception e){
                Log.e(e.getMessage(), "");
            }

            Button button = (Button)findViewById(buttonId);
            button.setText(rv.getName());

            ImageView picture = (ImageView)findViewById(imageId);
            picture.setImageBitmap(rv.getPicture());
        }

    }
}


