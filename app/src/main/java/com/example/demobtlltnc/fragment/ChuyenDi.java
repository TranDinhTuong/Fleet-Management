package com.example.demobtlltnc.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demobtlltnc.ChiTietKeHoach;
import com.example.demobtlltnc.R;
import com.example.demobtlltnc.adapter.KeHoachAdapter;
import com.example.demobtlltnc.lapKeHoachChuyenDi;
import com.example.demobtlltnc.model.KeHoach;
import com.example.demobtlltnc.model.TaiXe;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChuyenDi extends Fragment implements KeHoachAdapter.itemListener{
    private RecyclerView recyclerView;
    private Button btn_them;
    private List<KeHoach> mListKeHoach;
    private KeHoachAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chuyendi, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), lapKeHoachChuyenDi.class);
                startActivity(intent);
            }
        });

        getKeHoach();
    }

    private void initView(View v) {
        recyclerView = v.findViewById(R.id.recycler);
        btn_them = v.findViewById(R.id.btn_add_chuyendi);

        mListKeHoach = new ArrayList<>();
        adapter = new KeHoachAdapter(mListKeHoach);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setList(mListKeHoach);

        adapter.setItemListener(this);
    }

    public void getKeHoach(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ke hoach");

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
    public void onItemClickKeHoach(View v, int postion) {
        KeHoach keHoach = mListKeHoach.get(postion);
        Intent intent = new Intent(getContext(), ChiTietKeHoach.class);
        intent.putExtra("ke hoach", keHoach);
        startActivity(intent);
        //openDialogChiTiet(keHoach);
    }

    private void openDialogChiTiet(KeHoach keHoach){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.capnhat_chitiet_ke_hoach);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button thoat = dialog.findViewById(R.id.btn_thoat);



        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
