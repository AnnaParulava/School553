package com.example.school553.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school553.R;
import com.example.school553.model.ContactsRecyclerModel;
import com.example.school553.model.LessonsClassRecyclerModel;
import com.example.school553.model.LessonsMainRecyclerModel;

import java.util.ArrayList;
import java.util.List;

public class LessonsMainRecyclerAdapter extends RecyclerView.Adapter<LessonsMainRecyclerAdapter.LessonsMainViewHolder> {

    private Context context;
    private List<LessonsMainRecyclerModel> lessonsListModel; //список обьектов
    LinearLayoutManager layoutManager;
    LessonsClassRecyclerAdapter adapter;

    //конструктор
    public LessonsMainRecyclerAdapter(Context context, List<LessonsMainRecyclerModel> lessonsListModel) {
        this.context = context;
        this.lessonsListModel = lessonsListModel;
    }

    @NonNull
    @Override
    //возвращает объект ViewHolder, который будет хранить данные по одному объекту LessonsMainRecyclerModel
    public LessonsMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lessonsItemView = LayoutInflater.from(context).inflate(R.layout.lessons_row_item, parent, false);
        return new LessonsMainRecyclerAdapter.LessonsMainViewHolder(lessonsItemView);
    }

    @Override
    //выполняет привязку объекта ViewHolder к объекту LessonsMainRecyclerModel по определенной позиции.
    public void onBindViewHolder(@NonNull LessonsMainViewHolder holder, int position) {
        LessonsMainRecyclerModel lessonsMainRecyclerModel = lessonsListModel.get(position);

        holder.tvClassTitle.setText(lessonsMainRecyclerModel.getClass_title());
        holder.tvWeekday.setText(lessonsMainRecyclerModel.getWeekday());
        setItemRecycler(holder.itemRecycler, lessonsMainRecyclerModel.getLessonsItemList());
    }

    @Override
    // возвращает количество объектов в списке
    public int getItemCount() {
        return lessonsListModel.size();
    }

    //класс, объекты которого адаптер использует для хранения и визуализации элементов списка
    public static class LessonsMainViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassTitle;
        TextView tvWeekday;
        RecyclerView itemRecycler;
        public LessonsMainViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClassTitle = itemView.findViewById(R.id.tv_class_title);
            tvWeekday = itemView.findViewById(R.id.tv_weekday);
            itemRecycler = itemView.findViewById(R.id.rv_lessons_class);
        }
    }

    private void setItemRecycler(RecyclerView recyclerView, List<LessonsClassRecyclerModel> lessonsItemList){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        LessonsClassRecyclerAdapter adapter = new LessonsClassRecyclerAdapter(context, lessonsItemList);
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();

    }
}
