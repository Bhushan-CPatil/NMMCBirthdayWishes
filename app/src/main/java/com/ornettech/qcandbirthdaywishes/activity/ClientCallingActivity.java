package com.ornettech.qcandbirthdaywishes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.adapter.AdapterClientList;
import com.ornettech.qcandbirthdaywishes.adapter.Adapter_Soc_Room_Wise_List;
import com.ornettech.qcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.qcandbirthdaywishes.call.CallRecord;
import com.ornettech.qcandbirthdaywishes.model.ClientCallResponse;
import com.ornettech.qcandbirthdaywishes.model.ClientListItem;
import com.ornettech.qcandbirthdaywishes.model.ResponseListPojoItem;
import com.ornettech.qcandbirthdaywishes.model.SiteAndQCResponse;
import com.ornettech.qcandbirthdaywishes.model.SiteNameListPojoItem;
import com.ornettech.qcandbirthdaywishes.model.SocietyRoomWiseQCListPojoItem;
import com.ornettech.qcandbirthdaywishes.utility.CheckConnection;
import com.ornettech.qcandbirthdaywishes.utility.DBConnIP;
import com.ornettech.qcandbirthdaywishes.utility.OuterHolder;
import com.ornettech.qcandbirthdaywishes.utility.SendSMSWhatsApp;
import com.ornettech.qcandbirthdaywishes.utility.SharedPrefManager;
import com.ornettech.qcandbirthdaywishes.utility.Transalator;

import java.io.File;
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

public class ClientCallingActivity extends AppCompatActivity {

