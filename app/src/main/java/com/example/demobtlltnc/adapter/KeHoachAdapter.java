package com.example.demobtlltnc.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demobtlltnc.R;
import com.example.demobtlltnc.model.KeHoach;
import com.example.demobtlltnc.model.TaiXe;

import java.util.Calendar;
import java.util.List;

public class KeHoachAdapter extends RecyclerView.Adapter<KeHoachAdapter.ViewHolder> {
    private List<KeHoach> list;

    public KeHoachAdapter(List<KeHoach> list) {
        this.list = list;
    }

    private KeHoachAdapter.itemListener itemListener;
    public void setItemListener(KeHoachAdapter.itemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<KeHoach> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ke_hoach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KeHoach keHoach = list.get(position);

        holder.iDi.setText(keHoach.getXuatPhat());
        holder.iDen.setText("=> " + keHoach.getDen());
        holder.iThoiGian.setText(keHoach.getThoiGianXuatPhat());

        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24 giờ
        int minute = calendar.get(Calendar.MINUTE);
        int thoiGianUocTinh = Integer.parseInt(keHoach.getThoiGianToi());

        int hours = thoiGianUocTinh / 60; // Lấy phần nguyên
        int minutes = thoiGianUocTinh % 60; // Lấy phần dư

        Log.e("time", hours + " " + minutes);
        holder.iTinhTrang.setText(keHoach.getTinhTrangHienTai());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView iDi,iDen,iTinhTrang, iThoiGian;

        public ViewHolder(@NonNull View v) {
            super(v);
            iDi = v.findViewById(R.id.iDi);
            iDen = v.findViewById(R.id.iDen);
            iTinhTrang = v.findViewById(R.id.iTinhTrang);
            iThoiGian = v.findViewById(R.id.i_ngay_xuat_phat);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(itemListener != null){
                itemListener.onItemClickKeHoach(v, getAdapterPosition());
            }
        }
    }
    public interface itemListener{
        void onItemClickKeHoach(View v, int postion);
    }

}
