package com.example.demobtlltnc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demobtlltnc.R;
import com.example.demobtlltnc.model.TaiXe;
import com.example.demobtlltnc.model.Xe;

import java.util.List;

public class TaiXeAdapter extends RecyclerView.Adapter<TaiXeAdapter.ViewHolder> {
    private List<TaiXe> list;

    public TaiXeAdapter(List<TaiXe> list) {
        this.list = list;
    }

    private TaiXeAdapter.itemListener itemListener;
    public void setItemListener(TaiXeAdapter.itemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<TaiXe> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_taixe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaiXe taiXe = list.get(position);

        holder.iTen.setText(taiXe.getTen());
        holder.iID.setText("ID:" + taiXe.getId());
        holder.iTinhTrang.setText(taiXe.getTinhTrang());
        holder.iSoChuyen.setText("so chuyen: " + taiXe.getSoChuyenThanhCong());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView iTen,iID,iTinhTrang, iSoChuyen;

        public ViewHolder(@NonNull View v) {
            super(v);
            iTen = v.findViewById(R.id.iTen);
            iTinhTrang = v.findViewById(R.id.txt_tinh_trang);
            iID = v.findViewById(R.id.iID);
            iSoChuyen = v.findViewById(R.id.txt_soChuyen);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener != null){
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
    public interface itemListener{
        void onItemClick(View v, int postion);
    }

}
