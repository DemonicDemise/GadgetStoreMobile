package com.example.android.gadgetstoreproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.android.gadgetstoreproject.models.ViewAllModel;

public class DetailActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView price, rating, description;
    Button addToCart;
    ImageView addItem, removeItem;
    Toolbar detailedToolbar;

    ViewAllModel viewAllModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailedToolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(detailedToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof ViewAllModel){
            viewAllModel = (ViewAllModel) object;

        }

        detailedImg = findViewById(R.id.img_detail);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);

        price = findViewById(R.id.price_detail);
        rating = findViewById(R.id.datailed_rating);
        description = findViewById(R.id.detail_desc_text);

        if(viewAllModel != null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText("Price: $" + viewAllModel.getPrice());
        }

        addToCart = findViewById(R.id.add_to_cart);
    }
}