package com.example.school553.fragments;

import static com.yandex.runtime.Runtime.getApplicationContext;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.school553.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class InfoFragment extends Fragment {

    private View view;
    private ChipGroup chipGroup;

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info, container, false);
        loadFragment(AboutSchoolFragment.newInstance(), "about_school_fragment");
        chipGroup = view.findViewById(R.id.chip_group);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                Chip chip = chipGroup.findViewById(i);
                switch (chip.getId()) {
                    case R.id.chip_school:
                        loadFragment(AboutSchoolFragment.newInstance(), "about_school_fragment");
                        return;
                    case R.id.chip_camp:
                        loadFragment(SchoolCampFragment.newInstance(), "school_camp_fragment");
                        return;
                    case R.id.chip_contacts:
                        loadFragment(ContactsFragment.newInstance(), "contacts_fragment");
                        return;
                    case R.id.chip_classes:
                        loadFragment(ClassesFragment.newInstance(), "classes_fragment");
                        return;
                    case R.id.chip_1_class:
                        loadFragment(FirstGradeFragment.newInstance(), "first_grade_fragment");
                    default:
                        Log.e("OnCheckedChangeListener", "Error");
                        return;
                }
            }
        });
        return view;
    }

    private void loadFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.chip_host_fragment, fragment, tag);
        ft.commit();
    }


}
