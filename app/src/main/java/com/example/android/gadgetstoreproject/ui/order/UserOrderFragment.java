package com.example.android.gadgetstoreproject.ui.order;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.adapters.FavouriteAdapter;
import com.example.android.gadgetstoreproject.adapters.OrderAdapter;
import com.example.android.gadgetstoreproject.models.FavouriteModel;
import com.example.android.gadgetstoreproject.models.OrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UserOrderFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<OrderModel> orderModelList;
    private OrderAdapter orderAdapter;

    private FirebaseFirestore mDb;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private String documentId;

    public UserOrderFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_order, container, false);
        mDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.order_rec);
        recyclerView.setVisibility(View.GONE);

        progressBar = root.findViewById(R.id.order_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        orderModelList = new ArrayList<>();
        orderAdapter = new OrderAdapter(getActivity(), orderModelList);
        recyclerView.setAdapter(orderAdapter);

        if(mAuth.getCurrentUser() != null) {
            mDb.collection("CurrentUser").document(mAuth.getCurrentUser().getUid())
                    .collection("UserOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                            documentId = documentSnapshot.getId();

                            OrderModel orderModel = documentSnapshot.toObject(OrderModel.class);

                            orderModel.setDocumentId(documentId);

                            orderModelList.add(orderModel);
                            orderAdapter.notifyDataSetChanged();
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
