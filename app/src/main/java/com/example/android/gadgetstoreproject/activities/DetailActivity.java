package com.example.android.gadgetstoreproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.authentication.LoginActivity;
import com.example.android.gadgetstoreproject.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    private ImageView detailedImg;
    private TextView price, rating, description;
    private Button addToCart;
    private Toolbar detailedToolbar;

    private ViewAllModel viewAllModel = null;

    private ImageView addItemImg, removeItemImg;

    private TextView quantity;
    private int totalQuantity = 1;
    private int totalPrice = 1;

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    private LottieAnimationView lottieLike, lottieRatingBar;
    private boolean btnLike = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        detailedToolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(detailedToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof ViewAllModel){
            viewAllModel = (ViewAllModel) object;
        }


        detailedImg = findViewById(R.id.img_detail);

        price = findViewById(R.id.price_detail);
        rating = findViewById(R.id.datailed_rating);
        description = findViewById(R.id.detail_desc_text);

        lottieRatingBar = findViewById(R.id.lottie_rating_bar);

        quantity= findViewById(R.id.detail_quantity);

        lottieLike = findViewById(R.id.lottie_like);

        if(viewAllModel != null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText(viewAllModel.getPrice() + "$");

            totalPrice = totalQuantity * Integer.valueOf(viewAllModel.getPrice());
        }

        addToCart = findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedToCart();
            }
        });

        addItemImg = findViewById(R.id.add_item_detail);
        addItemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity < 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = totalQuantity * Integer.valueOf(viewAllModel.getPrice());
                }
            }
        });

        removeItemImg = findViewById(R.id.remove_item_detail);
        removeItemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = totalQuantity * Integer.valueOf(viewAllModel.getPrice());
                }
            }
        });

        detailedToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        lottieRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnLike) {
                    lottieRatingBar.playAnimation();
                    btnLike = true;
                } else{
                    lottieRatingBar.playAnimation();
                    btnLike = false;
                }
            }
        });
        btnLike = false;
        lottieLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnLike){
                    lottieLike.playAnimation();
                    btnLike = true;

                }else{
                    lottieLike.playAnimation();
                    btnLike = false;
                }
                favouriteProduct();
            }
        });
    }

    private void favouriteProduct() {
        final HashMap<String, Object> favMap = new HashMap<>();

        favMap.put("favName", viewAllModel.getName());
        favMap.put("favDesc", viewAllModel.getDescription());
        favMap.put("favPrice", price.getText().toString());
        favMap.put("favImg", viewAllModel.getImg_url());

        if(mAuth.getCurrentUser() != null) {
            mFirestore.collection("CurrentUser").document(mAuth.getCurrentUser().getUid())
                    .collection("FavouriteProducts").add(favMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    Toast.makeText(DetailActivity.this, "You may seen in favourites section", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        } else{
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            Toast.makeText(getApplicationContext(), "Wait you are not logged in!", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void addedToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productName", viewAllModel.getName());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        if(mAuth.getCurrentUser() != null) {
            mFirestore.collection("CurrentUser").document(mAuth.getCurrentUser().getUid())
                    .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    Toast.makeText(DetailActivity.this, "Added To A Cart", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        } else{
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            Toast.makeText(getApplicationContext(), "Wait you are not logged in!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}