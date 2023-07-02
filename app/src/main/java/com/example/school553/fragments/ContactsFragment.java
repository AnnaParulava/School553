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
import com.example.school553.adapters.ContactsRecyclerAdapter;
import com.example.school553.api.JsonAPI;
import com.example.school553.model.ClassesRecyclerModel;
import com.example.school553.model.ContactsRecyclerModel;
import com.example.school553.model.ContactsRecyclerModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsFragment extends Fragment {

    public static final String BASE_URL = "http://192.168.34.83:5000/";
    private View view;
    private List<ContactsRecyclerModel> mContactsList;
    ContactsRecyclerAdapter adapter;
    RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<ContactsRecyclerModel> posts;
    private ProgressBar mProgressBar;

    //инициализирует RecyclerView
    private void initRecyclerView(List<ContactsRecyclerModel> сontactsModelList){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactsRecyclerAdapter( getActivity(), сontactsModelList);
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

        Call<List<ContactsRecyclerModel>> call = service.getContacts();

        call.enqueue(new Callback<List<ContactsRecyclerModel>>() {
            @Override
            public void onResponse(Call<List<ContactsRecyclerModel>> call, Response<List<ContactsRecyclerModel>> response) {
                if (!response.isSuccessful()) {
                    Log.e("ContactsFragment error", "response " + response.code() + response.body());
                    return;
                }
                Log.d("ContactsFragment ", "response " + response.body());

                mProgressBar.setVisibility(View.GONE);
                mContactsList = (ArrayList<ContactsRecyclerModel>) response.body();

                initRecyclerView(mContactsList);
                //  adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ContactsRecyclerModel>> call, Throwable t) {
                Log.e("ClassesFragment ", "Error: " + t.getMessage());
            }
        });
    }



    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
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
        view = inflater.inflate(R.layout.fragment_contacts, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_contacts);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_contacts);

        getRetrofit();

        return view;
    }
}