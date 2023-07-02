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

import com.example.school553.R;
import com.example.school553.adapters.ClassesRecyclerAdapter;
import com.example.school553.adapters.NewsRecyclerAdapter;
import com.example.school553.api.JsonAPI;
import com.example.school553.api.WPpost;
import com.example.school553.model.ClassesRecyclerModel;
import com.example.school553.model.NewsRecyclerModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ClassesFragment extends Fragment {

    public static final String BASE_URL = "http://192.168.34.83:5000/";

    private View view;
    private List<ClassesRecyclerModel> mClassesModelList;
    ClassesRecyclerAdapter adapter;
    RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<ClassesRecyclerModel> posts;
    private ProgressBar mProgressBar;

    //использует помещенные аргументы и будет использовать их при воссоздании фрагмента
    public static ClassesFragment newInstance(String param1, String param2) {
        ClassesFragment fragment = new ClassesFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    public static Fragment newInstance() {
        ClassesFragment fragment = new ClassesFragment();
        return fragment;
    }

    //инициализирует RecyclerView
    private void initRecyclerView(List<ClassesRecyclerModel> сlassesModelList){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClassesRecyclerAdapter( getActivity(), сlassesModelList);
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

        Call<List<ClassesRecyclerModel>> call = service.getClasses();

        call.enqueue(new Callback<List<ClassesRecyclerModel>>() {
            @Override
            public void onResponse(Call<List<ClassesRecyclerModel>> call, Response<List<ClassesRecyclerModel>> response) {
                if (!response.isSuccessful()) {
                    Log.e("ClassesFragment error", "response " + response.code() + response.body());
                    return;
                }
                Log.d("ClassesFragment ", "response " + response.body());

                mProgressBar.setVisibility(View.GONE);
                mClassesModelList = (ArrayList<ClassesRecyclerModel>) response.body();

                 initRecyclerView(mClassesModelList);
            }

            @Override
            public void onFailure(Call<List<ClassesRecyclerModel>> call, Throwable t) {
                Log.e("ClassesFragment ", "Error: " + t.getMessage());

            }

        });
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    //создание фрагмента
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    //создает представление для фрагмента
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_classes, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_classes);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_classes);

        getRetrofit();

        return view;
    }
}