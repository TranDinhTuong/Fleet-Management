package com.example.demobtlltnc.adapter;

import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demobtlltnc.R;
import com.example.demobtlltnc.model.Xe;
import com.example.demobtlltnc.model.firebase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Xe> list;
    private itemListener itemListener;

    public RecyclerViewAdapter(List<Xe> list) {
        this.list = list;
    }
    public RecyclerViewAdapter(){
    }

    public void setItemListener(RecyclerViewAdapter.itemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Xe> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Xe xe = list.get(position);

        holder.iBienSo.setText("Bien So: " + xe.getBienSo() );
        holder.iLoaiXe.setText("Loai xe: " + xe.getLoaiXe());
        holder.iTinhTrang.setText(xe.getTinhTrangXe());

        // den ngay bao duong thi thong bao
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy", Locale.US); // chu y
        if(xe.getLichBaoDuong().equalsIgnoreCase(dateFormat.format(date))){
            xe.setTinhTrangXe("đang bảo trì");
            firebase db = new firebase("xe");
            db.update(xe.getBienSo(), xe.toMap());
            holder.layout.setBackgroundColor(R.color.red);
        }
        else {
            if(xe.getTinhTrangXe().equalsIgnoreCase("đang bảo trì")){
                xe.setTinhTrangXe("không hoạt động");
                firebase db = new firebase("xe");
                db.update(xe.getBienSo(), xe.toMap());
                holder.layout.setBackgroundColor(R.color.white);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView iLoaiXe,iBienSo, iTinhTrang;
        private LinearLayout layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            iLoaiXe = v.findViewById(R.id.iLoaiXe);
            iTinhTrang = v.findViewById(R.id.iTinhTrang);
            iBienSo = v.findViewById(R.id.iBienSo);
            layout = v.findViewById(R.id.layout);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener != null){
                itemListener.onItemClickXe(v, getAdapterPosition());
            }
        }
    }
    public interface itemListener{
        void onItemClickXe(View v, int postion);
    }
}
