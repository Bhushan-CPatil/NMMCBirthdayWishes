package com.ornettech.qcandbirthdaywishes.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.model.Ward;

import java.util.List;


/**
 * Created by Ornet on 11/18/2017.
 */

public class WardDetailAdapter extends RecyclerView.Adapter<WardDetailAdapter.MyViewHolder> {

    private Context mContext;
    private List<Ward> wardList;
    public WardDetailAdapter(Context mContext, List<Ward> wardList) {
        this.mContext = mContext;
        this.wardList = wardList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView textViewTitle, textViewCount;
        public MyViewHolder(final View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewCount = (TextView) itemView.findViewById(R.id.textViewCount);

        }


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_wardwise, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Ward ward = wardList.get(position);
        holder.textViewTitle.setText(ward.getTitle());
        holder.textViewCount.setText(ward.getCount());

    }

    @Override
    public int getItemCount() {
        return wardList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
