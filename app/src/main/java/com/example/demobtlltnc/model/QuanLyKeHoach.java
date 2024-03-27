package com.example.demobtlltnc.model;

import java.util.List;

public final class QuanLyKeHoach implements activity{

    private static QuanLyKeHoach quanLyKeHoach;
    private List<KeHoach> mListKeHoach;

    public QuanLyKeHoach(List<KeHoach> mListKeHoach) {
        this.mListKeHoach = mListKeHoach;
    }

    public static QuanLyKeHoach getInstance(List<KeHoach> mListKeHoach) {
        if (quanLyKeHoach == null) {
            quanLyKeHoach = new QuanLyKeHoach(mListKeHoach);
        }
        return quanLyKeHoach;
    }

    public List<KeHoach> getmListKeHoach() {
        return mListKeHoach;
    }

    public void setmListKeHoach(List<KeHoach> mListKeHoach) {
        this.mListKeHoach = mListKeHoach;
    }


    @Override
    public void add(Object temp) {
        KeHoach a = (KeHoach) temp;
        mListKeHoach.add(a);
    }

    @Override
    public void remove(Object temp) {

    }

    @Override
    public void update(Object temp) {

    }
}
