package com.example.android.gadgetstoreproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.activities.ViewAllActivity;
import com.example.android.gadgetstoreproject.models.RecommendedModel;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    private Context context;
    private List<RecommendedModel> recommendedModelsList;

    public RecommendedAdapter(Context context, List<RecommendedModel> recommendedModelsList) {
        this.context = context;
        this.recommendedModelsList = recommendedModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(recommendedModelsList.get(position).getImg_url()).into(holder.recImg);
        holder.name.setText(recommendedModelsList.get(position).getName());
        holder.description.setText(recommendedModelsList.get(position).getDescription());
        holder.recRating.setText(recommendedModelsList.get(position).getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type", recommendedModelsList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommendedModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView recImg;
        TextView name, description, recRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recImg = itemView.findViewById(R.id.rec_img);
            name = itemView.findViewById(R.id.rec_name);
            description = itemView.findViewById(R.id.rec_desc);
            recRating = itemView.findViewById(R.id.rec_rating);
        }
    }
}
