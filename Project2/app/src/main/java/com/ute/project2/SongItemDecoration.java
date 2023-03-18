package com.ute.project2;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongItemDecoration extends RecyclerView.ItemDecoration {
    private final int dividerHeight;
    private final int size;

    public SongItemDecoration(int dividerHeight, int size) {
        this.dividerHeight = dividerHeight;
        this.size = size;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition != size - 1) {
            outRect.bottom = dividerHeight;
        }
    }
}
