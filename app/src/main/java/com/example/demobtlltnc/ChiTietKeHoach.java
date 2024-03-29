package com.example.demobtlltnc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demobtlltnc.model.KeHoach;

public class ChiTietKeHoach extends AppCompatActivity implements View.OnClickListener{

    private EditText tvDiemDi, tvDiemDen, tvQuangDuong,tvChiPhi,tvThoiGianUocTinh,tvTaiXe,tvXe;
    private TextView tvThoiGian;
    private KeHoach keHoach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ke_hoach);
        initView();

        tvTaiXe.setOnClickListener(this);
        tvXe.setOnClickListener(this);
    }

    private void initData() {

    }

    private void initView() {
        tvDiemDi = findViewById(R.id.tv_diem_di);
        tvDiemDen = findViewById(R.id.tv_diem_den);
        tvQuangDuong = findViewById(R.id.tv_quang_duong);
        tvChiPhi = findViewById(R.id.tv_chi_phi);
        tvThoiGian = findViewById(R.id.tv_thoi_gian);
        tvThoiGianUocTinh = findViewById(R.id.tv_thoi_gian_uoc_tinh);
        tvTaiXe = findViewById(R.id.tv_tai_xe);
        tvXe = findViewById(R.id.tv_xe);

        Intent i = getIntent();
        keHoach = (KeHoach) i.getSerializableExtra("ke hoach");

        tvDiemDi.setText(keHoach.getXuatPhat());
        tvDiemDen.setText(keHoach.getDen());
        tvQuangDuong.setText(" " + keHoach.getKhoangCach().toString() + " Km");
        tvChiPhi.setText(" " + keHoach.getChiPhi() + "VND");
        tvThoiGian.setText(" " + keHoach.getThoiGianXuatPhat());
        tvThoiGianUocTinh.setText(keHoach.getThoiGianToi() + " Phut");
        tvTaiXe.setText(keHoach.getTaiXe().getTen().trim());
        tvXe.setText(keHoach.getXe().getLoaiXe());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_tai_xe){
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.capnhat_chitiet_taixe);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            //window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);

            dialog.show();
        }
    }
}