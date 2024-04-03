package com.example.demobtlltnc.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demobtlltnc.R;
import com.example.demobtlltnc.model.KeHoach;
import com.example.demobtlltnc.model.firebase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

public class DoanSo extends Fragment {

    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug","Sep","Oct","Nov","Dec"};

    private PieChart pieChart;
    private TextView doanhThu, chiPhi;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doanh_so, container, false);
        pieChart = view.findViewById(R.id.piechart);
        doanhThu = view.findViewById(R.id.tvDoanhThu);
        chiPhi = view.findViewById(R.id.tvChiPhi);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setData();


    }

    public void setData(){
        firebase db = new firebase("ke hoach");

        float totalDoanhThu = 0;
        final float[] totalChiPhi = {0};
        db.getMyRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren()){
                    KeHoach keHoach = i.getValue(KeHoach.class);
                    if(keHoach != null){
                        float chiPhi = Float.parseFloat(keHoach.getChiPhi());
                        totalChiPhi[0] += (chiPhi / 100);
                    }
                }

                pieChart.addPieSlice(
                        new PieModel(
                                "doanh thu",
                                totalChiPhi[0] + 100,
                                Color.parseColor("#EF5350")));

                pieChart.addPieSlice(
                        new PieModel(
                                "chi phi",
                                totalChiPhi[0],
                                Color.parseColor("#29B6F6")));

                pieChart.startAnimation();

                // Cập nhật dữ liệu lên giao diện
                chiPhi.setText(String.valueOf(totalChiPhi[0]));
                doanhThu.setText(String.valueOf(totalChiPhi[0] + 100));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
