package com.example.demobtlltnc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class themxe extends AppCompatActivity implements View.OnClickListener {

    private Spinner loaiXe, loaiNhienLieu;
    private EditText bienSo, kichThuoc, trongTai, lichBaoDuong;
    private Button them;
    private ImageButton thoat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themxe);
        initView();
        setSpinner();

        lichBaoDuong.setOnClickListener(this);
        them.setOnClickListener(this);

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
    }

    private void initView() {
        loaiXe = findViewById(R.id.spinner_loaixe);
        loaiNhienLieu = findViewById(R.id.spinner_loainhienlieu);
        bienSo = findViewById(R.id.edt_bienso);
        kichThuoc = findViewById(R.id.edt_kichThuoc);
        trongTai = findViewById(R.id.edt_trongTai);
        them = findViewById(R.id.btn_them);
        thoat = findViewById(R.id.btn_thoat);
        lichBaoDuong = findViewById(R.id.edt_lich_bao_duong);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.edt_lich_bao_duong){
            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            
            DatePickerDialog dialog = new DatePickerDialog(themxe.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int y, int m, int d) {
                    String date = "";
                    if (m > 8) {
                        date = d + "/" + (m + 1) + "/" + y; //m may tinh tu 0->11 => can +1
                    } else {
                        date = d + "/" + "0" + (m + 1) + "/" + y;
                    }
                    lichBaoDuong.setText(date);
                }
            }, year, month, day);
            dialog.show();
        }
        else if(v.getId() == R.id.btn_them){
            Xe xe = new Xe(
                    bienSo.getText().toString(),
                    kichThuoc.getText().toString(),
                    trongTai.getText().toString(),
                    loaiNhienLieu.getSelectedItem().toString(),
                    loaiXe.getSelectedItem().toString(),
                    lichBaoDuong.getText().toString()
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
    }
}