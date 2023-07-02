package com.example.school553.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school553.R;
import com.example.school553.model.LessonsClassRecyclerModel;
import com.example.school553.model.LessonsMainRecyclerModel;

import java.util.List;

public class LessonsClassRecyclerAdapter extends RecyclerView.Adapter<LessonsClassRecyclerAdapter.LessonsClassViewHolder> {

    private Context context;
    private List<LessonsClassRecyclerModel> lessonsListModel; //список обьектов

    public LessonsClassRecyclerAdapter(Context context, List<LessonsClassRecyclerModel> lessonsListModel) {
        this.context = context;
        this.lessonsListModel = lessonsListModel;
    }

    @NonNull
    @Override
    public LessonsClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lessonsItemView = LayoutInflater.from(context).inflate(R.layout.lessons_class_item, parent, false);
        return new LessonsClassRecyclerAdapter.LessonsClassViewHolder(lessonsItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonsClassViewHolder holder, int position) {
        LessonsClassRecyclerModel lessonsRecyclerModel = lessonsListModel.get(position);
        holder.tvNumber.setText(lessonsRecyclerModel.getNumber());
        holder.tvClassTitle.setText(lessonsRecyclerModel.getTitle());
        holder.tvOffice.setText(lessonsRecyclerModel.getOffice());
    }

    @Override
    public int getItemCount() {
        return lessonsListModel.size();
    }

    //класс, объекты которого адаптер использует для хранения и визуализации элементов списка
    public static class LessonsClassViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber;
        TextView tvClassTitle;
        TextView tvOffice;
        public LessonsClassViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_lessons_number);
            tvClassTitle = itemView.findViewById(R.id.tv_lessons_title);
            tvOffice = itemView.findViewById(R.id.tv_lessons_office);
        }
    }
}
