package com.example.android.gadgetstoreproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.ViewAllActivity;
import com.example.android.gadgetstoreproject.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    List<CategoryModel> categoryList;

    public CategoryAdapter(Context context, List<CategoryModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(categoryList.get(position).getImg_url()).into(holder.categoryImg);
        holder.name.setText(categoryList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type", categoryList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImg;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryImg = itemView.findViewById(R.id.image_category);
            name = itemView.findViewById(R.id.name_category);
        }
    }
}
