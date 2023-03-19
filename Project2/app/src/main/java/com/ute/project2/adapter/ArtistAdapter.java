package com.ute.project2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ute.project2.R;
import com.ute.project2.model.Artist;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    List<Artist> artistList;
    Context context;

    public ArtistAdapter(Context context, List<Artist> artistList) {
        this.artistList = artistList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArtistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_artist_item, parent, false);
        return new ArtistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.ViewHolder holder, int position) {
        Artist artist = artistList.get(position);
        holder.tvArtistName.setText(artist.getArtistName());
        holder.ivImageArtist.setImageResource(artist.getImageArtist());
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvArtistName;
        ImageView ivImageArtist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvArtistName = itemView.findViewById(R.id.tvArtistName);
            ivImageArtist = itemView.findViewById(R.id.ivImageArtist);
        }
    }
}
