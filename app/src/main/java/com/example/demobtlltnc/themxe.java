package com.example.demobtlltnc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demobtlltnc.model.DoiXe;
import com.example.demobtlltnc.model.Xe;
import com.example.demobtlltnc.model.XeContainer;
import com.example.demobtlltnc.model.XeKhach;
import com.example.demobtlltnc.model.XeTai;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class themxe extends AppCompatActivity {

    private Spinner loaiXe, loaiNhienLieu, tinhTrangXe;
    private EditText bienSo, kichThuoc, trongTai;
    private Button them, thoat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themxe);
        initView();
        setSpinner();

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Xe xe = new Xe(
                        bienSo.getText().toString(),
                        kichThuoc.getText().toString(),
                        trongTai.getText().toString(),
                        loaiNhienLieu.getSelectedItem().toString(),
                        tinhTrangXe.getSelectedItem().toString(),
                        loaiXe.getSelectedItem().toString()
                );

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("xe");

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        myRef.child(bienSo.getText().toString()).setValue(xe, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(themxe.this, "thanh cong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                finish();
            }
        });

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setSpinner() {
        loaiXe.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.loaixe)));
        loaiNhienLieu.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.loainhienlieu)));
        tinhTrangXe.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.tinhtrang)));
    }

    private void initView() {
        loaiXe = findViewById(R.id.spinner_loaixe);
        loaiNhienLieu = findViewById(R.id.spinner_loainhienlieu);
        tinhTrangXe = findViewById(R.id.spinner_tinhTrang);
        bienSo = findViewById(R.id.edt_bienso);
        kichThuoc = findViewById(R.id.edt_kichThuoc);
        trongTai = findViewById(R.id.edt_trongTai);
        them = findViewById(R.id.btn_them);
        thoat = findViewById(R.id.btn_thoat);
    }

}