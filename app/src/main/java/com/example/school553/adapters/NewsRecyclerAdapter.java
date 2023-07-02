package com.example.school553.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school553.R;
import com.example.school553.model.NewsRecyclerModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {

    private Context context;
    private List<NewsRecyclerModel> newsItemList;
    private ItemClickListener clickListener;


    public static String selectWords(int constCount, String wordsString) {
        if(wordsString.isEmpty()) {
            return "";
        }
    // Используем список, поскольку изначально неизвестно количество слов.
        ArrayList<String> words = new ArrayList<>();
        String wordsFinal = "";

        StringBuilder currentWord = new StringBuilder();
        for (Character letter : wordsString.toCharArray()) {
            // Прерываем формирование слова, если встретили пробел
            if (letter.equals(' ')) {
                if (currentWord.length() > 0) {
                    // Записываем слово в список, если оно содержит хотя бы 1 символ
                    words.add(currentWord.toString());
                    currentWord = new StringBuilder();
                }
            } else {
                currentWord.append(letter);
            }
        }
        // При завершении не забываем, что в билдере могло остаться слово.
        if (currentWord.length() > 0) {
            words.add(currentWord.toString());
        }

        //вывод определенного количества слов
        for (int i = 0 ; i < constCount; i++){
            wordsFinal += words.get(i);
            wordsFinal+=" ";
        }
        wordsFinal += "..."; //многоточие в конце текста

        return wordsFinal;
    }


    public NewsRecyclerAdapter(Context context, List<NewsRecyclerModel> newsItemList, ItemClickListener clickListener) {
        this.context = context;
        this.newsItemList = newsItemList.stream().filter((NewsRecyclerModel model) -> {
            return !model.getTitle().isEmpty();
        }).collect(Collectors.toList());
        this.clickListener = clickListener;
    }

    public interface ItemClickListener {
        void onItemClick(NewsRecyclerModel newsRecyclerModel);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newsItemView = LayoutInflater.from(context).inflate(R.layout.news_row_item, parent, false);
        return new NewsViewHolder(newsItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsRecyclerModel newsRecyclerModel = newsItemList.get(position);

        holder.tvTitle.setText(newsItemList.get(position).getTitle());
        //String count = String.valueOf(countWords(newsItemList.get(position).getExcerpt()));
       // holder.tvCount.setText(newsItemList.get(position).getExcerpt());
        holder.tvCount.setText(selectWords(20, newsItemList.get(position).getExcerpt()));

        holder.itemView.setOnClickListener(v -> clickListener.onItemClick(newsRecyclerModel));
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvCount;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.titleTextView);
            tvCount = itemView.findViewById(R.id.descriptionTextView);

        }
    }
}
