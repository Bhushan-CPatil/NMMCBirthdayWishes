package com.ornettech.nmmcqcandbirthdaywishes.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
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
import com.ornettech.nmmcqcandbirthdaywishes.adapter.DefaultResponse;
import com.ornettech.nmmcqcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.nmmcqcandbirthdaywishes.call.CallRecord;
import com.ornettech.nmmcqcandbirthdaywishes.model.BirthdayInWardResponse;
import com.ornettech.nmmcqcandbirthdaywishes.model.BirthdaysInWardItem;
import com.ornettech.nmmcqcandbirthdaywishes.utility.AndroidMultiPartEntity;
import com.ornettech.nmmcqcandbirthdaywishes.utility.SendSMSWhatsApp;
import com.ornettech.nmmcqcandbirthdaywishes.utility.SharedPrefManager;
import com.ornettech.nmmcqcandbirthdaywishes.utility.Transalator;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
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

public class QCBirthDayWardWiseActivity extends AppCompatActivity {

    public TextView prabhagdet, bdate, status, birthdayscnt, error;
    public RecyclerView wardlist;
    public String birthday,designation;
    private ProgressDialog progressBar = null;
    String day, month, year, ward, loggedinuser, acno, electionname, txtstatus, BirthdayWishMsgEng, BirthdayWishMsgMar;
    String corporatorname, corporatornamemar, designationname, designationnamemar, mobileno1, mobileno2, birthDayImageURL;
    public List<BirthdaysInWardItem> birthdaysInWard = new ArrayList<>();
    public DatePickerDialog fromDatePickerDialog;
    public String sharedelectionname, sharedusername, filename = "";
    public SimpleDateFormat dateFormatter, parseDate, parseDate2;
    public String newBirthDate = "", newBirthDate1 = "", newBirthDate2 = "";
    SendSMSWhatsApp sendSMSWhatsApp = new SendSMSWhatsApp();
    public List<String> arrayList2 = new ArrayList<>();
    long totalSize = 0;
    CallRecord callRecordBirthday = null;
    String deleteFileName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_c_birth_day_ward_wise);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Birthday QC</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        prabhagdet = findViewById(R.id.prabhagdet);
        bdate = findViewById(R.id.bdate);
        status = findViewById(R.id.status);
        birthdayscnt = findViewById(R.id.birthdayscnt);
        error = findViewById(R.id.error);
        wardlist = findViewById(R.id.wardlist);
        error.setVisibility(View.GONE);
        wardlist.setVisibility(View.GONE);

        sharedusername = SharedPrefManager.getInstance(QCBirthDayWardWiseActivity.this).username();
        sharedelectionname = SharedPrefManager.getInstance(QCBirthDayWardWiseActivity.this).getElectionName();

        designation = SharedPrefManager.getInstance(QCBirthDayWardWiseActivity.this).designation();


        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        parseDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        parseDate2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        day = getIntent().getStringExtra("day");
        month = getIntent().getStringExtra("month");
        year = getIntent().getStringExtra("year");
        ward = getIntent().getStringExtra("wardno");
        loggedinuser = SharedPrefManager.getInstance(QCBirthDayWardWiseActivity.this).username();
        acno = getIntent().getStringExtra("acno");
        electionname = getIntent().getStringExtra("elecname");
        txtstatus = getIntent().getStringExtra("status");
        corporatorname = getIntent().getStringExtra("corporatorname");
        corporatornamemar = getIntent().getStringExtra("corporatornamemar");
        designationname = getIntent().getStringExtra("designationname");
        designationnamemar = getIntent().getStringExtra("designationnamemar");
        mobileno1 = getIntent().getStringExtra("mobileno1");
        mobileno2 = getIntent().getStringExtra("mobileno2");

        prabhagdet.setText("Prabhagh No. - " + ward + " ( " + electionname + " / " + acno + " )");

        prabhagdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(callRecordBirthday != null){
                    callRecordBirthday.stopCallReceiver();
                    callRecordBirthday = null;
                }*/

                /*String filename = "Record_pp" + new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());
                if (checkFolder()) {

                    callRecordBirthday = new CallRecord.Builder(QCBirthDayWardWiseActivity.this)
                            .setRecordFileName(filename)
                            .setRecordDirName("BirthDayCallRecords")
                            .setRecordDirPath(Environment.getExternalStorageDirectory().getPath()) // optional & default value
                            //.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // optional & default value
                            .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB) // optional & default value
                            //.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) // optional & default value
                            .setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) // optional & default value
                            .setAudioSource(MediaRecorder.AudioSource.MIC) // optional & default value
                            //.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION) // optional & default value
                            .setShowSeed(false) // optional & default value ->Ex: RecordFileName_incoming.amr || RecordFileName_outgoing.amr
                            .setShowPhoneNumber(false)
                            .build();

                    Log.d("folder---->", "creation and setup"+Environment.getExternalStorageDirectory().getPath());
                }
                //callRecord.enableSaveFile();
                //audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                //audioManager.setMode(AudioManager.MODE_IN_CALL);
                //audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL), 0);
                //callRecord.changeRecordFileName(filename);
                callRecordBirthday.startCallReceiver();*/
                //dialMobNo(MainActivity.this);
                //Log.d("onclick start---->", "creation and setup");
                //Toast.makeText(QCBirthDayWardWiseActivity.this, "onclick start", Toast.LENGTH_SHORT).show();
                /*Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:9322554923"));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(QCBirthDayWardWiseActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(QCBirthDayWardWiseActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
                QCBirthDayWardWiseActivity.this.startActivity(callIntent);*/
                //Log.d("onclick start---->", "creation and setup");
                //Toast.makeText(QCBirthDayWardWiseActivity.this, "Calling", Toast.LENGTH_SHORT).show();
            }
        });

        birthday = day + "-" + month + "-" + year;
        bdate.setText("Date - " + birthday);
        status.setText("Status - " + txtstatus);

        arrayList2.clear();
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
        setAdapter();
        fetchDataFromApi();
    }

    private void fetchDataFromApi() {
        birthdaysInWard.clear();
        final ProgressDialog progressBar = new ProgressDialog(QCBirthDayWardWiseActivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait ......");
        progressBar.show();
        Call<BirthdayInWardResponse> call1 = RetrofitClient
                .getInstance().getApi().birthdaysOfWard(txtstatus, electionname, loggedinuser, acno, ward, day, month, year);
        call1.enqueue(new Callback<BirthdayInWardResponse>() {
            @Override
            public void onResponse(Call<BirthdayInWardResponse> call1, Response<BirthdayInWardResponse> response) {
                BirthdayInWardResponse res = response.body();
                if (res.getBirthdaysInWard().size() > 0) {
                    birthdaysInWard = res.getBirthdaysInWard();
                    BirthdayWishMsgEng = res.getBirthdayWishMsgEng();
                    BirthdayWishMsgMar = res.getBirthdayWishMsgMar();
                    birthDayImageURL = res.getWishImage();
                    birthdayscnt.setText("Total Birthdays - " + birthdaysInWard.size());
                    error.setVisibility(View.GONE);
                    wardlist.setVisibility(View.VISIBLE);
                    wardlist.getAdapter().notifyDataSetChanged();
                } else {
                    birthdayscnt.setText("Total Birthdays - " + birthdaysInWard.size());
                    BirthdayWishMsgEng = res.getBirthdayWishMsgEng();
                    BirthdayWishMsgMar = res.getBirthdayWishMsgMar();
                    birthDayImageURL = res.getWishImage();
                    error.setVisibility(View.VISIBLE);
                    wardlist.setVisibility(View.GONE);
                    wardlist.getAdapter().notifyDataSetChanged();
                    Toast.makeText(QCBirthDayWardWiseActivity.this, "No Records Exists For Selected Criteria!", Toast.LENGTH_LONG).show();
                }
                progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<BirthdayInWardResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(QCBirthDayWardWiseActivity.this, "Failed to fetch data !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        wardlist.setNestedScrollingEnabled(false);
        wardlist.setLayoutManager(new LinearLayoutManager(QCBirthDayWardWiseActivity.this));
        wardlist.setAdapter(new RecyclerView.Adapter() {
                                @NonNull
                                @Override
                                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                    View view = LayoutInflater.from(QCBirthDayWardWiseActivity.this).inflate(R.layout.adapter__birthday_list, viewGroup, false);
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
                                    final BirthdaysInWardItem model = birthdaysInWard.get(i);
                                    String res = model.getQCBirthdayWishStatus().equals("") ? "Pending" : model.getQCBirthdayWishStatus();
                                    if (!res.equalsIgnoreCase("Pending")) {
                                        innerHolder.resstatus.setText(Html.fromHtml("<font color='#464545'>QC Birthday Status - " + res + "</font>"));
                                    } else {
                                        innerHolder.resstatus.setText("QC Birthday Status - " + res);
                                    }
                                    String gender = model.getSex().equals("M") ? "MALE" : "FEMALE";
                                    innerHolder.votername.setText(model.getFullName() + " - (" + gender + ")");

                                    if(designation.equalsIgnoreCase("Software Developer") || designation.equalsIgnoreCase("Sr Manager") || designation.equalsIgnoreCase("Admin and Other") ||
                                            designation.equalsIgnoreCase("CEO/Director") || designation.equalsIgnoreCase("Manager") ||  designation.equalsIgnoreCase("HR")) {
                                        innerHolder.mob_no.setText(model.getMobileNo() + " / BD - " + model.getBirthDate());
                                    }else {
                                        innerHolder.mob_no.setText("********** / BD - " + model.getBirthDate());
                                    }


                                    if (model.getVoterId() == 0) {
                                        innerHolder.perbirthdate.setText("AC No-" + model.getAcNo() + " / Non-Voter");
                                    } else {
                                        innerHolder.perbirthdate.setText("AC No-" + model.getAcNo() + " / VID-" + model.getVoterId() + " / LN-" + model.getListNo());
                                    }
                                    innerHolder.edit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //Log.d("new bd3------>",model.getBirthDate());
                                            EditDialog(model.getVoterCd(), model.getMobileNo(), model.getAcNo(), model.getBirthDate(), model.getTablename(), model.getSiteName(), model.getVoterId(), model.getName(), model.getMiddleName(), model.getSurname(), model.getQCRemark());
                                            //Toast.makeText(QCBirthDayWardWiseActivity.this, "edit"+model.getFullName(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    innerHolder.callingbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            filename = "BirthdayQCRecord_" + model.getVoterCd() + "_" + electionname + "_" + model.getTablename();
                                            StartCallRecording(filename);

                                                              /*if(checkFolder()){

                                                                  callRecordBirthday = new CallRecord.Builder(QCBirthDayWardWiseActivity.this)
                                                                          .setRecordFileName(filename)
                                                                          .setRecordDirName("BirthDayCallRecords")
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

                                                              }*/
                                            //callRecord.enableSaveFile();
                                            //callRecordBirthday.startCallReceiver();
                                            innerHolder.llt2.setBackgroundColor(Color.parseColor("#FFDADA"));
                                            /*Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                            callIntent.setData(Uri.parse("tel:9322554923"));
                                            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            if (ActivityCompat.checkSelfPermission(QCBirthDayWardWiseActivity.this, Manifest.permission.CALL_PHONE)
                                                    != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(QCBirthDayWardWiseActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                                            }
                                            QCBirthDayWardWiseActivity.this.startActivity(callIntent);*/
                                            //sendSMSWhatsApp.dialMobNo(QCBirthDayWardWiseActivity.this, model.getMobileNo());
                                            sendSMSWhatsApp.callMobNo(QCBirthDayWardWiseActivity.this, model.getMobileNo());//model.getMobileNo()
                                            Toast.makeText(QCBirthDayWardWiseActivity.this, "Call Recording Started-" + filename, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    innerHolder.whatsappbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (corporatorname != null && corporatorname.length() > 0) {
                                                StringBuffer sb = new StringBuffer();
                                                sb.append("*Dear " + model.getFullName() + ",*\n\n");
                                                sb.append(BirthdayWishMsgMar);
                                                sb.append("*" + corporatornamemar + ",*\n");
                                                if(designationname.toUpperCase().contains("NAGARSEV") || designationname.toUpperCase().equalsIgnoreCase("CORPORATOR")){
                                                    sb.append(designationnamemar + " प्रभाग क्र. - " + Transalator.englishDigitToMarathiDigit(ward) + ",\n");
                                                }else{
                                                    sb.append(designationnamemar + ",\n");
                                                }

                                                sb.append(Transalator.englishDigitToMarathiDigit(mobileno1) + "\n");
                                                sb.append("________________________\n");
                                                sb.append("*Dear, " + model.getFullName() + "*\n\n");
                                                sb.append(BirthdayWishMsgEng);
                                                sb.append("*" + corporatorname + ",*\n");
                                                if(designationname.toUpperCase().contains("NAGARSEV") || designationname.toUpperCase().equalsIgnoreCase("CORPORATOR")){
                                                    sb.append(designationname + " Ward No. - " + ward + ",\n");
                                                }else{
                                                    sb.append(designationname + ",\n");
                                                }

                                                sb.append(mobileno1);
                                                WhatsAppPreviewDialog(sb, model.getMobileNo(),birthDayImageURL);
                                            } else {
                                                Toast.makeText(QCBirthDayWardWiseActivity.this, "Corporator entry not exist in corporation master !\nContact to adminitrator.", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                    if (model.getQCBirthdayWishStatus().equalsIgnoreCase("")) {
                                        innerHolder.llt.setBackgroundColor(Color.parseColor("#FF5555"));
                                    } else {
                                        innerHolder.llt.setBackgroundColor(Color.parseColor("#00C00C"));
                                    }

                                    innerHolder.itemView.setTag(i);
                                    innerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            UpdateStatusDialog(model.getVoterCd(), model.getQCBirthdayWishStatus(), model.getAcNo(), model.getTablename());
                                        }
                                    });
                                }

                                public void EditDialog(final String voterCd, String mobileNo, final int acNo, final String newBirthDate, final String tablename, final String sitename, final int voterid, final String voterfname, final String votermname, final String voterlname, final String remarktxt) {
                                    final Dialog dialog = new Dialog(QCBirthDayWardWiseActivity.this);
                                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(false);
                                    dialog.setContentView(R.layout.editbirthdate);
                                    Button close = dialog.findViewById(R.id.closebtn);
                                    Button update = dialog.findViewById(R.id.updatebtn);
                                    final EditText fname = dialog.findViewById(R.id.fname);
                                    final EditText mname = dialog.findViewById(R.id.mname);
                                    final EditText lname = dialog.findViewById(R.id.lname);
                                    final EditText birthdate = dialog.findViewById(R.id.birthdate);
                                    final EditText mobilenumber = dialog.findViewById(R.id.mobilenumber);
                                    final EditText remark = dialog.findViewById(R.id.remark);
                                    remark.setText(remarktxt);
                                    //Log.d("new bd4------>",newBirthDate);
                                    birthdate.setText(newBirthDate);
                                    birthdate.setFocusable(false);
                                    mobilenumber.setText(mobileNo);
                                    mobilenumber.setFocusable(false);
                                    fname.setText(voterfname);
                                    mname.setText(votermname);
                                    lname.setText(voterlname);
                                    if (voterid != 0) {
                                        fname.setEnabled(false);
                                        mname.setEnabled(false);
                                        lname.setEnabled(false);
                                        fname.setFocusable(false);
                                        mname.setFocusable(false);
                                        lname.setFocusable(false);
                                    } else {
                                        fname.setEnabled(true);
                                        mname.setEnabled(true);
                                        lname.setEnabled(true);
                                        fname.setFocusable(true);
                                        mname.setFocusable(true);
                                        lname.setFocusable(true);
                                    }

                                    birthdate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                                              /*final Calendar c = Calendar.getInstance();
                                                              int mYear = c.get(Calendar.YEAR); // current year
                                                              int mMonth = c.get(Calendar.MONTH); // current month
                                                              int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                                                              fromDatePickerDialog = new DatePickerDialog(QCBirthDayWardWiseActivity.this,
                                                                      new DatePickerDialog.OnDateSetListener() {
                                                                          @Override
                                                                          public void onDateSet(DatePicker view, int year,
                                                                                                int monthOfYear, int dayOfMonth) {
                                                                              birthdate.setText(String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year);
                                                                          }
                                                                      }, mYear, mMonth, mDay);
                                                              fromDatePickerDialog.show();*/
                                            try {
                                                int mYear, mMonth, mDay;
                                                if (newBirthDate.length() > 0) {
                                                    DateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy");
                                                    Date date = dateFormater.parse(newBirthDate);
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
                                                fromDatePickerDialog = new DatePickerDialog(QCBirthDayWardWiseActivity.this,
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

                                    close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    update.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(QCBirthDayWardWiseActivity.this);
                                            builder.setCancelable(false);
                                            builder.setTitle("Update ?");
                                            builder.setMessage("Are you sure want to update ?");
                                            builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogx, int which) {
                                                    if (voterid != 0) {
                                                        updateBDAndMobNo(dialog, birthdate.getText().toString().trim(), voterCd, acNo, mobilenumber.getText().toString(), tablename, sitename, voterfname, votermname, voterlname, remark.getText().toString());

                                                    } else {
                                                        String n = "", m = "", l = "";
                                                        n = fname.getText().toString();
                                                        m = mname.getText().toString();
                                                        l = lname.getText().toString();
                                                        if (n != null && n.isEmpty()) {
                                                            fname.setError("Voter name cannot be empty !");
                                                            fname.requestFocus();
                                                        } else if (n != null && l.isEmpty()) {
                                                            lname.setError("Voter surname name cannot be empty !");
                                                            lname.requestFocus();
                                                        } else {
                                                            if (n != null && n.trim().length() > 0)
                                                                n = Transalator.convertStringFirstCharToCapital(n).trim();

                                                            if (n != null && m.trim().length() > 0)
                                                                m = Transalator.convertStringFirstCharToCapital(m).trim();

                                                            if (n != null && l.trim().length() > 0)
                                                                l = Transalator.convertStringFirstCharToCapital(l).trim();


                                                            updateBDAndMobNo(dialog, birthdate.getText().toString().trim(), voterCd, acNo, mobilenumber.getText().toString(), tablename, sitename, n, m, l, remark.getText().toString());
                                                        }

                                                                          /*else if(n !=null && m.isEmpty()){
                                                                              mname.setError("Voter middle name cannot be empty !");
                                                                              mname.requestFocus();
                                                                          }*/
                                                    }
                                                    //updateBDAndMobNo(dialog,birthdate.getText().toString().trim(),voterCd,acNo,mobilenumber.getText().toString(),tablename,sitename);
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
                                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                                    dialog.show();
                                    dialog.getWindow().setAttributes(lp);
                                }

                                public void UpdateStatusDialog(final String voterCd, String qcstatus, final int acNo, final String tablename) {
                                    final Dialog dialog = new Dialog(QCBirthDayWardWiseActivity.this);
                                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(false);
                                    dialog.setContentView(R.layout.edit_popup);
                                    Button close = dialog.findViewById(R.id.closebtn);
                                    Button update = dialog.findViewById(R.id.updatebtn);
                                    final Spinner spinresponse1 = dialog.findViewById(R.id.edtspnsmsresponse);

                                    ArrayAdapter<String> adapterx = new ArrayAdapter<String>(QCBirthDayWardWiseActivity.this, android.R.layout.simple_spinner_item, arrayList2);
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
                                            AlertDialog.Builder builder = new AlertDialog.Builder(QCBirthDayWardWiseActivity.this);
                                            builder.setCancelable(false);
                                            builder.setTitle("Update ?");
                                            builder.setMessage("Are you sure want to update ?");
                                            builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogx, int which) {
                                                    updateQCResponse(dialog, spinresponse1.getSelectedItem().toString().trim(), voterCd, acNo, tablename);
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
                                }

                                @Override
                                public int getItemCount() {
                                    //return fullsumm.size();
                                    return birthdaysInWard.size();
                                }

                                class InnerHolder extends RecyclerView.ViewHolder {
                                    TextView votername, perbirthdate, mob_no, resstatus;
                                    LinearLayout llt, llt2;
                                    ImageButton edit, callingbtn, whatsappbtn;

                                    public InnerHolder(@NonNull View itemView) {
                                        super(itemView);
                                        llt = itemView.findViewById(R.id.llt);
                                        llt2 = itemView.findViewById(R.id.llt2);
                                        votername = itemView.findViewById(R.id.votername);
                                        perbirthdate = itemView.findViewById(R.id.perbirthdate);
                                        mob_no = itemView.findViewById(R.id.mob_no);
                                        resstatus = itemView.findViewById(R.id.resstatus);
                                        edit = itemView.findViewById(R.id.edit);
                                        callingbtn = itemView.findViewById(R.id.callingbtn);
                                        whatsappbtn = itemView.findViewById(R.id.whatsappbtn);
                                    }
                                }
                            }
        );
    }

    private void WhatsAppPreviewDialog(final StringBuffer sb, final String mobileNo, final String imageUrl) {
        final Dialog dialog = new Dialog(QCBirthDayWardWiseActivity.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.whatsapp_preview);
        ImageButton closepopup = dialog.findViewById(R.id.closepopup);
        Button sendbtn = dialog.findViewById(R.id.sendbtn);
        final TextView whatsappsms = dialog.findViewById(R.id.whatsappsms);
        ImageView bdimage = dialog.findViewById(R.id.bdimage);
        Glide.with(QCBirthDayWardWiseActivity.this).load(imageUrl).into(bdimage);
        bdimage.setVisibility(View.VISIBLE);
        final LinearLayout linlay = dialog.findViewById(R.id.linlay);
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(QCBirthDayWardWiseActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Whatsapp");
                    builder.setMessage("Send whatsapp message to voter.");
                    builder.setNeutralButton("Message", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SendSMSWhatsApp.sendWhatsApp(QCBirthDayWardWiseActivity.this, mobileNo, sb.toString());
                        }
                    });
                if(imageUrl !=null && !imageUrl.equalsIgnoreCase("")) {
                    builder.setPositiveButton("Image", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File file = process(linlay);
                            if (file != null) {
                                SendSMSWhatsApp.sentToWhatsapp(QCBirthDayWardWiseActivity.this, file, mobileNo, "");
                            }
                        }
                    });
                }
                    AlertDialog dialog2 = builder.create();
                    dialog2.show();
                    //SendSMSWhatsApp.sendWhatsApp(QCBirthDayWardWiseActivity.this, mobileNo, sb.toString(),file);

                    //new MainActivity().sentToWhatsapp(mycontext,file);
                    //}

                //ShowSMSPreviewOfSMS(sb,mobileNo);
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public File process(LinearLayout linearLayout) {
        File file = saveBitMap(linearLayout, linearLayout);    //which view you want to pass that view as parameter
        if (file != null) {
            Log.i("TAG", "Drawing saved to the gallery!");
            return file;
        } else {
            Log.i("TAG", "Oops! Image could not be saved.");
        }
        return null;
    }

    private File saveBitMap(View drawView, LinearLayout linearLayout){
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"CorporatorBanner");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated)
                Log.i("ATG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() +File.separator+ System.currentTimeMillis()+".jpg";
        //imageUrl.add(filename);
        File pictureFile = new File(filename);
        Bitmap bitmap =getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            linearLayout.setDrawingCacheEnabled(true);
            Bitmap b = linearLayout.getDrawingCache();
            b.compress(Bitmap.CompressFormat.JPEG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery(pictureFile.getAbsolutePath());
        return pictureFile;
    }

    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    private void scanGallery(String path) {
        try {
            MediaScannerConnection.scanFile(QCBirthDayWardWiseActivity.this, new String[] { path },null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBDAndMobNo(final Dialog dialog, final String birthdate, String selvotercd, final int ac_no, final String mobileno, String tablename, String intsite, final String voterfname, final String votermname, final String voterlname, final String remark) {
        //DBConnIP.updateQCResponse_status = false;
        Date startDate;
        newBirthDate1 = "";
        newBirthDate2 = "";
        try {
            startDate = parseDate.parse(birthdate);
            newBirthDate1 = dateFormatter.format(startDate);
            newBirthDate2 = parseDate2.format(startDate);
        } catch (Exception e) {
        }

        final ProgressDialog progressBar1 = new ProgressDialog(QCBirthDayWardWiseActivity.this);
        progressBar1.setCancelable(false);
        progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar1.setMessage("Please wait...");
        progressBar1.show();
        Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().updateBirthDateQC(selvotercd, mobileno, electionname, intsite, ac_no, newBirthDate1, newBirthDate2, loggedinuser, tablename, voterfname, votermname, voterlname, remark);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                progressBar1.dismiss();
                newBirthDate1 = "";
                newBirthDate2 = "";
                if (!res.isError()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(QCBirthDayWardWiseActivity.this);
                    builder1.setCancelable(false);
                    builder1.setTitle("Alert ?");
                    builder1.setMessage(res.getErrormsg());
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogx, int which) {
                            fetchDataFromApi();
                        }
                    });
                    AlertDialog dialog3 = builder1.create();
                    dialog3.show();
                    dialog.dismiss();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(QCBirthDayWardWiseActivity.this);
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
                Toast.makeText(QCBirthDayWardWiseActivity.this, "Failed to update !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateQCResponse(final Dialog dialog, final String selspinresponse, String selvotercd, final int ac_no, final String tablename) {
        //DBConnIP.updateQCResponse_status = false;
        StopCallRecording();
        deleteFileName = "";
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BirthDayCallRecords/BirthdayQCRecord_" + selvotercd + "_" + electionname + "_" + tablename+".m4a";
        File newfile = new File(filePath);
        if(newfile.exists()){
            Log.d("file exists--->","true");
            deleteFileName = filePath;
            //new UploadFileToServer().execute(selvotercd, tablename, electionname, Integer.toString(ac_no), loggedinuser, selspinresponse, filePath);
        }
        new UploadFileToServer().execute(selvotercd, tablename, electionname, Integer.toString(ac_no), loggedinuser, selspinresponse, filePath);

        dialog.dismiss();
        //UploadFileToServer(selvotercd, tablename, electionname, ac_no, loggedinuser, selspinresponse, filePath);
        /*final ProgressDialog progressBar1 = new ProgressDialog(QCBirthDayWardWiseActivity.this);
        progressBar1.setCancelable(false);
        progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar1.setMessage("Please wait...");
        progressBar1.show();
        Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().updateBirthDateQC(selvotercd, tablename, electionname, ac_no, loggedinuser, selspinresponse);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                progressBar1.dismiss();
                if (!res.isError()) {
                    if (filename != null && !filename.equalsIgnoreCase("")) {
                        File fdelete = new File(filename);
                        if (fdelete.exists()) {
                            if (fdelete.delete()) {
                                Toast.makeText(QCBirthDayWardWiseActivity.this, "file deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(QCBirthDayWardWiseActivity.this, "file not deleted", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(QCBirthDayWardWiseActivity.this, "file does not exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(QCBirthDayWardWiseActivity.this, "file path is empty", Toast.LENGTH_SHORT).show();
                    }
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(QCBirthDayWardWiseActivity.this);
                    builder1.setCancelable(false);
                    builder1.setTitle("Alert ?");
                    builder1.setMessage(res.getErrormsg());
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogx, int which) {
                            fetchDataFromApi();
                        }
                    });
                    AlertDialog dialog3 = builder1.create();
                    dialog3.show();
                    dialog.dismiss();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(QCBirthDayWardWiseActivity.this);
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
                Toast.makeText(QCBirthDayWardWiseActivity.this, "Failed to update !", Toast.LENGTH_SHORT).show();
            }
        });*/

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

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_whatsapp, menu);
        MenuItem sendtowhatsapp = menu.findItem(R.id.sendwhatsapp);
        MenuItem sendpdf = menu.findItem(R.id.sendpdf);

        sendtowhatsapp.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                generateBirthdayWishSMS();
                return false;
            }
        });

        sendpdf.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (birthdaysInWard.size() > 0) {
                    pdfGenerate(birthdaysInWard, day, month, electionname);
                } else {
                    Toast.makeText(QCBirthDayWardWiseActivity.this, "List is empty !", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        return true;
    }

    private void generateBirthdayWishSMS() {
        if (birthdaysInWard.size() > 0) {
            StringBuffer smsstr = new StringBuffer();
            smsstr.append("*खालील दिलेली माहिती हि दिनांक " + Transalator.englishDigitToMarathiDigit(day) + "/" + Transalator.englishDigitToMarathiDigit(month) + "/" + Transalator.englishDigitToMarathiDigit(year) + " रोजी वार्ड क्र. " + Transalator.englishDigitToMarathiDigit(ward) + " (" + electionname + ") येथील वाढदिवस असलेल्या रहिवाश्यांची आहे.* \n\n");
//            smsstr.append("*Ward Number* - "+ward+"\n");
//            smsstr.append("*Date* - "+day+"/"+month+"/"+year+"\n");
//            smsstr.append("*Total birthdays in ward* - "+birthdaysInWard.size()+"\n\n");
            for (int x = 0; x < birthdaysInWard.size(); x++) {
                int y = x + 1;
                if (birthdaysInWard.get(x).getVisible().equalsIgnoreCase("show")) {
                    if (birthdaysInWard.get(x).getQCBirthdayWishStatus().equalsIgnoreCase("")) {
                        smsstr.append(y + ". " + birthdaysInWard.get(x).getFullName() + " - " + birthdaysInWard.get(x).getMobileNo() + " (Pending) - Room No. " + birthdaysInWard.get(x).getRoomNo() + ", " + birthdaysInWard.get(x).getSocietyName() + ", " + birthdaysInWard.get(x).getPocketName() + "\n\n");
                    } else {
                        smsstr.append(y + ". " + birthdaysInWard.get(x).getFullName() + " - " + birthdaysInWard.get(x).getMobileNo() + " (" + birthdaysInWard.get(x).getQCBirthdayWishStatus() + ") - Room No. " + birthdaysInWard.get(x).getRoomNo() + ", " + birthdaysInWard.get(x).getSocietyName() + ", " + birthdaysInWard.get(x).getPocketName() + "\n\n");
                    }
                } else {
                    if (birthdaysInWard.get(x).getQCBirthdayWishStatus().equalsIgnoreCase("")) {
                        smsstr.append(y + ". " + birthdaysInWard.get(x).getFullName() + " - " + birthdaysInWard.get(x).getMobileNo() + " (Pending)" + "\n\n");
                    } else {
                        smsstr.append(y + ". " + birthdaysInWard.get(x).getFullName() + " - " + birthdaysInWard.get(x).getMobileNo() + " (" + birthdaysInWard.get(x).getQCBirthdayWishStatus() + ") \n\n");
                    }
                }

            }

            smsstr.append("___________________________\n");


            ShowSMSPreviewOfSMS(smsstr);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(QCBirthDayWardWiseActivity.this);
            builder.setCancelable(false);
            builder.setTitle("List is empty ?");
            builder.setMessage("Make sure that data is displayed in below list.");

            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogx, int which) {
                }
            });
            AlertDialog dialog2 = builder.create();
            dialog2.show();
        }

    }

    public void ShowSMSPreviewOfSMS(StringBuffer sb) {
        final Dialog dialog = new Dialog(QCBirthDayWardWiseActivity.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.whatsapp_preview);
        ImageButton closepopup = dialog.findViewById(R.id.closepopup);
        Button sendbtn = dialog.findViewById(R.id.sendbtn);
        final TextView whatsappsms = dialog.findViewById(R.id.whatsappsms);
        whatsappsms.setMovementMethod(new ScrollingMovementMethod());
        whatsappsms.setText(sb.toString());
        whatsappsms.setTextIsSelectable(true);


        closepopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, whatsappsms.getText().toString());
                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(QCBirthDayWardWiseActivity.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void ShowSMSPreviewOfSMS(final StringBuffer sb, final String corporatornumber) {
        final Dialog dialog = new Dialog(QCBirthDayWardWiseActivity.this);
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
                SendSMSWhatsApp.sendWhatsApp(QCBirthDayWardWiseActivity.this, corporatornumber, sb.toString());
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void pdfGenerate(List<BirthdaysInWardItem> mainModel, String day, String month, String ele) {

        Random rand = new Random();
        File sd = Environment.getExternalStorageDirectory();
        String name = "BIRTHDAYS OF " + day + " " + Transalator.convertIntMonthIntoName(month) + " (" + ele + " WARD NO. " + ward + ") ";
        String pdfFile = name + " " + rand.nextInt(100000) + ".pdf";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        Document document = new Document();
        try {
            File file = new File(directory, pdfFile);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file)); //new FileOutputStream("AddTableExample.pdf")

            document.open();
            Paragraph paragraph = new Paragraph(name, FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, BaseColor.BLACK));
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
            float[] columnWidths = {1f, 3f, 3f, 2f, 2f};
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

            /*PdfPCell cell6 = new PdfPCell(new Paragraph("ADDRESS"));
            cell6.setBorderColor(BaseColor.BLACK);
            cell6.setPaddingLeft(10);
            cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);*/

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

            PdfPCell cell5 = null;
            if (mainModel.get(0).getVisible().equalsIgnoreCase("show")) {
                cell5 = new PdfPCell(new Paragraph("ADDRESS"));
                cell3.setBorderColor(BaseColor.BLACK);
                cell3.setPaddingLeft(10);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            } else {
                cell5 = new PdfPCell(new Paragraph("WARD NO"));
                cell3.setBorderColor(BaseColor.BLACK);
                cell3.setPaddingLeft(10);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            }

            /*PdfPCell cell5 = new PdfPCell(new Paragraph("ADDRESS"));
            cell3.setBorderColor(BaseColor.BLACK);
            cell3.setPaddingLeft(10);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);*/

            table.addCell(cell1);
            table.addCell(cell2);
            if (mainModel.get(0).getVisible().equalsIgnoreCase("show")) {
                table.addCell(cell5);
                table.addCell(cell3);
                table.addCell(cell4);
            } else {
                table.addCell(cell3);
                table.addCell(cell4);
                table.addCell(cell5);
            }


            int cnt = 1;
            for (int k = 0; k < mainModel.size(); k++) {
                if (mainModel.get(k).getWardNo() != 0) {
                    table.addCell("" + cnt);
                    cnt++;

                    String nm = "";
                    if (mainModel.get(k).getQCBirthdayWishStatus().equalsIgnoreCase("")) {
                        nm = mainModel.get(k).getFullName().toUpperCase() + " (Pending)";
                    } else {
                        nm = mainModel.get(k).getFullName().toUpperCase() + " (" + mainModel.get(k).getQCBirthdayWishStatus() + ")";
                    }
                    table.addCell(nm);
                    if (mainModel.get(0).getVisible().equalsIgnoreCase("show")) {
                        table.addCell("Room no. " + mainModel.get(k).getRoomNo() + ", " + mainModel.get(k).getSocietyName() + ", " + mainModel.get(k).getPocketName());
                    }
                    table.addCell(mainModel.get(k).getMobileNo());
                    table.addCell(mainModel.get(k).getBirthDate());
                    if (!mainModel.get(0).getVisible().equalsIgnoreCase("show")) {
                        table.addCell(Integer.toString(mainModel.get(k).getWardNo()));
                    }

                }
            }
            document.add(table);

            String note = "\n\nThank & Regards,\n" +
                    "Chankya Election Management,\n" +
                    "Mobile No. 9223575189/90/91/92/93";

            document.add(new Paragraph(new String(note.getBytes("UTF-8"))));

            document.close();
            writer.close();
            //progressBar1.dismiss();
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
                Toast.makeText(QCBirthDayWardWiseActivity.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StartCallRecording(String filename) {
        if (checkFolder()) {

            callRecordBirthday = new CallRecord.Builder(QCBirthDayWardWiseActivity.this)
                    .setRecordFileName(filename)
                    .setRecordDirName("BirthDayCallRecords")
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
        callRecordBirthday.startCallReceiver();
    }

    public boolean checkFolder() {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BirthDayCallRecords/";
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
        if (callRecordBirthday != null) {
            callRecordBirthday.stopCallReceiver();
        }
    }

    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            Log.d("------>","onPreExecute");
            /*progressBar = new ProgressDialog(SocietyWIseVoterDet.this);
            progressBar.setMessage("Uploading data to server");
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setIndeterminate(true);
            progressBar.setProgress(0);
            progressBar.show();*/
            progressBar = new ProgressDialog(QCBirthDayWardWiseActivity.this);
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setMessage("Uploading data to server.....(0 %)");
            progressBar.show();
            //progressDialoge.show();
            //progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            //progressBar.setVisibility(View.VISIBLE);
            // updating progress bar value
            Log.d("------>","onProgressUpdate"+progress[0]);
            progressBar.setMessage("Uploading data to server.....("+progress[0]+" %)");
            // updating percentage value
            //txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(String... params) {
            return uploadFile(params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
        }

        @SuppressWarnings("deprecation")
        private String uploadFile(String votercd, String tablename, String elecname, String acno, String username, String callingresponse, String filePath) {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(RetrofitClient.BASE_URL + "updateBirthDateQCWithAudio.php");
           // Log.d("------>","uploadFile");

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
                entity.addPart("votercd", new StringBody(votercd));
                entity.addPart("tablename", new StringBody(tablename));
                entity.addPart("elecname", new StringBody(elecname));
                entity.addPart("acno", new StringBody(acno));
                entity.addPart("username", new StringBody(username));
                entity.addPart("callstatus", new StringBody(callingresponse));

                if(filePath != null && !filePath.equalsIgnoreCase("")) {
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
            //Log.d("------>","uploadFile end");

            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            //Log.d("----->",result);
           // Log.d("------>","onPostExecute");

            progressBar.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(result);
                totalSize = 0;
                if (!jsonObject.getBoolean("error")) {
                    if(jsonObject.getString("errormsg").equalsIgnoreCase("All Records Update Successfully.")) {
                        if (deleteFileName != null && !deleteFileName.equalsIgnoreCase("")) {
                            File fdelete = new File(deleteFileName);
                            if (fdelete.exists()) {
                                if (fdelete.delete()) {
                                    Toast.makeText(QCBirthDayWardWiseActivity.this, "file deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(QCBirthDayWardWiseActivity.this, "file not deleted", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(QCBirthDayWardWiseActivity.this, "file does not exists", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(QCBirthDayWardWiseActivity.this, "file path is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(QCBirthDayWardWiseActivity.this);
                    builder1.setCancelable(false);
                    builder1.setTitle("Alert ?");
                    builder1.setMessage(jsonObject.getString("errormsg"));
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogx, int which) {
                            Log.d("------>","fetchDataFromApi");

                            fetchDataFromApi();
                        }
                    });
                    AlertDialog dialog3 = builder1.create();
                    dialog3.show();
                    //dialog.dismiss();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(QCBirthDayWardWiseActivity.this);
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

                /*if (!jsonObject.getBoolean("error")) {
                    totalSize = 0;
                    Toast.makeText(QCBirthDayWardWiseActivity.this, jsonObject.getString("errormsg"), Toast.LENGTH_SHORT).show();
                    // showing the server response in an alert dialog
                    //showAlert(jsonObject.getString("errormsg"));
                } else {
                    Toast.makeText(QCBirthDayWardWiseActivity.this, jsonObject.getString("errormsg"), Toast.LENGTH_SHORT).show();
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
