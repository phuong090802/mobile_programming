package com.ute.project2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ute.project2.R;
import com.ute.project2.event.ItemLibraryListener;
import com.ute.project2.model.ItemLibrary;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
    ItemLibraryListener itemLibraryListener;
    Context context;
    List<ItemLibrary> itemLibraries;

    public LibraryAdapter(Context context, ItemLibraryListener itemLibraryListener, List<ItemLibrary> itemLibraries) {
        this.itemLibraryListener = itemLibraryListener;
        this.context = context;
        this.itemLibraries = itemLibraries;
    }

    @NonNull
    @Override
    public LibraryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_library_item, parent, false);
        return new LibraryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryAdapter.ViewHolder holder, int position) {
        ItemLibrary itemLibrary = itemLibraries.get(position);
        holder.textView.setText(itemLibrary.getText());
        holder.textView.setCompoundDrawablePadding(16);
        holder.textView.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(context, itemLibrary.getImage()), null, null, null);
        if(itemLibraryListener != null) {
            holder.cardView.setOnClickListener(view -> itemLibraryListener.onItemLibraryOnClick(itemLibrary));
        }
    }

    @Override
    public int getItemCount() {
        return itemLibraries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_lb);
            cardView = itemView.findViewById(R.id.card_view_library);
        }
    }
}
