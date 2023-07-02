package com.example.school553.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school553.R;
import com.example.school553.model.ClassesRecyclerModel;

import java.util.List;

public class ClassesRecyclerAdapter extends RecyclerView.Adapter<ClassesRecyclerAdapter.ClassesViewHolder>{

    private Context context;
    private List<ClassesRecyclerModel> classesItemList; //список обьектов

    //конструктор
    public ClassesRecyclerAdapter(Context context, List<ClassesRecyclerModel> classesItemList) {
        this.context = context;
        this.classesItemList = classesItemList;
    }

    @NonNull
    @Override
    //возвращает объект ViewHolder, который будет хранить данные по одному объекту ClassesRecyclerModel
    public ClassesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View classesItemView = LayoutInflater.from(context).inflate(R.layout.classes_row_item, parent, false);
        return new ClassesRecyclerAdapter.ClassesViewHolder(classesItemView);    }

    @Override
    //выполняет привязку объекта ViewHolder к объекту ClassesRecyclerModel по определенной позиции.
    public void onBindViewHolder(@NonNull ClassesViewHolder holder, int position) {
        ClassesRecyclerModel classesRecyclerModel = classesItemList.get(position);

        holder.tvName.setText(classesItemList.get(position).getName());
        holder.tvPatronymic.setText(classesItemList.get(position).getPatronymic());
        holder.tvSurname.setText(classesItemList.get(position).getSurname());
        holder.tvClass.setText(classesItemList.get(position).getClass_title());
        holder.tvOffice.setText(classesItemList.get(position).getOffice());
    }

    @Override
    // возвращает количество объектов в списке
    public int getItemCount() {
        return classesItemList.size();
    }

    //класс, объекты которого адаптер использует для хранения и визуализации элементов списка
    public class ClassesViewHolder extends RecyclerView.ViewHolder {
        TextView tvSurname;
        TextView tvName;
        TextView tvPatronymic;
        TextView tvClass;
        TextView tvOffice;

        public ClassesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSurname = itemView.findViewById(R.id.tvSurname);
            tvName = itemView.findViewById(R.id.tvName);
            tvPatronymic = itemView.findViewById(R.id.tvPatronymic);
            tvClass = itemView.findViewById(R.id.tvClass);
            tvOffice = itemView.findViewById(R.id.tvOffice);
        }
    }
}
