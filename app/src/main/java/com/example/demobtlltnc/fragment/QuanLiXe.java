package com.example.demobtlltnc.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.example.demobtlltnc.model.firebase;
import com.example.demobtlltnc.themtaixe;
import com.example.demobtlltnc.themxe;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuanLiXe extends Fragment implements RecyclerViewAdapter.itemListener{
    private SearchView searchView;
    private RecyclerView recyclerView;
    private Button btnAddXe;
    private RecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private Spinner filter;
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
        filter = view.findViewById(R.id.filter);

        filter.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spinner, getResources().getStringArray(R.array.loaixeFilter)));



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
        
        //getALlXe();
        setFilter();
        search();

        //adapter.notifyDataSetChanged();
        adapter.setItemListener(this);
    }

    private void setFilter() {
        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = filter.getItemAtPosition(position).toString();

                if(!select.equalsIgnoreCase("tất cả")){
                    firebase db = new firebase("xe");
                    Query query = db.getMyRef().orderByChild("loaiXe").equalTo(select);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Xe> temp = new ArrayList<>();
                            for(DataSnapshot i : snapshot.getChildren()){
                                Xe xe = i.getValue(Xe.class);
                                if(xe != null){
                                    temp.add(xe);
                                }
                            }
                            adapter.setList(temp);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    getALlXe();
                }
                //Toast.makeText(getContext(), temp.size() + " ", Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void search() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebase db = new firebase("xe");
                List<Xe> listSearch = db.getListXeByBienSo(newText);
                adapter.setList(listSearch);
                adapter.notifyDataSetChanged();
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
                }
                Log.i("myTag", mList.size() + "");
                adapter.notifyDataSetChanged();
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
