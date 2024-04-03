package com.example.demobtlltnc.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TaiXe implements Serializable {

    private String id;
    private String ten;
    private String diaChi;
    private String soDienThoai;
    private String giayPhepLaiXe;
    private String tinhTrang;
    private LichSuLaiXe lichSuLaiXe;

    public TaiXe() {

    }

    public TaiXe(String id, String ten, String diaChi, String soDienThoai, String giayPhepLaiXe) {
        this.id = id;
        this.ten = ten;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.giayPhepLaiXe = giayPhepLaiXe;
        this.tinhTrang = "đang rảnh";
        //this.soChuyenThanhCong = 0;
    }
    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("ten", ten);
        result.put("diaChi", diaChi);
        result.put("soDienThoai", soDienThoai);
        result.put("giayPhepLaiXe", giayPhepLaiXe);
        result.put("tinhTrang", tinhTrang);
        result.put("lichSuLaiXe", lichSuLaiXe);
        return  result;
    }

    public LichSuLaiXe getLichSuLaiXe() {
        return lichSuLaiXe;
    }

    public void setLichSuLaiXe(LichSuLaiXe lichSuLaiXe) {
        this.lichSuLaiXe = lichSuLaiXe;
    }
    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getGiayPhepLaiXe() {
        return giayPhepLaiXe;
    }

    public void setGiayPhepLaiXe(String giayPhepLaiXe) {
        this.giayPhepLaiXe = giayPhepLaiXe;
    }

}
