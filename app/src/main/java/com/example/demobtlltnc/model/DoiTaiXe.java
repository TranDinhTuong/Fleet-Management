package com.example.demobtlltnc.model;

import java.util.List;

public final class DoiTaiXe implements activity{
    private static DoiTaiXe doiTaiXe;
    private List<TaiXe> mListNguoi;

    public DoiTaiXe(List<TaiXe> mListNguoi) {
        this.mListNguoi = mListNguoi;
    }

    public static DoiTaiXe getDoiTaiXe() {
        return doiTaiXe;
    }

    public static DoiTaiXe getInstance(List<TaiXe> list) {
        if (doiTaiXe == null) {
            doiTaiXe = new DoiTaiXe(list);
        }
        return doiTaiXe;
    }

    public List<TaiXe> getmListNguoi() {
        return mListNguoi;
    }

    public void setmListNguoi(List<TaiXe> mListNguoi) {
        this.mListNguoi = mListNguoi;
    }


    @Override
    public void add(Object temp) {
        TaiXe a = (TaiXe) temp;
        mListNguoi.add(a);
    }

    @Override
    public void remove(Object temp) {

    }

    @Override
    public void update(Object temp) {

    }
}
