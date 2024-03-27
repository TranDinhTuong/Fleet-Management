package com.example.demobtlltnc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demobtlltnc.model.TaiXe;
import com.example.demobtlltnc.model.XeTai;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class themtaixe extends AppCompatActivity implements View.OnClickListener{

    private EditText eTen, eSdt, eDiaChi, eGiayPhepLaiXe;
    private Button them, thoat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themtaixe);
        initView();

    }

    private void initView() {
        eTen = findViewById(R.id.edt_ten);
        eSdt = findViewById(R.id.edt_std);
        eDiaChi = findViewById(R.id.edt_dia_chi);
        eGiayPhepLaiXe = findViewById(R.id.edt_giay_phep_lai_xe);
        them = findViewById(R.id.btn_them);
        thoat = findViewById(R.id.btn_thoat);

        them.setOnClickListener(this);
        thoat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_them){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("tai xe");
            String id = myRef.push().getKey();
            TaiXe taiXe = new TaiXe(
                    id,
                    eTen.getText().toString(),
                    eDiaChi.getText().toString(),
                    eSdt.getText().toString(),
                    eGiayPhepLaiXe.getText().toString()
            );

            myRef.child(id).setValue(taiXe, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(themtaixe.this, "them thanh cong", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }else{
            finish();
        }
    }
}