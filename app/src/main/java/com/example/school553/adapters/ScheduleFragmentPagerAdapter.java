package com.example.school553.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.school553.fragments.CallScheduleFragment;
import com.example.school553.fragments.LessonsScheduleFragment;

public class ScheduleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Уроки", "Звонки"};
    private Context context;

    //конструктор
    public ScheduleFragmentPagerAdapter(FragmentManager childFragmentManager) {
        super(childFragmentManager);
    }

    //вывести фрагмент в зависимости от выбранной позиции
    public Fragment getItem(int position)  {
        switch (position) {
            case 0:
                return  new LessonsScheduleFragment();
            case 1:
                return  new CallScheduleFragment();
        }
        return null;
    }
    public ScheduleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    //вернуть количество страниц
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    // генерирует заголовок в зависимости от позиции
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}

