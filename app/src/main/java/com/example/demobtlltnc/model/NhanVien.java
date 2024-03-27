package com.example.demobtlltnc.model;

public abstract class NhanVien {
    private String ten;
    private String diaChi;
    private String soDienThoai;

    public NhanVien(String ten, String diaChi, String soDienThoai) {
        this.ten = ten;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    @Override
    public String toString() {
        return "nguoi{" +
                "ten='" + ten + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                '}';
    }
}
