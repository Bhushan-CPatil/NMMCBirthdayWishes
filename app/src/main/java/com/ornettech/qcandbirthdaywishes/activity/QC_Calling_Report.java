package com.ornettech.qcandbirthdaywishes.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.qcandbirthdaywishes.model.QCReportItem;
import com.ornettech.qcandbirthdaywishes.model.QCReportResponse;
import com.ornettech.qcandbirthdaywishes.model.SiteAndQCResponse;
import com.ornettech.qcandbirthdaywishes.model.SiteNameListPojoItem;
import com.ornettech.qcandbirthdaywishes.utility.CheckConnection;
import com.ornettech.qcandbirthdaywishes.utility.DBConnIP;
import com.ornettech.qcandbirthdaywishes.utility.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QC_Calling_Report extends AppCompatActivity {

    EditText repdate;
    Button submit,sendtowhatsapp;
    Spinner spnsitename,spnreptype;
    public TextView whatsappsms,error;
    public String sharedelectionname;
    private DatePickerDialog reportDatePickerDialog;
    public List<SiteNameListPojoItem> sitelist = new ArrayList<>();
    public List<String> arrayList = new ArrayList<>();
    public List<String> arrayList2 = new ArrayList<>();
    public List<QCReportItem> newlist = new ArrayList<>();
    public String reporttype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qc__calling__report);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+SharedPrefManager.getInstance(QC_Calling_Report.this).getElectionName()+" Daily QC Report</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        submit =  findViewById(R.id.submit);
        spnsitename =  findViewById(R.id.spnsitename);
        spnreptype =  findViewById(R.id.spnreptype);
        sendtowhatsapp =  findViewById(R.id.sendtowhatsapp);
        repdate =  findViewById(R.id.reportdate);
        whatsappsms =  findViewById(R.id.sms);
        error =  findViewById(R.id.error);
        error.setVisibility(View.GONE);
        whatsappsms.setVisibility(View.GONE);
        sendtowhatsapp.setVisibility(View.GONE);
        sharedelectionname = SharedPrefManager.getInstance(QC_Calling_Report.this).getElectionName();
        String cdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        repdate.setText(cdate);
        repdate.setFocusable(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (new CheckConnection(QC_Calling_Report.this).isNetworkConnected()) {
                    submitMethod();
                }else {
                    Toast.makeText(QC_Calling_Report.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        repdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                reportDatePickerDialog = new DatePickerDialog(QC_Calling_Report.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                repdate.setText(String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                reportDatePickerDialog.show();
            }
        });

        sendtowhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentToWhatsappSMS();
            }
        });

        arrayList2.add("QC Calling");
        arrayList2.add("Birthday Calling");
        arrayList2.add("Round 1 Calling");
        arrayList2.add("Round 2 Calling");
        arrayList2.add("Round 3 Calling");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(QC_Calling_Report.this, android.R.layout.simple_spinner_item, arrayList2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnreptype.setAdapter(adapter1);

        getSiteAndResponse();
    }

    private void getSiteAndResponse() {
        arrayList.clear();
        DBConnIP.arrayListToDialog.clear();
        sitelist.clear();
        final ProgressDialog progressBar = new ProgressDialog(QC_Calling_Report.this);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(QC_Calling_Report.this, android.R.layout.simple_spinner_item, arrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnsitename.setAdapter(adapter);
                }else{
                    Toast.makeText(QC_Calling_Report.this, "No Site Exists !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SiteAndQCResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(QC_Calling_Report.this, "Failed to fetch data !", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void submitMethod() {
        if(sitelist.size() > 0 ){
            if(spnreptype.getSelectedItem().toString().equalsIgnoreCase("QC Calling")) {
                reporttype = "QC";
            }else if(spnreptype.getSelectedItem().toString().equalsIgnoreCase("Birthday Calling")) {
                reporttype = "BD";
            }else if(spnreptype.getSelectedItem().toString().equalsIgnoreCase("Round 1 Calling")) {
                reporttype = "R1";
            }else if(spnreptype.getSelectedItem().toString().equalsIgnoreCase("Round 2 Calling")) {
                reporttype = "R2";
            }else if(spnreptype.getSelectedItem().toString().equalsIgnoreCase("Round 3 Calling")) {
                reporttype = "R3";
            }
            callApi(repdate.getText().toString(), spnsitename.getSelectedItem().toString().trim());
        }else{
            getSiteAndResponse();
            Toast.makeText(QC_Calling_Report.this, "Loading site name list please wait !", Toast.LENGTH_SHORT).show();
        }
    }

    private void callApi(final String repdate, final String sitename) {
        newlist.clear();
        final ProgressDialog progressBar = new ProgressDialog(QC_Calling_Report.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();
        String username = SharedPrefManager.getInstance(QC_Calling_Report.this).username();
        Call<QCReportResponse> call1 = RetrofitClient
                .getInstance().getApi().qc_report(repdate, sharedelectionname, username, reporttype, SharedPrefManager.getInstance(QC_Calling_Report.this).designation(), sitename);
        call1.enqueue(new Callback<QCReportResponse>() {
            @Override
            public void onResponse(Call<QCReportResponse> call1, Response<QCReportResponse> response) {
                QCReportResponse res = response.body();
                progressBar.dismiss();
                if(res.getQCReport().size()>0) {
                    newlist = res.getQCReport();
                    HashSet<String> hsdate = new HashSet<String>();
                    HashSet<Integer> hsward = new HashSet<Integer>();
                    HashSet<String> hssitename = new HashSet<String>();
                    LinkedHashMap<String, Integer> hm = new LinkedHashMap<String, Integer>();
                    StringBuffer sb = new StringBuffer();
                    int total = 0;

                    for(int i=0;i<newlist.size();i++){
                        hsdate.add(newlist.get(i).getUpdatedDate());
                        hsward.add(newlist.get(i).getWard_no());
                        hssitename.add(newlist.get(i).getSiteName());

                        if(hm.containsKey(newlist.get(i).getQCResponse())){
                            hm.put(newlist.get(i).getQCResponse(), hm.get(newlist.get(i).getQCResponse()) + newlist.get(i).getCnt());
                        }else{
                            hm.put(newlist.get(i).getQCResponse(),newlist.get(i).getCnt());
                        }
                    }

                    //String dates = String.join(", ", hsdate);
                    StringBuffer concateDate = new StringBuffer();
                    hsdate.remove(null);
                    TreeSet<String> treeDate = new TreeSet<String>(hsdate);
                    int k=0;
                    for (String dates : treeDate) {
                        if(k>0){
                            concateDate.append(", ");
                        }
                        concateDate.append(dates);
                        k++;
                    }

                    StringBuffer concateWard = new StringBuffer();
                    hsward.remove(null);
                    TreeSet<Integer> treeward = new TreeSet<Integer>(hsward);
                    int l=0;
                    for (int ward : treeward) {
                        if(l>0){
                            concateWard.append(", ");
                        }
                        concateWard.append(ward);
                        l++;
                    }

                    StringBuffer concateSiteName = new StringBuffer();
                    hssitename.remove(null);
                    TreeSet<String> treeSiteName = new TreeSet<String>(hssitename);
                    int m=0;
                    for (String site : treeSiteName) {
                        if(m>0){
                            concateSiteName.append(", ");
                        }
                        concateSiteName.append(site);
                        m++;
                    }

                    sb.append("*Executive Name* : "+SharedPrefManager.getInstance(QC_Calling_Report.this).name()+" \n");
                    if(reporttype.equalsIgnoreCase("QC")
                            || reporttype.equalsIgnoreCase("R1")
                            || reporttype.equalsIgnoreCase("R2")
                            || reporttype.equalsIgnoreCase("R3")) {
                        sb.append("*Survey Date* : " + concateDate.toString() + " \n");
                    }
                    sb.append("*Calling Date* : "+repdate+" \n");
                    sb.append("*Ward No.* : "+concateWard.toString()+" \n");
                    sb.append("*Site Name* : "+concateSiteName.toString()+" \n");
                    if(reporttype.equalsIgnoreCase("QC")
                            || reporttype.equalsIgnoreCase("R1")
                            || reporttype.equalsIgnoreCase("R2")
                            || reporttype.equalsIgnoreCase("R3")) {
                        sb.append("*Data Call QC* : \n");
                    }else{
                        sb.append("*Data Birthday Calls* : \n");
                    }

                    for (HashMap.Entry<String,Integer> entry : hm.entrySet()) {
                        sb.append(entry.getKey()+" - "+entry.getValue()+" \n");
                        total += entry.getValue();
                    }
                    sb.append("___________________________ \n");
                    sb.append("Total - "+total+" \n");
                    whatsappsms.setText(sb.toString());

                    /*for(int i=0;i<newlist.size();i++){
                        if(i==0) {
                            sb.append("*Survey Date* : "+newlist.get(i).getUpdatedDate()+" \n");
                            sb.append("*Calling Date* : "+repdate+" \n");
                            sb.append("*Ward No.* : "+newlist.get(i).getWard_no()+" \n");
                            sb.append("*Site Name* : "+sitename+" \n");
                            sb.append("*Data Call QC* : \n");
                        }
                        sb.append(newlist.get(i).getQCResponse()+" - "+newlist.get(i).getCnt()+" \n");
                        total += newlist.get(i).getCnt();
                    }
                    sb.append("___________________________ \n");
                    sb.append("Total - "+total+" \n");*/

                }else{
                    whatsappsms.setText("");
                }

                if(whatsappsms.length()>0){
                    whatsappsms.setVisibility(View.VISIBLE);
                    sendtowhatsapp.setVisibility(View.VISIBLE);
                    error.setVisibility(View.GONE);
                }else{
                    error.setVisibility(View.VISIBLE);
                    whatsappsms.setVisibility(View.GONE);
                    sendtowhatsapp.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<QCReportResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(QC_Calling_Report.this, "Failed to fetch data !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sentToWhatsappSMS(){

        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, whatsappsms.getText().toString());
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(QC_Calling_Report.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
