package com.example.school553.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.school553.MainActivity;
import com.example.school553.R;
import com.example.school553.adapters.ExamsFragmentPagerAdapter;
import com.example.school553.adapters.ScheduleFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class ExamsFragment extends Fragment {

    private View view;
    TabLayout tabLayout;
    private Typeface montserrat;

    public static ExamsFragment newInstance() {
        ExamsFragment fragment = new ExamsFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onStart() {
        super.onStart();

    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(montserrat);

                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exams, container, false);
        // Получаем ViewPager и устанавливаем в него адаптер
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(
                new ExamsFragmentPagerAdapter(getChildFragmentManager()));

        // Передаём ViewPager в TabLayout
        tabLayout = view.findViewById(R.id.sliding_tabs);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        changeTabsFont();
        return view;
    }

}
