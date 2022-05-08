package com.example.android.gadgetstoreproject.ui.order;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.android.gadgetstoreproject.R;

import org.w3c.dom.Text;

public class UserOrderFragment extends Fragment {

    private TextView orderText;
    private LottieAnimationView orderLottie;

    public UserOrderFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_order, container, false);

        orderText = root.findViewById(R.id.new_order_text);
        orderLottie = root.findViewById(R.id.new_order_lottie);

        return root;
    }
}
