package com.example.android.gadgetstoreproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.models.UserCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserCartAdapter extends RecyclerView.Adapter<UserCartAdapter.ViewHolder> {

    private Context context;
    private List<UserCartModel> userCartModelList;
    int totalPrice = 0;
    FirebaseFirestore mDb;
    FirebaseAuth mAuth;


    public UserCartAdapter(Context context, List<UserCartModel> userCartModelList) {
        this.context = context;
        this.userCartModelList = userCartModelList;

        mDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
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

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDb.collection("UserCollection").document(mAuth.getCurrentUser().getUid())
                        .collection("AddToCart")
                        .document(userCartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    userCartModelList.remove(userCartModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item deleter", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

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
        ImageView deleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.product_date);
            time = itemView.findViewById(R.id.product_time);
            quantity = itemView.findViewById(R.id.product_total_quantity);
            totalPrice = itemView.findViewById(R.id.product_total_price);
            deleteItem = itemView.findViewById(R.id.delete_cart_image);
        }
    }
}
