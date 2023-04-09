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

import com.squareup.picasso.Picasso;
import com.ute.project2.R;
import com.ute.project2.event.SelectGenreListener;
import com.ute.project2.model.Genre;

import java.io.File;
import java.util.List;


public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    private final List<Genre> genreList;
    private final Context context;
    private final SelectGenreListener selectGenreListener;

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
        File cacheDir = context.getCacheDir();
        new File(cacheDir, "genre-image-picasso-cache");
        Picasso.get().load(genre.getGenreImage()).into(holder.imageViewCategory);
        holder.textViewCategory.setText(genre.getGenreName());
        holder.cardViewCategory.setOnClickListener(view -> selectGenreListener.onItemClicked(genre));
    }

    @Override
    public int getItemCount() {
        return genreList == null ? 0 : genreList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewCategory;
        private final TextView textViewCategory;
        private final CardView cardViewCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCategory = itemView.findViewById(R.id.image_view_genre);
            textViewCategory = itemView.findViewById(R.id.text_view_genre);
            cardViewCategory = itemView.findViewById(R.id.card_view_genre);
        }
    }
}
