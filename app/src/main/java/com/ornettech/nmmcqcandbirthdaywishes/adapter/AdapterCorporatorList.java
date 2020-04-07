package com.ornettech.nmmcqcandbirthdaywishes.adapter;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ornettech.nmmcqcandbirthdaywishes.R;
import com.ornettech.nmmcqcandbirthdaywishes.activity.WardWiseBirthdayImageList;
import com.ornettech.nmmcqcandbirthdaywishes.listener.GalleryListener;
import com.ornettech.nmmcqcandbirthdaywishes.model.CorporatorsElectionWiseItem;
import com.ornettech.nmmcqcandbirthdaywishes.utility.Transalator;

import java.util.ArrayList;
import java.util.List;

public class AdapterCorporatorList extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<CorporatorsElectionWiseItem> myItems;
    private ItemListener myListener;
    private List<CorporatorsElectionWiseItem> filterArrayList;
    private Context mycontext;
    public GalleryListener itemClickListener;
    String flg;
    String electionname;
    //SendSMSWhatsApp sendSMSWhatsApp = new SendSMSWhatsApp();
    public List<String> responselist;
    public String spinsitename,fromdate,todate,msgres;

    public AdapterCorporatorList(Context context, List<CorporatorsElectionWiseItem> items, String ele) {
        this.myItems = items;
        //myListener = listener;
        this.mycontext = context;
        this.electionname = ele;
        this.filterArrayList = items;
    }

    public void onItemClick(GalleryListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mycontext).inflate(R.layout.adapter_ward_wise_birthday_photo, parent, false);
        InnerHolder holder = new InnerHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return filterArrayList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final InnerHolder innerHolder = (InnerHolder) holder;
        final CorporatorsElectionWiseItem model = filterArrayList.get(position);

        if(model.getBirthDayWishImage()!=null && !model.getBirthDayWishImage().equalsIgnoreCase("")){
            innerHolder.llt.setBackgroundColor(Color.parseColor("#00C00C"));
            Glide.with(mycontext).load(model.getBirthDayWishImage()).into(innerHolder.banner);
        }else{
            innerHolder.llt.setBackgroundColor(Color.parseColor("#FF5555"));
            innerHolder.banner.setImageDrawable(ContextCompat.getDrawable(mycontext, R.drawable.ic_no_image_found));
        }

        if(model.getCorporatorNameMar()!=null && !model.getCorporatorNameMar().equalsIgnoreCase("")){
            innerHolder.corporatorname.setText(model.getCorporatorNameMar());
        }else{
            innerHolder.corporatorname.setText(model.getCorporatorName());
        }

        if(model.getDesignationNameMar()!=null && !model.getDesignationNameMar().equalsIgnoreCase("")){
            innerHolder.corpdesig.setText(model.getDesignationNameMar()+", प्रभाग क्र - "+ Transalator.englishDigitToMarathiDigit(model.getWardNo()+""));
        }else{
            innerHolder.corpdesig.setText(model.getDesignationName()+", Ward No. - "+Transalator.englishDigitToMarathiDigit(model.getWardNo()+""));
        }

        if(model.getMobileNo1()!=null && !model.getMobileNo1().equalsIgnoreCase("")){
            innerHolder.corpmob.setText(model.getMobileNo1());
        }else{
            innerHolder.corpmob.setText("");
        }

        if(model.getParty()!=null && !model.getParty().equalsIgnoreCase("")){
            innerHolder.corpparty.setText(model.getParty());
        }else{
            innerHolder.corpparty.setText("");
        }

        innerHolder.itemView.setTag(position);
        innerHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getBirthDayWishImage()!=null && model.getBirthDayWishImage().equalsIgnoreCase("")){
                    itemClickListener.onItemClick(position, model.getWardNo(),"I",electionname,Integer.toString(model.getCorporatorCd()));
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(mycontext);
                    builder.setCancelable(true);
                    builder.setTitle("New ?");
                    builder.setMessage("Are you sure you want to upload new image ?");
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    itemClickListener.onItemClick(position, model.getWardNo(),"U",electionname,Integer.toString(model.getCorporatorCd()));
                                    dialog.dismiss();
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
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
        void onItemClick(CorporatorsElectionWiseItem item);
    }


    class InnerHolder extends RecyclerView.ViewHolder {
        TextView corporatorname, corpdesig, corpmob, corpparty;
        LinearLayout llt;
        ImageView banner;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            llt = itemView.findViewById(R.id.llt);
            banner = itemView.findViewById(R.id.banner);
            corporatorname = itemView.findViewById(R.id.corporatorname);
            corpdesig = itemView.findViewById(R.id.corpdesig);
            corpmob = itemView.findViewById(R.id.corpmob);
            corpparty = itemView.findViewById(R.id.corpparty);
        }
    }

    public List<CorporatorsElectionWiseItem> filterSearchPosition() {
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

                    ArrayList<CorporatorsElectionWiseItem> filteredList = new ArrayList<>();

                    for (CorporatorsElectionWiseItem soc : myItems) {

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
                filterArrayList = (ArrayList<CorporatorsElectionWiseItem>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public List<CorporatorsElectionWiseItem> searchFilteredData() {
        return filterArrayList;
    }
}

