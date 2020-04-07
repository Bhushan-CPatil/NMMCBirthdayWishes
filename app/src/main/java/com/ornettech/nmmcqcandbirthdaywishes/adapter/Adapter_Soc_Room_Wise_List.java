package com.ornettech.nmmcqcandbirthdaywishes.adapter;

        import android.content.Context;
        import android.content.Intent;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Filter;
        import android.widget.Filterable;
        import android.widget.TextView;

        import com.ornettech.nmmcqcandbirthdaywishes.R;
        import com.ornettech.nmmcqcandbirthdaywishes.activity.SocietyWIseVoterDet;
        import com.ornettech.nmmcqcandbirthdaywishes.activity.SocietyWIseVoterDetIConnectQC;
        import com.ornettech.nmmcqcandbirthdaywishes.activity.SocietyWIseVoterDetOtherQC;
        import com.ornettech.nmmcqcandbirthdaywishes.activity.SocietyWIseVoterDetRoundQC;
        import com.ornettech.nmmcqcandbirthdaywishes.model.SocietyRoomWiseQCListPojoItem;
        import com.ornettech.nmmcqcandbirthdaywishes.model.VoterCallingQCPojoItem;

        import java.util.ArrayList;
        import java.util.List;

public class Adapter_Soc_Room_Wise_List extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<SocietyRoomWiseQCListPojoItem> myItems;
    private ItemListener myListener;
    private List<SocietyRoomWiseQCListPojoItem> filterArrayList;
    private Context mycontext;
    String flg;
    public List<String> responselist;
    public String spinsitename,fromdate,todate,msgres;

    public Adapter_Soc_Room_Wise_List(Context context, List<SocietyRoomWiseQCListPojoItem> items, String flag, String spinsitename1, String from, String to, String msgres1) {
        this.myItems = items;
        //myListener = listener;
        this.mycontext = context;
        this.filterArrayList = items;
        this.flg = flag;
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
        View view = LayoutInflater.from(mycontext).inflate(R.layout.adapter_society_room_list, parent, false);
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
        final SocietyRoomWiseQCListPojoItem model = filterArrayList.get(position);

        myHolder.socname.setText(model.getSocietyName());
        myHolder.roomcnt.setText(""+model.getRoomcnt());

        myHolder.itemView.setTag(position);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flg.equalsIgnoreCase("QC")) {
                    Intent intent = new Intent(mycontext, SocietyWIseVoterDet.class);
                    intent.putExtra("fromdate", fromdate);
                    intent.putExtra("todate", todate);
                    intent.putExtra("qcres", msgres);
                    intent.putExtra("site", spinsitename);
                    intent.putExtra("sublocation_cd", model.getSubLocationCd());
                    intent.putExtra("society_name", model.getSocietyName());
                    intent.putExtra("survey_soc_cd", model.getSurveySocietyCd());
                    intent.putExtra("roomcnt", model.getRoomcnt());
                    mycontext.startActivity(intent);
                }else if(flg.equalsIgnoreCase("MQC")){
                    Intent intent = new Intent(mycontext, SocietyWIseVoterDetOtherQC.class);
                    intent.putExtra("fromdate", fromdate);
                    intent.putExtra("todate", todate);
                    intent.putExtra("qcres", msgres);
                    intent.putExtra("ward", spinsitename);
                    intent.putExtra("sublocation_cd", model.getSubLocationCd());
                    intent.putExtra("society_name", model.getSocietyName());
                    intent.putExtra("survey_soc_cd", model.getSurveySocietyCd());
                    intent.putExtra("roomcnt", model.getRoomcnt());
                    mycontext.startActivity(intent);
                }else if(flg.equalsIgnoreCase("IConnect")){
                    Intent intent = new Intent(mycontext, SocietyWIseVoterDetIConnectQC.class);
                    intent.putExtra("qcres", msgres);
                    intent.putExtra("ward", spinsitename);
                    intent.putExtra("sublocation_cd", model.getSubLocationCd());
                    intent.putExtra("society_name", model.getSocietyName());
                    intent.putExtra("survey_soc_cd", model.getSurveySocietyCd());
                    intent.putExtra("roomcnt", model.getRoomcnt());
                    mycontext.startActivity(intent);
                }else if(flg.contains("round")){
                    Intent intent = new Intent(mycontext, SocietyWIseVoterDetRoundQC.class);
                    intent.putExtra("qcres", msgres);
                    intent.putExtra("roundno", flg);
                    intent.putExtra("ward", spinsitename);
                    intent.putExtra("sublocation_cd", model.getSubLocationCd());
                    intent.putExtra("society_name", model.getSocietyName());
                    intent.putExtra("survey_soc_cd", model.getSurveySocietyCd());
                    intent.putExtra("roomcnt", model.getRoomcnt());
                    mycontext.startActivity(intent);
                }
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
        void onItemClick(VoterCallingQCPojoItem item);
    }


    class Holder extends RecyclerView.ViewHolder {
        TextView socname, roomcnt;

        public Holder(@NonNull View itemView) {
            super(itemView);
            socname = itemView.findViewById(R.id.socname);
            roomcnt = itemView.findViewById(R.id.roomcnt);
        }
    }

    public List<SocietyRoomWiseQCListPojoItem> filterSearchPosition() {
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

                    ArrayList<SocietyRoomWiseQCListPojoItem> filteredList = new ArrayList<>();

                    for (SocietyRoomWiseQCListPojoItem soc : myItems) {

                        if (soc.getSocietyName() != null
                                && (soc.getSocietyName().toLowerCase().contains(charString)
                                || soc.getSocietyName().toUpperCase().contains(charString))) {

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
                filterArrayList = (ArrayList<SocietyRoomWiseQCListPojoItem>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public List<SocietyRoomWiseQCListPojoItem> searchFilteredData() {
        return filterArrayList;
    }
}
                                