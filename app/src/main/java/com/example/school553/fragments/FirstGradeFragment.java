package com.example.school553.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.school553.R;

public class FirstGradeFragment extends Fragment {

    private String mURL = "https://spbschool553.com/postupayushhim/";
    private ProgressBar mProgressBar;


    public static FirstGradeFragment newInstance(String param1, String param2) {
        FirstGradeFragment fragment = new FirstGradeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance() {
        FirstGradeFragment fragment = new FirstGradeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    //создает представление для фрагмента
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_grade, container, false);

        return view;
    }
}