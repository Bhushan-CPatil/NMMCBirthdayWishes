package com.ornettech.qcandbirthdaywishes.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.activity.QCBirthDayWardWiseActivity;
import com.ornettech.qcandbirthdaywishes.listener.WhatsappItemClickListner;
import com.ornettech.qcandbirthdaywishes.model.PrabhagWiseBirthdaysItem;

import java.util.ArrayList;
import java.util.List;

public class AdapterWardList extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable{

    // implements Filterable
    private List<PrabhagWiseBirthdaysItem> myItems;
    private WhatsappItemClickListner whatsappItemClickListner;
    private List<PrabhagWiseBirthdaysItem> filterArrayList;
    private Context mycontext;
    private String status, elecname, day, month, year;

    public AdapterWardList(Context context, List<PrabhagWiseBirthdaysItem> items,String status1,String elecname1,String day1,String month1,String year1) {
        this.myItems = items;
        this.mycontext = context;
        this.filterArrayList = items;
        this.status = status1;
        this.elecname = elecname1;
        this.day = day1;
        this.month = month1;
        this.year = year1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mycontext).inflate(R.layout.adapter_birthday_ward_list, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    public void onItemClick(WhatsappItemClickListner whatsappItemClickListner){
        this.whatsappItemClickListner = whatsappItemClickListner;
    }

    @Override
    public int getItemCount() {
        return filterArrayList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder myHolder = (Holder) holder;
        final PrabhagWiseBirthdaysItem model = filterArrayList.get(position);

        myHolder.socname.setText("AC No. - "+model.getAcNo()+" / Ward No. - "+model.getWardNo());
        myHolder.roomcnt.setText(""+model.getTotalBirthDays());
        if(model.getCorporatorName() != null && !model.getCorporatorName().equalsIgnoreCase("")
                && model.getMobileNo1() != null && !model.getMobileNo1().equalsIgnoreCase("")){
            myHolder.clientname.setText(model.getCorporatorName()+" - "+model.getMobileNo1());
        }else{
            myHolder.clientname.setText("Unknown");
        }
        myHolder.corporatorwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsappItemClickListner.onItemClick(position, model.getWardNo(),model.getAcNo(),elecname,day,month,year,status);
            }
        });

        myHolder.itemView.setTag(position);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mycontext, QCBirthDayWardWiseActivity.class);
                intent.putExtra("wardno", ""+model.getWardNo());
                intent.putExtra("day", day);
                intent.putExtra("month", month);
                intent.putExtra("year", year);
                intent.putExtra("elecname", elecname);
                intent.putExtra("acno", ""+model.getAcNo());
                intent.putExtra("status", status);
                intent.putExtra("corporatorname", model.getCorporatorName());
                intent.putExtra("corporatornamemar", model.getCorporatorNameMar());
                intent.putExtra("designationname", model.getDesignationName());
                intent.putExtra("designationnamemar", model.getDesignationNameMar());
                intent.putExtra("mobileno1", model.getMobileNo1());
                intent.putExtra("mobileno2", model.getMobileNo2());
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

    class Holder extends RecyclerView.ViewHolder {
        TextView socname, roomcnt, clientname;
        ImageButton corporatorwhatsapp;

        public Holder(@NonNull View itemView) {
            super(itemView);
            socname = itemView.findViewById(R.id.socname);
            roomcnt = itemView.findViewById(R.id.roomcnt);
            clientname = itemView.findViewById(R.id.clientname);
            corporatorwhatsapp = itemView.findViewById(R.id.corporatorwhatsapp);
        }
    }

    public List<PrabhagWiseBirthdaysItem> filterSearchPosition() {
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

                    ArrayList<PrabhagWiseBirthdaysItem> filteredList = new ArrayList<>();

                    for (PrabhagWiseBirthdaysItem soc : myItems) {

                        if (Integer.toString(soc.getWardNo()).contains(charString)) {
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
                filterArrayList = (ArrayList<PrabhagWiseBirthdaysItem>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public List<PrabhagWiseBirthdaysItem> searchFilteredData() {
        return filterArrayList;
    }
}
