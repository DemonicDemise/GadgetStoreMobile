package com.example.android.gadgetstoreproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.models.FavouriteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private Context context;
    private List<FavouriteModel> favouriteModelList;
    private FirebaseFirestore mDb;
    private FirebaseAuth mAuth;

    public FavouriteAdapter(Context context, List<FavouriteModel> favouriteModelList) {
        this.context = context;
        this.favouriteModelList = favouriteModelList;

        mDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_favourite_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(favouriteModelList.get(position).getFavImg()).into(holder.favImg);
        holder.name.setText(favouriteModelList.get(position).getFavName());
        holder.desc.setText(favouriteModelList.get(position).getFavDesc());
        holder.price.setText(favouriteModelList.get(position).getFavPrice());

        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth.getCurrentUser() != null) {
                    mDb.collection("UserCollection").document(mAuth.getCurrentUser().getUid())
                            .collection("FavouriteProducts")
                            .document(favouriteModelList.get(position).getDocumentId())
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        favouriteModelList.remove(favouriteModelList.get(position));
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
        return favouriteModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favImg, deleteImg;
        TextView name, desc, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            favImg = itemView.findViewById(R.id.favourite_img);
            name = itemView.findViewById(R.id.favourite_name);
            desc = itemView.findViewById(R.id.favourite_description);
            price = itemView.findViewById(R.id.favourite_price);
            deleteImg = itemView.findViewById(R.id.favourite_delete);
        }
    }
}
