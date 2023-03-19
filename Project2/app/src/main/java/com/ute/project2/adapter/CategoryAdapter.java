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
import com.ute.project2.SelectCategoryListener;
import com.ute.project2.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<Category> categoryList;
    Context context;
    SelectCategoryListener selectCategoryListener;

    public CategoryAdapter(Context context, List<Category> categoryList, SelectCategoryListener selectCategoryListener) {
        this.context = context;
        this.categoryList = categoryList;
        this.selectCategoryListener = selectCategoryListener;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.ivCategory.setImageResource(category.getCategoryImage());
        holder.tvCategory.setText(category.getCategoryName());
        holder.cardViewCategory.setOnClickListener(view -> selectCategoryListener.onItemClicked(categoryList.get(position)));
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCategory;
        TextView tvCategory;
        CardView cardViewCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategory = itemView.findViewById(R.id.ivCategory);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            cardViewCategory = itemView.findViewById(R.id.cardViewCategory);
        }
    }
}
