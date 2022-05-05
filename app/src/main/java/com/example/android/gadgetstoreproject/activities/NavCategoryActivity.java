package com.example.android.gadgetstoreproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.adapters.NavCategoryAdapter;
import com.example.android.gadgetstoreproject.adapters.NavCategoryDetailAdapter;
import com.example.android.gadgetstoreproject.models.NavCategoryDetailModel;
import com.example.android.gadgetstoreproject.models.PopularModel;
import com.example.android.gadgetstoreproject.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<NavCategoryDetailModel> list;
    private NavCategoryDetailAdapter adapter;
    private FirebaseFirestore mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);

        mDb = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.nav_category_detail);
        String type = getIntent().getStringExtra("type");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list = new ArrayList<>();
        adapter = new NavCategoryDetailAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(adapter);

        //Getting Gaming
        if(type != null && type.equalsIgnoreCase("gaming")){
            mDb.collection("NavCategoryDetailed").whereEqualTo("type","gaming")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryDetailModel navCategoryDetailModel = documentSnapshot.toObject(NavCategoryDetailModel.class);
                        list.add(navCategoryDetailModel);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
        //Getting photo_equipment_and_quadrocopters
        if(type != null && type.equalsIgnoreCase("photo_equipment_and_quadrocopters")){
            mDb.collection("NavCategoryDetailed").whereEqualTo("type","photo_equipment_and_quadrocopters")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryDetailModel navCategoryDetailModel = documentSnapshot.toObject(NavCategoryDetailModel.class);
                        list.add(navCategoryDetailModel);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}