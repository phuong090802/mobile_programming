package com.ute.project2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ute.project2.R;
import com.ute.project2.event.SelectGenreListener;
import com.ute.project2.model.Genre;

import java.util.List;


public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    List<Genre> genreList;
    Context context;
    SelectGenreListener selectGenreListener;

    public GenreAdapter(Context context, List<Genre> genreList, SelectGenreListener selectGenreListener) {
        this.genreList = genreList;
        this.context = context;
        this.selectGenreListener = selectGenreListener;
    }

    @NonNull
    @Override
    public GenreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_genre_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.ViewHolder holder, int position) {
        Genre genre = genreList.get(position);
        holder.ivCategory.setImageResource(genre.getGenreImage());
        holder.tvCategory.setText(genre.getGenreName());
        holder.cardViewCategory.setOnClickListener(view -> selectGenreListener.onItemClicked(genreList.get(position)));
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategory;
        TextView tvCategory;
        CardView cardViewCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategory = itemView.findViewById(R.id.ivGenre);
            tvCategory = itemView.findViewById(R.id.tvGenre);
            cardViewCategory = itemView.findViewById(R.id.card_view_genre);
        }
    }
}
