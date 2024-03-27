package com.example.demobtlltnc.model;

import java.util.List;

public final class DoiXe implements activity{
    private static DoiXe doiXe;
    private List<Xe> mListXe;

    public DoiXe(List<Xe> mListXe) {
        this.mListXe = mListXe;
    }

    public static DoiXe getInstance(List<Xe> list) {
        if (doiXe == null) {
            doiXe = new DoiXe(list);
        }
        return doiXe;
    }

    public static DoiXe getDoiXe() {
        return doiXe;
    }

    public static void setDoiXe(DoiXe doiXe) {
        DoiXe.doiXe = doiXe;
    }

    public List<Xe> getmListXe() {
        return mListXe;
    }

    public void setmListXe(List<Xe> mListXe) {
        this.mListXe = mListXe;
    }


    @Override
    public void add(Object temp) {
        Xe a = (Xe) temp;
        mListXe.add(a);
    }

    @Override
    public void remove(Object temp) {
        Xe a = (Xe) temp;
        for (Xe x : mListXe){
            if(x.getBienSo().equalsIgnoreCase(a.getBienSo())){
                mListXe.remove(x);
                break;
            }
        }
    }

    @Override
    public void update(Object temp) {

    }
}
