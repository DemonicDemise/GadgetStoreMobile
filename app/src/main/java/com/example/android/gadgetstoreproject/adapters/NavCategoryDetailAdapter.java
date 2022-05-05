package com.example.android.gadgetstoreproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.models.NavCategoryDetailModel;

import java.util.List;

public class NavCategoryDetailAdapter extends RecyclerView.Adapter<NavCategoryDetailAdapter.ViewHolder> {

    private Context context;
    private List<NavCategoryDetailModel> navCategoryDetailModelList;

    public NavCategoryDetailAdapter(Context context, List<NavCategoryDetailModel> navCategoryDetailModelList) {
        this.context = context;
        this.navCategoryDetailModelList = navCategoryDetailModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_category_detail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(navCategoryDetailModelList.get(position).getImg_url()).into(holder.navCatDetailImg);
        holder.name.setText(navCategoryDetailModelList.get(position).getName());
        holder.description.setText(navCategoryDetailModelList.get(position).getDescription());
        holder.price.setText(navCategoryDetailModelList.get(position).getPrice() + "$");
    }

    @Override
    public int getItemCount() {
        return navCategoryDetailModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView navCatDetailImg;
        TextView name, description, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            navCatDetailImg = itemView.findViewById(R.id.nav_category_detail_img);
            name = itemView.findViewById(R.id.nav_category_detail_name);
            description = itemView.findViewById(R.id.nav_category_detail_description);
            price = itemView.findViewById(R.id.nav_category_detail_price);
        }
    }
}
