package com.example.demobtlltnc.model;

import java.util.List;

public class LichSuLaiXe {
    private List<KeHoach> keHoachList;

    public LichSuLaiXe(List<KeHoach> keHoachList) {
        this.keHoachList = keHoachList;
    }

    public List<KeHoach> getKeHoachList() {
        return keHoachList;
    }

    public void setKeHoachList(List<KeHoach> keHoachList) {
        this.keHoachList = keHoachList;
    }
}
