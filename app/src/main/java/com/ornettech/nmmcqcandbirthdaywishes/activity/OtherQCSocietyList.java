package com.ornettech.nmmcqcandbirthdaywishes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ornettech.nmmcqcandbirthdaywishes.R;
import com.ornettech.nmmcqcandbirthdaywishes.adapter.Adapter_Soc_Room_Wise_List;
import com.ornettech.nmmcqcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.nmmcqcandbirthdaywishes.model.ResponseListPojoItem;
import com.ornettech.nmmcqcandbirthdaywishes.model.SiteAndQCResponse;
import com.ornettech.nmmcqcandbirthdaywishes.model.SiteNameListPojoItem;
import com.ornettech.nmmcqcandbirthdaywishes.model.SocRoomWiseResponse;
import com.ornettech.nmmcqcandbirthdaywishes.model.SocietyRoomWiseQCListPojoItem;
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

public class OtherQCSocietyList extends AppCompatActivity {

    EditText from,to;
    Button submit;
    public List<String> arrayList = new ArrayList<>();
    public List<String> arrayList1 = new ArrayList<>();
    //public List<String> arrayListToDialog = new ArrayList<>();
    Spinner spinsitename,spinmsgres;
    public List<SocietyRoomWiseQCListPojoItem> newlist = new ArrayList<>();
    public TextView soccount,roomcount;
    public String sharedelectionname;
    public RecyclerView rcyclr_list;
    public Adapter_Soc_Room_Wise_List mAdapter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter,parseDate;

    public List<SiteNameListPojoItem> sitelist = new ArrayList<>();
    public List<ResponseListPojoItem> responselist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_qcsociety_list);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ SharedPrefManager.getInstance(OtherQCSocietyList.this).getElectionName()+" QC</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        spinsitename =  findViewById(R.id.spnsitename);
        from =  findViewById(R.id.fromdate);
        rcyclr_list =  findViewById(R.id.surveysocietylist);
        submit = findViewById(R.id.submit);
        soccount = findViewById(R.id.soccount);
        roomcount = findViewById(R.id.roomcount);
        to = findViewById(R.id.todate);
        spinmsgres= findViewById(R.id.spnsmsstatus);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        sharedelectionname = SharedPrefManager.getInstance(OtherQCSocietyList.this).getElectionName();

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
                fromDatePickerDialog = new DatePickerDialog(OtherQCSocietyList.this,
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
                toDatePickerDialog = new DatePickerDialog(OtherQCSocietyList.this,
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
                if (new CheckConnection(OtherQCSocietyList.this).isNetworkConnected()) {
                    submitMethod(OtherQCSocietyList.this);
                }else {
                    Toast.makeText(OtherQCSocietyList.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        getSiteAndResponse();
    }

    private void getSiteAndResponse() {
        arrayList.clear();
        DBConnIP.arrayListToDialog.clear();
        sitelist.clear();
        responselist.clear();
        arrayList1.clear();
        final ProgressDialog progressBar = new ProgressDialog(OtherQCSocietyList.this);
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
                    //sitelist = res.getSiteNameListPojo();
                    arrayList.add("ALL");
                    /*for (int i=0;i<sitelist.size();i++){
                        arrayList.add(sitelist.get(i).getSiteName().substring(2));
                    }*/
                    for (int i=1;i<=200;i++){
                        arrayList.add(i+"");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(OtherQCSocietyList.this, android.R.layout.simple_spinner_item, arrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinsitename.setAdapter(adapter);
                }else{
                    Toast.makeText(OtherQCSocietyList.this, "No Site Exists !", Toast.LENGTH_LONG).show();
                }

                if(res.getResponseListPojo().size()>0) {
                    responselist = res.getResponseListPojo();
                    arrayList1.add("ALL");
                    arrayList1.add("Pending");
                    for (int i=0;i<responselist.size();i++){
                        arrayList1.add(responselist.get(i).getQCResponse());
                        DBConnIP.arrayListToDialog.add(responselist.get(i).getQCResponse());
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(OtherQCSocietyList.this, android.R.layout.simple_spinner_item, arrayList1);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinmsgres.setAdapter(adapter1);
                }else{
                    Toast.makeText(OtherQCSocietyList.this, "No Site Exists !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SiteAndQCResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(OtherQCSocietyList.this, "Failed to fetch data !", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        MenuItem report = menu.findItem(R.id.action_report);
        MenuItem report2 = menu.findItem(R.id.action_report2);
        MenuItem logout = menu.findItem(R.id.action_logout);

        String designation = SharedPrefManager.getInstance(OtherQCSocietyList.this).designation();


        if(designation.equalsIgnoreCase("Software Developer") || designation.equalsIgnoreCase("Sr Manager") || designation.equalsIgnoreCase("Admin and Other") ||
                designation.equalsIgnoreCase("CEO/Director") || designation.equalsIgnoreCase("Manager") ||  designation.equalsIgnoreCase("HR")){
            report2.setVisible(true);
        }else{
            report2.setVisible(false);
        }

        report.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(OtherQCSocietyList.this,QC_Calling_Report.class));
                return false;
            }
        });

        report2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(OtherQCSocietyList.this,QCResponseWiseReportActivity.class));
                return false;
            }
        });

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharedPrefManager.getInstance(OtherQCSocietyList.this).clear();
                Intent intent = new Intent(OtherQCSocietyList.this, LoginAcivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return false;
            }
        });

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setQueryHint("Search Society");
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

    private void submitMethod(final Context context) {
        if(arrayList.size() > 0 && responselist.size() > 0){
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

        newlist.clear();
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();
        Call<SocRoomWiseResponse> call1 = RetrofitClient
                .getInstance().getApi().getSocietyListOfOtherQC(newEdtFrom, newEdtTo, sharedelectionname, sitename, msgres);
        call1.enqueue(new Callback<SocRoomWiseResponse>() {
            @Override
            public void onResponse(Call<SocRoomWiseResponse> call1, Response<SocRoomWiseResponse> response) {
                SocRoomWiseResponse res = response.body();
                progressBar.dismiss();
                if(res.getSocietyRoomWiseQCListPojo().size()>0) {
                    newlist = res.getSocietyRoomWiseQCListPojo();
                    soccount.setText("Total Society - "+newlist.size());
                    int cnt = 0;
                    for(int k=0; k<newlist.size();k++){
                        if(newlist.get(k).getRoomcnt()>0){
                            cnt += newlist.get(k).getRoomcnt();
                        }
                    }
                    roomcount.setText("Total Rooms - "+cnt);
                    mAdapter = new Adapter_Soc_Room_Wise_List(context, newlist, "MQC", sitename,newEdtFrom,newEdtTo,msgres);
                    rcyclr_list.setNestedScrollingEnabled(false);
                    rcyclr_list.setLayoutManager(new LinearLayoutManager(context));
                    rcyclr_list.setAdapter(mAdapter);
                    rcyclr_list.getAdapter().notifyDataSetChanged();
                }else{
                    soccount.setText("Total Society - 0");
                    roomcount.setText("Total Rooms - 0");
                    Toast.makeText(context, "No Records Exists For Selected Criteria!", Toast.LENGTH_LONG).show();
                    mAdapter = new Adapter_Soc_Room_Wise_List(context, newlist, "MQC", sitename,newEdtFrom,newEdtTo,msgres);
                    rcyclr_list.setNestedScrollingEnabled(false);
                    rcyclr_list.setLayoutManager(new LinearLayoutManager(context));
                    rcyclr_list.setAdapter(mAdapter);
                    rcyclr_list.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SocRoomWiseResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(context, "Failed to fetch data !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
