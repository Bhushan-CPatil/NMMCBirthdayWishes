package com.ornettech.qcandbirthdaywishes.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.adapter.DefaultResponse;
import com.ornettech.qcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.qcandbirthdaywishes.call.CallRecord;
import com.ornettech.qcandbirthdaywishes.model.MainResponse;
import com.ornettech.qcandbirthdaywishes.model.RoomWiseDetailedListPojoItem;
import com.ornettech.qcandbirthdaywishes.model.VoterDetailedDataItem;
import com.ornettech.qcandbirthdaywishes.utility.AndroidMultiPartEntity;
import com.ornettech.qcandbirthdaywishes.utility.DBConnIP;
import com.ornettech.qcandbirthdaywishes.utility.OuterHolder;
import com.ornettech.qcandbirthdaywishes.utility.SendSMSWhatsApp;
import com.ornettech.qcandbirthdaywishes.utility.SharedPrefManager;
import com.ornettech.qcandbirthdaywishes.utility.Transalator;

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

public class SocietyWIseVoterDet extends AppCompatActivity {

    public TextView societyname, from, to, callingres, soccount, roomcount;
    public RecyclerView surveyroomlist;
    public String intsocname, intfrm, intto, intcalling, intsite, designation;
    public int introomcnt, intsubloccd, intsoccd;
    public String sharedelectionname, sharedusername, filename = "";
    public List<RoomWiseDetailedListPojoItem> mainlist = new ArrayList<>();
    SendSMSWhatsApp sendSMSWhatsApp = new SendSMSWhatsApp();
    public String newBirthDate = "", newBirthDate1 = "", newBirthDate2 = "";
    public DatePickerDialog fromDatePickerDialog;
    public SimpleDateFormat dateFormatter, parseDate, parseDate2;
    CallRecord callRecordSurvey = null;
    String deleteFileName="";
    private ProgressDialog progressBar = null;
    long totalSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_wise_voter_det);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + SharedPrefManager.getInstance(SocietyWIseVoterDet.this).getElectionName() + " QC</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        societyname = findViewById(R.id.societyname);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        callingres = findViewById(R.id.callingres);
        soccount = findViewById(R.id.soccount);
        roomcount = findViewById(R.id.roomcount);
        surveyroomlist = findViewById(R.id.surveyroomlist);

        sharedusername = SharedPrefManager.getInstance(SocietyWIseVoterDet.this).username();
        sharedelectionname = SharedPrefManager.getInstance(SocietyWIseVoterDet.this).getElectionName();
        designation = SharedPrefManager.getInstance(SocietyWIseVoterDet.this).designation();
        intsocname = getIntent().getStringExtra("society_name");
        intfrm = getIntent().getStringExtra("fromdate");
        intto = getIntent().getStringExtra("todate");
        intcalling = getIntent().getStringExtra("qcres");
        introomcnt = getIntent().getIntExtra("roomcnt", 0);
        intsite = getIntent().getStringExtra("site");
        intsubloccd = getIntent().getIntExtra("sublocation_cd", 0);
        intsoccd = getIntent().getIntExtra("survey_soc_cd", 0);

        societyname.setText("Society Name - " + intsocname);
        from.setText("From Date - " + intfrm);
        to.setText("To Date - " + intto);
        callingres.setText("QC Calling - " + intcalling + " (Site Name - " + intsite + ")");
        roomcount.setText("Total Room - " + introomcnt);
        //Log.d("sublocation_cd---->",intsubloccd+"");

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        parseDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        parseDate2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        setAdapter();
        callApi();
    }

    private void setAdapter() {
        surveyroomlist.setNestedScrollingEnabled(false);
        surveyroomlist.setLayoutManager(new LinearLayoutManager(SocietyWIseVoterDet.this));
        surveyroomlist.setAdapter(new RecyclerView.Adapter() {
                                      @NonNull
                                      @Override
                                      public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                          View view = LayoutInflater.from(SocietyWIseVoterDet.this).inflate(R.layout.adapter_room_listing, viewGroup, false);
                                          OuterHolder holder = new OuterHolder(view);
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
                                          final OuterHolder myHolder = (OuterHolder) viewHolder;
                                          final RoomWiseDetailedListPojoItem model = mainlist.get(i);
                                          myHolder.txtroomnumber.setText("Room Number - " + model.getRoomNo());
                                          setInnerAdapter(myHolder, model.getVoterDetailedData());
                                      }

                                      @Override
                                      public int getItemCount() {
                                          //return fullsumm.size();
                                          return mainlist.size();
                                      }

                                  }
        );
    }


    private void setInnerAdapter(OuterHolder myHolder, final List<VoterDetailedDataItem> voterDetailedData) {
        myHolder.innerrecyclerview.setNestedScrollingEnabled(false);
        myHolder.innerrecyclerview.setLayoutManager(new LinearLayoutManager(SocietyWIseVoterDet.this));
        myHolder.innerrecyclerview.setAdapter(new RecyclerView.Adapter() {
                                                  @NonNull
                                                  @Override
                                                  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                                      View view = LayoutInflater.from(SocietyWIseVoterDet.this).inflate(R.layout.adapter_survey_voter, viewGroup, false);
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
                                                      final VoterDetailedDataItem model = voterDetailedData.get(i);
                                                      String res = model.getQCResponse().equals("") ? "Pending" : model.getQCResponse();
                                                      if (!res.equalsIgnoreCase("Pending")) {
                                                          innerHolder.responsedet.setText(Html.fromHtml("<font color='#464545'>QC Status - " + res + "</font>"));
                                                      } else {
                                                          innerHolder.responsedet.setText("QC Status - " + res);
                                                      }
                                                      //innerHolder.responsedet.setText("QC Status - "+res);
                                                      String gender = model.getSex().equals("M") ? "MALE" : "FEMALE";
                                                      innerHolder.votername.setText(model.getFullName() + " - (" + gender + ")");

                                          /*SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                                          SimpleDateFormat parseDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US);*/
                                                      Date startDate;
                                                      newBirthDate = "";
                                                      try {
                                                          startDate = dateFormatter.parse(model.getBirthDate());
                                                          newBirthDate = parseDate.format(startDate);
                                                      } catch (Exception e) {

                                                      }

                                                      if(designation.equalsIgnoreCase("Software Developer") || designation.equalsIgnoreCase("Sr Manager") || designation.equalsIgnoreCase("Admin and Other") ||
                                                              designation.equalsIgnoreCase("CEO/Director") || designation.equalsIgnoreCase("Manager") ||  designation.equalsIgnoreCase("HR")) {
                                                          if (newBirthDate.equalsIgnoreCase("")) {
                                                              innerHolder.genage.setText(model.getMobileNo());
                                                          } else {
                                                              model.setBirthDate(newBirthDate);
                                                              innerHolder.genage.setText(model.getMobileNo() + " / BD - " + model.getBirthDate());
                                                          }
                                                      }else {
                                                          if (newBirthDate.equalsIgnoreCase("")) {
                                                              innerHolder.genage.setText("**********");
                                                          } else {
                                                              model.setBirthDate(newBirthDate);
                                                              innerHolder.genage.setText("********** / BD - " + model.getBirthDate());
                                                          }
                                                      }

                                                      /*if (newBirthDate.equalsIgnoreCase("")) {
                                                          innerHolder.genage.setText(model.getMobileNo());
                                                      } else {
                                                          model.setBirthDate(newBirthDate);
                                                          innerHolder.genage.setText(model.getMobileNo() + " / BD - " + model.getBirthDate());
                                                      }*/

                                                      if (model.getVoterId() == 0) {
                                                          innerHolder.socdet.setText("AC No-" + model.getAcNo() + " / Non-Voter");
                                                      } else {
                                                          innerHolder.socdet.setText("AC No-" + model.getAcNo() + " / VID-" + model.getVoterId() + " / LN-" + model.getListNo());
                                                      }

                                                      innerHolder.edit.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              //Log.d("new bd3------>",model.getBirthDate());
                                                              EditDialog(model.getVoterCd(), model.getMobileNo(), model.getAcNo(), model.getBirthDate(), model.getTablename(), model.getVoterId(), model.getName(), model.getMiddleName(), model.getSurname(), model.getQCRemark());
                                                              //Toast.makeText(SocietyWIseVoterDet.this, "edit"+model.getFullName(), Toast.LENGTH_SHORT).show();
                                                          }
                                                      });

                                                      innerHolder.callingbtn.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              filename = "SurveyQCRecord_" + model.getVoterCd() + "_" + sharedelectionname + "_" + model.getMobileNo();
                                                              StartCallRecording(filename);
                                                              innerHolder.llt2.setBackgroundColor(Color.parseColor("#FFDADA"));
                                                              sendSMSWhatsApp.callMobNo(SocietyWIseVoterDet.this, model.getMobileNo());
                                                              /*Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                                              callIntent.setData(Uri.parse("tel:9322554923"));
                                                              callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                              if (ActivityCompat.checkSelfPermission(SocietyWIseVoterDet.this, Manifest.permission.CALL_PHONE)
                                                                      != PackageManager.PERMISSION_GRANTED) {
                                                                  ActivityCompat.requestPermissions(SocietyWIseVoterDet.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                                                              }
                                                              SocietyWIseVoterDet.this.startActivity(callIntent);*/
                                                              Toast.makeText(SocietyWIseVoterDet.this, "calling", Toast.LENGTH_SHORT).show();
                                                          }
                                                      });

                                                      if (model.getSMSFlag().equalsIgnoreCase("4") || model.getSMSFlag().equalsIgnoreCase("")) {
                                                          innerHolder.llt.setBackgroundColor(Color.parseColor("#FF5555"));
                                                      } else {
                                                          innerHolder.llt.setBackgroundColor(Color.parseColor("#00C00C"));
                                                      }

                                                      innerHolder.itemView.setTag(i);
                                                      innerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              //StopCallRecording();

                                                              UpdateStatusDialog(model.getVoterCd(), model.getQCResponse(), model.getAcNo(), model.getMobileNo());
                                                          }
                                                      });
                                                  }

                                                  public void EditDialog(final String voterCd, String mobileNo, final int acNo, final String newBirthDate, final String tablename, final int voterid, final String voterfname, final String votermname, final String voterlname, final String remarktxt) {
                                                      final Dialog dialog = new Dialog(SocietyWIseVoterDet.this);
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
                                                                  fromDatePickerDialog = new DatePickerDialog(SocietyWIseVoterDet.this,
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
                                                              AlertDialog.Builder builder = new AlertDialog.Builder(SocietyWIseVoterDet.this);
                                                              builder.setCancelable(false);
                                                              builder.setTitle("Update ?");
                                                              builder.setMessage("Are you sure want to update ?");
                                                              builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                                                  @Override
                                                                  public void onClick(DialogInterface dialogx, int which) {
                                                                      if (voterid != 0) {
                                                                          updateBDAndMobNo(dialog, birthdate.getText().toString().trim(), voterCd, acNo, mobilenumber.getText().toString(), tablename, voterfname, votermname, voterlname, remark.getText().toString());
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

                                                                              updateBDAndMobNo(dialog, birthdate.getText().toString().trim(), voterCd, acNo, mobilenumber.getText().toString(), tablename, n, m, l, remark.getText().toString());
                                                                          }

                                    /*else if(m.isEmpty()){
                                        mname.setError("Voter middle name cannot be empty !");
                                        mname.requestFocus();
                                    }*/
                                                                      }

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

                                                  public void UpdateStatusDialog(final String voterCd, String qcstatus, final int acNo, final String mobileNo) {
                                                      final Dialog dialog = new Dialog(SocietyWIseVoterDet.this);
                                                      dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                                      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                      dialog.setCancelable(false);
                                                      dialog.setContentView(R.layout.edit_popup);
                                                      Button close = dialog.findViewById(R.id.closebtn);
                                                      Button update = dialog.findViewById(R.id.updatebtn);
                                                      final Spinner spinresponse1 = dialog.findViewById(R.id.edtspnsmsresponse);
                                                      ArrayAdapter<String> adapterx = new ArrayAdapter<String>(SocietyWIseVoterDet.this, android.R.layout.simple_spinner_item, DBConnIP.arrayListToDialog);
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
                                                              AlertDialog.Builder builder = new AlertDialog.Builder(SocietyWIseVoterDet.this);
                                                              builder.setCancelable(false);
                                                              builder.setTitle("Update ?");
                                                              builder.setMessage("Are you sure want to update ?");
                                                              builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                                                  @Override
                                                                  public void onClick(DialogInterface dialogx, int which) {
                                                                      updateQCResponse(dialog, spinresponse1.getSelectedItem().toString().trim(), voterCd, acNo, mobileNo);
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
                                                      return voterDetailedData.size();
                                                  }

                                                  class InnerHolder extends RecyclerView.ViewHolder {
                                                      TextView votername, genage, socdet, responsedet;
                                                      LinearLayout llt, llt2;
                                                      ImageButton edit, callingbtn;

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
                                                      }
                                                  }
                                              }
        );
    }


    private void callApi() {
        mainlist.clear();
        final ProgressDialog progressBar = new ProgressDialog(SocietyWIseVoterDet.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();
        Call<MainResponse> call1 = RetrofitClient
                .getInstance().getApi().getRoomWiseDetailedListQC(intfrm, intto, sharedelectionname, intsite, intsubloccd, intcalling);
        call1.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call1, Response<MainResponse> response) {
                MainResponse res = response.body();
                progressBar.dismiss();
                if (res.getRoomWiseDetailedListPojo().size() > 0) {
                    mainlist = res.getRoomWiseDetailedListPojo();
                    roomcount.setText("Total Room - " + mainlist.size());
                    surveyroomlist.getAdapter().notifyDataSetChanged();
                } else {
                    roomcount.setText("Total Room - " + mainlist.size());
                    Toast.makeText(SocietyWIseVoterDet.this, "No Records Exists For Selected Criteria!", Toast.LENGTH_LONG).show();
                    surveyroomlist.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(SocietyWIseVoterDet.this, "Failed to fetch data !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateQCResponse(final Dialog dialog, final String selspinresponse, String selvotercd, final int ac_no, final String mobileno) {
        //DBConnIP.updateQCResponse_status = false;
        StopCallRecording();
        deleteFileName = "";
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SurveyCallRecords/SurveyQCRecord_" + selvotercd + "_" + sharedelectionname + "_" + mobileno+".m4a";
        File newfile = new File(filePath);
        if(newfile.exists()){
            Log.d("file exists--->","true");
            deleteFileName = filePath;

        }

        new UploadFileToServer().execute(selvotercd, mobileno, sharedelectionname, Integer.toString(ac_no), intsite,
                SharedPrefManager.getInstance(SocietyWIseVoterDet.this).username(), selspinresponse, filePath);
        dialog.dismiss();
        /*final ProgressDialog progressBar1 = new ProgressDialog(SocietyWIseVoterDet.this);
        progressBar1.setCancelable(false);
        progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar1.setMessage("Please wait...");
        progressBar1.show();
        Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().updateCallingQC(selvotercd, mobileno, sharedelectionname, intsite, ac_no, SharedPrefManager.getInstance(SocietyWIseVoterDet.this).username(), selspinresponse);
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
                                Toast.makeText(SocietyWIseVoterDet.this, "file deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SocietyWIseVoterDet.this, "file not deleted", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SocietyWIseVoterDet.this, "file does not exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SocietyWIseVoterDet.this, "file path is empty", Toast.LENGTH_SHORT).show();
                    }
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SocietyWIseVoterDet.this);
                    builder1.setCancelable(false);
                    builder1.setTitle("Alert ?");
                    builder1.setMessage(res.getErrormsg());
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogx, int which) {
                            callApi();
                        }
                    });
                    AlertDialog dialog3 = builder1.create();
                    dialog3.show();
                    dialog.dismiss();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SocietyWIseVoterDet.this);
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
                Toast.makeText(SocietyWIseVoterDet.this, "Failed to update !", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    public void updateBDAndMobNo(final Dialog dialog, final String birthdate, String selvotercd, final int ac_no, final String mobileno, String tablename, final String voterfname, final String votermname, final String voterlname, final String remark) {
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

        final ProgressDialog progressBar1 = new ProgressDialog(SocietyWIseVoterDet.this);
        progressBar1.setCancelable(false);
        progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar1.setMessage("Please wait...");
        progressBar1.show();
        Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().updateBirthDateQC(selvotercd, mobileno, sharedelectionname, intsite, ac_no, newBirthDate1, newBirthDate2, SharedPrefManager.getInstance(SocietyWIseVoterDet.this).username(), tablename, voterfname, votermname, voterlname, remark);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                progressBar1.dismiss();
                newBirthDate1 = "";
                newBirthDate2 = "";
                if (!res.isError()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SocietyWIseVoterDet.this);
                    builder1.setCancelable(false);
                    builder1.setTitle("Alert ?");
                    builder1.setMessage(res.getErrormsg());
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogx, int which) {
                            callApi();
                        }
                    });
                    AlertDialog dialog3 = builder1.create();
                    dialog3.show();
                    dialog.dismiss();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SocietyWIseVoterDet.this);
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
                Toast.makeText(SocietyWIseVoterDet.this, "Failed to update !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void StartCallRecording(String filename) {
        if (checkFolder()) {

            callRecordSurvey = new CallRecord.Builder(SocietyWIseVoterDet.this)
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

    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            /*progressBar = new ProgressDialog(SocietyWIseVoterDet.this);
            progressBar.setMessage("Uploading data to server");
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setIndeterminate(true);
            progressBar.setProgress(0);
            progressBar.show();*/
            progressBar = new ProgressDialog(SocietyWIseVoterDet.this);
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
            progressBar.setMessage("Uploading data to server.....("+progress[0]+" %)");
            // updating percentage value
            //txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(String... params) {
            return uploadFile(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
        }

        @SuppressWarnings("deprecation")
        private String uploadFile(String votercd, String mobileno, String elecname, String acno, String intsite, String username, String callingresponse, String filePath) {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(RetrofitClient.BASE_URL + "updateCallingQC_WithAudio.php");

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
                entity.addPart("mobileno", new StringBody(mobileno));
                entity.addPart("elecname", new StringBody(elecname));
                entity.addPart("acno", new StringBody(acno));
                entity.addPart("sitename", new StringBody(intsite));
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
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("----->",result);
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
                                    Toast.makeText(SocietyWIseVoterDet.this, "file deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SocietyWIseVoterDet.this, "file not deleted", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SocietyWIseVoterDet.this, "file does not exists", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SocietyWIseVoterDet.this, "file path is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SocietyWIseVoterDet.this);
                    builder1.setCancelable(false);
                    builder1.setTitle("Alert ?");
                    builder1.setMessage(jsonObject.getString("errormsg"));
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogx, int which) {
                            callApi();
                        }
                    });
                    AlertDialog dialog3 = builder1.create();
                    dialog3.show();
                    //dialog.dismiss();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SocietyWIseVoterDet.this);
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
