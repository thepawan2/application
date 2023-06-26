package com.example.newappwork;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class FragmentBuyCourse extends Fragment {



    public FragmentBuyCourse() {
        // Required empty public constructor
    }

    RecyclerView recyclerVie;
    myadapter adapter;
   // ArrayList<myadapter> myadapters = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_course, container, false);
        recyclerVie = (RecyclerView) view.findViewById(R.id.recsyllabusdown);

        recyclerVie.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<syllabusmodel> options = new FirebaseRecyclerOptions.Builder<syllabusmodel>()
                .setQuery (FirebaseDatabase.getInstance().getReference().child("mydocuments"),syllabusmodel.class)
                .build();



        adapter = new myadapter(options);
        recyclerVie.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }


}