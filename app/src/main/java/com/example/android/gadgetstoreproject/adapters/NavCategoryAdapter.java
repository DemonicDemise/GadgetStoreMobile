package com.example.android.gadgetstoreproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.activities.NavCategoryActivity;
import com.example.android.gadgetstoreproject.models.NavCategoryModel;

import java.util.List;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.ViewHolder> {

    private Context context;
    List<NavCategoryModel> navCategoryModelsList;

    public NavCategoryAdapter(Context context, List<NavCategoryModel> navCategoryModelsList) {
        this.context = context;
        this.navCategoryModelsList = navCategoryModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_cat_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(navCategoryModelsList.get(position).getImg_url()).into(holder.catImageView);
        holder.name.setText(navCategoryModelsList.get(position).getName());
        holder.discount.setText(navCategoryModelsList.get(position).getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NavCategoryActivity.class);
                intent.putExtra("type", navCategoryModelsList.get(position).getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return navCategoryModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImageView;
        TextView name, discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImageView = itemView.findViewById(R.id.cat_nav_img);
            name = itemView.findViewById(R.id.nav_cat_name);
            discount = itemView.findViewById(R.id.nav_cat_discount);
        }
    }
}
