package com.example.demobtlltnc.model;

import com.example.demobtlltnc.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeHoach implements Serializable {
    private String id;
    private String xuatPhat;
    private String Den;
    private String thoiGianXuatPhat;
    private Xe xe;
    private TaiXe taiXe;
    private String TinhTrangHienTai;
    private String chiPhi;
    private Double khoangCach;
    private String thoiGianToi;



    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("xuatPhat", xuatPhat);
        result.put("Den", Den);
        result.put("thoiGianXuatPhat", thoiGianXuatPhat);
        result.put("xe", xe);
        result.put("taiXe", taiXe);
        result.put("chiPhi", chiPhi);
        result.put("khoangCach", khoangCach);
        result.put("thoiGianToi", thoiGianToi);
        return  result;
    }

    public String getThoiGianToi() {
        return thoiGianToi;
    }

    public void setThoiGianToi(String thoiGianToi) {
        this.thoiGianToi = thoiGianToi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public KeHoach() {
    }

    public KeHoach(String id, String xuatPhat, String den, String thoiGianXuatPhat, Xe xe, TaiXe taiXe) {
        this.id = id;
        this.xuatPhat = xuatPhat;
        Den = den;
        this.thoiGianXuatPhat = thoiGianXuatPhat;
        this.xe = xe;
        this.taiXe = taiXe;
        this.TinhTrangHienTai = "chưa bắt đầu";
        this.chiPhi = "0";
        this.khoangCach = 0.0;
        this.chiPhi = "0";
    }

    public Double getKhoangCach() {
        return khoangCach;
    }

    public void setKhoangCach(Double khoangCach) {
        this.khoangCach = khoangCach;
    }

    public String getChiPhi() {
        return chiPhi;
    }

    public void setChiPhi(String chiPhi) {
        this.chiPhi = chiPhi;
    }

    public String getXuatPhat() {
        return xuatPhat;
    }

    public void setXuatPhat(String xuatPhat) {
        this.xuatPhat = xuatPhat;
    }

    public String getDen() {
        return Den;
    }

    public void setDen(String den) {
        Den = den;
    }

    public String getThoiGianXuatPhat() {
        return thoiGianXuatPhat;
    }

    public void setThoiGianXuatPhat(String thoiGianXuatPhat) {
        this.thoiGianXuatPhat = thoiGianXuatPhat;
    }

    public Xe getXe() {
        return xe;
    }

    public void setXe(Xe xe) {
        this.xe = xe;
    }

    public TaiXe getTaiXe() {
        return taiXe;
    }

    public void setTaiXe(TaiXe taiXe) {
        this.taiXe = taiXe;
    }

    public String getTinhTrangHienTai() {
        return TinhTrangHienTai;
    }

    public void setTinhTrangHienTai(String tinhTrangHienTai) {
        TinhTrangHienTai = tinhTrangHienTai;
    }
}
