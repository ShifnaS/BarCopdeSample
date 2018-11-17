package com.example.softex.barcopdesample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.softex.barcopdesample.R;
import com.example.softex.barcopdesample.dataClass.Student;

import java.util.List;

public class BarcodeAdapter extends RecyclerView.Adapter<BarcodeAdapter.MyViewHolder> {

    private List<Student> barcodeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, name, count,date,time,mcount;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.idd);
            name = (TextView) view.findViewById(R.id.name);
            count = (TextView) view.findViewById(R.id.count);
            date = (TextView) view.findViewById(R.id.date);
            mcount = (TextView) view.findViewById(R.id.mcount);
           // time = (TextView) view.findViewById(R.id.time);
        }
    }


    public BarcodeAdapter(List<Student> barcodeList) {
        this.barcodeList = barcodeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.barcode_count_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Student barcode = barcodeList.get(position);
         int i=position+1;
         holder.id.setText(""+i);
         holder.name.setText(""+barcode.getContent());
         holder.count.setText(""+barcode.getCount());
         holder.date.setText(""+barcode.getVehicleNo());
        holder.mcount.setText(""+barcode.getManualcount());
       //  holder.time.setText(""+barcode.getTime());
    }

    @Override
    public int getItemCount() {
        return barcodeList.size();
    }
}