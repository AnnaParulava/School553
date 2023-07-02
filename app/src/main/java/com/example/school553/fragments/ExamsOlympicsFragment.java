package com.example.school553.fragments;

import android.app.assist.AssistStructure;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.school553.R;
import com.example.school553.adapters.OlympicRecyclerAdapter;
import com.example.school553.adapters.OlympicRecyclerAdapter;
import com.example.school553.api.JsonAPI;
import com.example.school553.model.OlympicEventModel;
import com.example.school553.model.OlympicRecyclerModel;
import com.example.school553.model.OlympicRecyclerModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ExamsOlympicsFragment extends Fragment {


    public static final String BASE_URL = "http://192.168.34.83:5000/";
    private View view;
    private List<OlympicRecyclerModel> mOlympicsList;
    OlympicRecyclerAdapter adapter;
    RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<OlympicRecyclerModel> posts;
    private ProgressBar mProgressBar;
    private TextView titleState;
    private TextView mainTitle;


    //инициализирует RecyclerView
    private void initRecyclerView(List<OlympicRecyclerModel> olympicsModelList){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OlympicRecyclerAdapter( getActivity(), olympicsModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }


    //возвращает заполнененный список
    private void getRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonAPI service = retrofit.create(JsonAPI.class);

        Call<List<OlympicRecyclerModel>> call = service.getOlympic();

        call.enqueue(new Callback<List<OlympicRecyclerModel>>() {
            @Override
            public void onResponse(Call<List<OlympicRecyclerModel>> call, Response<List<OlympicRecyclerModel>> response) {
                if (!response.isSuccessful()) {
                    Log.e("OlympicFragment error", "response " + response.code() + response.body());
                    return;
                }
                Log.d("OlympicFragment ", "response " + response.body());

                mOlympicsList = (ArrayList<OlympicRecyclerModel>) response.body();

                initRecyclerView(mOlympicsList);
                //  adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<OlympicRecyclerModel>> call, Throwable t) {
                Log.e("OlympicFragment ", "Error: " + t.getMessage());
            }
        });

        Call <List<OlympicEventModel>> callEvent = service.getOlympicEvent(1);
        callEvent.enqueue(new Callback<List<OlympicEventModel>>() {
            @Override
            public void onResponse(Call<List<OlympicEventModel>> call, Response<List<OlympicEventModel>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e("OlympicFragment error", "response " + response.code() + response.body());
                    return;
                }
                Log.d("OlympicFragment ", "response " + response.body());

                mProgressBar.setVisibility(View.GONE);
                titleState.setText(  response.body().get(0).getTitle());
                mainTitle.setText(R.string.graph_event);

            }

            @Override
            public void onFailure(Call<List<OlympicEventModel>> call, Throwable t) {
                Log.e("OlympicFragment ", "Error: " + t.getMessage());
            }
        });

    }



    public static ExamsOlympicsFragment newInstance(int page) {
        ExamsOlympicsFragment fragment = new ExamsOlympicsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exams_olympics, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_olympics);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_olympics);
        titleState= (TextView) view.findViewById(R.id.tv_title_state);
        mainTitle = (TextView) view.findViewById(R.id.tv_title);

        getRetrofit();
        return view;
    }
}