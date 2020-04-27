package com.ornettech.qcandbirthdaywishes.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.qcandbirthdaywishes.model.WardListItem;
import com.ornettech.qcandbirthdaywishes.model.ExecutiveListPojoItem;
import com.ornettech.qcandbirthdaywishes.model.QCResposeWiseReportItem;
import com.ornettech.qcandbirthdaywishes.model.QCResposeWiseReportResponse;
import com.ornettech.qcandbirthdaywishes.model.ResponseListPojoItem;
import com.ornettech.qcandbirthdaywishes.model.SpinnerResponse;
import com.ornettech.qcandbirthdaywishes.utility.CheckConnection;
import com.ornettech.qcandbirthdaywishes.utility.SendSMSWhatsApp;
import com.ornettech.qcandbirthdaywishes.utility.SharedPrefManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QCResponseWiseReportActivity extends AppCompatActivity {

    EditText repdate;
    Button submit,sendtowhatsapp,sendexceltowhatsapp;
    Spinner spnresponsename,spnexecutive,spinreptype,spnward;//,spnelection
    public TextView whatsappsms,error;
    private DatePickerDialog reportDatePickerDialog;
    public List<ResponseListPojoItem> responselist = new ArrayList<>();
    public List<WardListItem> wardlist = new ArrayList<>();
    public List<ExecutiveListPojoItem> executivelist = new ArrayList<>();
    public List<String> arrayList = new ArrayList<>();
    public List<String> arrayList1 = new ArrayList<>();
    public List<String> arrayList2 = new ArrayList<>();
    public List<String> arrayList3 = new ArrayList<>();
    public List<String> arrayListRep = new ArrayList<>();
    public List<String> arrayListWard = new ArrayList<>();
    public List<String> smsbreak = new ArrayList<>();
    public List<QCResposeWiseReportItem> newlist = new ArrayList<>();
    public String txtresponse, elename, qcres, qcexe, calldate;
    String header="",footer="",reptype="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_c_response_wise_report);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+SharedPrefManager.getInstance(QCResponseWiseReportActivity.this).getElectionName()+" QC Response Wise Report</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        submit =  findViewById(R.id.submit);
        //spnelection =  findViewById(R.id.electionname);
        spinreptype =  findViewById(R.id.spinreptype);
        spnward =  findViewById(R.id.spnward);
        spnresponsename =  findViewById(R.id.spnqcresponse);
        sendexceltowhatsapp =  findViewById(R.id.sendexceltowhatsapp);
        spnexecutive =  findViewById(R.id.executivename);
        sendtowhatsapp =  findViewById(R.id.sendtowhatsapp);
        repdate =  findViewById(R.id.callingdate);
        whatsappsms =  findViewById(R.id.sms);
        error =  findViewById(R.id.error);
        error.setVisibility(View.GONE);
        whatsappsms.setVisibility(View.GONE);
        sendtowhatsapp.setVisibility(View.GONE);
        sendexceltowhatsapp.setVisibility(View.GONE);

        whatsappsms.setMovementMethod(new ScrollingMovementMethod());

        String cdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        repdate.setText(cdate);
        repdate.setFocusable(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (new CheckConnection(QCResponseWiseReportActivity.this).isNetworkConnected()) {
                    if(submitMethod()){
                        //spnelection.getSelectedItem().toString()
                        Log.d("----->",spnresponsename.isSelected()+"");
                        String selreptype = spinreptype.getSelectedItem().toString();
                        if(selreptype.equalsIgnoreCase("QC Calling")){
                            selreptype = "QC";
                        }else if(selreptype.equalsIgnoreCase("Birthday Calling")) {
                            selreptype = "BD";
                        }else if(selreptype.equalsIgnoreCase("Round 1 Calling")) {
                            selreptype = "R1";
                        }else if(selreptype.equalsIgnoreCase("Round 2 Calling")) {
                            selreptype = "R2";
                        }else if(selreptype.equalsIgnoreCase("Round 3 Calling")) {
                            selreptype = "R3";
                        }else if(selreptype.equalsIgnoreCase("Client Calling")) {
                            selreptype = "CC";
                        }else if(selreptype.equalsIgnoreCase("Karyakarta Calling")) {
                            selreptype = "KK";
                        }else if(selreptype.equalsIgnoreCase("Hitachintak Calling")) {
                            selreptype = "HI";
                        }

                        reptype = selreptype;

                        callAPI(SharedPrefManager.getInstance(QCResponseWiseReportActivity.this).username(),
                                SharedPrefManager.getInstance(QCResponseWiseReportActivity.this).getElectionName(), spnexecutive.getSelectedItem().toString(),
                                spnresponsename.getSelectedItem().toString(), repdate.getText().toString(),selreptype,spnward.getSelectedItem().toString());
                    }else{
                        Toast.makeText(QCResponseWiseReportActivity.this, "Loading Lists please wait....", Toast.LENGTH_SHORT).show();
                        getSpinnersData();
                    }
                }else {
                    Toast.makeText(QCResponseWiseReportActivity.this,
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
                reportDatePickerDialog = new DatePickerDialog(QCResponseWiseReportActivity.this,
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
                //generateExcelFile();
                //sentToWhatsappSMS();
                ShowSMSPreviewOfSMS("",header,footer);
            }
        });

        sendexceltowhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(QCResponseWiseReportActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    ActivityCompat.requestPermissions(QCResponseWiseReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }else {
                    generateExcelFile();
                }
            }
        });

        arrayListRep.add("QC Calling");
        arrayListRep.add("Birthday Calling");
        arrayListRep.add("Round 1 Calling");
        arrayListRep.add("Round 2 Calling");
        arrayListRep.add("Round 3 Calling");
        arrayListRep.add("Client Calling");
        arrayListRep.add("Karyakarta Calling");
        arrayListRep.add("Hitachintak Calling");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(QCResponseWiseReportActivity.this, android.R.layout.simple_spinner_item, arrayListRep);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinreptype.setAdapter(adapter1);

        /*arrayListTemp.add("ALL");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(QCResponseWiseReportActivity.this, android.R.layout.simple_spinner_item, arrayListTemp);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnresponsename.setAdapter(adapter2);*/

        spinreptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 0){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(QCResponseWiseReportActivity.this, android.R.layout.simple_spinner_item, arrayList2);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnresponsename.setAdapter(adapter);
                }else{
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(QCResponseWiseReportActivity.this, android.R.layout.simple_spinner_item, arrayList3);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnresponsename.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        getSpinnersData();
    }

    private void callAPI(final String username, final String electionname, final String executivename, final String responsename, final String callingdate, final String selreptype, final String ward) {
        newlist.clear();
        smsbreak.clear();
        error.setVisibility(View.GONE);
        whatsappsms.setVisibility(View.GONE);
        sendtowhatsapp.setVisibility(View.GONE);
        sendexceltowhatsapp.setVisibility(View.GONE);
        final ProgressDialog progressBar = new ProgressDialog(QCResponseWiseReportActivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();
        Call<QCResposeWiseReportResponse> call1 = RetrofitClient
                .getInstance().getApi().QCResponseWiseReport(callingdate, electionname, username, responsename, selreptype, SharedPrefManager.getInstance(QCResponseWiseReportActivity.this).designation(), ward, executivename);
        call1.enqueue(new Callback<QCResposeWiseReportResponse>() {
            @Override
            public void onResponse(Call<QCResposeWiseReportResponse> call1, Response<QCResposeWiseReportResponse> response) {
                QCResposeWiseReportResponse res = response.body();
                progressBar.dismiss();
                txtresponse = responsename;
                elename = electionname;
                qcexe = SharedPrefManager.getInstance(QCResponseWiseReportActivity.this).name();
                calldate = callingdate;
                if(res.getQCResposeWiseReport().size()>0) {
                    newlist = res.getQCResposeWiseReport();
                    StringBuffer sb = new StringBuffer();

                    sb.append("*Executive Name* : "+SharedPrefManager.getInstance(QCResponseWiseReportActivity.this).name()+" \n");
                    sb.append("*Election Name* : "+electionname+" \n");
                    sb.append("*Executive Name* : "+executivename+" \n");
                    sb.append("*Calling Date* : "+callingdate+" \n");
                    sb.append("*Data* : \n");
                    sb.append(" \n");
                    for (int i=0;i<newlist.size();i++) {
                        int x = i+1;

                        if(selreptype.equalsIgnoreCase("QC")
                                || selreptype.equalsIgnoreCase("R1")
                                || selreptype.equalsIgnoreCase("BD")
                                || selreptype.equalsIgnoreCase("R2")
                                || selreptype.equalsIgnoreCase("R3")){
                            sb.append("*"+x +". Name* - "+newlist.get(i).getFullName()+" ("+newlist.get(i).getVtype()+") \n");
                            sb.append("    *Mob. No.* - "+newlist.get(i).getMobileNo()+" \n");
                            sb.append("    *Society Name* - "+newlist.get(i).getSocietyName()+" \n");
                            sb.append("    *Room No.* - "+newlist.get(i).getRoomNo()+" \n");
                            sb.append("    *Ward No.* - "+newlist.get(i).getWardNo()+" \n");
                            sb.append("    *QC Response.* - "+newlist.get(i).getQCResponse()+" \n");
                            sb.append("    *QC By* - "+newlist.get(i).getQCByUser()+" \n");
                            sb.append("    *Survey Date* - "+newlist.get(i).getSurveyDate()+" \n");
                            sb.append("    *Survey By* - "+newlist.get(i).getExecutiveName()+" \n");
                        }else if(selreptype.equalsIgnoreCase("CC")
                                || selreptype.equalsIgnoreCase("KK")
                                || selreptype.equalsIgnoreCase("HI")){
                            sb.append("*"+x +". Name* - "+newlist.get(i).getFullName()+" \n");
                            if(selreptype.equalsIgnoreCase("CC")){
                                sb.append("    *Contact Per. Mob. No.* - "+newlist.get(i).getMobileNo()+" \n");
                                sb.append("    *Contact Person* - "+newlist.get(i).getSocietyName()+" \n");
                            }else{
                                sb.append("    *Mob. No.* - "+newlist.get(i).getMobileNo()+" \n");
                                sb.append("    *Area Name* - "+newlist.get(i).getSocietyName()+" \n");
                            }

                            sb.append("    *Designation* - "+newlist.get(i).getVtype()+" \n");
                            sb.append("    *Ward No.* - "+newlist.get(i).getWardNo()+" \n");
                            sb.append("    *QC Response.* - "+newlist.get(i).getQCResponse()+" \n");
                            sb.append("    *QC By* - "+newlist.get(i).getQCByUser()+" \n");
                            sb.append("    *Remark* - "+newlist.get(i).getRoomNo()+" \n");
                        }

                        sb.append(" \n");
                    }
                    whatsappsms.setText(sb.toString());


                    //new list

                    StringBuffer sbheader = new StringBuffer();
                    StringBuffer sb2 = new StringBuffer();

                    sbheader.append("*Executive Name* : "+SharedPrefManager.getInstance(QCResponseWiseReportActivity.this).name()+" \n");
                    sbheader.append("*Election Name* : "+electionname+" \n");
                    sbheader.append("*Executive Name* : "+executivename+" \n");
                    sbheader.append("*Calling Date* : "+callingdate+" \n");
                    sbheader.append("*Data* : \n");
                    sbheader.append(" \n");
                    /*sbheader.append("*प्रभाग क्रमांक - "+ Transalator.englishDigitToMarathiDigit(Integer.toString(ward_no))+"* \n");
                    sbheader.append("*दिनांक - "+ Transalator.englishDigitToMarathiDigit(day)+"/"+Transalator.englishDigitToMarathiDigit(month)+"/"+Transalator.englishDigitToMarathiDigit(year) +"* \n");
                    sbheader.append("*प्रभागातील रहिवाश्यांची नावे :-* \n");*/
                    header = sbheader.toString();
                    sbheader.setLength(0);
                    for(int i=0;i<newlist.size();i++){
                        int x = i+1;
                        //sb2.append(i+". "+birthdays.get(k).getFullName()+" - "+birthdays.get(k).getMobileNo()+"\n");
                        if(selreptype.equalsIgnoreCase("QC")
                                || selreptype.equalsIgnoreCase("R1")
                                || selreptype.equalsIgnoreCase("BD")
                                || selreptype.equalsIgnoreCase("R2")
                                || selreptype.equalsIgnoreCase("R3")){
                            sb2.append("*"+x +". Name* - "+newlist.get(i).getFullName()+" ("+newlist.get(i).getVtype()+") \n");
                            sb2.append("    *Mob. No.* - "+newlist.get(i).getMobileNo()+" \n");
                            sb2.append("    *Society Name* - "+newlist.get(i).getSocietyName()+" \n");
                            sb2.append("    *Room No.* - "+newlist.get(i).getRoomNo()+" \n");
                            sb2.append("    *Ward No.* - "+newlist.get(i).getWardNo()+" \n");
                            sb2.append("    *QC Response.* - "+newlist.get(i).getQCResponse()+" \n");
                            sb2.append("    *QC By* - "+newlist.get(i).getQCByUser()+" \n");
                            sb2.append("    *Survey Date* - "+newlist.get(i).getSurveyDate()+" \n");
                            sb2.append("    *Survey By* - "+newlist.get(i).getExecutiveName()+" \n");
                        }else if(selreptype.equalsIgnoreCase("CC")
                                || selreptype.equalsIgnoreCase("KK")
                                || selreptype.equalsIgnoreCase("HI")){
                            sb2.append("*"+x +". Name* - "+newlist.get(i).getFullName()+" \n");

                            if(selreptype.equalsIgnoreCase("CC")){
                                sb2.append("    *Contact Per. Mob. No.* - "+newlist.get(i).getMobileNo()+" \n");
                                sb2.append("    *Contact Person* - "+newlist.get(i).getSocietyName()+" \n");
                            }else{
                                sb2.append("    *Mob. No.* - "+newlist.get(i).getMobileNo()+" \n");
                                sb2.append("    *Area Name* - "+newlist.get(i).getSocietyName()+" \n");
                            }
                            sb2.append("    *Designation.* - "+newlist.get(i).getVtype()+" \n");
                            sb2.append("    *Ward No.* - "+newlist.get(i).getWardNo()+" \n");
                            sb2.append("    *QC Response.* - "+newlist.get(i).getQCResponse()+" \n");
                            sb2.append("    *QC By* - "+newlist.get(i).getQCByUser()+" \n");
                            sb2.append("    *Remark* - "+newlist.get(i).getRoomNo()+" \n");
                        }
                        sb2.append(" \n");
                        if(x%10==0){
                            smsbreak.add(sb2.toString());
                            sb2.setLength(0);
                        }
                    }
                    if(sb2.toString().length()>0){
                        smsbreak.add(sb2.toString());
                        sb2.setLength(0);
                    }
                    footer = "\n_____________________________ ";

                }else{
                    whatsappsms.setText("");
                }

                if(whatsappsms.length()>0){
                    whatsappsms.setVisibility(View.VISIBLE);
                    sendtowhatsapp.setVisibility(View.VISIBLE);
                    error.setVisibility(View.GONE);
                    sendexceltowhatsapp.setVisibility(View.VISIBLE);
                }else{
                    error.setVisibility(View.VISIBLE);
                    whatsappsms.setVisibility(View.GONE);
                    sendtowhatsapp.setVisibility(View.GONE);
                    sendexceltowhatsapp.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<QCResposeWiseReportResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(QCResponseWiseReportActivity.this, "Failed to fetch data !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean submitMethod() {
        /*if(electionlist.size() == 0){
            return false;
        }else*/
        if(responselist.size() == 0){
            return false;
        }else if(executivelist.size() == 0){
            return false;
        }else{
            return true;
        }
    }

    private void getSpinnersData() {
        arrayList.clear();
        arrayList1.clear();
        arrayList2.clear();
        arrayList3.clear();
        responselist.clear();
        //electionlist.clear();
        executivelist.clear();
        final ProgressDialog progressBar = new ProgressDialog(QCResponseWiseReportActivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait ......");
        progressBar.show();

        Call<SpinnerResponse> call1 = RetrofitClient
                .getInstance().getApi().spinnerOptionForRep(SharedPrefManager.getInstance(QCResponseWiseReportActivity.this).username(), SharedPrefManager.getInstance(QCResponseWiseReportActivity.this).getElectionName());
        call1.enqueue(new Callback<SpinnerResponse>() {
            @Override
            public void onResponse(Call<SpinnerResponse> call1, Response<SpinnerResponse> response) {
                SpinnerResponse res = response.body();
                progressBar.dismiss();
                if(res.getWardList().size()>0) {
                    wardlist = res.getWardList();
                    arrayList.add("ALL");
                    for (int i=0;i<wardlist.size();i++){
                        arrayList.add(""+wardlist.get(i).getWardNo());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(QCResponseWiseReportActivity.this, android.R.layout.simple_spinner_item, arrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnward.setAdapter(adapter);
                }

                if(res.getExecutiveListPojo().size()>0) {
                    executivelist = res.getExecutiveListPojo();
                    arrayList1.add("ALL");
                    for (int i=0;i<executivelist.size();i++){
                        arrayList1.add(executivelist.get(i).getExecutiveName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(QCResponseWiseReportActivity.this, android.R.layout.simple_spinner_item, arrayList1);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnexecutive.setAdapter(adapter);
                }

                if(res.getResponseListPojo().size()>0) {
                    responselist = res.getResponseListPojo();
                    arrayList2.add("ALL");
                    arrayList3.add("ALL");
                    arrayList3.add("Wished");
                    arrayList3.add("Expired");
                    arrayList3.add("Birth Date Update");
                    for (int i=0;i<responselist.size();i++){
                        arrayList2.add(responselist.get(i).getQCResponse());
                        arrayList3.add(responselist.get(i).getQCResponse());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(QCResponseWiseReportActivity.this, android.R.layout.simple_spinner_item, arrayList2);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnresponsename.setAdapter(adapter);
                    /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(QCResponseWiseReportActivity.this, android.R.layout.simple_spinner_item, arrayList2);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnresponsename.setAdapter(adapter);*/
                }
            }

            @Override
            public void onFailure(Call<SpinnerResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(QCResponseWiseReportActivity.this, "Failed to fetch data !", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sentToWhatsappSMS(){

        SendSMSWhatsApp.sendWhatsApp(QCResponseWiseReportActivity.this,"", whatsappsms.getText().toString());

        /*Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, whatsappsms.getText().toString());
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(QCResponseWiseReportActivity.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }*/
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

    public void generateExcelFile(){
        final ProgressDialog progressBar = new ProgressDialog(QCResponseWiseReportActivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();
        Random rand = new Random();
        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "QC_Calling_Report ("+txtresponse+")-"+rand.nextInt(100000)+".xls";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("QC_Report", 0);
            // column and row
            if(reptype.equalsIgnoreCase("QC")
                    || reptype.equalsIgnoreCase("R1")
                    || reptype.equalsIgnoreCase("BD")
                    || reptype.equalsIgnoreCase("R2")
                    || reptype.equalsIgnoreCase("R3")){

                sheet.addCell(new Label(0, 0, "NAME"));
                sheet.addCell(new Label(1, 0, "MOBILE NO"));
                sheet.addCell(new Label(2, 0, "SOCIETY NAME"));
                sheet.addCell(new Label(3, 0, "ROOM NO"));
                sheet.addCell(new Label(4, 0, "Ward NO"));
                sheet.addCell(new Label(5, 0, "SURVEY DATE"));
                sheet.addCell(new Label(6, 0, "SURVEY BY"));
                sheet.addCell(new Label(7, 0, "ELECTION NAME"));
                sheet.addCell(new Label(8, 0, "QC RESPONSE"));
                sheet.addCell(new Label(9, 0, "QC CALLING EXE."));
                sheet.addCell(new Label(10, 0, "CALLING DATE"));
                sheet.addCell(new Label(11, 0, "Voter / Non-Voter"));

                for (int k=0;k<newlist.size();k++) {
                    QCResposeWiseReportItem model = newlist.get(k);
                    int i = k + 1;
                    sheet.addCell(new Label(0, i, model.getFullName()));
                    sheet.addCell(new Label(1, i, model.getMobileNo()));
                    sheet.addCell(new Label(2, i, model.getSocietyName()));
                    sheet.addCell(new Label(3, i, model.getRoomNo()));
                    sheet.addCell(new Label(4, i, ""+model.getWardNo()));
                    sheet.addCell(new Label(5, i, model.getSurveyDate()));
                    sheet.addCell(new Label(6, i, model.getExecutiveName()));
                    sheet.addCell(new Label(7, i, elename));
                    sheet.addCell(new Label(8, i, model.getQCResponse()));
                    sheet.addCell(new Label(9, i, model.getQCByUser()));
                    sheet.addCell(new Label(10, i, model.getQC_Calling_UpdatedDate()));
                    sheet.addCell(new Label(11, i, model.getVtype()));
                }

            }else if(reptype.equalsIgnoreCase("CC")
                    || reptype.equalsIgnoreCase("KK")
                    || reptype.equalsIgnoreCase("HI")){

                sheet.addCell(new Label(0, 0, "NAME"));

                if(reptype.equalsIgnoreCase("CC")){
                    sheet.addCell(new Label(1, 0, "CONTACT PERSON MOBILE NO"));
                    sheet.addCell(new Label(2, 0, "CONTACT PERSON"));
                }else{
                    sheet.addCell(new Label(1, 0, "MOBILE NO"));
                    sheet.addCell(new Label(2, 0, "AREA NAME"));
                }
                sheet.addCell(new Label(3, 0, "DESIGNATION"));
                sheet.addCell(new Label(4, 0, "Ward NO"));
                sheet.addCell(new Label(5, 0, "ELECTION NAME"));
                sheet.addCell(new Label(6, 0, "QC RESPONSE"));
                sheet.addCell(new Label(7, 0, "QC CALLING EXE."));
                sheet.addCell(new Label(8, 0, "CALLING DATE"));
                sheet.addCell(new Label(9, 0, "REMARK"));

                for (int k=0;k<newlist.size();k++) {
                    QCResposeWiseReportItem model = newlist.get(k);
                    int i = k + 1;
                    sheet.addCell(new Label(0, i, model.getFullName()));
                    sheet.addCell(new Label(1, i, model.getMobileNo()));
                    sheet.addCell(new Label(2, i, model.getSocietyName()));
                    sheet.addCell(new Label(3, i, model.getVtype()));
                    sheet.addCell(new Label(4, i, ""+model.getWardNo()));
                    sheet.addCell(new Label(5, i, elename));
                    sheet.addCell(new Label(6, i, model.getQCResponse()));
                    sheet.addCell(new Label(7, i, model.getQCByUser()));
                    sheet.addCell(new Label(8, i, model.getQC_Calling_UpdatedDate()));
                    sheet.addCell(new Label(9, i, model.getRoomNo()));
                }

            }



            //closing cursor
            workbook.write();
            workbook.close();
            progressBar.dismiss();
            Toast.makeText(QCResponseWiseReportActivity.this,"Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();

            /*File outputFile = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS), "example.pdf");*/
            Uri uri = Uri.fromFile(file);

            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("application/xls");
            share.putExtra(Intent.EXTRA_STREAM, uri);
            share.setPackage("com.whatsapp");

            try {
                startActivity(share);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(QCResponseWiseReportActivity.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ShowSMSPreviewOfSMS(final String corporatornumber, final String header, final String footer){
        final Dialog dialog = new Dialog(QCResponseWiseReportActivity.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.whatsapp_multiple_preview);
        ImageButton closepopup = dialog.findViewById(R.id.closepopup);
        RecyclerView whatsappsmslist = dialog.findViewById(R.id.whatsappsmslist);
        //Button sendbtn = dialog.findViewById(R.id.sendbtn);
        /*final TextView whatsappsms = dialog.findViewById(R.id.whatsappsms);
        whatsappsms.setMovementMethod(new ScrollingMovementMethod());
        whatsappsms.setText(sb.toString());*/

        closepopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        whatsappsmslist.setNestedScrollingEnabled(false);
        whatsappsmslist.setLayoutManager(new LinearLayoutManager(QCResponseWiseReportActivity.this));
        whatsappsmslist.setAdapter(new RecyclerView.Adapter() {
                                       @NonNull
                                       @Override
                                       public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                           View view = LayoutInflater.from(QCResponseWiseReportActivity.this).inflate(R.layout.adapter_whatsapp_long_sms_split, viewGroup, false);
                                           InnerHolder holder = new InnerHolder(view);
                                           return holder;
                                       }

                                       @Override
                                       public long getItemId(int position) {
                                           return position;
                                       }

                                       @Override
                                       public int getItemViewType(int position) {
                                           return position;
                                       }

                                       @Override
                                       public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                                           final InnerHolder innerHolder = (InnerHolder) viewHolder;
                                           final String model = smsbreak.get(i);

//                                        String header = "";
//                                        String footer = "";
                                           final String msg = header+"\n"+model+"\n"+footer;
                                           innerHolder.wamsg.setText(msg);

                                           innerHolder.sendtxt.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   innerHolder.llt.setBackgroundColor(Color.parseColor("#02be25"));
                                                   SendSMSWhatsApp.sendWhatsApp(QCResponseWiseReportActivity.this, corporatornumber, msg);
                                                   //sendSMSWhatsApp.callMobNo(QCBirthDayActivity.this, model.getMobileNo());
                                               }
                                           });

                                       }

                                       @Override
                                       public int getItemCount() {
                                           //return fullsumm.size();
                                           return smsbreak.size();
                                       }

                                       class InnerHolder extends RecyclerView.ViewHolder {
                                           TextView wamsg, sendtxt;
                                           LinearLayout llt,llt2;

                                           public InnerHolder(@NonNull View itemView) {
                                               super(itemView);
                                               llt = itemView.findViewById(R.id.llt);
                                               llt2 = itemView.findViewById(R.id.llt2);
                                               sendtxt = itemView.findViewById(R.id.whatsappbtn);
                                               wamsg = itemView.findViewById(R.id.smspreview);
                                           }
                                       }
                                   }
        );
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
