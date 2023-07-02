package com.example.school553.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.school553.R;
import com.example.school553.adapters.ClassesRecyclerAdapter;
import com.example.school553.adapters.LessonsMainRecyclerAdapter;
import com.example.school553.adapters.NewsRecyclerAdapter;
import com.example.school553.api.JsonAPI;
import com.example.school553.model.LessonsClassRecyclerModel;
import com.example.school553.model.LessonsClassRecyclerModel;
import com.example.school553.model.LessonsMainRecyclerModel;
import com.example.school553.model.NewsRecyclerModel;
import com.example.school553.ui.BottomSheet;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LessonsScheduleFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String BASE_URL = "http://192.168.45.83:5000/";
    private List<LessonsMainRecyclerModel> lessonsListModel;
    List<LessonsClassRecyclerModel> lessonsClassesListModel;
    LessonsMainRecyclerAdapter adapter;
    RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<LessonsMainRecyclerModel> posts;
    private ProgressBar mProgressBar;
    Chip chip;
    String className;
    private int mPage;
    private boolean flag = true;
    LinearLayout llBottomSheet;
    private FloatingActionButton buttonFilter;
    private FloatingActionButton buttonScrollDown;
    private ChipGroup chipGroup;
    private ScrollView myScroll;


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    JsonAPI service = retrofit.create(JsonAPI.class);


    private void initMainList(String className) {
        //getRetrofit();
        //Внешний список
      //  lessonsListModel.clear();
        Call<List<LessonsMainRecyclerModel>> callMain;

        if (className != null) {callMain = service.getOrderWeekday(className);}
        else {callMain =  service.getClassWeekday();}

        callMain.enqueue(new Callback<List<LessonsMainRecyclerModel>>() {
            @Override
            public void onResponse(Call<List<LessonsMainRecyclerModel>> call, Response<List<LessonsMainRecyclerModel>> response) {
                if (!response.isSuccessful()) {
                    Log.e("LessonsScheduleFragment error", "response " + response.code() + response.body());
                    return;
                }
                Log.d("LessonsScheduleFragment ", "response " + response.body());

                for (int i = 0; i < response.body().size(); i++) {
                    Log.e("LessonsScheduleFragment ", "response: " + response.body().get(i).getClass_title() +
                            " " + response.body().get(i).getWeekday() + " " + response.body().get(i).getClass_title_id() +
                            " " + response.body().get(i).getWeekday_id());

                    String classTitle = response.body().get(i).getClass_title();
                    String weekday = response.body().get(i).getWeekday();
                    Integer classTitleId = response.body().get(i).getClass_title_id();
                    Integer weekdayId = response.body().get(i).getWeekday_id();
                    Integer number = response.body().get(i).getNumber();

                    Call<List<LessonsClassRecyclerModel>> listCall = service.getLessonsClass(classTitleId, weekdayId);
                    Log.d("LessonsScheduleFragment ", "call " + number + " " + classTitle + " " + weekday + classTitleId + " " + weekdayId);

                    listCall.enqueue(new Callback<List<LessonsClassRecyclerModel>>() {
                        @Override
                        public void onResponse(Call<List<LessonsClassRecyclerModel>> listCall, Response<List<LessonsClassRecyclerModel>> resp) {
                            if (!resp.isSuccessful()) {
                                Log.e("LessonsScheduleFragment error", "response " + resp.code() + resp.body());
                                return;
                            }
                            Log.d("LessonsScheduleFragment ", "response " + resp.body());

                            lessonsClassesListModel = (ArrayList<LessonsClassRecyclerModel>) resp.body();
                            lessonsListModel.add(new LessonsMainRecyclerModel(number, classTitle, weekday, lessonsClassesListModel, classTitleId, weekdayId));
                            Collections.sort(lessonsListModel, new Comparator<LessonsMainRecyclerModel>() {
                                public int compare(LessonsMainRecyclerModel obj1, LessonsMainRecyclerModel obj2) {
                                    // return obj1.getWeekday().compareToIgnoreCase(obj2.getWeekday()); // To compare string values
                                    return Integer.valueOf(obj1.getNumber()).compareTo(Integer.valueOf(obj2.getNumber())); // To compare integer values
                                    // return Integer.valueOf(obj1.getWeekday_id()).compareTo(Integer.valueOf(obj2.getWeekday_id())); // To compare integer values

                                }
                            });
                            Log.e("LessonsScheduleFragment ", "to model: number-" + number + " classTitle-" + classTitle + " weekday-" + weekday + " classTitleId-" + classTitleId + " weekdayId-" + weekdayId);

                            mProgressBar.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<List<LessonsClassRecyclerModel>> listCall, Throwable t) {
                            Log.e("LessonsScheduleFragment ", "Error: " + t.getMessage());

                        }
                    });
                 //   adapter.notifyDataSetChanged();

                }
                lessonsListModel.clear();
                      adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<LessonsMainRecyclerModel>> call, Throwable t) {
                Log.e("LessonsScheduleFragment ", "Error: " + t.getMessage());
            }
        });
    }


    public static LessonsScheduleFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        LessonsScheduleFragment fragment = new LessonsScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void changeBottomSheet(){

        final BottomSheetDialog bottomSheetDialogFragment = new BottomSheetDialog(
                getActivity(), R.style.BottomSheetDialogTheme
        );
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        buttonScrollDown.setVisibility(View.VISIBLE);
        //нажатие на кнопку поиска
        buttonFilter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    buttonScrollDown.setVisibility(View.GONE);
                }
                else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    buttonScrollDown.setVisibility(View.VISIBLE);
                }
            }
        });

        // настройка колбэков при изменениях
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN){
                    buttonScrollDown.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN){
                    buttonScrollDown.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void changeChipGroup(){
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                chip = chipGroup.findViewById(i);

                if(chip == chipGroup.findViewById(R.id.chip_all)){
                    initMainList(null);
                }
                else{
                    //   chip = chipGroup.findViewById(i);
                    Log.d("ChipGroup",chip.getText().toString());
                    initMainList(chip.getText().toString());
                }

                buttonScrollDown.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                buttonScrollDown.setColorFilter(Color.argb(255, 255, 255, 255));
                flag = true;
            }
        });
    }

    public void changRecyclerScroll(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    Log.w("LessonsScheduleFragment", "scroll view is at bottom");
                    buttonScrollDown.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
                    buttonScrollDown.setColorFilter(Color.argb(255, 255, 255, 255));
                    flag = false;

                }

                if (dy < 0) {
                    Log.w("LessonsScheduleFragment", "scroll view is not at bottom");
                    buttonScrollDown.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                    buttonScrollDown.setColorFilter(Color.argb(255, 255, 255, 255));
                    flag = true;
                }
            }
        });
    }

    public void buttonChangeScroll(){
        buttonScrollDown.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    buttonScrollDown.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
                    buttonScrollDown.setColorFilter(Color.argb(255, 255, 255, 255));
                    flag = false;
                } else {
                    recyclerView.getLayoutManager().scrollToPosition(0);
                    buttonScrollDown.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                    buttonScrollDown.setColorFilter(Color.argb(255, 255, 255, 255));
                    flag = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons_schedule, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_lessons);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_lessons);
        buttonFilter = (FloatingActionButton) view.findViewById(R.id.buttonFilter);
        buttonFilter.setColorFilter(Color.argb(255, 255, 255, 255));
        buttonScrollDown = (FloatingActionButton) view.findViewById(R.id.buttonScrollDown);
        buttonScrollDown.setColorFilter(Color.argb(255, 255, 255, 255));
        llBottomSheet = (LinearLayout) view.findViewById(R.id.bottom_sheet_container);
        chipGroup = view.findViewById(R.id.chip_group_btmsheet);
      //  myScroll = (ScrollView) view.findViewById(R.id.scroll_lessons);

        changeBottomSheet();



