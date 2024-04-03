package com.example.demobtlltnc.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Xe implements Serializable {
    private  String bienSo;
    private String kichThuoc;
    private String trongTai;
    private String loaiNhienLieu;
    private String tinhTrangXe;
    private String LoaiXe;
    private String lichBaoDuong;


    public Xe() {
    }

    public String getLichBaoDuong() {
        return lichBaoDuong;
    }

    public void setLichBaoDuong(String lichBaoDuong) {
        this.lichBaoDuong = lichBaoDuong;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getTrongTai() {
        return trongTai;
    }

    public void setTrongTai(String trongTai) {
        this.trongTai = trongTai;
    }

    public String getLoaiNhienLieu() {
        return loaiNhienLieu;
    }

    public void setLoaiNhienLieu(String loaiNhienLieu) {
        this.loaiNhienLieu = loaiNhienLieu;
    }

    public String getTinhTrangXe() {
        return tinhTrangXe;
    }

    public void setTinhTrangXe(String tinhTrangXe) {
        this.tinhTrangXe = tinhTrangXe;
    }

    public String getLoaiXe() {
        return LoaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        LoaiXe = loaiXe;
    }

    public Xe(String bienSo, String kichThuoc, String trongTai, String loaiNhienLieu, String loaiXe, String lichBaoDuong) {
        this.bienSo = bienSo;
        this.kichThuoc = kichThuoc;
        this.trongTai = trongTai;
        this.loaiNhienLieu = loaiNhienLieu;
        this.tinhTrangXe = "không hoạt động";
        this.LoaiXe = loaiXe;
        this.lichBaoDuong = lichBaoDuong;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("kichThuoc", kichThuoc);
        result.put("trongTai", trongTai);
        result.put("loaiNhienLieu", loaiNhienLieu);
        result.put("tinhTrangXe", tinhTrangXe);
        result.put("LoaiXe", LoaiXe);
        result.put("lichBaoDuong", lichBaoDuong);
        return  result;
    }

}
