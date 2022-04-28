package com.example.android.gadgetstoreproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.adapters.PopularAdapters;
import com.example.android.gadgetstoreproject.models.PopularModel;

import java.util.List;

public class HomeFragment extends Fragment {

    //popular list
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container,false);

        return root;
    }
}