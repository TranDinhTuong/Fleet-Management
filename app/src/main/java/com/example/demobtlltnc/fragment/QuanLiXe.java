package com.example.demobtlltnc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demobtlltnc.R;
import com.example.demobtlltnc.adapter.RecyclerViewAdapter;
import com.example.demobtlltnc.capnhatvachitiet;
import com.example.demobtlltnc.model.DoiTaiXe;
import com.example.demobtlltnc.model.DoiXe;
import com.example.demobtlltnc.model.Xe;
import com.example.demobtlltnc.model.XeContainer;
import com.example.demobtlltnc.model.XeKhach;
import com.example.demobtlltnc.model.XeTai;
import com.example.demobtlltnc.themtaixe;
import com.example.demobtlltnc.themxe;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanLiXe extends Fragment implements RecyclerViewAdapter.itemListener{
    private SearchView searchView;
    private RecyclerView recyclerView;
    private Button btnAddXe;
    private RecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private List<Xe> mList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quanlixe, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progress_bar);
        btnAddXe = view.findViewById(R.id.btn_add_xe);

        btnAddXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), themxe.class);
                startActivity(intent);
            }
        });

        mList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(mList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setList(mList);
        
        getALlXe();
        search();

        //adapter.notifyDataSetChanged();
        adapter.setItemListener(this);
    }

    private void search() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }


    public void getALlXe(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("xe");

        progressBar.setVisibility(View.VISIBLE);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Xe xe = snapshot.getValue(Xe.class);
                if(xe != null){
                    mList.add(xe);
                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Xe xe = snapshot.getValue(Xe.class);
                if(xe == null || mList.isEmpty()){
                    return;
                }

                for(int i = 0; i < mList.size(); i++){
                    Xe temp = mList.get(i);
                    if(xe.getBienSo().equalsIgnoreCase(temp.getBienSo())){
                        mList.set(i, xe);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Xe xe = snapshot.getValue(Xe.class);
                if(xe == null || mList.isEmpty()){
                    return;
                }

                for(int i = 0; i < mList.size(); i++){
                    Xe temp = mList.get(i);
                    if(xe.getBienSo().equalsIgnoreCase(temp.getBienSo())){
                        mList.remove(i);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClickXe(View v, int postion) {
        Xe xe = mList.get(postion);
        Intent i = new Intent(getActivity(), capnhatvachitiet.class);
        i.putExtra("xe", xe);
        startActivity(i);
    }
}