    EditText from;
    Button submit;
    public List<String> arrayList = new ArrayList<>();
    Spinner spinmsgres;
    private AdapterClientList adapterClientList;
    public List<ClientListItem> newlist = new ArrayList<>();
    public TextView soccount,roomcount;
    public String sharedelectionname,filename = "";
    public RecyclerView rcyclr_list;
    CallRecord callRecordSurvey = null;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    SendSMSWhatsApp sendSMSWhatsApp = new SendSMSWhatsApp();
    private SimpleDateFormat dateFormatter,parseDate;
    public List<ResponseListPojoItem> responselist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_calling);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ SharedPrefManager.getInstance(ClientCallingActivity.this).getElectionName()+" Clients</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        from =  findViewById(R.id.fromdate);
        rcyclr_list =  findViewById(R.id.corporatorslist);
        submit = findViewById(R.id.submit);
        spinmsgres= findViewById(R.id.spnsmsstatus);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        sharedelectionname = SharedPrefManager.getInstance(ClientCallingActivity.this).getElectionName();

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
                fromDatePickerDialog = new DatePickerDialog(ClientCallingActivity.this,
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
                if (new CheckConnection(ClientCallingActivity.this).isNetworkConnected()) {
                    submitMethod(ClientCallingActivity.this);
                }else {
                    Toast.makeText(ClientCallingActivity.this,
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
        responselist.clear();
        final ProgressDialog progressBar = new ProgressDialog(ClientCallingActivity.this);
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

                if(res.getResponseListPojo().size()>0) {
                    responselist = res.getResponseListPojo();
                    arrayList.add("ALL");
                    arrayList.add("Pending");
                    for (int i=0;i<responselist.size();i++){
                        arrayList.add(responselist.get(i).getQCResponse());
                        DBConnIP.arrayListToDialog.add(responselist.get(i).getQCResponse());
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ClientCallingActivity.this, android.R.layout.simple_spinner_item, arrayList);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinmsgres.setAdapter(adapter1);
                }else{
                    Toast.makeText(ClientCallingActivity.this, "No Site Exists !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SiteAndQCResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(ClientCallingActivity.this, "Failed to fetch data !", Toast.LENGTH_LONG).show();
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
        String designation = SharedPrefManager.getInstance(ClientCallingActivity.this).designation();


        if(designation.equalsIgnoreCase("Software Developer") || designation.equalsIgnoreCase("Sr Manager") || designation.equalsIgnoreCase("Admin and Other") ||
                designation.equalsIgnoreCase("CEO/Director") || designation.equalsIgnoreCase("Manager") ||  designation.equalsIgnoreCase("HR")){
            report2.setVisible(true);
        }else{
            report2.setVisible(false);
        }

        report.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(ClientCallingActivity.this,QC_Calling_Report.class));
                return false;
            }
        });

        report2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(ClientCallingActivity.this,QCResponseWiseReportActivity.class));
                return false;
            }
        });

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharedPrefManager.getInstance(ClientCallingActivity.this).clear();
                Intent intent = new Intent(ClientCallingActivity.this, LoginAcivity.class);
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
        if(responselist.size() > 0){
            String edtfrom = from.getText().toString();


            parseDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date startDate;
            Date endDate;

            try {
                startDate = dateFormatter.parse(edtfrom);
                String newEdtFrom = parseDate.format(startDate);

                callApi(newEdtFrom,context, spinmsgres.getSelectedItem().toString().trim());

            }catch (Exception e){
                e.toString();
            }
        }else{
            getSiteAndResponse();
            Toast.makeText(context, "Loading site name list please wait !", Toast.LENGTH_SHORT).show();
        }

    }

    private void callApi(final String newEdtFrom, final Context context, final String msgres) {

        newlist.clear();
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();
        Call<ClientCallResponse> call1 = RetrofitClient
                .getInstance().getApi().getClientListForQC(newEdtFrom, sharedelectionname, msgres);
        call1.enqueue(new Callback<ClientCallResponse>() {
            @Override
            public void onResponse(Call<ClientCallResponse> call1, Response<ClientCallResponse> response) {
                ClientCallResponse res = response.body();
                progressBar.dismiss();
                if(res.getClientList().size()>0) {
                    newlist = res.getClientList();
                    setInnerAdapter(res.getClientList());
                }else{
                    Toast.makeText(context, "No Records Exists For Selected Criteria!", Toast.LENGTH_LONG).show();
                    setInnerAdapter(res.getClientList());
                }
            }

            @Override
            public void onFailure(Call<ClientCallResponse> call1, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(context, "Failed to fetch data !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setInnerAdapter(final List<ClientListItem> clientListItems) {
        rcyclr_list.setNestedScrollingEnabled(false);
        rcyclr_list.setLayoutManager(new LinearLayoutManager(ClientCallingActivity.this));
        rcyclr_list.setAdapter(new RecyclerView.Adapter() {
                                                  @NonNull
                                                  @Override
                                                  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                                      View view = LayoutInflater.from(ClientCallingActivity.this).inflate(R.layout.adapter_survey_voter, viewGroup, false);
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
                                                      final ClientListItem model = clientListItems.get(i);
                                                      String res = model.getQCResponse().equals("") ? "Pending" : model.getQCResponse();
                                                      if (!res.equalsIgnoreCase("Pending")) {
                                                          innerHolder.responsedet.setText(Html.fromHtml("<font color='#464545'>QC Status - " + res + "</font>"));
                                                      } else {
                                                          innerHolder.responsedet.setText("QC Status - " + res);
                                                      }
                                                      //innerHolder.responsedet.setText("QC Status - "+res);

                                                      innerHolder.votername.setText(model.getCorporatorNameMar() + " - ( वॉर्ड - " + Transalator.englishDigitToMarathiDigit(model.getWardNo()+"") + ")");
                                                      innerHolder.genage.setText(Transalator.englishDigitToMarathiDigit(model.getMobileNo1()));
                                                      innerHolder.socdet.setText("पद -" + model.getDesignationNameMar() + " ("+model.getParty()+")");


                                                      innerHolder.edit.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              //Log.d("new bd3------>",model.getBirthDate());
                                                              //todo EditDialog(model.getMobileNo1(), model.getAcNo(), model.getRemark1(), model.getRemark2(), model.getRemark3());
                                                              //Toast.makeText(ClientCallingActivity.this, "edit"+model.getFullName(), Toast.LENGTH_SHORT).show();
                                                          }
                                                      });

                                                      innerHolder.callingbtn.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              filename = "ClientQCRecord_" + model.getClientCd() + "_" + sharedelectionname + "_" + model.getMobileNo1();
                                                              StartCallRecording(filename);
                                                              innerHolder.llt2.setBackgroundColor(Color.parseColor("#FFDADA"));
                                                              sendSMSWhatsApp.callMobNo(ClientCallingActivity.this, model.getMobileNo1());
                                                              /*Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                                              callIntent.setData(Uri.parse("tel:9322554923"));
                                                              callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                              if (ActivityCompat.checkSelfPermission(ClientCallingActivity.this, Manifest.permission.CALL_PHONE)
                                                                      != PackageManager.PERMISSION_GRANTED) {
                                                                  ActivityCompat.requestPermissions(ClientCallingActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                                                              }
                                                              ClientCallingActivity.this.startActivity(callIntent);*/
                                                              Toast.makeText(ClientCallingActivity.this, "calling", Toast.LENGTH_SHORT).show();
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

                                                              //todo UpdateStatusDialog(model.getQCResponse(),  model.getMobileNo1(), model.getCorporatorCd(), model.getClientQCCd());
                                                          }
                                                      });
                                                  }

                                                  public void EditDialog(final String voterCd, String mobileNo, final int acNo, final String newBirthDate, final String tablename, final int voterid, final String voterfname, final String votermname, final String voterlname, final String remarktxt) {
                                                      final Dialog dialog = new Dialog(ClientCallingActivity.this);
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
                                                                  fromDatePickerDialog = new DatePickerDialog(ClientCallingActivity.this,
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
                                                              AlertDialog.Builder builder = new AlertDialog.Builder(ClientCallingActivity.this);
                                                              builder.setCancelable(false);
                                                              builder.setTitle("Update ?");
                                                              builder.setMessage("Are you sure want to update ?");
                                                              builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                                                  @Override
                                                                  public void onClick(DialogInterface dialogx, int which) {
                                                                      if (voterid != 0) {
                                                                          // todo updateBDAndMobNo(dialog, birthdate.getText().toString().trim(), voterCd, acNo, mobilenumber.getText().toString(), tablename, voterfname, votermname, voterlname, remark.getText().toString());
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

                                                                              //todo updateBDAndMobNo(dialog, birthdate.getText().toString().trim(), voterCd, acNo, mobilenumber.getText().toString(), tablename, n, m, l, remark.getText().toString());
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
                                                      final Dialog dialog = new Dialog(ClientCallingActivity.this);
                                                      dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                                      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                      dialog.setCancelable(false);
                                                      dialog.setContentView(R.layout.edit_popup);
                                                      Button close = dialog.findViewById(R.id.closebtn);
                                                      Button update = dialog.findViewById(R.id.updatebtn);
                                                      final Spinner spinresponse1 = dialog.findViewById(R.id.edtspnsmsresponse);
                                                      ArrayAdapter<String> adapterx = new ArrayAdapter<String>(ClientCallingActivity.this, android.R.layout.simple_spinner_item, DBConnIP.arrayListToDialog);
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
                                                              AlertDialog.Builder builder = new AlertDialog.Builder(ClientCallingActivity.this);
                                                              builder.setCancelable(false);
                                                              builder.setTitle("Update ?");
                                                              builder.setMessage("Are you sure want to update ?");
                                                              builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                                                  @Override
                                                                  public void onClick(DialogInterface dialogx, int which) {
                                                                      //todo updateQCResponse(dialog, spinresponse1.getSelectedItem().toString().trim(), voterCd, acNo, mobileNo);
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
                                                      return clientListItems.size();
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

    public void StartCallRecording(String filename) {
        if (checkFolder()) {

            callRecordSurvey = new CallRecord.Builder(ClientCallingActivity.this)
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

}
