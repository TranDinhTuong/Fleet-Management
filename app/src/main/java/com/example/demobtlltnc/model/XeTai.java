package com.example.demobtlltnc.model;

public class XeTai extends Xe{
    protected String loaiXe = "xe tai";
    public XeTai(){

    }



    public void In(){
        System.out.println("Day la xe Tai");
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }
}
