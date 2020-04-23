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
import android.os.AsyncTask;
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
import com.ornettech.qcandbirthdaywishes.adapter.DefaultResponse;
import com.ornettech.qcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.qcandbirthdaywishes.call.CallRecord;
import com.ornettech.qcandbirthdaywishes.model.ClientCallResponse;
import com.ornettech.qcandbirthdaywishes.model.ClientListItem;
import com.ornettech.qcandbirthdaywishes.model.ResponseListPojoItem;
import com.ornettech.qcandbirthdaywishes.model.SiteAndQCResponse;
import com.ornettech.qcandbirthdaywishes.model.SiteNameListPojoItem;
import com.ornettech.qcandbirthdaywishes.model.SocietyRoomWiseQCListPojoItem;
import com.ornettech.qcandbirthdaywishes.utility.AndroidMultiPartEntity;
import com.ornettech.qcandbirthdaywishes.utility.CheckConnection;
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
    long totalSize = 0;
    private DatePickerDialog fromDatePickerDialog;
    SendSMSWhatsApp sendSMSWhatsApp = new SendSMSWhatsApp();
    private SimpleDateFormat dateFormatter,parseDate;
    private ProgressDialog progressBar = null;
    String deleteFileName="";
    public List<ResponseListPojoItem> responselist = new ArrayList<>();
    String searchdate,message,contact_person,contact_person_phone;

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
                searchdate = newEdtFrom;
                message = spinmsgres.getSelectedItem().toString().trim();
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
                                                      View view = LayoutInflater.from(ClientCallingActivity.this).inflate(R.layout.adapter_client_call, viewGroup, false);
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

                                                      if(!model.getMoblieNoPerson1().equalsIgnoreCase("")) {
                                                          innerHolder.llt3.setVisibility(View.VISIBLE);
                                                          innerHolder.contactp1.setText(model.getContactPerson1() + "~" + model.getMoblieNoPerson1());
                                                      }else{
                                                          innerHolder.llt3.setVisibility(View.GONE);
                                                      }

                                                      if(!model.getMoblieNoPerson2().equalsIgnoreCase("")) {
                                                          innerHolder.llt4.setVisibility(View.VISIBLE);
                                                          innerHolder.contactp2.setText(model.getContactPerson2() + "~" + model.getMoblieNoPerson2());
                                                      }else{
                                                          innerHolder.llt4.setVisibility(View.GONE);
                                                      }

                                                      innerHolder.genage.setText(Transalator.englishDigitToMarathiDigit(model.getMobileNo1()));
                                                      innerHolder.socdet.setText("पद -" + model.getDesignationNameMar() + " ("+model.getParty()+")");


                                                      innerHolder.edit.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              //Log.d("new bd3------>",model.getBirthDate());
                                                              EditDialog(model.getCorporatorCd(), model.getSelecteddate(), model.getRemark1(), model.getRemark2(), model.getRemark3());
                                                              //Toast.makeText(ClientCallingActivity.this, "edit"+model.getFullName(), Toast.LENGTH_SHORT).show();
                                                          }
                                                      });

                                                      innerHolder.callingbtn.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              filename = "ClientQCRecord_" + model.getClientCd() + "_" + sharedelectionname + "_" + model.getMobileNo1();
                                                              StartCallRecording(filename);
                                                              innerHolder.llt2.setBackgroundColor(Color.parseColor("#FFDADA"));
                                                              contact_person = model.getCorporatorName();
                                                              contact_person_phone = model.getMobileNo1();
                                                              sendSMSWhatsApp.callMobNo(ClientCallingActivity.this, model.getMobileNo1());
                                                              Toast.makeText(ClientCallingActivity.this, "calling", Toast.LENGTH_SHORT).show();
                                                          }
                                                      });

                                                      innerHolder.callingbtn1.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              filename = "ClientQCRecord_" + model.getClientCd() + "_" + sharedelectionname + "_" + model.getMobileNo1();
                                                              StartCallRecording(filename);
                                                              innerHolder.llt3.setBackgroundColor(Color.parseColor("#FFDADA"));
                                                              contact_person = model.getContactPerson1();
                                                              contact_person_phone = model.getMoblieNoPerson1();
                                                              sendSMSWhatsApp.callMobNo(ClientCallingActivity.this, model.getMoblieNoPerson1());
                                                              Toast.makeText(ClientCallingActivity.this, "calling", Toast.LENGTH_SHORT).show();
                                                          }
                                                      });

                                                      innerHolder.callingbtn2.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              filename = "ClientQCRecord_" + model.getClientCd() + "_" + sharedelectionname + "_" + model.getMobileNo1();
                                                              StartCallRecording(filename);
                                                              innerHolder.llt4.setBackgroundColor(Color.parseColor("#FFDADA"));
                                                              contact_person = model.getContactPerson2();
                                                              contact_person_phone = model.getMoblieNoPerson2();
                                                              sendSMSWhatsApp.callMobNo(ClientCallingActivity.this, model.getMoblieNoPerson2());
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
                                                              List<String> arrayList2 = new ArrayList<>();
                                                              arrayList2.clear();
                                                              arrayList2.add("Select contact person name");
                                                              arrayList2.add(model.getCorporatorName() + "~" + model.getMobileNo1());

                                                              if(!model.getMoblieNoPerson1().equalsIgnoreCase("")) {
                                                                  arrayList2.add(model.getContactPerson1() + "~" + model.getMoblieNoPerson1());
                                                              }

                                                              if(!model.getMoblieNoPerson2().equalsIgnoreCase("")) {
                                                                  arrayList2.add(model.getContactPerson2() + "~" + model.getMoblieNoPerson2());
                                                              }

                                                              UpdateStatusDialog(model.getQCResponse(), model.getCorporatorCd(), model.getSelecteddate(), model.getWardNo(), model.getClientCd(), model.getMobileNo1(), arrayList2);
                                                          }
                                                      });
                                                  }

                                                  public void EditDialog(final int corporatorcd, final String date, final String remark1, final String remark2, final String remark3) {
                                                      final Dialog dialog = new Dialog(ClientCallingActivity.this);
                                                      dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                                      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                      dialog.setCancelable(false);
                                                      dialog.setContentView(R.layout.remark_edit_dialog);
                                                      Button close = dialog.findViewById(R.id.closebtn);
                                                      Button update = dialog.findViewById(R.id.updatebtn);
                                                      final EditText r1 = dialog.findViewById(R.id.remark1);
                                                      final EditText r2 = dialog.findViewById(R.id.remark2);
                                                      final EditText r3 = dialog.findViewById(R.id.remark3);
                                                      r1.setText(remark1);
                                                      r2.setText(remark2);
                                                      r3.setText(remark3);

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

                                                                    updateRemark(dialog, corporatorcd, date, r1.getText().toString(), r2.getText().toString(), r3.getText().toString());

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

                                                  public void UpdateStatusDialog(final String qcstatus, final int corporatorcd, final String date, final int wardno, final int clientcd, final String mobile, final List<String> peoples) {
                                                      final Dialog dialog = new Dialog(ClientCallingActivity.this);
                                                      dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                                      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                      dialog.setCancelable(false);
                                                      dialog.setContentView(R.layout.edit_popup_client);
                                                      Button close = dialog.findViewById(R.id.closebtn);
                                                      Button update = dialog.findViewById(R.id.updatebtn);
                                                      final Spinner spinresponse1 = dialog.findViewById(R.id.edtspnsmsresponse);
                                                      final Spinner calledto = dialog.findViewById(R.id.calledto);
                                                      ArrayAdapter<String> adapterx = new ArrayAdapter<String>(ClientCallingActivity.this, android.R.layout.simple_spinner_item, DBConnIP.arrayListToDialog);
                                                      adapterx.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                      spinresponse1.setAdapter(adapterx);
                                                      if (qcstatus != null && !qcstatus.equalsIgnoreCase("")) {
                                                          int spinnerPosition = adapterx.getPosition(qcstatus);
                                                          spinresponse1.setSelection(spinnerPosition);
                                                      }
                                                      ArrayAdapter<String> adaptery = new ArrayAdapter<String>(ClientCallingActivity.this, android.R.layout.simple_spinner_item, peoples);
                                                      adaptery.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                      calledto.setAdapter(adaptery);

                                                      String val = contact_person+"~"+contact_person_phone;

                                                      if (!val.equalsIgnoreCase("~") && val.length()>11) {
                                                          int spinnerPosition2 = adaptery.getPosition(val);
                                                          calledto.setSelection(spinnerPosition2);
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
                                                              if(calledto.getSelectedItem().toString().trim().equalsIgnoreCase("Select contact person name")){
                                                                  Toast.makeText(ClientCallingActivity.this, "Please select contact person name !", Toast.LENGTH_SHORT).show();
                                                              }else{
                                                                  AlertDialog.Builder builder = new AlertDialog.Builder(ClientCallingActivity.this);
                                                                  builder.setCancelable(false);
                                                                  builder.setTitle("Update ?");
                                                                  builder.setMessage("Are you sure want to update ?");
                                                                  builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                                                      @Override
                                                                      public void onClick(DialogInterface dialogx, int which) {
                                                                          updateQCResponse(dialog, spinresponse1.getSelectedItem().toString().trim(), corporatorcd, date, wardno, clientcd, mobile, calledto.getSelectedItem().toString().trim());
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
                                                      TextView votername, genage, socdet, responsedet, contactp1, contactp2;
                                                      LinearLayout llt, llt2, llt3, llt4;
                                                      ImageButton edit, callingbtn, callingbtn1, callingbtn2;

                                                      public InnerHolder(@NonNull View itemView) {
                                                          super(itemView);
                                                          llt = itemView.findViewById(R.id.llt);
                                                          llt2 = itemView.findViewById(R.id.llt2);
                                                          llt3 = itemView.findViewById(R.id.llt3);
                                                          llt4 = itemView.findViewById(R.id.llt4);
                                                          contactp1 = itemView.findViewById(R.id.contactp1);
                                                          contactp2 = itemView.findViewById(R.id.contactp2);
                                                          votername = itemView.findViewById(R.id.votername);
                                                          genage = itemView.findViewById(R.id.genage);
                                                          socdet = itemView.findViewById(R.id.socdet);
                                                          responsedet = itemView.findViewById(R.id.responsedet);
                                                          edit = itemView.findViewById(R.id.edit);
                                                          callingbtn = itemView.findViewById(R.id.callingbtn);
                                                          callingbtn1 = itemView.findViewById(R.id.callingbtn1);
                                                          callingbtn2 = itemView.findViewById(R.id.callingbtn2);
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

    public void updateRemark(final Dialog dialog, final int corporatorcd, final String date, final String r1, final String r2, final String r3) {

        final ProgressDialog progressBar1 = new ProgressDialog(ClientCallingActivity.this);
        progressBar1.setCancelable(false);
        progressBar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar1.setMessage("Please wait...");
        progressBar1.show();
        Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().updateRemark(corporatorcd, date, sharedelectionname, r1, r2, r3, SharedPrefManager.getInstance(ClientCallingActivity.this).username());
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                progressBar1.dismiss();
                if (!res.isError()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ClientCallingActivity.this);
                    builder1.setCancelable(false);
                    builder1.setTitle("Alert ?");
                    builder1.setMessage(res.getErrormsg());
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogx, int which) {
                            callApi(searchdate, ClientCallingActivity.this, message);
                        }
                    });
                    AlertDialog dialog3 = builder1.create();
                    dialog3.show();
                    dialog.dismiss();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ClientCallingActivity.this);
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
                Toast.makeText(ClientCallingActivity.this, "Failed to update !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateQCResponse(final Dialog dialog, final String selspinresponse, final int corporatorcd, final String date, final int wardno, final int clientcd, final String mobile, final String ContactPersonNameAndMobileNumber) {
        StopCallRecording();
        deleteFileName = "";
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SurveyCallRecords/ClientQCRecord_" + clientcd + "_" + sharedelectionname + "_" + mobile+".m4a";
        File newfile = new File(filePath);
        if(newfile.exists()){
            Log.d("file exists--->","true");
            deleteFileName = filePath;

        }

        new UploadFileToServer().execute(selspinresponse, Integer.toString(corporatorcd), sharedelectionname, date, Integer.toString(wardno),
                SharedPrefManager.getInstance(ClientCallingActivity.this).username(), filePath, ContactPersonNameAndMobileNumber);
        dialog.dismiss();

    }

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
            progressBar = new ProgressDialog(ClientCallingActivity.this);
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
        private String uploadFile(String callingresponse, String corporatorcd, String elecname, String date, String wardno, String username, String filePath, String ContactPersonNameAndMobileNo) {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(RetrofitClient.BASE_URL + "updateClientCallingQC.php");

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
                entity.addPart("corporatorcd", new StringBody(corporatorcd));
                entity.addPart("date", new StringBody(date));
                entity.addPart("elecname", new StringBody(elecname));
                entity.addPart("wardno", new StringBody(wardno));
                entity.addPart("username", new StringBody(username));
                entity.addPart("callstatus", new StringBody(callingresponse));
                entity.addPart("calledto", new StringBody(ContactPersonNameAndMobileNo));

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
                    if(jsonObject.getString("errormsg").equalsIgnoreCase("Entry Update Successfully.")) {
                        if (deleteFileName != null && !deleteFileName.equalsIgnoreCase("")) {
                            File fdelete = new File(deleteFileName);
                            if (fdelete.exists()) {
                                if (fdelete.delete()) {
                                    Toast.makeText(ClientCallingActivity.this, "file deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ClientCallingActivity.this, "file not deleted", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ClientCallingActivity.this, "file does not exists", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ClientCallingActivity.this, "file path is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ClientCallingActivity.this);
                    builder1.setCancelable(false);
                    builder1.setTitle("Alert ?");
                    builder1.setMessage(jsonObject.getString("errormsg"));
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogx, int which) {
                            callApi(searchdate, ClientCallingActivity.this, message);
                        }
                    });
                    AlertDialog dialog3 = builder1.create();
                    dialog3.show();
                    //dialog.dismiss();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ClientCallingActivity.this);
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

                contact_person = "";
                contact_person_phone = "";

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
