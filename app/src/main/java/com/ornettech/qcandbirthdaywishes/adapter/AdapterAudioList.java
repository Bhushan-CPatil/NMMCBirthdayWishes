package com.ornettech.qcandbirthdaywishes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.activity.AudioPlayerActivity;
import com.ornettech.qcandbirthdaywishes.model.QCResposeWiseReportItem;

import java.util.ArrayList;
import java.util.List;

public class AdapterAudioList  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<QCResposeWiseReportItem> myItems;
    private AdapterAudioList.ItemListener myListener;
    private List<QCResposeWiseReportItem> filterArrayList;
    private Context mycontext;
    public List<String> responselist;

    public AdapterAudioList(Context context, List<QCResposeWiseReportItem> items) {
        this.myItems = items;
        //myListener = listener;
        this.mycontext = context;
        this.filterArrayList = items;
    }

    public void setListener(AdapterAudioList.ItemListener listener) {
        myListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mycontext).inflate(R.layout.audio_list_adapter, parent, false);
        AdapterAudioList.InnerHolder holder = new AdapterAudioList.InnerHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return filterArrayList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final InnerHolder innerHolder = (InnerHolder) holder;
        final QCResposeWiseReportItem model = filterArrayList.get(position);

        if(model.getQCResponse().equalsIgnoreCase("Received / Verified")
        || model.getQCResponse().equalsIgnoreCase("Wished")
        || model.getQCResponse().equalsIgnoreCase("Received / Expired")) {
            innerHolder.llt.setBackgroundColor(Color.parseColor("#00C00C"));
        }else{
            innerHolder.llt.setBackgroundColor(Color.parseColor("#FF5555"));
        }

        innerHolder.name.setText(model.getFullName());

        if(model.getVtype().equalsIgnoreCase("NV")){
            innerHolder.qcbyanddate.setText("Ward - "+model.getWardNo()+" / Non-Voter");
        }else if(model.getVtype().equalsIgnoreCase("V")){
            innerHolder.qcbyanddate.setText("Ward - "+model.getWardNo()+" / Voter");
        }else{
            innerHolder.qcbyanddate.setText("Ward - "+model.getWardNo()+" / "+model.getVtype());
        }

        innerHolder.responsedet.setText("QC Status - " + model.getQCResponse());

        innerHolder.type.setText(model.getQCByUser()+" / "+model.getQC_Calling_UpdatedDate());

        innerHolder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mycontext, AudioPlayerActivity.class);
                intent.putExtra("url", model.getQC_Calling_Recording());
                intent.putExtra("calledto", model.getFullName());
                intent.putExtra("dialer", model.getQCByUser());
                intent.putExtra("status", model.getQCResponse());
                mycontext.startActivity(intent);
            }
        });
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
        void onItemClick(QCResposeWiseReportItem item);
    }


    class InnerHolder extends RecyclerView.ViewHolder {
        TextView name, qcbyanddate, type, responsedet;
        LinearLayout llt, llt2;
        ImageButton play, whatsappbtn;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            llt = itemView.findViewById(R.id.llt);
            llt2 = itemView.findViewById(R.id.llt2);
            name = itemView.findViewById(R.id.name);
            qcbyanddate = itemView.findViewById(R.id.qcbyanddate);
            type = itemView.findViewById(R.id.type);
            responsedet = itemView.findViewById(R.id.responsedet);
            play = itemView.findViewById(R.id.play);
            whatsappbtn = itemView.findViewById(R.id.whatsappbtn);
        }
    }

    public List<QCResposeWiseReportItem> filterSearchPosition() {
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

                    ArrayList<QCResposeWiseReportItem> filteredList = new ArrayList<>();

                    for (QCResposeWiseReportItem soc : myItems) {

                        if (soc.getFullName().toLowerCase().contains(charString.toLowerCase())
                                || soc.getQCByUser().toLowerCase().contains(charString.toLowerCase())
                                || soc.getExecutiveName().toLowerCase().contains(charString.toLowerCase())
                                || Integer.toString(soc.getWardNo()).contains(charString)
                                || soc.getSocietyName().contains(charString)) {

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
                filterArrayList = (ArrayList<QCResposeWiseReportItem>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public List<QCResposeWiseReportItem> searchFilteredData() {
        return filterArrayList;
    }
}
