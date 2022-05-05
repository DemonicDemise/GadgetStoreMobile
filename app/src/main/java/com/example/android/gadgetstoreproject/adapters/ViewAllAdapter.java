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
import com.example.android.gadgetstoreproject.activities.DetailActivity;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.models.ViewAllModel;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

    Context context;
    List<ViewAllModel> viewAllModelsList;

    public ViewAllAdapter(Context context, List<ViewAllModel> viewAllModelsList) {
        this.context = context;
        this.viewAllModelsList = viewAllModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(viewAllModelsList.get(position).getImg_url()).into(holder.viewAllImage);
        holder.name.setText(viewAllModelsList.get(position).getName());
        holder.description.setText(viewAllModelsList.get(position).getDescription());
        holder.rating.setText(viewAllModelsList.get(position).getRating());
        holder.price.setText(viewAllModelsList.get(position).getPrice() + "/$");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail", viewAllModelsList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewAllModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView viewAllImage;
        TextView name, description, price, rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewAllImage = itemView.findViewById(R.id.view_all_img);
            name = itemView.findViewById(R.id.view_all_name);
            description = itemView.findViewById(R.id.view_all_description);
            price = itemView.findViewById(R.id.view_all_price);
            rating = itemView.findViewById(R.id.view_all_rating);
        }
    }
}