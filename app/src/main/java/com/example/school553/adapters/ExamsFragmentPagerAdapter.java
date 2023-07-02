package com.example.school553.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.school553.fragments.ExamsEGEFragment;
import com.example.school553.fragments.ExamsOGEFragment;
import com.example.school553.fragments.ExamsOlympicsFragment;

public class ExamsFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Олимпиады", "ОГЭ", "ЕГЭ"};
    private Context context;

    public ExamsFragmentPagerAdapter(FragmentManager childFragmentManager) {
        super(childFragmentManager);
    }

    @Override public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return  new ExamsOlympicsFragment();
            case 1:
                return  new ExamsOGEFragment();
            case 2:
                return  new ExamsEGEFragment();
        }
        return null;
    }

    @Override
    //вернуть количество страниц
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override public CharSequence getPageTitle(int position) {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position];
    }
}
