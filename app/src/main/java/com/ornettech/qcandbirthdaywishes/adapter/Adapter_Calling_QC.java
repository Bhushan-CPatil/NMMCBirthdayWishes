package com.ornettech.qcandbirthdaywishes.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.activity.CallingQCMainActivity;
import com.ornettech.qcandbirthdaywishes.model.VoterCallingQCPojoItem;
import com.ornettech.qcandbirthdaywishes.utility.SendSMSWhatsApp;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Calling_QC extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<VoterCallingQCPojoItem> myItems;
    private ItemListener myListener;
    private List<VoterCallingQCPojoItem> filterArrayList;
    private Context mycontext;
    String flg;
    SendSMSWhatsApp sendSMSWhatsApp = new SendSMSWhatsApp();
    public List<String> responselist;
    public String spinsitename,fromdate,todate,msgres;

    public Adapter_Calling_QC(Context context, List<VoterCallingQCPojoItem> items, String flag, List<String> responselist1, String spinsitename1, String from, String to, String msgres1) {
        this.myItems = items;
        //myListener = listener;
        this.mycontext = context;
        this.filterArrayList = items;
        this.flg = flag;
        this.responselist = responselist1;
        this.spinsitename = spinsitename1;
        this.fromdate = from;
        this.todate = to;
        this.msgres = msgres1;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mycontext).inflate(R.layout.adapter_survey_voter, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return filterArrayList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder myHolder = (Holder) holder;
        final VoterCallingQCPojoItem model = filterArrayList.get(position);

        String res= model.getQCResponse().equals("") ? "Pending" : model.getQCResponse();
        myHolder.responsedet.setText("QC Status - "+res);
        String gender = model.getSex().equals("M") ? "MALE" : "FEMALE";
        myHolder.votername.setText(model.getFullName()+" - ("+gender+")");
        myHolder.genage.setText(model.getMobileNo()+" / AC No-"+model.getAcNo()+" / WN-"+model.getWardNo()+" / RN-"+model.getRoomNo());
        myHolder.socdet.setText(model.getSocietyName());
        myHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHolder.llt2.setBackgroundColor(Color.parseColor("#CBFDCD"));
                //notifyDataSetChanged();
                //boolean stst = ;

                CallingQCMainActivity.EditDialog(mycontext,model.getVoterCd(),model.getQCResponse(),responselist,model.getAcNo(),
                        model.getMobileNo(),model.getWardNo(),spinsitename,fromdate,todate,msgres,position);
                    //DBConnIP.updateQCResponse_status = false;
                    notifyDataSetChanged();

                //notifyDataSetChanged();
                Toast.makeText(mycontext, "edit", Toast.LENGTH_SHORT).show();

            }
        });
        myHolder.callingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHolder.llt2.setBackgroundColor(Color.parseColor("#FFDADA"));
                sendSMSWhatsApp.callMobNo(mycontext, model.getMobileNo());
                Toast.makeText(mycontext, "calling", Toast.LENGTH_SHORT).show();
            }
        });

        if(model.getSMSFlag().equalsIgnoreCase("1")) {
            myHolder.llt.setBackgroundColor(Color.parseColor("#00C00C"));
        }else{
            myHolder.llt.setBackgroundColor(Color.parseColor("#FF5555"));
        }

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
        void onItemClick(VoterCallingQCPojoItem item);
    }


    class Holder extends RecyclerView.ViewHolder {
        TextView votername, genage, socdet, responsedet;
        LinearLayout llt,llt2;
        ImageButton edit,callingbtn;

        public Holder(@NonNull View itemView) {
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

    public List<VoterCallingQCPojoItem> filterSearchPosition() {
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

                    ArrayList<VoterCallingQCPojoItem> filteredList = new ArrayList<>();

                    for (VoterCallingQCPojoItem soc : myItems) {

                        if (soc.getFullName() != null
                                && (soc.getFullName().toLowerCase().contains(charString)
                                || soc.getFullName().toUpperCase().contains(charString))) {

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
                filterArrayList = (ArrayList<VoterCallingQCPojoItem>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public List<VoterCallingQCPojoItem> searchFilteredData() {
        return filterArrayList;
    }
}
                                