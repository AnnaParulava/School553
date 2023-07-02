package com.example.school553.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school553.R;
import com.example.school553.model.OlympicRecyclerModel;

import java.util.List;

public class OlympicRecyclerAdapter extends RecyclerView.Adapter<OlympicRecyclerAdapter.OlympicViewHolder> {

    private Context context;
    private List<OlympicRecyclerModel> olympicItemList; //список обьектов

    //конструктор
    public OlympicRecyclerAdapter(Context context, List<OlympicRecyclerModel> olympicItemList) {
        this.context = context;
        this.olympicItemList = olympicItemList;
    }

    @NonNull
    @Override
    //возвращает объект ViewHolder, который будет хранить данные по одному объекту OlympicRecyclerModel
    public OlympicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View olympicItemList = LayoutInflater.from(context).inflate(R.layout.exams_row_item, parent, false);
        return new OlympicRecyclerAdapter.OlympicViewHolder(olympicItemList);
    }

    @Override
    public void onBindViewHolder(@NonNull OlympicViewHolder holder, int position) {
        OlympicRecyclerModel olympicRecyclerModel = olympicItemList.get(position);

        holder.tvSubjectTitle.setText(olympicRecyclerModel.getTitle());
        holder.tvSubjectDate.setText(olympicRecyclerModel.getDate());
    }

    @Override
    // возвращает количество объектов в списке
    public int getItemCount() {
        return olympicItemList.size();
    }

    //класс, объекты которого адаптер использует для хранения и визуализации элементов списка
    public class OlympicViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubjectTitle;
        TextView tvSubjectDate;

        public OlympicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubjectTitle = itemView.findViewById(R.id.tv_subject_title);
            tvSubjectDate = itemView.findViewById(R.id.tv_subject_date);
        }
    }
}
