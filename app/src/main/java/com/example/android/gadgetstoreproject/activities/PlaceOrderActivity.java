package com.example.android.gadgetstoreproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.example.android.gadgetstoreproject.MainActivity;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.authentication.LoginActivity;
import com.example.android.gadgetstoreproject.models.UserCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDb;
    private LottieAnimationView orderAcceptedLottie;
    private ConstraintLayout constraintBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        mAuth= FirebaseAuth.getInstance();
        mDb = FirebaseFirestore.getInstance();
        orderAcceptedLottie = findViewById(R.id.order_accepted_lottie);
        constraintBack = findViewById(R.id.constraint_back);

        orderAcceptedLottie.playAnimation();

        List<UserCartModel> list = (ArrayList<UserCartModel>) getIntent().getSerializableExtra("itemList");

        if(list != null && list.size() > 0){
            for(UserCartModel model : list){
                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("orderName", model.getProductName());
                cartMap.put("orderPrice", model.getProductPrice());
                cartMap.put("orderDate", model.getCurrentDate());
                cartMap.put("orderTime", model.getCurrentTime());
                cartMap.put("totalQuantity", model.getTotalQuantity());
                cartMap.put("totalPrice", model.getTotalPrice());

                if(mAuth.getCurrentUser() != null) {
                    mDb.collection("CurrentUser").document(mAuth.getCurrentUser().getUid())
                            .collection("UserOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(PlaceOrderActivity.this, "Your Order Has Been Placed", Toast.LENGTH_LONG).show();
                        }
                    });
                } else{
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    Toast.makeText(getApplicationContext(), "Wait you are not logged in!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }

        constraintBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}