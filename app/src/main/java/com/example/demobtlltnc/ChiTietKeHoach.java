package com.example.demobtlltnc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demobtlltnc.model.KeHoach;
import com.example.demobtlltnc.model.TaiXe;
import com.example.demobtlltnc.model.Xe;
import com.example.demobtlltnc.model.firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChiTietKeHoach extends AppCompatActivity implements View.OnClickListener{

    private EditText tvDiemDi, tvDiemDen, tvQuangDuong,tvChiPhi,tvThoiGianUocTinh;
    private TextView tvThoiGian, tvTaiXe,tvXe;
    private Button xoa;
    private ImageButton thoat;
    private KeHoach keHoach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ke_hoach);
        initView();

        tvTaiXe.setOnClickListener(this);
        tvXe.setOnClickListener(this);
        thoat.setOnClickListener(this);
        xoa.setOnClickListener(this);
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
        thoat = findViewById(R.id.btn_thoat);
        xoa = findViewById(R.id.btn_remove);

        Intent i = getIntent();
        keHoach = (KeHoach) i.getSerializableExtra("ke hoach");

        tvDiemDi.setText(keHoach.getXuatPhat());
        tvDiemDen.setText(keHoach.getDen());
        tvQuangDuong.setText(" " + keHoach.getKhoangCach().toString() + " Km");
        tvChiPhi.setText(" " + keHoach.getChiPhi() + "VND");
        tvThoiGian.setText(" " + keHoach.getThoiGianXuatPhat());
        tvThoiGianUocTinh.setText(" " + keHoach.getThoiGianToi() + " Gio");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_tai_xe){
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.chitiet_taixe);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            //window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);

            EditText id = dialog.findViewById(R.id.edt_id);
            EditText ten = dialog.findViewById(R.id.edt_ten);
            EditText diaChi = dialog.findViewById(R.id.edt_dia_chi);
            EditText sdt = dialog.findViewById(R.id.edt_std);
            EditText giayPhep = dialog.findViewById(R.id.edt_giay_phep_lai_xe);

            TaiXe taiXe = keHoach.getTaiXe();
            id.setText(" " + taiXe.getId());
            ten.setText(" " +taiXe.getTen());
            diaChi.setText(" " +taiXe.getDiaChi());
            sdt.setText(" " +taiXe.getSoDienThoai());
            giayPhep.setText(" " +taiXe.getGiayPhepLaiXe());

            Button thoat = dialog.findViewById(R.id.btn_thoat);
            thoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else if(v.getId() == R.id.tv_xe){

            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.chi_tiet_xe);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            //window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);

            EditText bienSo = dialog.findViewById(R.id.edt_bienso);
            EditText kichThuoc = dialog.findViewById(R.id.edt_kichThuoc);
            EditText trongTai = dialog.findViewById(R.id.edt_trongTai);
            EditText loaiXe = dialog.findViewById(R.id.edt_loai_xe);
            EditText loaiNhienLieu = dialog.findViewById(R.id.edt_nhien_lieu);
            EditText lichBaoDuong = dialog.findViewById(R.id.edt_lich_bao_duong);

            Xe xe = keHoach.getXe();
            bienSo.setText(" " + xe.getBienSo());
            kichThuoc.setText(" " +xe.getKichThuoc());
            trongTai.setText(" " +xe.getTrongTai());
            loaiXe.setText(" " +xe.getLoaiXe());
            loaiNhienLieu.setText(" " +xe.getLoaiNhienLieu());
            lichBaoDuong.setText(xe.getLichBaoDuong());

            Button thoat = dialog.findViewById(R.id.btn_thoat);
            thoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else if(v.getId() == R.id.btn_remove){
            //chuyen ve trang thai dang ranh va ko hoat dong -> bug no se cap nhat doi tuong rong sang ben phia database taixe va xe
            // kiem tra xem thang nay da bi xoa tren firebase chua

            TaiXe taiXe = keHoach.getTaiXe();
            firebase d1 = new firebase("tai xe");
            d1.getMyRef().child(taiXe.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    TaiXe tmp = snapshot.getValue(TaiXe.class);
                    if(tmp != null){
                        taiXe.setTinhTrang("dang ranh");
                        d1.update(taiXe.getId(), taiXe.toMap());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            Xe xe = keHoach.getXe();
            firebase d2 = new firebase("xe");
            d2.getMyRef().child(xe.getBienSo()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Xe tmp = snapshot.getValue(Xe.class);
                    if(tmp != null){
                        xe.setTinhTrangXe("không hoạt động");
                        d2.update(xe.getBienSo(), xe.toMap());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //cap nhat tren firebase

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thong bao xoa");
            builder.setIcon(R.drawable.cancel_ic);
            builder.setMessage("Ban co muon xoa  khong ?");
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    firebase db = new firebase("ke hoach");
                    db.getMyRef().child(keHoach.getId()).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Toast.makeText(ChiTietKeHoach.this, "xoa thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    });
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
        else if(v.getId() == R.id.btn_thoat){
            finish();
        }
    }
}