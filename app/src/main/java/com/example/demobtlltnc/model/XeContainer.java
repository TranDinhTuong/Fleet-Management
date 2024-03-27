package com.example.demobtlltnc.model;

public class XeContainer extends Xe{
    private String loaiXe = "xe container";


    @Override
    public String getLoaiXe() {
        return loaiXe;
    }

    public XeContainer(){

    }


    public void In(){
        System.out.println("Day la xe Container");
    }


    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }
}
