package com.ornettech.qcandbirthdaywishes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.adapter.AdapterAudioList;
import com.ornettech.qcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.qcandbirthdaywishes.model.ExecutiveListPojoItem;
import com.ornettech.qcandbirthdaywishes.model.QCResposeWiseReportItem;
import com.ornettech.qcandbirthdaywishes.model.QCResposeWiseReportResponse;
import com.ornettech.qcandbirthdaywishes.model.SpinnerResponse;
import com.ornettech.qcandbirthdaywishes.utility.CheckConnection;
import com.ornettech.qcandbirthdaywishes.utility.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AudioFilesActivity extends AppCompatActivity {

    EditText repdate;
    public List<ExecutiveListPojoItem> executivelist = new ArrayList<>();
    private DatePickerDialog reportDatePickerDialog;
    Button submit;
    public List<String> arrayList1 = new ArrayList<>();
    public List<String> arrayListRep = new ArrayList<>();
    Spinner spnexecutive,spinreptype;
    public String reptype="";
    public List<QCResposeWiseReportItem> newlist = new ArrayList<>();
    public AdapterAudioList mAdapter;
    public RecyclerView audiolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_files);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ SharedPrefManager.getInstance(AudioFilesActivity.this).getElectionName()+" Audio Recordings</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        submit =  findViewById(R.id.submit);
        spinreptype =  findViewById(R.id.spinreptype);
        spnexecutive =  findViewById(R.id.executivename);
        repdate =  findViewById(R.id.callingdate);
        audiolist =  findViewById(R.id.audiolist);

        String cdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        repdate.setText(cdate);
        repdate.setFocusable(false);

        repdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                reportDatePickerDialog = new DatePickerDialog(AudioFilesActivity.this,
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

        arrayListRep.add("QC Calling");
        arrayListRep.add("Birthday Calling");
        arrayListRep.add("Round 1 Calling");
        arrayListRep.add("Round 2 Calling");
        arrayListRep.add("Round 3 Calling");
        arrayListRep.add("Client Calling");
        arrayListRep.add("Karyakarta Calling");
        arrayListRep.add("Hitachintak Calling");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AudioFilesActivity.this, android.R.layout.simple_spinner_item, arrayListRep);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinreptype.setAdapter(adapter1);
        getSpinnersData();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (new CheckConnection(AudioFilesActivity.this).isNetworkConnected()) {
                    if(submitMethod()){
                        //spnelection.getSelectedItem().toString()
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

                        callAPI(SharedPrefManager.getInstance(AudioFilesActivity.this).username(),
                                SharedPrefManager.getInstance(AudioFilesActivity.this).getElectionName(),
                                spnexecutive.getSelectedItem().toString(),
                                repdate.getText().toString(),selreptype);
                    }else{
                        Toast.makeText(AudioFilesActivity.this, "Loading Lists please wait....", Toast.LENGTH_SHORT).show();
                        getSpinnersData();
                    }
                }else {
                    Toast.makeText(AudioFilesActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean submitMethod() {
        if(executivelist.size() == 0){
            return false;
        }else{
            return true;
        }
    }

    private void callAPI(final String username, final String electionname, final String executivename, final String callingdate,
                         final String selreptype) {
        newlist.clear();
        final ProgressDialog progressBar = new ProgressDialog(AudioFilesActivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();
        Call<QCResposeWiseReportResponse> call1 = RetrofitClient
                .getInstance().getApi().QCResponseWiseAudio(callingdate, electionname, username, selreptype, SharedPrefManager.getInstance(AudioFilesActivity.this).designation(), executivename);
        call1.enqueue(new Callback<QCResposeWiseReportResponse>() {
            @Override
            public void onResponse(Call<QCResposeWiseReportResponse> call1, Response<QCResposeWiseReportResponse> response) {
                QCResposeWiseReportResponse res = response.body();
                progressBar.dismiss();

                if(res.getQCResposeWiseReport() != null && res.getQCResposeWiseReport().size() > 0){
                    newlist = res.getQCResposeWiseReport();
                    mAdapter = new AdapterAudioList(AudioFilesActivity.this, newlist);
                    audiolist.setNestedScrollingEnabled(false);
                    audiolist.setLayoutManager(new LinearLayoutManager(AudioFilesActivity.this));
                    audiolist.setAdapter(mAdapter);
                    audiolist.getAdapter().notifyDataSetChanged();
                }else{
                    Toast.makeText(AudioFilesActivity.this, "No Records Exists For Selected Criteria!", Toast.LENGTH_LONG).show();
                    mAdapter = new AdapterAudioList(AudioFilesActivity.this, newlist);
                    audiolist.setNestedScrollingEnabled(false);
                    audiolist.setLayoutManager(new LinearLayoutManager(AudioFilesActivity.this));
                    audiolist.setAdapter(mAdapter);
                    audiolist.getAdapter().notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<QCResposeWiseReportResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(AudioFilesActivity.this, "Failed to fetch data !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSpinnersData() {
        arrayList1.clear();
        executivelist.clear();
        final ProgressDialog progressBar = new ProgressDialog(AudioFilesActivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait ......");
        progressBar.show();

        Call<SpinnerResponse> call1 = RetrofitClient
                .getInstance().getApi().spinnerOptionForRep(SharedPrefManager.getInstance(AudioFilesActivity.this).username(), SharedPrefManager.getInstance(AudioFilesActivity.this).getElectionName());
        call1.enqueue(new Callback<SpinnerResponse>() {
            @Override
            public void onResponse(Call<SpinnerResponse> call1, Response<SpinnerResponse> response) {
                SpinnerResponse res = response.body();
                progressBar.dismiss();

                if(res.getExecutiveListPojo().size()>0) {
                    executivelist = res.getExecutiveListPojo();
                    arrayList1.add("ALL");
                    for (int i=0;i<executivelist.size();i++){
                        arrayList1.add(executivelist.get(i).getExecutiveName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AudioFilesActivity.this, android.R.layout.simple_spinner_item, arrayList1);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnexecutive.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<SpinnerResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(AudioFilesActivity.this, "Failed to fetch data !", Toast.LENGTH_LONG).show();
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
        //search.setVisible(false);
        String designation = SharedPrefManager.getInstance(AudioFilesActivity.this).designation();


        if(designation.equalsIgnoreCase("Software Developer") || designation.equalsIgnoreCase("Sr Manager") || designation.equalsIgnoreCase("Admin and Other") ||
                designation.equalsIgnoreCase("CEO/Director") || designation.equalsIgnoreCase("Manager") ||  designation.equalsIgnoreCase("HR")){
            report2.setVisible(true);
        }else{
            report2.setVisible(false);
        }

        report.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(AudioFilesActivity.this,QC_Calling_Report.class));
                return false;
            }
        });

        report2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(AudioFilesActivity.this,QCResponseWiseReportActivity.class));
                return false;
            }
        });

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharedPrefManager.getInstance(AudioFilesActivity.this).clear();
                Intent intent = new Intent(AudioFilesActivity.this, LoginAcivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return false;
            }
        });

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setQueryHint("Search Here");
        search(searchView);

        return true;
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


}
