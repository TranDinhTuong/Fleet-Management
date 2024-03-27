package com.example.demobtlltnc.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demobtlltnc.fragment.ChuyenDi;
import com.example.demobtlltnc.fragment.DoanSo;
import com.example.demobtlltnc.fragment.QuanLiTaiXe;
import com.example.demobtlltnc.fragment.QuanLiXe;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int number;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.number = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new QuanLiXe();
            case 1: return new QuanLiTaiXe();
            case 2: return new ChuyenDi();
            case 3: return new DoanSo();
            default: return new QuanLiXe();
        }
    }

    @Override
    public int getCount() {
        return number;
    }
}
