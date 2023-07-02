package com.example.school553.fragments;

import android.os.Bundle;

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
import com.example.school553.api.JsonAPI;
import com.example.school553.model.OlympicEventModel;
import com.example.school553.model.OlympicRecyclerModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ExamsOGEFragment extends Fragment {


    public static final String BASE_URL = "http://192.168.34.83:5000/";
    private View view;
    private List<OlympicRecyclerModel> mOlympicsList;
    OlympicRecyclerAdapter adapter;
    RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<OlympicRecyclerModel> posts;
    private ProgressBar mProgressBar;
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

        Call<List<OlympicRecyclerModel>> call = service.getExamsClass(1);

        call.enqueue(new Callback<List<OlympicRecyclerModel>>() {
            @Override
            public void onResponse(Call<List<OlympicRecyclerModel>> call, Response<List<OlympicRecyclerModel>> response) {
                if (!response.isSuccessful()) {
                    Log.e("ExamsOGEFragment error", "response " + response.code() + response.body());
                    return;
                }
                Log.d("ExamsOGEFragment ", "response " + response.body());

                mOlympicsList = (ArrayList<OlympicRecyclerModel>) response.body();

                initRecyclerView(mOlympicsList);
                mainTitle.setText(R.string.oge_title);
                //  adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<OlympicRecyclerModel>> call, Throwable t) {
                Log.e("ExamsOGEFragment ", "Error: " + t.getMessage());
            }
        });

        Call <List<OlympicEventModel>> callEvent = service.getOlympicEvent(1);
        callEvent.enqueue(new Callback<List<OlympicEventModel>>() {
            @Override
            public void onResponse(Call<List<OlympicEventModel>> call, Response<List<OlympicEventModel>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e("ExamsOGEFragment error", "response " + response.code() + response.body());
                    return;
                }
                Log.d("ExamsOGEFragment ", "response " + response.body());

                mProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<OlympicEventModel>> call, Throwable t) {
                Log.e("ExamsOGEFragment ", "Error: " + t.getMessage());
            }
        });

    }


    public static ExamsOGEFragment newInstance(String param1, String param2) {
        ExamsOGEFragment fragment = new ExamsOGEFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exams_o_g_e, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_oge);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_oge);
        mainTitle = (TextView) view.findViewById(R.id.tv_title);

        getRetrofit();
        return view;
    }
}