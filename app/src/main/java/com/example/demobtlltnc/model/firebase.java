package com.example.demobtlltnc.model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.demobtlltnc.lapKeHoachChuyenDi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class firebase {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    public firebase(String path) {
        this.myRef = database.getReference(path);
    }
    public void update(String path, Map<String, Object> mp){
        myRef.child(path).updateChildren(mp);
    }

    public DatabaseReference getMyRef() {
        return myRef;
    }

    public void setMyRef(DatabaseReference myRef) {
        this.myRef = myRef;
    }

    public List<Xe> getListXeByTinhTrang(){
        List<Xe> ListXe = new ArrayList<>();
        Query query = myRef.orderByChild("tinhTrangXe").equalTo("không hoạt động");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren()){
                    Xe xe = i.getValue(Xe.class);
                    if(xe != null){
                        ListXe.add(xe);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return ListXe;
    }
    public List<Xe> getListByLoaiXe(String select){
        List<Xe> list = new ArrayList<>();
        Query query = myRef.orderByChild("loaiXe").equalTo(select);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
    public List<Xe> getListXeByBienSo(String newText){
        List<Xe> list = new ArrayList<>();
        Query query = myRef.orderByChild("bienSo").equalTo(newText);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren()){
                    Xe xe = i.getValue(Xe.class);
                    if(xe != null){
                        list.add(xe);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }
    public List<TaiXe> getListTaiXeByTinhTrang(){
        List<TaiXe> ListTaiXe = new ArrayList<>();
        Query query = myRef.orderByChild("tinhTrang").equalTo("dang ranh");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren()){
                    TaiXe taiXe = i.getValue(TaiXe.class);
                    if(taiXe != null){
                        ListTaiXe.add(taiXe);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return ListTaiXe;
    }


}
