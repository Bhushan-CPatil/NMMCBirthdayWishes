package com.ornettech.qcandbirthdaywishes.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.listener.ItemClickListener;
import com.ornettech.qcandbirthdaywishes.model.MonthlyReport;

import java.util.ArrayList;


/**
 * Created by Ornet on 11/18/2017.
 */

public class MonthlyWardAdapter extends RecyclerView.Adapter<MonthlyWardAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<MonthlyReport> monthlyReports;
    public MonthlyWardAdapter(Context mContext, ArrayList<MonthlyReport> wardList) {
        this.mContext = mContext;
        this.monthlyReports = wardList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewWardName, textViewMessage;
        ItemClickListener itemClickListener;

        public MyViewHolder(final View itemView) {
            super(itemView);
            textViewWardName = (TextView) itemView.findViewById(R.id.textViewWardName);
            textViewMessage = (TextView) itemView.findViewById(R.id.textViewMessage);
            itemView.setOnClickListener(this);
        }


        public void OnItemClick(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view,getAdapterPosition());
        }
    }

    @Override
    public MonthlyWardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_monthly_ward_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MonthlyWardAdapter.MyViewHolder holder, final int position) {
        final MonthlyReport monthlyReport = monthlyReports.get(position);
        holder.textViewWardName.setText(monthlyReport.getTitle());
        holder.textViewMessage.setText(monthlyReport.getMessage());

        holder.OnItemClick(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(mContext, ""+monthlyReport.getTitle()+"\n"+monthlyReport.getMessage(), Toast.LENGTH_SHORT).show();
                sendWhatsApp(monthlyReport.getTitle()+"\n"+monthlyReport.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return monthlyReports.size();
    }

    private void sendWhatsApp(String whatsappmessage){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, whatsappmessage);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        mContext.startActivity(sendIntent);
    }

}
