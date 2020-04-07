package com.ornettech.nmmcqcandbirthdaywishes.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ornettech.nmmcqcandbirthdaywishes.R;
import com.ornettech.nmmcqcandbirthdaywishes.adapter.AdapterWardList;
import com.ornettech.nmmcqcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.nmmcqcandbirthdaywishes.listener.WhatsappItemClickListner;
import com.ornettech.nmmcqcandbirthdaywishes.model.BirthdayInWardResponse;
import com.ornettech.nmmcqcandbirthdaywishes.model.BirthdaysInSelectedWardItem;
import com.ornettech.nmmcqcandbirthdaywishes.model.BirthdaysInWardItem;
import com.ornettech.nmmcqcandbirthdaywishes.model.CorporateWhatsappResponse;
import com.ornettech.nmmcqcandbirthdaywishes.model.CorporatorDetailsItem;
import com.ornettech.nmmcqcandbirthdaywishes.model.ElectionListItem;
import com.ornettech.nmmcqcandbirthdaywishes.model.PrabhagResponse;
import com.ornettech.nmmcqcandbirthdaywishes.model.PrabhagWiseBirthdaysItem;
import com.ornettech.nmmcqcandbirthdaywishes.model.SpinnerResponse;
import com.ornettech.nmmcqcandbirthdaywishes.utility.CheckConnection;
import com.ornettech.nmmcqcandbirthdaywishes.utility.SendSMSWhatsApp;
import com.ornettech.nmmcqcandbirthdaywishes.utility.SharedPrefManager;
import com.ornettech.nmmcqcandbirthdaywishes.utility.Transalator;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QCBirthDayActivity extends AppCompatActivity implements WhatsappItemClickListner {

    private DatePickerDialog birthDatePickerDialog;
    public Spinner electionname,status;
    EditText birthdate;
    Button submit;
    ImageButton wardsms,genpdf;

    public List<PrabhagWiseBirthdaysItem> newlist = new ArrayList<>();
    public List<CorporatorDetailsItem> corporator = new ArrayList<>();
    public List<BirthdaysInSelectedWardItem> birthdays = new ArrayList<>();
    //public List<BirthdaysInWardItem> birthdaysInWard = new ArrayList<>();
    public String note;
    public StringBuffer sb = new StringBuffer();
    public TextView wardcount,birthdayscount,error;
    public AdapterWardList mAdapter;
    public List<ElectionListItem> electionlist = new ArrayList<>();
    public List<String> arrayList = new ArrayList<>();
    public List<String> arrayList2 = new ArrayList<>();
    public List<String> smsbreak = new ArrayList<>();
    private SimpleDateFormat dateFormatter;
    public RecyclerView rcyclr_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_c_birth_day);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Birthday QC</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);


        rcyclr_list =  findViewById(R.id.wardlist);
        electionname =  findViewById(R.id.electionname);
        wardsms =  findViewById(R.id.wardsms);
        genpdf =  findViewById(R.id.genpdf);
        error =  findViewById(R.id.error);
        status =  findViewById(R.id.spnstatus);
        birthdate =  findViewById(R.id.dob);
        submit =  findViewById(R.id.submit);
        wardcount =  findViewById(R.id.wardcount);
        birthdayscount =  findViewById(R.id.birthdayscount);
        error.setVisibility(View.GONE);
        rcyclr_list.setVisibility(View.GONE);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        String cdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        birthdate.setText(cdate);
        birthdate.setFocusable(false);
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                birthDatePickerDialog = new DatePickerDialog(QCBirthDayActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                birthdate.setText(String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                birthDatePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new CheckConnection(QCBirthDayActivity.this).isNetworkConnected()) {
                    submitMethod("submit");
                }else {
                    Toast.makeText(QCBirthDayActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        wardsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newlist.size()>0 && sb.length()>0) {
                    ShowSMSPreviewOfWardSMS(sb);
                }else {
                    Toast.makeText(QCBirthDayActivity.this,
                            "List is empty !",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        genpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new CheckConnection(QCBirthDayActivity.this).isNetworkConnected()) {
                    submitMethod("pdf");
                }else {
                    Toast.makeText(QCBirthDayActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        getSpinnersData();

    }

    private void submitMethod(String type) {
        if(electionlist.size() > 0 && arrayList2.size() > 0){
            String edtfrom = birthdate.getText().toString();

            SimpleDateFormat dayofDate = new SimpleDateFormat("dd", Locale.US);
            SimpleDateFormat monthofDate = new SimpleDateFormat("MM", Locale.US);
            SimpleDateFormat yearofDate = new SimpleDateFormat("yyyy", Locale.US);
            Date startDate;
            Date endDate;

            try {
                startDate = dateFormatter.parse(edtfrom);
                String day = dayofDate.format(startDate);
                String month = monthofDate.format(startDate);
                String year = yearofDate.format(startDate);
                if(type.equalsIgnoreCase("submit")) {
                    callApi(day, month, year, SharedPrefManager.getInstance(QCBirthDayActivity.this).username(), electionname.getSelectedItem().toString(), status.getSelectedItem().toString());
                }else if(type.equalsIgnoreCase("pdf")){
                    fetchDataFromApi(electionname.getSelectedItem().toString(),day, month, year);
                }
            }catch (Exception e){
                e.toString();
            }
        }else{
            getSpinnersData();
            Toast.makeText(QCBirthDayActivity.this, "Loading site name list please wait !", Toast.LENGTH_SHORT).show();
        }
    }

    private void callApi(final String day, final String month, final String year, final String username, final String elecname, final String status) {
        newlist.clear();
        final ProgressDialog progressBar = new ProgressDialog(QCBirthDayActivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();
        Call<PrabhagResponse> call1 = RetrofitClient
                .getInstance().getApi().getBirthDaysWard(status,elecname,username,"ALL",day,month,year);
        call1.enqueue(new Callback<PrabhagResponse>() {
            @Override
            public void onResponse(Call<PrabhagResponse> call1, Response<PrabhagResponse> response) {
                PrabhagResponse res = response.body();
                progressBar.dismiss();
                if(res.getPrabhagWiseBirthdays().size()>0) {
                    newlist = res.getPrabhagWiseBirthdays();
                    wardcount.setText("Total Wards - "+newlist.size());
                    int cnt = 0, sbcnt=0;
                    sb.setLength(0);
                    sb.append(elecname+" मधील दिनांक "+Transalator.englishDigitToMarathiDigit(day)+"/"+Transalator.englishDigitToMarathiDigit(month)+"/"+Transalator.englishDigitToMarathiDigit(year)+" चे प्रभागानुसार वाढदिवस - \n");
                    for(int k=0; k<newlist.size();k++){
                        if(newlist.get(k).getTotalBirthDays()>0){
                            cnt += newlist.get(k).getTotalBirthDays();

                            if(newlist.get(k).getWardNo() != 0) {
                                sbcnt += newlist.get(k).getTotalBirthDays();
                                sb.append("प्रभाग क्र. " + Transalator.englishDigitToMarathiDigit(Integer.toString(newlist.get(k).getWardNo())) + " - " + Transalator.englishDigitToMarathiDigit(Integer.toString(newlist.get(k).getTotalBirthDays()))+",   ");
                                if(k%2==0){
                                    sb.append("\n");
                                }
                            }
                        }
                    }
                    birthdayscount.setText("Total Birthdays - "+cnt);
                    sb.append("_____________________\n");
                    sb.append("एकूण वाढदिवस - "+Transalator.englishDigitToMarathiDigit(Integer.toString(sbcnt))+"\n\n");
                    sb.append("Chankya Election Management\n");
                    sb.append("मोबाईल क्र. ९२२३५७५१८९/९०/९१/९२/९३\n");
                    error.setVisibility(View.GONE);
                    rcyclr_list.setVisibility(View.VISIBLE);
                    mAdapter = new AdapterWardList(QCBirthDayActivity.this, newlist,status,elecname,day,month,year);
                    rcyclr_list.setNestedScrollingEnabled(false);
                    rcyclr_list.setLayoutManager(new LinearLayoutManager(QCBirthDayActivity.this));
                    rcyclr_list.setAdapter(mAdapter);
                    mAdapter.onItemClick(QCBirthDayActivity.this);
                    rcyclr_list.getAdapter().notifyDataSetChanged();
                }else{
                    wardcount.setText("Total Wards - 0");
                    birthdayscount.setText("Total Birthdays - 0");
                    error.setVisibility(View.VISIBLE);
                    rcyclr_list.setVisibility(View.GONE);
                    Toast.makeText(QCBirthDayActivity.this, "No Records Exists For Selected Criteria!", Toast.LENGTH_LONG).show();
                    mAdapter = new AdapterWardList(QCBirthDayActivity.this, newlist,status,elecname,day,month,year);
                    rcyclr_list.setNestedScrollingEnabled(false);
                    rcyclr_list.setLayoutManager(new LinearLayoutManager(QCBirthDayActivity.this));
                    rcyclr_list.setAdapter(mAdapter);
                    mAdapter.onItemClick(QCBirthDayActivity.this);
                    rcyclr_list.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PrabhagResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(QCBirthDayActivity.this, "Failed to fetch birthdays ward wise !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSpinnersData() {
        arrayList.clear();
        arrayList2.clear();
        electionlist.clear();
        final ProgressDialog progressBar = new ProgressDialog(QCBirthDayActivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait ......");
        progressBar.show();

        Call<SpinnerResponse> call1 = RetrofitClient
                .getInstance().getApi().spinnerOptionForRep(SharedPrefManager.getInstance(QCBirthDayActivity.this).username(),SharedPrefManager.getInstance(QCBirthDayActivity.this).getElectionName());
        call1.enqueue(new Callback<SpinnerResponse>() {
            @Override
            public void onResponse(Call<SpinnerResponse> call1, Response<SpinnerResponse> response) {
                SpinnerResponse res = response.body();
                progressBar.dismiss();
                if(res.getElectionList().size()>0) {
                    electionlist = res.getElectionList();
                    for (int i=0;i<electionlist.size();i++){
                        arrayList.add(electionlist.get(i).getElectionName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(QCBirthDayActivity.this, android.R.layout.simple_spinner_item, arrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    electionname.setAdapter(adapter);
                }

                arrayList2.add("ALL");
                arrayList2.add("Pending");
                arrayList2.add("Wished");
                arrayList2.add("Expired");
                arrayList2.add("Birth Date Update");
                arrayList2.add("Ringing");
                arrayList2.add("Switch Off");
                arrayList2.add("Not Available");
                //arrayList2.add("Received / Verified");
                arrayList2.add("Out of Coverage Area");
                arrayList2.add("Received / Wrong No");
                arrayList2.add("Busy");
                arrayList2.add("Invalid Number");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(QCBirthDayActivity.this, android.R.layout.simple_spinner_item, arrayList2);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                status.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<SpinnerResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(QCBirthDayActivity.this, "Failed to fetch data !", Toast.LENGTH_LONG).show();
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

        String designation = SharedPrefManager.getInstance(QCBirthDayActivity.this).designation();


        if(designation.equalsIgnoreCase("Software Developer") || designation.equalsIgnoreCase("Sr Manager") || designation.equalsIgnoreCase("Admin and Other") ||
                designation.equalsIgnoreCase("CEO/Director") || designation.equalsIgnoreCase("Manager") ||  designation.equalsIgnoreCase("HR")){
            report2.setVisible(true);
        }else{
            report2.setVisible(false);
        }

        report.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(QCBirthDayActivity.this,QC_Calling_Report.class));
                return false;
            }
        });

        report2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(QCBirthDayActivity.this,QCResponseWiseReportActivity.class));
                return false;
            }
        });

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharedPrefManager.getInstance(QCBirthDayActivity.this).clear();
                Intent intent = new Intent(QCBirthDayActivity.this, LoginAcivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return false;
            }
        });

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setQueryHint("Search Ward No.");
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
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void ShowSMSPreviewOfSMS(final String corporatornumber, final String header, final String footer){
        final Dialog dialog = new Dialog(QCBirthDayActivity.this);
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

        /*sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendSMSWhatsApp.sendWhatsApp(QCBirthDayActivity.this, corporatornumber, sb.toString());
            }
        });*/
        whatsappsmslist.setNestedScrollingEnabled(false);
        whatsappsmslist.setLayoutManager(new LinearLayoutManager(QCBirthDayActivity.this));
        whatsappsmslist.setAdapter(new RecyclerView.Adapter() {
                                    @NonNull
                                    @Override
                                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                        View view = LayoutInflater.from(QCBirthDayActivity.this).inflate(R.layout.adapter_whatsapp_long_sms_split, viewGroup, false);
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
                                                SendSMSWhatsApp.sendWhatsApp(QCBirthDayActivity.this, corporatornumber, msg);
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

    @Override
    public void onItemClick(int position, int ward_no, int ac_no, String electionname, String day, String month, String year, String response) {
        getCorporatorDetails(response,ward_no,day,month,year,electionname,ac_no);
    }

    private void getCorporatorDetails(String response, final int ward_no, final String day, final String month, final String year, String electionname, int ac_no) {
        corporator.clear();
        birthdays.clear();
        smsbreak.clear();
        final ProgressDialog progressBar = new ProgressDialog(QCBirthDayActivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();
        Call<CorporateWhatsappResponse> call1 = RetrofitClient
                .getInstance().getApi().birthdaysOfWardForCorporator(response,electionname,SharedPrefManager.getInstance(QCBirthDayActivity.this).username(),ac_no,ward_no,day,month,year);
        call1.enqueue(new Callback<CorporateWhatsappResponse>() {
            @Override
            public void onResponse(Call<CorporateWhatsappResponse> call1, Response<CorporateWhatsappResponse> response) {
                CorporateWhatsappResponse res = response.body();
                corporator = res.getCorporatorDetails();
                note = res.getNote();
                birthdays = res.getBirthdaysInSelectedWard();
                StringBuffer sbheader = new StringBuffer();
                StringBuffer sbfooter = new StringBuffer();
                StringBuffer sb = new StringBuffer();
                String corporatormob="",header="",footer="";
                if(corporator.size()>0) {
                    corporatormob=corporator.get(0).getMobileNo1();
                    sbheader.append("*" + corporator.get(0).getDesignationNameMar() + " - " + corporator.get(0).getCorporatorNameMar() + "* \n");
                }
                sbheader.append("*प्रभाग क्रमांक - "+Transalator.englishDigitToMarathiDigit(Integer.toString(ward_no))+"* \n");
                sbheader.append("*दिनांक - "+ Transalator.englishDigitToMarathiDigit(day)+"/"+Transalator.englishDigitToMarathiDigit(month)+"/"+Transalator.englishDigitToMarathiDigit(year) +"* \n");
                sbheader.append("*प्रभागातील रहिवाश्यांची नावे :-* \n");
                header = sbheader.toString();
                sbheader.setLength(0);
                for(int k=0;k<birthdays.size();k++){
                    int i = k+1;
                    sb.append(i+". "+birthdays.get(k).getFullName()+" - "+birthdays.get(k).getMobileNo()+"\n");
                    if(i%50==0){
                        smsbreak.add(sb.toString());
                        sb.setLength(0);
                    }
                }
                if(sb.toString().length()>0){
                    smsbreak.add(sb.toString());
                    sb.setLength(0);
                }
                sbfooter.append("_____________________________ \n\n");
                sbfooter.append(note);
                footer = sbfooter.toString();
                sbfooter.setLength(0);
                progressBar.dismiss();
                ShowSMSPreviewOfSMS(corporatormob,header,footer);
            }

            @Override
            public void onFailure(Call<CorporateWhatsappResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(QCBirthDayActivity.this, "Failed to fetch corporator date !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ShowSMSPreviewOfWardSMS(final StringBuffer sb){
        final Dialog dialog = new Dialog(QCBirthDayActivity.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.whatsapp_preview);
        ImageButton closepopup = dialog.findViewById(R.id.closepopup);
        Button sendbtn = dialog.findViewById(R.id.sendbtn);
        final TextView whatsappsms = dialog.findViewById(R.id.whatsappsms);
        whatsappsms.setMovementMethod(new ScrollingMovementMethod());
        whatsappsms.setText(sb.toString());

        closepopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendSMSWhatsApp.sendWhatsApp(QCBirthDayActivity.this, "", sb.toString());
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }



    public void pdfGenerate(List<BirthdaysInWardItem> mainModel,ProgressDialog progressBar1, String day, String month,String ele){

        Random rand = new Random();
        File sd = Environment.getExternalStorageDirectory();
        String name = "BIRTHDAYS OF "+day+" "+Transalator.convertIntMonthIntoName(month)+" ("+ele+") ";
        String pdfFile = name+" "+rand.nextInt(100000)+".pdf";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        Document document = new Document();
        try
        {
            File file = new File(directory, pdfFile);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file)); //new FileOutputStream("AddTableExample.pdf")

            document.open();
            Paragraph paragraph = new Paragraph(name, FontFactory.getFont(FontFactory.TIMES_ROMAN,18, Font.BOLD, BaseColor.BLACK));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            //Set attributes here
            document.addAuthor("Chankya Election Management");
            document.addCreationDate();
            document.addCreator("chankya.in");
            document.addTitle("Birthdays");

            PdfPTable table = new PdfPTable(5); // 5 columns.
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table
            table.setHeaderRows(1);

            //Set Column widths
            float[] columnWidths = {1f, 5f, 2f, 2f, 1f};
            table.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("SR NO"));
            cell1.setBorderColor(BaseColor.BLACK);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell2 = new PdfPCell(new Paragraph("NAME"));
            cell2.setBorderColor(BaseColor.BLACK);
            cell2.setPaddingLeft(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell3 = new PdfPCell(new Paragraph("MOBILE NO"));
            cell3.setBorderColor(BaseColor.BLACK);
            cell3.setPaddingLeft(10);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell4 = new PdfPCell(new Paragraph("DOB"));
            cell2.setBorderColor(BaseColor.BLACK);
            cell2.setPaddingLeft(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell5 = new PdfPCell(new Paragraph("WARD NO"));
            cell3.setBorderColor(BaseColor.BLACK);
            cell3.setPaddingLeft(10);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);


            //To avoid having the cell border and the content overlap, if you are having thick cell borders
            //cell1.setUserBorderPadding(true);
            //cell2.setUserBorderPadding(true);
            //cell3.setUserBorderPadding(true);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);

            int cnt=1;
            for(int k=0;k<mainModel.size();k++) {
                if(mainModel.get(k).getWardNo() != 0){
                    table.addCell(""+cnt);
                    cnt++;
                    table.addCell(mainModel.get(k).getFullName().toUpperCase());
                    table.addCell(mainModel.get(k).getMobileNo());
                    table.addCell(mainModel.get(k).getBirthDate());
                    table.addCell(Integer.toString(mainModel.get(k).getWardNo()));
                }
            }
            document.add(table);

            /*String note = "\n\nनोंद:-\n" +
                    "वरील दिलेल्या जन्मदिनांकांमध्ये काही प्रमाणात जन्मदिनांक \n" +
                    "चुकीच्या असू शकतात ते तुम्ही verify करून घ्यावे हि विनंती.\n" +
                    "धन्यवाद!\n" +
                    "Chankya Election Management\n" +
                    "मोबाईल क्र. ९२२३५७५१८९/९०/९१/९२/९३";*/

            /*String note = "\n\nNote :- \n" +
                    "Some of the above birthdays may have incorrect birth dates.\n" +
                    "We request that you verify it.\n" +
                    "Thank & Regards !\n" +
                    "Chankya Election Management\n" +
                    "Mobile No. 9223575189/90/91/92/93";*/

            String note = "\n\nThank & Regards,\n" +
                    "Chankya Election Management,\n" +
                    "Mobile No. 9223575189/90/91/92/93";

            document.add(new Paragraph(new String(note.getBytes("UTF-8"))));

            //Add Image
            //Image image1 = Image.getInstance("temp.jpg");
            //Fixed Positioning
            //image1.setAbsolutePosition(100f, 550f);
            //Scale to new height and new width of image
            //image1.scaleAbsolute(200, 200);
            //Add to document
            //document.add(image1);

            /*
            String imageUrl = RetrofitClient.BASE_URL+"/marnote.png";
            Image image2 = Image.getInstance(new URL(imageUrl));
            document.add(image2);*/


            /*document.add(new Paragraph("\n\nनोंद:-\n" +
                    "वरील दिलेल्या जन्मदिनांकांमध्ये काही प्रमाणात जन्मदिनांक \n" +
                    "चुकीच्या असू शकतात ते तुम्ही verify करून घ्यावे हि विनंती.\n" +
                    "धन्यवाद!\n" +
                    "Chankya Election Management\n" +
                    "मोबाईल क्र. ९२२३५७५१८९/९०/९१/९२/९३"));*/

            document.close();
            writer.close();
            progressBar1.dismiss();
            Uri uri = Uri.fromFile(file);

            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setDataAndType(uri, "application/pdf");
            share.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            share.putExtra(Intent.EXTRA_STREAM, uri);
            share.setPackage("com.whatsapp");

            try {
                startActivity(share);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(QCBirthDayActivity.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void fetchDataFromApi(final String elec,final String day, final String month,final String year) {
        //birthdaysInWard.clear();
        final ProgressDialog progressBar = new ProgressDialog(QCBirthDayActivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait ......");
        progressBar.show();
        Call<BirthdayInWardResponse> call1 = RetrofitClient
                .getInstance().getApi().getAllBirthdateDateWiseForPDF(elec,day,month,year);
        call1.enqueue(new Callback<BirthdayInWardResponse>() {
            @Override
            public void onResponse(Call<BirthdayInWardResponse> call1, Response<BirthdayInWardResponse> response) {
                BirthdayInWardResponse res = response.body();
                if(res.getBirthdaysInWard().size()>0) {
                    pdfGenerate(res.getBirthdaysInWard(),progressBar,day,month,elec);
                }else{
                    progressBar.dismiss();
                    Toast.makeText(QCBirthDayActivity.this, "No Records Exists For Selected Criteria!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BirthdayInWardResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(QCBirthDayActivity.this, "Failed to fetch data !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
