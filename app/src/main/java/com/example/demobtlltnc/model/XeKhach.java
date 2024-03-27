package com.example.demobtlltnc.model;

public class XeKhach extends Xe{
    private String loaiXe = "xe khach";
    public XeKhach(){

    }


    public void In(){
        System.out.println("Day la xe Khach");
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }
}