//        myScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//
//            @Override
//            public void onScrollChanged() {
//
//                if (myScroll.getChildAt(0).getBottom() <= (myScroll.getHeight() + myScroll.getScrollY())) {
//                    //ScrollView достиг дна
//                    Log.w("LessonsScheduleFragment", "scroll view is at bottom");
//                    buttonScrollDown.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
//                    buttonScrollDown.setColorFilter(Color.argb(255, 255, 255, 255));
//                    flag = false;
//
//                } else {
//                    //ScrollView не достиг дна
//                    Log.w("LessonsScheduleFragment", "scroll view is not at bottom");
//                    buttonScrollDown.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
//                    buttonScrollDown.setColorFilter(Color.argb(255, 255, 255, 255));
//                    flag = true;
//
//                }
//            }
//        });



        changeChipGroup();
        changRecyclerScroll();
        buttonChangeScroll();

        initMainList(null);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        lessonsListModel = new ArrayList<LessonsMainRecyclerModel>();

        adapter = new LessonsMainRecyclerAdapter(getActivity(), lessonsListModel);
        recyclerView.setAdapter(adapter);
        return view;
    }
}








////нажатие на кнопку поиска
//        buttonFilter.setOnClickListener(new Button.OnClickListener() {
////@Override
////public void onClick(View v) {
////
////final BottomSheetDialog bottomSheetDialogFragment = new BottomSheetDialog(
////        getActivity(), R.style.BottomSheetDialogTheme
////        );
////
////        View bottomSheetView = LayoutInflater.from(getContext())
////        .inflate(
////        R.layout.bottom_sheet,
////        (LinearLayout) view.findViewById(R.id.bottom_sheet_container)
////        );
////        bottomSheetDialogFragment.setContentView(bottomSheetView);
////
////        if (bottomSheetDialogFragment.isShowing()) {
////        bottomSheetDialogFragment.dismiss();
////        } else {
////        bottomSheetDialogFragment.show();
////        }
////        }
////        });