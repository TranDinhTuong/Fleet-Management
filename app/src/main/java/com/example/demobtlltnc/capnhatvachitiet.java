package com.example.demobtlltnc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demobtlltnc.model.Xe;
import com.example.demobtlltnc.model.XeContainer;
import com.example.demobtlltnc.model.XeKhach;
import com.example.demobtlltnc.model.XeTai;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class capnhatvachitiet extends AppCompatActivity implements View.OnClickListener{
    private Spinner loaiXe, loaiNhienLieu, tinhTrangXe;
    private EditText bienSo, kichThuoc, trongTai, lichBaoDuong;
    private TextView tBienSo;
    private Button capNhat, Xoa;
    private ImageButton quayLai;
    private Xe xe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capnhatvachitiet);
        initView();
    }

    private void initView() {
        loaiXe = findViewById(R.id.spinner_loaixe);
        loaiNhienLieu = findViewById(R.id.spinner_loainhienlieu);
        tinhTrangXe = findViewById(R.id.spinner_tinhTrang);
        tBienSo = findViewById(R.id.txt_bien_so);
        kichThuoc = findViewById(R.id.edt_kichThuoc);
        trongTai = findViewById(R.id.edt_trongTai);
        capNhat = findViewById(R.id.update);
        Xoa = findViewById(R.id.remove);
        quayLai = findViewById(R.id.back);
        tBienSo = findViewById(R.id.txt_bien_so);
        lichBaoDuong = findViewById(R.id.edt_lich_bao_duong);

        setSpinner();

        Intent intent = getIntent();
        xe = (Xe)intent.getSerializableExtra("xe");
        tBienSo.setText("Bien so: " + xe.getBienSo());

        kichThuoc.setText(xe.getKichThuoc());
        trongTai.setText(xe.getTrongTai());
        lichBaoDuong.setText(xe.getLichBaoDuong());

        setSpinnerLoaiXe(xe);
        setSpinnerNhienLieu(xe);
        setSpinnerTinhTrang(xe);

        capNhat.setOnClickListener(this);
        Xoa.setOnClickListener(this);
        quayLai.setOnClickListener(this);
        lichBaoDuong.setOnClickListener(this);

    }
    private void setSpinnerLoaiXe(Xe xe){
        int k = 0;
        for(String x : getResources().getStringArray(R.array.loaixe)){
            if(xe.getLoaiXe().equalsIgnoreCase(x)){
                loaiXe.setSelection(k);
                break;
            }
            k++;
        }
    }
    private void setSpinnerNhienLieu(Xe xe){
        int k = 0;
        for(String x : getResources().getStringArray(R.array.loainhienlieu)){
            if(xe.getLoaiNhienLieu().equalsIgnoreCase(x)){
                loaiNhienLieu.setSelection(k);
                break;
            }
            k++;
        }
    }
    private void setSpinnerTinhTrang(Xe xe){
        int k = 0;
        for(String x : getResources().getStringArray(R.array.tinhtrang)){
            if(xe.getTinhTrangXe().equalsIgnoreCase(x)){
                tinhTrangXe.setSelection(k);
                break;
            }
            k++;
        }
    }
    private void setSpinner() {
        loaiXe.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.loaixe)));
        loaiNhienLieu.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.loainhienlieu)));
        tinhTrangXe.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.tinhtrang)));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.update){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("xe").child(xe.getBienSo().toString());

            Xe c = new Xe();

            c.setLoaiXe(loaiXe.getSelectedItem().toString());
            c.setLoaiNhienLieu(loaiNhienLieu.getSelectedItem().toString());
            c.setKichThuoc(kichThuoc.getText().toString());
            c.setTinhTrangXe(tinhTrangXe.getSelectedItem().toString());
            c.setTrongTai(trongTai.getText().toString());

            myRef.updateChildren(c.toMap(), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    finish();
                }
            });

        }else if(v.getId() == R.id.remove){
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thong bao xoa");
            builder.setIcon(R.drawable.cancel_ic);
            builder.setMessage("Ban co muon xoa co bien so: " + xe.getBienSo() + " nay khong ?");
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef = database.getReference("xe").child(xe.getBienSo());
                    myRef.removeValue();
                    finish();
                }
            });
            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else if(v.getId() == R.id.edt_lich_bao_duong){
            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(capnhatvachitiet.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int y, int m, int d) {
                    String date = "";

                    if (m > 8) {
                        date = d + "/" + (m + 1) + "/" + y; //m may tinh tu 0->11 => can +1
                    } else {
                        date = d + "/" + "0" + (m + 1) + "/" + y;
                    }
                    lichBaoDuong.setText(" " + date);
                }
            }, year, month, day);
            dialog.show();
        }
        else{
            finish();
        }
    }
}