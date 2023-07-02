package com.example.school553.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school553.R;
import com.example.school553.model.ContactsRecyclerModel;
import com.example.school553.model.NewsRecyclerModel;

import java.util.List;

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.ContactsViewHolder>{

    private Context context;
    private List<ContactsRecyclerModel> contactsItemList; //список обьектов

    //конструктор
    public ContactsRecyclerAdapter(Context context, List<ContactsRecyclerModel> contactsItemList) {
        this.context = context;
        this.contactsItemList = contactsItemList;
    }

    @NonNull
    @Override
    //возвращает объект ViewHolder, который будет хранить данные по одному объекту ContactsRecyclerModel
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactsItemView = LayoutInflater.from(context).inflate(R.layout.contacts_row_item, parent, false);
        return new ContactsViewHolder(contactsItemView);
    }

    @Override
    //выполняет привязку объекта ViewHolder к объекту ContactsRecyclerModel по определенной позиции.
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        ContactsRecyclerModel contactsRecyclerModel = contactsItemList.get(position);

        holder.tvName.setText(contactsRecyclerModel.getName());
        holder.tvPatronymic.setText(contactsRecyclerModel.getPatronymic());
        holder.tvSurname.setText(contactsRecyclerModel.getSurname());
        holder.tvPosition.setText(contactsRecyclerModel.getPosition());
        holder.tvPhone.setText(contactsRecyclerModel.getPhone());
        holder.tvWeekday.setText(contactsRecyclerModel.getWeekday());
        holder.tvWorkTime.setText(contactsRecyclerModel.getOffice_hours());

    }

    @Override
    // возвращает количество объектов в списке
    public int getItemCount() {
        return contactsItemList.size();
    }

    //класс, объекты которого адаптер использует для хранения и визуализации элементов списка
    public class ContactsViewHolder extends RecyclerView.ViewHolder {

        TextView tvSurname;
        TextView tvName;
        TextView tvPatronymic;
        TextView tvPosition;
        TextView tvPhone;
        TextView tvWeekday;
        TextView tvWorkTime;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSurname = itemView.findViewById(R.id.tvSurname);
            tvName = itemView.findViewById(R.id.tvName);
            tvPatronymic = itemView.findViewById(R.id.tvPatronymic);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvWeekday = itemView.findViewById(R.id.tvWeekday);
            tvWorkTime = itemView.findViewById(R.id.tvWorkTime);
        }
    }
}
