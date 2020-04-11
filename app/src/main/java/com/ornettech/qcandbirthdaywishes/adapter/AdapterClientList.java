package com.ornettech.qcandbirthdaywishes.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.listener.GalleryListener;
import com.ornettech.qcandbirthdaywishes.model.ClientListItem;
import com.ornettech.qcandbirthdaywishes.model.CorporatorsElectionWiseItem;
import com.ornettech.qcandbirthdaywishes.utility.Transalator;

import java.util.ArrayList;
import java.util.List;

public class AdapterClientList  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<ClientListItem> myItems;
    private AdapterClientList.ItemListener myListener;
    private List<ClientListItem> filterArrayList;
    private Context mycontext;
    public GalleryListener itemClickListener;
    String flg;
    String electionname;
    //SendSMSWhatsApp sendSMSWhatsApp = new SendSMSWhatsApp();
    public List<String> responselist;
    public String spinsitename,fromdate,todate,msgres;

    public AdapterClientList(Context context, List<ClientListItem> items, String ele) {
        this.myItems = items;
        //myListener = listener;
        this.mycontext = context;
        this.electionname = ele;
        this.filterArrayList = items;
    }

    public void onItemClick(GalleryListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void setListener(AdapterClientList.ItemListener listener) {
        myListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mycontext).inflate(R.layout.adapter_survey_voter, parent, false);
        AdapterClientList.InnerHolder holder = new AdapterClientList.InnerHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return filterArrayList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final AdapterClientList.InnerHolder innerHolder = (AdapterClientList.InnerHolder) holder;
        final ClientListItem model = filterArrayList.get(position);


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public interface ItemListener {
        void onItemClick(CorporatorsElectionWiseItem item);
    }


    class InnerHolder extends RecyclerView.ViewHolder {
        TextView votername, genage, socdet, responsedet;
        LinearLayout llt, llt2;
        ImageButton edit, callingbtn;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            llt = itemView.findViewById(R.id.llt);
            llt2 = itemView.findViewById(R.id.llt2);
            votername = itemView.findViewById(R.id.votername);
            genage = itemView.findViewById(R.id.genage);
            socdet = itemView.findViewById(R.id.socdet);
            responsedet = itemView.findViewById(R.id.responsedet);
            edit = itemView.findViewById(R.id.edit);
            callingbtn = itemView.findViewById(R.id.callingbtn);
        }
    }

    public List<ClientListItem> filterSearchPosition() {
        return filterArrayList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    filterArrayList = myItems;
                } else {

                    ArrayList<ClientListItem> filteredList = new ArrayList<>();

                    for (ClientListItem soc : myItems) {

                        if (soc.getCorporatorName().toLowerCase().contains(charString.toLowerCase())
                                || soc.getParty().toLowerCase().contains(charString.toLowerCase())
                                || soc.getDesignationName().toLowerCase().contains(charString.toLowerCase())
                                || Integer.toString(soc.getWardNo()).contains(charString)
                                || soc.getCorporatorNameMar().contains(charString)) {

                            filteredList.add(soc);
                        }
                    }
                    filterArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence,
                                          FilterResults filterResults) {
                filterArrayList = (ArrayList<ClientListItem>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public List<ClientListItem> searchFilteredData() {
        return filterArrayList;
    }
}


