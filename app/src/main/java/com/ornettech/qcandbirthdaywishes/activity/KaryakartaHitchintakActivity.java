package com.ornettech.qcandbirthdaywishes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.adapter.AdapterClientList;
import com.ornettech.qcandbirthdaywishes.adapter.DefaultResponse;
import com.ornettech.qcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.qcandbirthdaywishes.call.CallRecord;
import com.ornettech.qcandbirthdaywishes.model.ClientListItem;
import com.ornettech.qcandbirthdaywishes.model.DesignationMasterItem;
import com.ornettech.qcandbirthdaywishes.model.KKHIResponse;
import com.ornettech.qcandbirthdaywishes.model.KaryakartaHitachintakListItem;
import com.ornettech.qcandbirthdaywishes.model.ResponseListPojoItem;
import com.ornettech.qcandbirthdaywishes.model.SiteAndQCResponse;
import com.ornettech.qcandbirthdaywishes.utility.AndroidMultiPartEntity;
import com.ornettech.qcandbirthdaywishes.utility.CheckConnection;
import com.ornettech.qcandbirthdaywishes.utility.DBConnIP;
import com.ornettech.qcandbirthdaywishes.utility.SendSMSWhatsApp;
import com.ornettech.qcandbirthdaywishes.utility.SharedPrefManager;
import com.ornettech.qcandbirthdaywishes.utility.Transalator;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaryakartaHitchintakActivity extends AppCompatActivity {

    EditText from;
    Button submit;
    public List<String> arrayList = new ArrayList<>();
    public List<String> arrayList1 = new ArrayList<>();
    public List<String> arrayList2 = new ArrayList<>();
    Spinner spinmsgres, spnward, spntype;
    private AdapterClientList adapterClientList;
    public List<KaryakartaHitachintakListItem> newlist = new ArrayList<>();
    public List<DesignationMasterItem> desiglist = new ArrayList<>();
    public List<String> searchablespinner_list = new ArrayList<String>();
    public TextView soccount, roomcount;
    public String sharedelectionname, filename = "", bd = "", ad = "";
    public RecyclerView rcyclr_list;
    CallRecord callRecordSurvey = null;
    long totalSize = 0;
    private DatePickerDialog fromDatePickerDialog;
    SendSMSWhatsApp sendSMSWhatsApp = new SendSMSWhatsApp();
    private SimpleDateFormat dateFormatter, parseDate, parseDate2, parseDateC, dateFormatterC;
    private ProgressDialog progressBar = null;
    String deleteFileName = "";
    public List<ResponseListPojoItem> responselist = new ArrayList<>();
    String searchdate, message, ward, type, username, kkhicd;
    private Uri fileUri;
    int wardno = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karyakarta_hitchintak);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + SharedPrefManager.getInstance(KaryakartaHitchintakActivity.this).getElectionName() + " Karyakarta / Hitachintak</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        parseDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        parseDate2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        from = findViewById(R.id.fromdate);
        rcyclr_list = findViewById(R.id.corporatorslist);
        submit = findViewById(R.id.submit);
        spinmsgres = findViewById(R.id.spnsmsstatus);
        spnward = findViewById(R.id.spnward);
        spntype = findViewById(R.id.spntype);
        dateFormatterC = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        sharedelectionname = SharedPrefManager.getInstance(KaryakartaHitchintakActivity.this).getElectionName();

        String cdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        from.setText(cdate);
        from.setFocusable(false);
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                fromDatePickerDialog = new DatePickerDialog(KaryakartaHitchintakActivity.this,
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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new CheckConnection(KaryakartaHitchintakActivity.this).isNetworkConnected()) {
                    submitMethod(KaryakartaHitchintakActivity.this);
                } else {
                    Toast.makeText(KaryakartaHitchintakActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        getSiteAndResponse();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(DBConnIP.runmethod){
            DBConnIP.runmethod = false;
            callApi(searchdate, KaryakartaHitchintakActivity.this, message, type, ward, username);
        }
    }

    private void getSiteAndResponse() {
        arrayList.clear();
        DBConnIP.arrayListToDialog.clear();
        responselist.clear();
        final ProgressDialog progressBar = new ProgressDialog(KaryakartaHitchintakActivity.this);
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

                if (res.getResponseListPojo().size() > 0) {
                    responselist = res.getResponseListPojo();
                    arrayList.add("ALL");
                    arrayList.add("Pending");
                    for (int i = 0; i < responselist.size(); i++) {
                        arrayList.add(responselist.get(i).getQCResponse());
                        DBConnIP.arrayListToDialog.add(responselist.get(i).getQCResponse());
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(KaryakartaHitchintakActivity.this, android.R.layout.simple_spinner_item, arrayList);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinmsgres.setAdapter(adapter1);

                    arrayList1.add("ALL");
                    for (int i = 1; i <= 200; i++) {
                        arrayList1.add(i + "");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(KaryakartaHitchintakActivity.this, android.R.layout.simple_spinner_item, arrayList1);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnward.setAdapter(adapter);

                    arrayList2.add("Karyakarta");
                    arrayList2.add("Hitachintak");
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(KaryakartaHitchintakActivity.this, android.R.layout.simple_spinner_item, arrayList2);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spntype.setAdapter(adapter2);


                } else {
                    Toast.makeText(KaryakartaHitchintakActivity.this, "No Site Exists !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SiteAndQCResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(KaryakartaHitchintakActivity.this, "Failed to fetch data !", Toast.LENGTH_LONG).show();
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
        search.setVisible(false);
        String designation = SharedPrefManager.getInstance(KaryakartaHitchintakActivity.this).designation();


        if (designation.equalsIgnoreCase("Software Developer") || designation.equalsIgnoreCase("Sr Manager") || designation.equalsIgnoreCase("Admin and Other") ||
                designation.equalsIgnoreCase("CEO/Director") || designation.equalsIgnoreCase("Manager") || designation.equalsIgnoreCase("HR")) {
            report2.setVisible(true);
        } else {
            report2.setVisible(false);
        }

        report.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(KaryakartaHitchintakActivity.this, QC_Calling_Report.class));
                return false;
            }
        });

        report2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(KaryakartaHitchintakActivity.this, QCResponseWiseReportActivity.class));
                return false;
            }
        });

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharedPrefManager.getInstance(KaryakartaHitchintakActivity.this).clear();
                Intent intent = new Intent(KaryakartaHitchintakActivity.this, LoginAcivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return false;
            }
        });

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setQueryHint("Search Corporator");
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
                adapterClientList.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void submitMethod(final Context context) {
        if (responselist.size() > 0) {
            String edtfrom = from.getText().toString();


            parseDateC = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date startDate;
            Date endDate;

            try {
                startDate = dateFormatterC.parse(edtfrom);
                String newEdtFrom = parseDateC.format(startDate);
                searchdate = newEdtFrom;
                message = spinmsgres.getSelectedItem().toString().trim();
                ward = spnward.getSelectedItem().toString().trim();
                if (spntype.getSelectedItem().toString().trim().equalsIgnoreCase("Karyakarta")) {
                    type = "KK";
                } else if (spntype.getSelectedItem().toString().trim().equalsIgnoreCase("Hitachintak")) {
                    type = "HI";
                }
                username = SharedPrefManager.getInstance(KaryakartaHitchintakActivity.this).username();
                callApi(newEdtFrom, context, message, type, ward, username);

            } catch (Exception e) {
                e.toString();
            }
        } else {
            getSiteAndResponse();
            Toast.makeText(context, "Loading site name list please wait !", Toast.LENGTH_SHORT).show();
        }

    }

    private void callApi(final String newEdtFrom, final Context context, final String msgres, final String utype, final String wardno, final String username1) {

        newlist.clear();
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();
        Call<KKHIResponse> call1 = RetrofitClient
                .getInstance().getApi().getKaryakartaHitachintakListForQC(newEdtFrom, sharedelectionname, msgres, utype, wardno, username1);
        call1.enqueue(new Callback<KKHIResponse>() {
            @Override
            public void onResponse(Call<KKHIResponse> call1, Response<KKHIResponse> response) {
                KKHIResponse res = response.body();
                progressBar.dismiss();
                if (res.getKaryakartaHitachintakList().size() > 0) {
                    newlist = res.getKaryakartaHitachintakList();
                    desiglist = res.getDesignationMaster();
                    for (int i = 0; i < desiglist.size(); i++) {
                        searchablespinner_list.add(desiglist.get(i).getDesignationName());
                    }
                    setInnerAdapter(res.getKaryakartaHitachintakList());
                } else {
                    Toast.makeText(context, "No Records Exists For Selected Criteria!", Toast.LENGTH_LONG).show();
                    setInnerAdapter(res.getKaryakartaHitachintakList());
                }
            }

            @Override
            public void onFailure(Call<KKHIResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(context, "Failed to fetch data !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setInnerAdapter(final List<KaryakartaHitachintakListItem> karyakartaHitachintakListItems) {
        rcyclr_list.setNestedScrollingEnabled(false);
        rcyclr_list.setLayoutManager(new LinearLayoutManager(KaryakartaHitchintakActivity.this));
        rcyclr_list.setAdapter(new RecyclerView.Adapter() {
                                   @NonNull
                                   @Override
                                   public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                       View view = LayoutInflater.from(KaryakartaHitchintakActivity.this).inflate(R.layout.kk_hi_adapter, viewGroup, false);
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
                                       final KaryakartaHitachintakListItem model = karyakartaHitachintakListItems.get(i);
                                       String res = model.getQCResponse().equals("") ? "Pending" : model.getQCResponse();
                                       if (!res.equalsIgnoreCase("Pending")) {
                                           innerHolder.responsedet.setText(Html.fromHtml("<font color='#464545'>QC Status - " + res + "</font>"));
                                       } else {
                                           innerHolder.responsedet.setText("QC Status - " + res);
                                       }

                                       innerHolder.votername.setText(model.getFullNameMar() + " - ( वॉर्ड - " + Transalator.englishDigitToMarathiDigit(model.getWardNo() + "") + ")");
                                       innerHolder.genage.setText(Transalator.englishDigitToMarathiDigit(model.getMobileNo1()));
                                       innerHolder.socdet.setText("पद -" + model.getDesignationNameMar());

                                       if (model.getKKHIPhoto() != null && !model.getKKHIPhoto().equalsIgnoreCase("")) {
                                           Glide.with(KaryakartaHitchintakActivity.this).load(model.getKKHIPhoto()).into(innerHolder.photo);
                                       } else {
                                           innerHolder.photo.setImageDrawable(ContextCompat.getDrawable(KaryakartaHitchintakActivity.this, R.drawable.ic_no_image_found));
                                       }

                                       innerHolder.photo.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               final Dialog dialog = new Dialog(KaryakartaHitchintakActivity.this);
                                               dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                               dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                               dialog.setCancelable(true);
                                               dialog.setContentView(R.layout.dialog_custom_layout);
                                               ImageView photoView = (ImageView) dialog.findViewById(R.id.imageView);
                                               if (model.getKKHIPhoto() != null && !model.getKKHIPhoto().equalsIgnoreCase("")) {
                                                   Glide.with(KaryakartaHitchintakActivity.this).load(model.getKKHIPhoto()).into(photoView);
                                               } else {
                                                   photoView.setImageResource(R.drawable.ic_no_image_found);
                                               }

                                               WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                               lp.copyFrom(dialog.getWindow().getAttributes());
                                               lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                               lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                                               dialog.show();
                                               dialog.getWindow().setAttributes(lp);
                                           }
                                       });

                                       innerHolder.edit.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               EditDialog(karyakartaHitachintakListItems.get(i));
                                           }
                                       });

                                       innerHolder.piccapture.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               if (model.getKKHIPhoto() != null && model.getKKHIPhoto().equalsIgnoreCase("")) {
                                                   uploadPhoto(model.getKKHICd(), model.getWardNo());
                                               } else {
                                                   AlertDialog.Builder builder = new AlertDialog.Builder(KaryakartaHitchintakActivity.this);
                                                   builder.setCancelable(true);
                                                   builder.setTitle("New ?");
                                                   builder.setMessage("Are you sure you want to upload new image ?");
                                                   builder.setPositiveButton("Yes",
                                                           new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialog, int which) {
                                                                   uploadPhoto(model.getKKHICd(), model.getWardNo());
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

                                       innerHolder.callingbtn.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               filename = "ClientQCRecord_" + model.getKKHICd() + "_" + sharedelectionname + "_" + model.getMobileNo1();
                                               StartCallRecording(filename);
                                               innerHolder.llt2.setBackgroundColor(Color.parseColor("#FFDADA"));
                                               sendSMSWhatsApp.callMobNo(KaryakartaHitchintakActivity.this, model.getMobileNo1());
                                               Toast.makeText(KaryakartaHitchintakActivity.this, "calling", Toast.LENGTH_SHORT).show();
                                           }
                                       });

                                       if (res.equalsIgnoreCase("Pending")) {
                                           innerHolder.llt.setBackgroundColor(Color.parseColor("#FF5555"));
                                       } else {
                                           innerHolder.llt.setBackgroundColor(Color.parseColor("#00C00C"));
                                       }

                                       innerHolder.itemView.setTag(i);
                                       innerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               //StopCallRecording();
                                               UpdateStatusDialog(model.getQCResponse(), model.getKKHICd(), model.getSelecteddate(), model.getWardNo(), model.getKKHICd(), model.getMobileNo1());
                                           }
                                       });
                                   }

                                   public void EditDialog(KaryakartaHitachintakListItem karyakartaHitachintakListItemsXX) {
                                       final Dialog dialog = new Dialog(KaryakartaHitchintakActivity.this);
                                       dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                       dialog.setCancelable(false);
                                       dialog.setContentView(R.layout.kk_hi_edit_popup);
                                       Button close = dialog.findViewById(R.id.closebtn);
                                       Button update = dialog.findViewById(R.id.updatebtn);
                                       final EditText fname = dialog.findViewById(R.id.fname);
                                       final EditText mname = dialog.findViewById(R.id.mname);
                                       final EditText lname = dialog.findViewById(R.id.lname);
                                       final EditText fnamemar = dialog.findViewById(R.id.fnamemar);
                                       final EditText mnamemar = dialog.findViewById(R.id.mnamemar);
                                       final EditText lnamemar = dialog.findViewById(R.id.lnamemar);
                                       final EditText gender = dialog.findViewById(R.id.gender);
                                       final EditText area = dialog.findViewById(R.id.area);
                                       final EditText areamar = dialog.findViewById(R.id.areamar);
                                       final EditText birthdate = dialog.findViewById(R.id.birthdate);
                                       final EditText annidate = dialog.findViewById(R.id.annidate);
                                       final EditText mobilenumber1 = dialog.findViewById(R.id.mobilenumber1);
                                       final EditText mobilenumber2 = dialog.findViewById(R.id.mobilenumber2);
                                       final EditText remark1 = dialog.findViewById(R.id.remark1);
                                       final EditText remark2 = dialog.findViewById(R.id.remark2);
                                       final SearchableSpinner searchablespinner = dialog.findViewById(R.id.searchablespinner);
                                       KaryakartaHitachintakListItem kkhi = karyakartaHitachintakListItemsXX;

                                       ArrayAdapter<String> adapterx = new ArrayAdapter<String>(KaryakartaHitchintakActivity.this, android.R.layout.simple_spinner_dropdown_item, searchablespinner_list);
                                       searchablespinner.setAdapter(adapterx);
                                       if (kkhi.getDesignationName() != null && !kkhi.getDesignationName().equalsIgnoreCase("")) {
                                           int spinnerPosition = adapterx.getPosition(kkhi.getDesignationName());
                                           searchablespinner.setSelection(spinnerPosition);
                                       }
                                       fname.setText(kkhi.getFirstName());
                                       mname.setText(kkhi.getMiddleName());
                                       lname.setText(kkhi.getLastName());
                                       fnamemar.setText(kkhi.getFirstNameMar());
                                       mnamemar.setText(kkhi.getMiddleNameMar());
                                       lnamemar.setText(kkhi.getLastNameMar());
                                       gender.setText(kkhi.getGender());
                                       area.setText(kkhi.getArea());
                                       areamar.setText(kkhi.getAreaMar());
                                       birthdate.setText(kkhi.getBirthDate());
                                       birthdate.setFocusable(false);
                                       annidate.setText(kkhi.getAnniversaryDate());
                                       annidate.setFocusable(false);
                                       mobilenumber1.setText(kkhi.getMobileNo1());
                                       mobilenumber2.setText(kkhi.getMobileNo2());
                                       remark1.setText(kkhi.getRemark1());
                                       remark2.setText(kkhi.getRemark2());

                                       final String bdate = kkhi.getBirthDate();
                                       final String adate = kkhi.getAnniversaryDate();
                                       final int KKHICd = kkhi.getKKHICd();
                                       final String Selecteddate = kkhi.getSelecteddate();

                                       birthdate.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               try {
                                                   int mYear, mMonth, mDay;
                                                   if (bdate.length() > 0) {
                                                       DateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy");
                                                       Date date = dateFormater.parse(bdate);
                                                       final Calendar cal = Calendar.getInstance();
                                                       cal.setTime(date);
                                                       mYear = cal.get(Calendar.YEAR);
                                                       mMonth = cal.get(Calendar.MONTH);
                                                       mDay = cal.get(Calendar.DAY_OF_MONTH);
                                                   } else {
                                                       final Calendar c = Calendar.getInstance();
                                                       mYear = c.get(Calendar.YEAR); // current year
                                                       mMonth = c.get(Calendar.MONTH); // current month
                                                       mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                                                   }
                                                   fromDatePickerDialog = new DatePickerDialog(KaryakartaHitchintakActivity.this,
                                                           new DatePickerDialog.OnDateSetListener() {
                                                               @Override
                                                               public void onDateSet(DatePicker view, int year,
                                                                                     int monthOfYear, int dayOfMonth) {
                                                                   birthdate.setText(String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year);
                                                               }
                                                           }, mYear, mMonth, mDay);
                                                   fromDatePickerDialog.show();
                                               } catch (Exception e) {
                                               }
                                           }
                                       });

                                       annidate.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               try {
                                                   int mYear, mMonth, mDay;
                                                   if (bdate.length() > 0) {
                                                       DateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy");
                                                       Date date = dateFormater.parse(adate);
                                                       final Calendar cal = Calendar.getInstance();
                                                       cal.setTime(date);
                                                       mYear = cal.get(Calendar.YEAR);
                                                       mMonth = cal.get(Calendar.MONTH);
                                                       mDay = cal.get(Calendar.DAY_OF_MONTH);
                                                   } else {
                                                       final Calendar c = Calendar.getInstance();
                                                       mYear = c.get(Calendar.YEAR); // current year
                                                       mMonth = c.get(Calendar.MONTH); // current month
                                                       mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                                                   }
                                                   fromDatePickerDialog = new DatePickerDialog(KaryakartaHitchintakActivity.this,
                                                           new DatePickerDialog.OnDateSetListener() {
                                                               @Override
                                                               public void onDateSet(DatePicker view, int year,
                                                                                     int monthOfYear, int dayOfMonth) {
                                                                   annidate.setText(String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year);
                                                               }
                                                           }, mYear, mMonth, mDay);
                                                   fromDatePickerDialog.show();
                                               } catch (Exception e) {
                                               }
                                           }
                                       });

                                       close.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               dialog.dismiss();
                                           }
                                       });

                                       update.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               AlertDialog.Builder builder = new AlertDialog.Builder(KaryakartaHitchintakActivity.this);
                                               builder.setCancelable(false);
                                               builder.setTitle("Update ?");
                                               builder.setMessage("Are you sure want to update ?");
                                               builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialogx, int which) {

                                                       updateData(dialog, fname.getText().toString(), mname.getText().toString(), lname.getText().toString(), fnamemar.getText().toString(),
                                                               mnamemar.getText().toString(), lnamemar.getText().toString(), gender.getText().toString(), area.getText().toString(),
                                                               areamar.getText().toString(), birthdate.getText().toString(), annidate.getText().toString(), mobilenumber1.getText().toString(),
                                                               mobilenumber2.getText().toString(), remark1.getText().toString(), remark2.getText().toString(), searchablespinner.getSelectedItem().toString(),
                                                               KKHICd, Selecteddate);

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
                                       lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                       lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                                       dialog.show();
                                       dialog.getWindow().setAttributes(lp);
                                   }

                                   private void updateData(final Dialog dialog, final String fname, final String mname, final String lname, final String fnamemar, final String mnamemar, final String lnamemar, final String gender,
                                                           final String area, final String areamar, final String birthdate, final String annidate, final String mobilenumber1, final String mobilenumber2,
                                                           final String remark1, final String remark2, final String searchablespinner, final int KKHICd, final String Selecteddate) {
                                       Date startDateA, startDateB;
                                       bd = "";
                                       ad = "";
                                       try {
                                           startDateB = parseDate.parse(birthdate);
                                           startDateA = parseDate.parse(annidate);
                                           ad = parseDate2.format(startDateA);
                                           bd = parseDate2.format(startDateB);
                                       } catch (Exception e) {
                                       }

                                       final ProgressDialog progressBar1 = new ProgressDialog(KaryakartaHitchintakActivity.this);
                                       progressBar1.setCancelable(false);
                                       progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                       progressBar1.setMessage("Please wait...");
                                       progressBar1.show();
                                       Call<DefaultResponse> call1 = RetrofitClient
                                               .getInstance().getApi().updateKKHIDateQC(fname, mname, lname, fnamemar, mnamemar, lnamemar, gender, area, areamar,
                                                       bd, ad, mobilenumber1, mobilenumber2, remark1, remark2, searchablespinner, KKHICd, Selecteddate, username,
                                                       type, sharedelectionname);
                                       call1.enqueue(new Callback<DefaultResponse>() {
                                           @Override
                                           public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                                               DefaultResponse res = response.body();
                                               progressBar1.dismiss();
                                               bd = "";
                                               ad = "";
                                               if (!res.isError()) {
                                                   AlertDialog.Builder builder1 = new AlertDialog.Builder(KaryakartaHitchintakActivity.this);
                                                   builder1.setCancelable(false);
                                                   builder1.setTitle("Alert ?");
                                                   builder1.setMessage(res.getErrormsg());
                                                   builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                       @Override
                                                       public void onClick(DialogInterface dialogx, int which) {
                                                           callApi(searchdate, KaryakartaHitchintakActivity.this, message, type, ward, username);
                                                       }
                                                   });
                                                   AlertDialog dialog3 = builder1.create();
                                                   dialog3.show();
                                                   dialog.dismiss();
                                               } else {
                                                   AlertDialog.Builder builder1 = new AlertDialog.Builder(KaryakartaHitchintakActivity.this);
                                                   builder1.setCancelable(false);
                                                   builder1.setTitle("Alert ?");
                                                   builder1.setMessage(res.getErrormsg());
                                                   builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                       @Override
                                                       public void onClick(DialogInterface dialogx, int which) {
                                                       }
                                                   });
                                                   AlertDialog dialog3 = builder1.create();
                                                   dialog3.show();
                                                   dialog.dismiss();
                                               }
                                           }

                                           @Override
                                           public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                                               progressBar1.dismiss();
                                               Toast.makeText(KaryakartaHitchintakActivity.this, "Failed to update !", Toast.LENGTH_SHORT).show();
                                           }
                                       });
                                   }

                                   public void UpdateStatusDialog(final String qcstatus, final int corporatorcd, final String date, final int wardno, final int clientcd, final String mobile) {
                                       final Dialog dialog = new Dialog(KaryakartaHitchintakActivity.this);
                                       dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                       dialog.setCancelable(false);
                                       dialog.setContentView(R.layout.edit_popup);
                                       Button close = dialog.findViewById(R.id.closebtn);
                                       Button update = dialog.findViewById(R.id.updatebtn);
                                       final Spinner spinresponse1 = dialog.findViewById(R.id.edtspnsmsresponse);
                                       ArrayAdapter<String> adapterx = new ArrayAdapter<String>(KaryakartaHitchintakActivity.this, android.R.layout.simple_spinner_item, DBConnIP.arrayListToDialog);
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
                                               AlertDialog.Builder builder = new AlertDialog.Builder(KaryakartaHitchintakActivity.this);
                                               builder.setCancelable(false);
                                               builder.setTitle("Update ?");
                                               builder.setMessage("Are you sure want to update ?");
                                               builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialogx, int which) {
                                                       updateQCResponse(dialog, spinresponse1.getSelectedItem().toString().trim(), corporatorcd, date, wardno, clientcd, mobile);
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
                                   }

                                   @Override
                                   public int getItemCount() {
                                       //return fullsumm.size();
                                       return karyakartaHitachintakListItems.size();
                                   }

                                   class InnerHolder extends RecyclerView.ViewHolder {
                                       TextView votername, genage, socdet, responsedet;
                                       LinearLayout llt, llt2;
                                       ImageButton edit, callingbtn, piccapture;
                                       ImageView photo;

                                       public InnerHolder(@NonNull View itemView) {
                                           super(itemView);
                                           llt = itemView.findViewById(R.id.llt);
                                           llt2 = itemView.findViewById(R.id.llt2);
                                           votername = itemView.findViewById(R.id.votername);
                                           genage = itemView.findViewById(R.id.genage);
                                           socdet = itemView.findViewById(R.id.socdet);
                                           responsedet = itemView.findViewById(R.id.responsedet);
                                           edit = itemView.findViewById(R.id.edit);
                                           callingbtn = itemView.findViewById(R.id.callingbtn);
                                           piccapture = itemView.findViewById(R.id.piccapture);
                                           photo = itemView.findViewById(R.id.photo);
                                       }
                                   }
                               }
        );
    }

    private void uploadPhoto(int kkhiCd, int wardNo) {
        kkhicd = Integer.toString(kkhiCd);
        wardno = wardNo;
        getImageFromGallery();
    }

    public void StartCallRecording(String filename) {
        if (checkFolder()) {

            callRecordSurvey = new CallRecord.Builder(KaryakartaHitchintakActivity.this)
                    .setRecordFileName(filename)
                    .setRecordDirName("SurveyCallRecords")
                    .setRecordDirPath(Environment.getExternalStorageDirectory().getPath()) // optional & default value
                    //.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // optional & default value
                    .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB) // optional & default value
                    //.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) // optional & default value
                    .setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) // optional & default value
                    .setAudioSource(MediaRecorder.AudioSource.MIC) // optional & default value
                    //.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION) // optional & default value
                    .setShowSeed(false)
                    .setShowPhoneNumber(false)
                    .build();

        }
        callRecordSurvey.startCallReceiver();
    }

    public boolean checkFolder() {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SurveyCallRecords/";
        Log.d("folder---->", "creation and setup" + path);
        File dir = new File(path);
        boolean isDirectoryCreated = dir.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = dir.mkdir();
        }
        if (isDirectoryCreated) {
            // do something\
            Log.d("Folder", "Already Created");
            return true;
        } else {
            return false;
        }

    }

    private void StopCallRecording() {
        if (callRecordSurvey != null) {
            callRecordSurvey.stopCallReceiver();
        }
    }

    public void updateQCResponse(final Dialog dialog, final String selspinresponse, final int corporatorcd, final String date, final int wardno, final int kkhicd, final String mobile) {
        StopCallRecording();
        deleteFileName = "";
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SurveyCallRecords/ClientQCRecord_" + kkhicd + "_" + sharedelectionname + "_" + mobile + ".m4a";
        File newfile = new File(filePath);
        if (newfile.exists()) {
            Log.d("file exists--->", "true");
            deleteFileName = filePath;

        }

        new UploadFileToServer().execute(selspinresponse, Integer.toString(corporatorcd), sharedelectionname, date, Integer.toString(wardno),
                SharedPrefManager.getInstance(KaryakartaHitchintakActivity.this).username(), filePath, type);
        dialog.dismiss();

    }

    private class UploadFileToServer extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            progressBar = new ProgressDialog(KaryakartaHitchintakActivity.this);
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setMessage("Uploading data to server.....(0 %)");
            progressBar.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            //progressBar.setVisibility(View.VISIBLE);
            // updating progress bar value
            progressBar.setMessage("Uploading data to server.....(" + progress[0] + " %)");
            // updating percentage value
            //txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(String... params) {
            return uploadFile(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
        }

        @SuppressWarnings("deprecation")
        private String uploadFile(String callingresponse, String kkhicd, String elecname, String date, String wardno, String username, String filePath, String utype) {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(RetrofitClient.BASE_URL + "updateKaryakartaHitchintakCallingQC.php");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {
                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });
                //Log.d("file path", filePath);
                // Extra parameters if you want to pass to server
                entity.addPart("kkhicd", new StringBody(kkhicd));
                entity.addPart("date", new StringBody(date));
                entity.addPart("elecname", new StringBody(elecname));
                entity.addPart("wardno", new StringBody(wardno));
                entity.addPart("username", new StringBody(username));
                entity.addPart("usertype", new StringBody(utype));
                entity.addPart("callstatus", new StringBody(callingresponse));

                if (filePath != null && !filePath.equalsIgnoreCase("")) {
                    deleteFileName = filePath;
                    File sourceFile = new File(filePath);
                    if (sourceFile.exists()) {
                        entity.addPart("audiofile", new FileBody(sourceFile));
                    }
                }

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("----->", result);
            progressBar.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(result);
                totalSize = 0;
                if (!jsonObject.getBoolean("error")) {
                    if (jsonObject.getString("errormsg").equalsIgnoreCase("Entry Update Successfully.")) {
                        if (deleteFileName != null && !deleteFileName.equalsIgnoreCase("")) {
                            File fdelete = new File(deleteFileName);
                            if (fdelete.exists()) {
                                if (fdelete.delete()) {
                                    Toast.makeText(KaryakartaHitchintakActivity.this, "file deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(KaryakartaHitchintakActivity.this, "file not deleted", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(KaryakartaHitchintakActivity.this, "file does not exists", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(KaryakartaHitchintakActivity.this, "file path is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(KaryakartaHitchintakActivity.this);
                    builder1.setCancelable(false);
                    builder1.setTitle("Alert ?");
                    builder1.setMessage(jsonObject.getString("errormsg"));
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogx, int which) {
                            callApi(searchdate, KaryakartaHitchintakActivity.this, message, type, ward, username);
                        }
                    });
                    AlertDialog dialog3 = builder1.create();
                    dialog3.show();
                    //dialog.dismiss();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(KaryakartaHitchintakActivity.this);
                    builder1.setCancelable(false);
                    builder1.setTitle("Alert ?");
                    builder1.setMessage(jsonObject.getString("errormsg"));
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogx, int which) {
                        }
                    });
                    AlertDialog dialog3 = builder1.create();
                    dialog3.show();
                    //dialog.dismiss();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getImageFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 99);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            if (resultCode == RESULT_OK) {
                fileUri = data.getData();
                fileUri = Uri.parse(getRealPathFromURI(fileUri));
                launchUploadActivity(fileUri);
            } else if (requestCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image selection !", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to get image !", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void launchUploadActivity(Uri fileUriC) {
        //runmethod = true;
        Intent i = new Intent(KaryakartaHitchintakActivity.this, KKHIPhotoUploadActivity.class);
        i.putExtra("fileUri", fileUriC.getPath());
        i.putExtra("options", type);
        i.putExtra("wardno", Integer.toString(wardno));
        i.putExtra("electionname", sharedelectionname);
        i.putExtra("kkhicd", kkhicd);
        startActivity(i);
    }

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
}
