package com.example.demobtlltnc.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demobtlltnc.ChiTietKeHoach;
import com.example.demobtlltnc.R;
import com.example.demobtlltnc.adapter.TaiXeAdapter;
import com.example.demobtlltnc.model.DoiTaiXe;
import com.example.demobtlltnc.model.TaiXe;
import com.example.demobtlltnc.model.Xe;
import com.example.demobtlltnc.model.firebase;
import com.example.demobtlltnc.themtaixe;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuanLiTaiXe extends Fragment implements TaiXeAdapter.itemListener{
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TaiXeAdapter adapter;
    private Button btnAddtaixe;
    private List<TaiXe> mList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quanlitaixe, container, false);
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView);
        btnAddtaixe = view.findViewById(R.id.btn_add_taixe);
        progressBar = view.findViewById(R.id.progress_bar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAddtaixe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), themtaixe.class);
                startActivity(intent);
            }
        });

        mList = new ArrayList<>();
        adapter = new TaiXeAdapter(mList);

        adapter.setList(mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        getTaiXe();
        adapter.setItemListener(this);
    }

    private void getTaiXe() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("tai xe");
        progressBar.setVisibility(View.VISIBLE);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TaiXe taiXe = snapshot.getValue(TaiXe.class);
                if(taiXe != null){
                    mList.add(taiXe);
                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TaiXe taiXe = snapshot.getValue(TaiXe.class);
                if(taiXe == null || mList.isEmpty()){
                    return;
                }

                for(int i = 0; i < mList.size(); i++){
                    TaiXe temp = mList.get(i);
                    if(taiXe.getId().equalsIgnoreCase(temp.getId())){
                        mList.set(i, taiXe);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                TaiXe taiXe = snapshot.getValue(TaiXe.class);
                if(taiXe == null || mList.isEmpty()){
                    return;
                }

                for(int i = 0; i < mList.size(); i++){
                    TaiXe temp = mList.get(i);
                    if(taiXe.getId().equalsIgnoreCase(temp.getId())){
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
    public void onItemClick(View v, int postion) {
        TaiXe taiXe = mList.get(postion);
        openDialogUpdateItem(taiXe);
    }
    private void openDialogUpdateItem(TaiXe taiXe){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.capnhat_chitiet_taixe);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText eTen = dialog.findViewById(R.id.edt_ten);
        EditText eSdt = dialog.findViewById(R.id.edt_std);
        EditText eDiaChi = dialog.findViewById(R.id.edt_dia_chi);
        EditText eGiayPhepLaiXe = dialog.findViewById(R.id.edt_giay_phep_lai_xe);

        Spinner spTinhTrang = dialog.findViewById(R.id.sp_tinhTrang);
        spTinhTrang.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spinner, getResources().getStringArray(R.array.tinhtrangTaiXe)));
        int k = 0;
        for(String x : getResources().getStringArray(R.array.tinhtrangTaiXe)){
            if(taiXe.getTinhTrang().equalsIgnoreCase(x)){
                spTinhTrang.setSelection(k);
                break;
            }
            k++;
        }


        eTen.setText(taiXe.getTen());
        eSdt.setText(taiXe.getSoDienThoai());
        eDiaChi.setText(taiXe.getDiaChi());
        eGiayPhepLaiXe.setText(taiXe.getGiayPhepLaiXe());

        Button btnCapNhat = dialog.findViewById(R.id.btn_update);
        Button btnXoa = dialog.findViewById(R.id.btn_remove);
        ImageButton btnThoat = dialog.findViewById(R.id.btn_thoat);

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();//tat dialog
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference myRef = db.getReference("tai xe");

                taiXe.setTen(eTen.getText().toString().trim());
                taiXe.setSoDienThoai(eSdt.getText().toString().trim());
                taiXe.setDiaChi(eDiaChi.getText().toString().trim());
                taiXe.setGiayPhepLaiXe(eGiayPhepLaiXe.getText().toString().trim());
                taiXe.setTinhTrang(spTinhTrang.getSelectedItem().toString());

                myRef.child(taiXe.getId()).updateChildren(taiXe.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        dialog.dismiss();
                    }
                });
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Thong bao xoa");
                builder.setIcon(R.drawable.cancel_ic);
                builder.setMessage("Ban co muon xoa  khong ?");
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        firebase db = new firebase("tai xe");
                        db.getMyRef().child(taiXe.getId()).removeValue();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        dialog.show();
    }

}
