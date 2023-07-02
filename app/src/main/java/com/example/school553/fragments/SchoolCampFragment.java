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

public class SchoolCampFragment extends Fragment {

    private String mURL = "https://spbschool553.com/letniy-lager-gol-raduga/";
    private ProgressBar mProgressBar;

    public static SchoolCampFragment newInstance(String param1, String param2) {
        SchoolCampFragment fragment = new SchoolCampFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance() {
        SchoolCampFragment fragment = new SchoolCampFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_school_camp, container, false);
//        WebView webView = view.findViewById(R.id.webViewCamp);
//        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_camp);
//
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                mProgressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                mProgressBar.setVisibility(View.GONE);
//            }
//        }); webView.loadUrl(mURL);
//        webView.loadUrl(mURL);
        return view;
    }
}