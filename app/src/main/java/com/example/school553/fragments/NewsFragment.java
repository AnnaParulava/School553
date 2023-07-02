package com.example.school553.fragments;

import static android.content.Context.NOTIFICATION_SERVICE;

import static com.yandex.runtime.Runtime.getApplicationContext;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school553.MainActivity;
import com.example.school553.R;
import com.example.school553.api.JsonAPI;
import com.example.school553.api.WPpost;
import com.example.school553.adapters.NewsRecyclerAdapter;
import com.example.school553.model.NewsRecyclerModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends Fragment implements NewsRecyclerAdapter.ItemClickListener {

    private View view;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<NewsRecyclerModel> mNewsModelList;
    // static ArrayList<Integer> newsArrayListCl = new ArrayList<Integer>();
    private NewsRecyclerAdapter mNewsRecyclerAdapter;
    private String baseURL = "https://spbschool553.com";
    private String title;
    Bundle arguments;
    private String excerpt;
    NotificationCompat.Builder builder;
    private SharedPreferences mSettings;

    // это будет именем файла настроек
    public static final String APP_PREFERENCES = "notificationPref";
    public static final String APP_PREFERENCES_ARTICLE_ID = "id";
    //  public static final String APP_PREFERENCES_ID= "id";

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();

        return fragment;
    }

    private void getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonAPI service = retrofit.create(JsonAPI.class);
        service.getPosts();

        Call<List<WPpost>> call = service.getPosts();


        call.enqueue(new Callback<List<WPpost>>() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onResponse(Call<List<WPpost>> call, Response<List<WPpost>> response) {
                if (!response.isSuccessful()) {
                    Log.e("NewsFragment error", "response " + response.code() + response.body());
                }
                Log.d("NewsFragment ", "response " + response.body());

                mProgressBar.setVisibility(View.GONE);

                mNewsModelList.clear();

//                mNewsModelList.add(new NewsRecyclerModel(34564, "Петербургский международный образовательный форум",
//                        "https://spbschool553.com/%d0%bf%d0%b5%d1%82%d0%b5%d1%80%d0%b1%d1%83%d1%80%d0%b3%d1%81%d0%ba%d0%b8%d0%b9-%d0%bc%d0%b5%d0%b6%d0%b4%d1%83%d0%bd%d0%b0%d1%80%d0%be%d0%b4%d0%bd%d1%8b%d0%b9-%d0%be%d0%b1%d1%80%d0%b0%d0%b7%d0%be-2/",
//                        "Ежегодно педагоги нашей школы принимают участие в Международном образовательном форуме (далее — ПМОФ), проводимом в Санкт-Петербурге весной во время школьных каникул. В текуще",
//                        "2022-04-04T23:03:16"));

                for (int i = 0; i < response.body().size(); i++) {
                    Log.e("NewsFragment ", "title " + response.body().get(i).getTitle().getRendered() +
                            " " + response.body().get(i).getId());

                    title = response.body().get(i).getTitle().getRendered();
                    title = title.replace("&#8212;", "—");
                    title = title.replace("&#171;", "«");
                    title = title.replace("&#187;", "»");

                    excerpt = response.body().get(i).getExcerpt().getRendered();
                    excerpt = excerpt.replace("    ", "");
                    excerpt = excerpt.replace("<p>", "");
                    excerpt = excerpt.replace("&#8212;", "—");
                    excerpt = excerpt.replace("&#171;", "«");
                    excerpt = excerpt.replace("&#187;", "»");

                    mNewsModelList.add(new NewsRecyclerModel(response.body().get(i).getId(), title,
                            response.body().get(i).getLink(), excerpt, response.body().get(i).getDate()));

                    Log.d("NewsFragment ", title + " " + response.body().get(i).getLink() + " " + excerpt + " " + response.body().get(i).getDate());

                }

                sharedPreferenceNotification();

                mNewsRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<WPpost>> call, Throwable t) {
                Log.d("NewsFragment ", "Error: " + t.getMessage());

            }
        });
    }

    private NotificationManagerCompat notificationManager;
    private static final String NORMAL_CHANNEL = "NORMAL_CHANNEL";
    private static final String IMPORTANT_CHANNEL = "IMPORTANT_CHANNEL";


    private void initSharedPreference() {
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    private void sharedPreferenceNotification(){
        SharedPreferences.Editor editor = mSettings.edit();
        Log.d("TEST NewsFragment APP_PREFERENCES_ARTICLE_ID ", String.valueOf(mSettings.getAll()));
        //перенести в onPaused

        if (!mSettings.contains(APP_PREFERENCES_ARTICLE_ID) || mSettings.getString(APP_PREFERENCES_ARTICLE_ID, "str") == null){
            editor.putString(APP_PREFERENCES_ARTICLE_ID, (String.valueOf(mNewsModelList.get(0).getId())));
            editor.apply();
        }
        else {
            if (mSettings.getString(APP_PREFERENCES_ARTICLE_ID, "str").equals(String.valueOf(mNewsModelList.get(0).getId()))) {
                Log.d("EQUALS NewsFragment APP_PREFERENCES_ARTICLE_ID ", mSettings.getString(APP_PREFERENCES_ARTICLE_ID, "str"));

            } else {

                Log.e("NewsFragment ", "новость!");

                builder
                        .setSmallIcon(android.R.drawable.btn_star)
                        .setContentTitle("Новости")
                        .setContentText(mNewsModelList.get(0).getTitle());
                //генерация уникального идентификатора
                notificationManager.notify(R.id.SIMPLE_NOTIFICATION_ID, builder.build());
                Log.d("NEW NewsFragment APP_PREFERENCES_ARTICLE_ID ", mSettings.getString(APP_PREFERENCES_ARTICLE_ID, "str"));
                editor.putString(APP_PREFERENCES_ARTICLE_ID, (String.valueOf(mNewsModelList.get(0).getId())));
                editor.apply();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_news);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_news);


        notificationManager = NotificationManagerCompat.from(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            {
                String name = getResources().getString(R.string.NOT_IMPORTANT_CHANNEL_NAME);
                NotificationChannel channel = new NotificationChannel(
                        NORMAL_CHANNEL,
                        name,
                        NotificationManager.IMPORTANCE_LOW
                );
                String description = getResources().getString(R.string.NOT_IMPORTANT_CHANNEL_DESCRIPTION);
                channel.setDescription(description);
                channel.enableVibration(false);
                notificationManager.createNotificationChannel(channel);
            }
        }
        builder = new NotificationCompat.Builder(getActivity(), NORMAL_CHANNEL);


        getRetrofit();
        initSharedPreference();
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mNewsModelList = new ArrayList<NewsRecyclerModel>();


        mNewsRecyclerAdapter = new NewsRecyclerAdapter(getActivity(), mNewsModelList, this);
        mRecyclerView.setAdapter(mNewsRecyclerAdapter);


        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        getRetrofit();
        initSharedPreference();
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onItemClick(NewsRecyclerModel newsRecyclerModel) {

        Fragment fragment = NewsArticleFragment.newInstance(newsRecyclerModel.getLink());
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("news_fragment"));
        fragmentTransaction.add(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}








//                if (mSettings.getString(APP_PREFERENCES_ARTICLE_ID, "str").equals("") || mSettings.getString(APP_PREFERENCES_ARTICLE_ID, "str") == null) {
//
//                    editor.putString(APP_PREFERENCES_ARTICLE_ID, String.valueOf(mNewsModelList.get(0).getId()));
//                    editor.apply();
//                    editor.clear();
//
//                    Log.d("NULL NewsFragment APP_PREFERENCES_ARTICLE_ID ", mSettings.getString(APP_PREFERENCES_ARTICLE_ID, "str"));
//                } else {


//                Log.d("NewsFragment Array ", String.valueOf(mNewsModelList.get(0).getId()));
//
//                if ( mNewsModelList.get(1).getId() == newsArrayListClone.get(1).getId()) {
//                    Log.e("NewsFragment ", "новость!");
//
//                    builder
//                            .setSmallIcon(android.R.drawable.btn_star)
//                            .setContentTitle("Новости")
//                            .setContentText(mNewsModelList.get(0).getTitle());
//                    //генерация уникального идентификатора
//                    notificationManager.notify(R.id.SIMPLE_NOTIFICATION_ID, builder.build());
//                    newsArrayListClone.clear();
//                }



//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), NORMAL_CHANNEL);
//        builder
//                .setSmallIcon(android.R.drawable.btn_star)
//                .setContentTitle("Новости")
//                .setContentText("Переводим классику");
////        builder.setLargeIcon(
////                BitmapFactory.decodeResource(getResources(), R.drawable.icon3)
////        );
//        //генерация уникального идентификатора
//        notificationManager.notify(R.id.SIMPLE_NOTIFICATION_ID, builder.build());