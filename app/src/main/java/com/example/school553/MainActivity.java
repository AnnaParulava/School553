package com.example.school553;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.school553.fragments.ExamsFragment;
import com.example.school553.fragments.InfoFragment;
import com.example.school553.fragments.NewsFragment;
import com.example.school553.fragments.ScheduleFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private final String MAPKIT_API_KEY = "47f3ed99-d84f-4dca-a42b-ffaee88cb299";
    private final Point TARGET_LOCATION = new Point(59.945933, 30.320045);

    @SuppressLint("NonConstantResourceId")
    private final NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_news:
                loadFragment(NewsFragment.newInstance(), "news_fragment");
                return true;
            case R.id.navigation_info:
                loadFragment(InfoFragment.newInstance(), "info_fragment");
                return true;
            case R.id.navigation_schedule:
                loadFragment(ScheduleFragment.newInstance(), "schedule_fragment");
                return true;
            case R.id.navigation_exams:
                loadFragment(ExamsFragment.newInstance(), "exams_fragment");
                return true;
        }
        return false;
    };


    private void loadFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment, tag);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
     //   MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        loadFragment(NewsFragment.newInstance(), "news_fragment");

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.navigation_news);
//
//        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.navigation_news);
//        badge.setBackgroundColor(Color.parseColor("@color/bn_ic_color"));
    }

}