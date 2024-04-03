package com.example.demobtlltnc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.demobtlltnc.adapter.KeHoachAdapter;
import com.example.demobtlltnc.model.KeHoach;
import com.example.demobtlltnc.model.TaiXe;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LichSuTaiXe extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView tvSoChuyen;
    private ImageButton thoat;
    private KeHoachAdapter adapter;
    private List<KeHoach> mListKeHoach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_tai_xe);

        initView();

        Intent i = getIntent();
        TaiXe taiXe = (TaiXe) i.getSerializableExtra("tai xe");
        getKeHoach(taiXe.getId());
        getCountKehoach(taiXe.getId());
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        tvSoChuyen = findViewById(R.id.tv_so_chuyen);
        thoat = findViewById(R.id.btn_cancel);

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mListKeHoach = new ArrayList<>();
        adapter = new KeHoachAdapter(mListKeHoach);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setList(mListKeHoach);
    }

    public void getCountKehoach(String idTaiXe){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("lich su tai xe").child(idTaiXe);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvSoChuyen.setText("số chuyến : " + snapshot.getChildrenCount()) ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void getKeHoach(String idTaiXe){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("lich su tai xe").child(idTaiXe);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                KeHoach keHoach = snapshot.getValue(KeHoach.class);
                if(keHoach != null ){
                    mListKeHoach.add(keHoach);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                KeHoach keHoach = snapshot.getValue(KeHoach.class);
                if(keHoach == null || mListKeHoach.isEmpty()){
                    return;
                }

                for(int i = 0; i < mListKeHoach.size(); i++){
                    KeHoach temp = mListKeHoach.get(i);
                    if(keHoach.getId().equalsIgnoreCase(temp.getId())){
                        mListKeHoach.set(i, keHoach);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                KeHoach keHoach = snapshot.getValue(KeHoach.class);
                if(keHoach == null || mListKeHoach.isEmpty()){
                    return;
                }

                for(int i = 0; i < mListKeHoach.size(); i++){
                    KeHoach temp = mListKeHoach.get(i);
                    if(keHoach.getId().equalsIgnoreCase(temp.getId())){
                        mListKeHoach.remove(i);
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
}