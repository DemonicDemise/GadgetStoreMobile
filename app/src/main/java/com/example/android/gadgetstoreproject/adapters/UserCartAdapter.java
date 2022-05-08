package com.example.android.gadgetstoreproject.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.models.UserCartModel;
import com.example.android.gadgetstoreproject.ui.cart.UserCartFragment;
import com.example.android.gadgetstoreproject.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserCartAdapter extends RecyclerView.Adapter<UserCartAdapter.ViewHolder> {

    private Context context;
    private List<UserCartModel> userCartModelList;
    private TextView totalAmount;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(userCartModelList.get(position).getProductName());
        holder.price.setText(userCartModelList.get(position).getProductPrice() + "$");
        holder.date.setText(userCartModelList.get(position).getCurrentDate());
        holder.time.setText(userCartModelList.get(position).getCurrentTime());
        holder.quantity.setText(userCartModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(userCartModelList.get(position).getTotalPrice()) + "$");

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth.getCurrentUser() != null) {
                    mDb.collection("UserCollection").document(mAuth.getCurrentUser().getUid())
                            .collection("AddToCart")
                            .document(userCartModelList.get(position).getDocumentId())
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        userCartModelList.remove(userCartModelList.get(position));
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Item deleted", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(context, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(context, "You are not logged in! \n Please log in!", Toast.LENGTH_LONG).show();
                }
            }
        });
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
