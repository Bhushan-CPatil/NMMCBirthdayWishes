package com.ornettech.qcandbirthdaywishes.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.model.AlertPopup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ornet25 on 7/27/2018.
 */

public class PopUpAdapter extends RecyclerView.Adapter<PopUpAdapter.MyViewHolder>
        implements Filterable {

    private Context mContext;
    private List<AlertPopup> popupList;
    private List<AlertPopup> filterpopupList;
    private ItemClickListener itemClickListener;

    public PopUpAdapter(Context mContext, List<AlertPopup> popupList){

        this.mContext = mContext;
        this.popupList = popupList;
        this.filterpopupList = popupList;
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnAreaItemClick(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popup_name, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtvwname;

        public MyViewHolder(final View itemView){
            super(itemView);
            txtvwname = (TextView) itemView.findViewById(R.id.textvwname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            itemClickListener.onItemClick(itemView, getAdapterPosition());
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){

        final AlertPopup alertPopup = filterpopupList.get(position);
        try{
            holder.txtvwname.setText(alertPopup.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return filterpopupList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filterpopupList = popupList;
                } else {
                    List<AlertPopup> filteredList = new ArrayList<>();

                    for (AlertPopup alertPopup : popupList) {

                        if (alertPopup.getName().toLowerCase().contains(charString) ) {
                            filteredList.add(alertPopup);
                        }
                    }
                    filterpopupList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterpopupList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filterpopupList = (ArrayList<AlertPopup>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public List<AlertPopup> filterSearchPosition() {
        return filterpopupList;
    }
}
