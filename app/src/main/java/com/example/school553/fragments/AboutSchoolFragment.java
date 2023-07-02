package com.example.school553.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.school553.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;


public class AboutSchoolFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private View view;
    private MapView mapView;
    private final Point TARGET_LOCATION = new Point(59.833522201269396, 30.38588227851915);
    private FloatingActionButton buttonScrollDown;
    private ScrollView myScroll;
    private boolean flag = true;

    //использует помещенные аргументы и будет использовать их при воссоздании фрагмента
    public static AboutSchoolFragment newInstance() {
        AboutSchoolFragment fragment = new AboutSchoolFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    public void buttonChangeScroll() {
        buttonScrollDown.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    myScroll.post(new Runnable() {
                        public void run() {
                            myScroll.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                    flag = false;
                } else {
                    myScroll.post(new Runnable() {
                        public void run() {
                            myScroll.fullScroll(View.FOCUS_UP);
                        }
                    });
                    flag = true;
                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @Override
    //создает представление для фрагмента
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MapKitFactory.initialize(getActivity());
        view = inflater.inflate(R.layout.fragment_about_school, container, false);
        buttonScrollDown = (FloatingActionButton) view.findViewById(R.id.buttonScrollDown);
        buttonScrollDown.setColorFilter(Color.argb(255, 255, 255, 255));
        myScroll = (ScrollView) view.findViewById(R.id.scroll);
        mapView = (MapView) view.findViewById(R.id.map);

        // Перемещение камеры
        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 15.54f, 0.0f, 0.0f),
                new Animation(Animation.Type.LINEAR, 3),
                null);
        mapView.getMap().getMapObjects().addPlacemark(TARGET_LOCATION,
                ImageProvider.fromBitmap(drawSimpleBitmap("0")));

        myScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {

                int scrollAmount = myScroll.getMaxScrollAmount(); //для горизонтального ScrollView
                if (myScroll.getChildAt(0).getBottom() <= (myScroll.getHeight() + myScroll.getScrollY())) {
                    //ScrollView достиг дна
                    Log.w("ASF", "scroll view is at bottom");
                    buttonScrollDown.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
                    buttonScrollDown.setColorFilter(Color.argb(255, 255, 255, 255));
                    flag = false;

                } else {
                    //ScrollView не достиг дна
                    Log.w("ASF", "scroll view is not at bottom");
                    buttonScrollDown.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                    buttonScrollDown.setColorFilter(Color.argb(255, 255, 255, 255));
                    flag = true;

                }
            }
        });

        buttonChangeScroll();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Bitmap drawSimpleBitmap(String number) {
        int picSize = 20;
        Bitmap bitmap = Bitmap.createBitmap(picSize, picSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // отрисовка плейсмарка
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(picSize / 2, picSize / 2, picSize / 2, paint);
        // отрисовка текста
//        paint.setColor(Color.WHITE);
//        paint.setAntiAlias(true);
//        paint.setTextSize(28);
//        paint.setTextAlign(Paint.Align.CENTER);
//        canvas.drawText(number, picSize / 2,
//                picSize / 2 - ((paint.descent() + paint.ascent()) / 2), pa
//                int);
        return bitmap;
    }

    @Override
    //фрагмент больше не является видимым и вместе с представлением переходит в состояние CREATED
    public void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    //вызывается, когда фрагмент видим для пользователя
    public void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }
}