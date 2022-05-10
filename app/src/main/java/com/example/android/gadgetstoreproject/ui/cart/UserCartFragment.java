package com.example.android.gadgetstoreproject.ui.cart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.activities.PlaceOrderActivity;
import com.example.android.gadgetstoreproject.adapters.UserCartAdapter;
import com.example.android.gadgetstoreproject.models.UserCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserCartFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDb;

    private RecyclerView recyclerView;
    private UserCartAdapter userCartAdapter;
    private List<UserCartModel> userCartModelList;

    private ProgressBar progressBar;
    private TextView overTotalAmount;
    private Button buyNow;

    private String documentId;

    public UserCartFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        mDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = root.findViewById(R.id.cart_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        buyNow = root.findViewById(R.id.cart_buy_now);

        overTotalAmount = root.findViewById(R.id.total_price);

        userCartModelList = new ArrayList<>();
        userCartAdapter = new UserCartAdapter(getActivity(), userCartModelList);
        recyclerView.setAdapter(userCartAdapter);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        if(mAuth.getCurrentUser() != null) {
            mDb.collection("CurrentUser").document(mAuth.getCurrentUser().getUid())
                    .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                            documentId = documentSnapshot.getId();

                            UserCartModel userCartModel = documentSnapshot.toObject(UserCartModel.class);

                            userCartModel.setDocumentId(documentId);

                            userCartModelList.add(userCartModel);
                            userCartAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        calculateSumList(userCartModelList);
                    }
                }
            });
        }

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getContext(), R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getContext())
                        .inflate(
                                R.layout.checkout_bottom_layout,
                                view.findViewById(R.id.checkout_bottom_container)
                        );
                    bottomSheetView.findViewById(R.id.place_order_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(), "Place Order", Toast.LENGTH_LONG).show();
                            bottomSheetDialog.dismiss();
                            Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                            intent.putExtra("itemList", (Serializable) userCartModelList);
                            startActivity(intent);
                        }
                    });
                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();
            }
        });

        return root;
    }

    public void calculateSumList(List<UserCartModel> list) {
        double totalSum = 0.0;
        for(UserCartModel model: list){
            totalSum += model.getTotalPrice();
        }
        overTotalAmount.setText("Total Sum: " + totalSum);
    }
}
