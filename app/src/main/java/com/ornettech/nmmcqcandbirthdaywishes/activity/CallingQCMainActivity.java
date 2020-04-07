package com.ornettech.nmmcqcandbirthdaywishes.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ornettech.nmmcqcandbirthdaywishes.R;
import com.ornettech.nmmcqcandbirthdaywishes.adapter.Adapter_Calling_QC;
import com.ornettech.nmmcqcandbirthdaywishes.adapter.DefaultResponse;
import com.ornettech.nmmcqcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.nmmcqcandbirthdaywishes.model.CallingQCMainResponse;
import com.ornettech.nmmcqcandbirthdaywishes.model.ResponseListPojoItem;
import com.ornettech.nmmcqcandbirthdaywishes.model.SiteAndQCResponse;
import com.ornettech.nmmcqcandbirthdaywishes.model.SiteNameListPojoItem;
import com.ornettech.nmmcqcandbirthdaywishes.utility.CheckConnection;
import com.ornettech.nmmcqcandbirthdaywishes.utility.DBConnIP;
import com.ornettech.nmmcqcandbirthdaywishes.utility.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallingQCMainActivity extends AppCompatActivity {

    EditText from,to;
    Button submit;
    public List<String> arrayList = new ArrayList<>();
    public List<String> arrayList1 = new ArrayList<>();
    //public List<String> arrayListToDialog = new ArrayList<>();
    Spinner spinsitename,spinmsgres;
    public String sharedelectionname;
    public TextView pending,total;
    public RecyclerView rcyclr_list;
    public Adapter_Calling_QC mAdapter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter,parseDate;

    public List<SiteNameListPojoItem> sitelist = new ArrayList<>();
    public List<ResponseListPojoItem> responselist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling_qcmain);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+SharedPrefManager.getInstance(CallingQCMainActivity.this).getElectionName()+" QC</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        spinsitename =  findViewById(R.id.spnsitename);
        from =  findViewById(R.id.fromdate);
        rcyclr_list =  findViewById(R.id.surveyvoterlist);
        submit = findViewById(R.id.submit);
        pending = findViewById(R.id.pending);
        total = findViewById(R.id.total);
        to = findViewById(R.id.todate);
        spinmsgres= findViewById(R.id.spnsmsstatus);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        sharedelectionname = SharedPrefManager.getInstance(CallingQCMainActivity.this).getElectionName();

        String cdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        from.setText(cdate);
        from.setFocusable(false);
        to.setText(cdate);
        to.setFocusable(false);
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                fromDatePickerDialog = new DatePickerDialog(CallingQCMainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                from.setText(String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                fromDatePickerDialog.show();
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                toDatePickerDialog = new DatePickerDialog(CallingQCMainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                to.setText(String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                toDatePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (new CheckConnection(CallingQCMainActivity.this).isNetworkConnected()) {
                    submitMethod(CallingQCMainActivity.this);
                }else {
                    Toast.makeText(CallingQCMainActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        getSiteAndResponse();
    }

    private void submitMethod(final Context context) {
            if(sitelist.size() > 0 && responselist.size() > 0){
                String edtfrom = from.getText().toString();
                String edtto= to.getText().toString();

                parseDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date startDate;
                Date endDate;

                try {
                    startDate = dateFormatter.parse(edtfrom);
                    String newEdtFrom = parseDate.format(startDate);
                    endDate = dateFormatter.parse(edtto);
                    String newEdtTo = parseDate.format(endDate);

                    if (startDate.after(endDate)) {

                        to.setError("ToDate should be greater than from date");
                        Toast.makeText(context,
                                "ToDate should be greater than from date",
                                Toast.LENGTH_LONG).show();
                    }else{
                        callApi(newEdtFrom,newEdtTo,context, spinsitename.getSelectedItem().toString().trim(), spinmsgres.getSelectedItem().toString().trim());
                    }
                }catch (Exception e){
                    e.toString();
                }
            }else{
                getSiteAndResponse();
                Toast.makeText(context, "Loading site name list please wait !", Toast.LENGTH_SHORT).show();
            }

    }

    private void callApi(final String newEdtFrom, final String newEdtTo, final Context context, final String sitename, final String msgres) {

        DBConnIP.newlist.clear();
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();
        Call<CallingQCMainResponse> call1 = RetrofitClient
                .getInstance().getApi().fetchCallingData(newEdtFrom, newEdtTo, sharedelectionname, sitename, msgres);
        call1.enqueue(new Callback<CallingQCMainResponse>() {
            @Override
            public void onResponse(Call<CallingQCMainResponse> call1, Response<CallingQCMainResponse> response) {
                CallingQCMainResponse res = response.body();
                progressBar.dismiss();
                if(res.getVoterCallingQCPojo().size()>0) {
                    DBConnIP.newlist = res.getVoterCallingQCPojo();
                    total.setText("Total - "+DBConnIP.newlist.size());
                    int cnt = 0;
                    for(int k=0; k<DBConnIP.newlist.size();k++){
                        if(DBConnIP.newlist.get(k).getQCCallingUpdatedDate().equalsIgnoreCase("")){
                            cnt++;
                        }
                    }
                    pending.setText("QC Pending - "+cnt);
                    mAdapter = new Adapter_Calling_QC(context, DBConnIP.newlist, "E", DBConnIP.arrayListToDialog, sitename,newEdtFrom,newEdtTo,msgres);
                    rcyclr_list.setNestedScrollingEnabled(false);
                    rcyclr_list.setLayoutManager(new LinearLayoutManager(context));
                    rcyclr_list.setAdapter(mAdapter);
                    rcyclr_list.getAdapter().notifyDataSetChanged();
                }else{
                    Toast.makeText(context, "No Records Exists For Selected Criteria!", Toast.LENGTH_LONG).show();
                    mAdapter = new Adapter_Calling_QC(context, DBConnIP.newlist, "E",DBConnIP.arrayListToDialog, sitename,newEdtFrom,newEdtTo,msgres);
                    rcyclr_list.setNestedScrollingEnabled(false);
                    rcyclr_list.setLayoutManager(new LinearLayoutManager(context));
                    rcyclr_list.setAdapter(mAdapter);
                    rcyclr_list.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CallingQCMainResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(context, "Failed to fetch data !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSiteAndResponse() {
        arrayList.clear();
        DBConnIP.arrayListToDialog.clear();
        sitelist.clear();
        responselist.clear();
        arrayList1.clear();
        final ProgressDialog progressBar = new ProgressDialog(CallingQCMainActivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();

        Call<SiteAndQCResponse> call1 = RetrofitClient
                .getInstance().getApi().siteNameAndQCResList(sharedelectionname);
        call1.enqueue(new Callback<SiteAndQCResponse>() {
            @Override
            public void onResponse(Call<SiteAndQCResponse> call1, Response<SiteAndQCResponse> response) {
                SiteAndQCResponse res = response.body();
                progressBar.dismiss();
                if(res.getSiteNameListPojo().size()>0) {
                    sitelist = res.getSiteNameListPojo();
                    arrayList.add("ALL");
                    for (int i=0;i<sitelist.size();i++){
                        arrayList.add(sitelist.get(i).getSiteName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CallingQCMainActivity.this, android.R.layout.simple_spinner_item, arrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinsitename.setAdapter(adapter);
                }else{
                    Toast.makeText(CallingQCMainActivity.this, "No Site Exists !", Toast.LENGTH_LONG).show();
                }

                if(res.getResponseListPojo().size()>0) {
                    responselist = res.getResponseListPojo();
                    arrayList1.add("ALL");
                    arrayList1.add("Pending");
                    for (int i=0;i<responselist.size();i++){
                        arrayList1.add(responselist.get(i).getQCResponse());
                        DBConnIP.arrayListToDialog.add(responselist.get(i).getQCResponse());
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(CallingQCMainActivity.this, android.R.layout.simple_spinner_item, arrayList1);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinmsgres.setAdapter(adapter1);
                }else{
                    Toast.makeText(CallingQCMainActivity.this, "No Site Exists !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SiteAndQCResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(CallingQCMainActivity.this, "Failed to fetch data !", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem search = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;*/
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    public static void EditDialog(final Context context, final String selvotercd, final String qcstatus, final List<String> resitem, final int ac_no, final String mobileno, final int wardno,
                           final String spinsitename1,final String from, final String to, final String msgres1,final int position) {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.edit_popup);
        Button close = dialog.findViewById(R.id.closebtn);
        Button update = dialog.findViewById(R.id.updatebtn);
        final Spinner spinresponse1 = dialog.findViewById(R.id.edtspnsmsresponse);
        ArrayAdapter<String> adapterx = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, resitem);
        adapterx.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinresponse1.setAdapter(adapterx);
        if (qcstatus != null && !qcstatus.equalsIgnoreCase("")) {
            int spinnerPosition = adapterx.getPosition(qcstatus);
            spinresponse1.setSelection(spinnerPosition);
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle("Update ?");
                builder.setMessage("Are you sure want to update ?");
                builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogx, int which) {

                        updateQCResponse(context, dialog, spinresponse1.getSelectedItem().toString().trim(),selvotercd, ac_no, mobileno, wardno, spinsitename1,from,to,msgres1,position);
                        //Log.d("status--->",""+DBConnIP.status);
                        //dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogx, int which) {

                    }
                });
                AlertDialog dialog2 = builder.create();
                dialog2.show();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        //return DBConnIP.status;
        //return true;
    }

    private static void updateQCResponse(final Context context, final Dialog dialog, final String selspinresponse, String selvotercd, final int ac_no, final String mobileno,
                                  final int wardno, final String spinsitename1, final String from, final String to, final String msgres1, final int position) {
        //DBConnIP.updateQCResponse_status = false;
        final ProgressDialog progressBar1 = new ProgressDialog(context);
        progressBar1.setCancelable(false);
        progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar1.setMessage("Please wait...");
        progressBar1.show();
        Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().updateCallingQC(selvotercd, mobileno, SharedPrefManager.getInstance(context).getElectionName(), spinsitename1, ac_no, SharedPrefManager.getInstance(context).username(), selspinresponse);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                progressBar1.dismiss();
                if(!res.isError()){
                    //Toast.makeText(context, res.getErrormsg(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    DBConnIP.newlist.get(position).setQCResponse(selspinresponse);
                    /*mDataSet.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,mDataSet.size());*/
                    //new CallingQCMainActivity().rcyclr_list.getAdapter().notifyDataSetChanged();
                    //callApi(from,to,context,spinsitename1,msgres1);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setCancelable(false);
                    builder1.setTitle("Update Successfully.");
                    builder1.setMessage(res.getErrormsg());
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogx, int which) {

                        }
                    });
                    AlertDialog dialog3 = builder1.create();
                    dialog3.show();
                    //DBConnIP.updateQCResponse_status = true;

                }else{
                    Toast.makeText(context, res.getErrormsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                progressBar1.dismiss();
                Toast.makeText(context, "Failed to update !", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
