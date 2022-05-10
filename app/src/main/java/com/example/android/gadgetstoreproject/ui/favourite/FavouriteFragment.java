package com.example.android.gadgetstoreproject.ui.favourite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.adapters.FavouriteAdapter;
import com.example.android.gadgetstoreproject.adapters.NavCategoryAdapter;
import com.example.android.gadgetstoreproject.models.FavouriteModel;
import com.example.android.gadgetstoreproject.models.NavCategoryModel;
import com.example.android.gadgetstoreproject.models.UserCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<FavouriteModel> favouriteModelList;
    private FavouriteAdapter favouriteAdapter;

    private FirebaseFirestore mDb;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private String documentId;


    public FavouriteFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_favourite, container, false);

        mDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.fav_rec);
        recyclerView.setVisibility(View.GONE);

        progressBar = root.findViewById(R.id.favourite_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        favouriteModelList = new ArrayList<>();
        favouriteAdapter = new FavouriteAdapter(getActivity(), favouriteModelList);
        recyclerView.setAdapter(favouriteAdapter);

        if(mAuth.getCurrentUser() != null) {
            mDb.collection("CurrentUser").document(mAuth.getCurrentUser().getUid())
                    .collection("FavouriteProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                            documentId = documentSnapshot.getId();

                            FavouriteModel favouriteModel = documentSnapshot.toObject(FavouriteModel.class);

                            favouriteModel.setDocumentId(documentId);

                            favouriteModelList.add(favouriteModel);
                            favouriteAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }

        return root;
    }
}
