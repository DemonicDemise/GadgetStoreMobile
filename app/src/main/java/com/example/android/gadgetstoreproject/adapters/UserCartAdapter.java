package com.example.android.gadgetstoreproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.models.UserCartModel;

import java.util.List;

public class UserCartAdapter extends RecyclerView.Adapter<UserCartAdapter.ViewHolder> {

    private Context context;
    private List<UserCartModel> userCartModelList;
    int totalPrice = 0;

    public UserCartAdapter(Context context, List<UserCartModel> userCartModelList) {
        this.context = context;
        this.userCartModelList = userCartModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(userCartModelList.get(position).getProductName());
        holder.price.setText(userCartModelList.get(position).getProductPrice());
        holder.date.setText(userCartModelList.get(position).getCurrentDate());
        holder.time.setText(userCartModelList.get(position).getCurrentTime());
        holder.quantity.setText(userCartModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(userCartModelList.get(position).getTotalPrice()));

        //Pass Total Price to User Cart Fragment
        totalPrice = totalPrice + userCartModelList.get(position).getTotalPrice();
        Intent intent = new Intent("UserTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return userCartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, date, time, quantity, totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.product_date);
            time = itemView.findViewById(R.id.product_time);
            quantity = itemView.findViewById(R.id.product_total_quantity);
            totalPrice = itemView.findViewById(R.id.product_total_price);
        }
    }
}
