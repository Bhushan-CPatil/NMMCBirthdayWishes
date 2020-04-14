package com.ornettech.qcandbirthdaywishes.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.adapter.DefaultResponse;
import com.ornettech.qcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.qcandbirthdaywishes.model.ElectionListItem;
import com.ornettech.qcandbirthdaywishes.model.SpinnerResponse;
import com.ornettech.qcandbirthdaywishes.utility.CheckConnection;
import com.ornettech.qcandbirthdaywishes.utility.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    ProgressDialog mProgressDialog;
    InitializeDbTask initializeDbTask;
    private boolean doubleBackToExitPressedOnce = false;
    TextView logout, birthdaytxt, qctxt, mqctxt, loogedinuser, imageupload, iconnect, clientcall, karhitcall;
    TextView round1, round2, round3;
    public LinearLayout activity_splash;
    public List<ElectionListItem> electionlist = new ArrayList<>();
    public List<String> arrayList = new ArrayList<>();
    //CallRecord callRecordx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.hide();
        }

        logout = findViewById(R.id.logout);
        birthdaytxt = findViewById(R.id.birthdaytxt);
        loogedinuser = findViewById(R.id.loogedinuser);
        qctxt = findViewById(R.id.qctxt);
        mqctxt = findViewById(R.id.mqctxt);
        imageupload = findViewById(R.id.imageupload);
        iconnect = findViewById(R.id.iconnect);
        round1 = findViewById(R.id.round1);
        round2 = findViewById(R.id.round2);
        round3 = findViewById(R.id.round3);
        clientcall = findViewById(R.id.clientcall);
        karhitcall = findViewById(R.id.karhitcall);

        loogedinuser.setText("Welcome " + SharedPrefManager.getInstance(SplashActivity.this).name() + ".");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(SplashActivity.this).clear();
                Intent intent = new Intent(SplashActivity.this, LoginAcivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        birthdaytxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String filename = "Record_" + new SimpleDateFormat("ddMMyyyyHHmmss", Locale.US).format(new Date());
                if (checkFolder()) {

                    callRecordx = new CallRecord.Builder(SplashActivity.this)
                            .setRecordFileName(filename)
                            .setRecordDirName("QCCallRecordsX")
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
                callRecordx.startCallReceiver();
                //dialMobNo(MainActivity.this);
                Log.d("onclick start---->", "creation and setup");
                Toast.makeText(SplashActivity.this, "onclick start", Toast.LENGTH_SHORT).show();*/
                //Toast.makeText(SplashActivity.this, "Under Development", Toast.LENGTH_SHORT).show();
                /*try {
                    progressBar("Please wait...");
                } catch (Exception e) {
                    e.toString();
                }*/

                if (new CheckConnection(SplashActivity.this).isNetworkConnected()) {
                    if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Toast.makeText(SplashActivity.this, "Grant Calling Permission First !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (arrayList.size() > 0) {
                            startActivity(new Intent(SplashActivity.this, QCBirthDayActivity.class));
                        } else {
                            Toast.makeText(SplashActivity.this, "List is empty ! Please wait reloading...", Toast.LENGTH_SHORT).show();
                            checkAndCallApi();
                        }
                    }
                } else {
                    Toast.makeText(SplashActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        qctxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callRecordx.stopCallReceiver();
                if (new CheckConnection(SplashActivity.this).isNetworkConnected()) {
                    if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Toast.makeText(SplashActivity.this, "Grant Calling Permission First !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (arrayList.size() > 0) {
                            openElectionPopup("QC");
                        } else {
                            Toast.makeText(SplashActivity.this, "List is empty ! Please wait reloading...", Toast.LENGTH_SHORT).show();
                            checkAndCallApi();
                        }
                        //startActivity(new Intent(SplashActivity.this, SocietyListOfQC.class));
                    }
                } else {
                    Toast.makeText(SplashActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        mqctxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callRecordx.stopCallReceiver();
                if (new CheckConnection(SplashActivity.this).isNetworkConnected()) {
                    if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Toast.makeText(SplashActivity.this, "Grant Calling Permission First !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (arrayList.size() > 0) {
                            openElectionPopup("MQC");
                        } else {
                            Toast.makeText(SplashActivity.this, "List is empty ! Please wait reloading...", Toast.LENGTH_SHORT).show();
                            checkAndCallApi();
                        }
                        //startActivity(new Intent(SplashActivity.this, SocietyListOfQC.class));
                    }
                } else {
                    Toast.makeText(SplashActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        iconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callRecordx.stopCallReceiver();
                if (new CheckConnection(SplashActivity.this).isNetworkConnected()) {
                    if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Toast.makeText(SplashActivity.this, "Grant Calling Permission First !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (arrayList.size() > 0) {
                            openElectionPopup("iconnect");
                        } else {
                            Toast.makeText(SplashActivity.this, "List is empty ! Please wait reloading...", Toast.LENGTH_SHORT).show();
                            checkAndCallApi();
                        }
                        //startActivity(new Intent(SplashActivity.this, SocietyListOfQC.class));
                    }
                } else {
                    Toast.makeText(SplashActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        clientcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callRecordx.stopCallReceiver();
                if (new CheckConnection(SplashActivity.this).isNetworkConnected()) {
                    if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Toast.makeText(SplashActivity.this, "Grant Calling Permission First !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (SharedPrefManager.getInstance(SplashActivity.this).designation().equalsIgnoreCase("Review") ||
                                SharedPrefManager.getInstance(SplashActivity.this).designation().equalsIgnoreCase("Admin and Other") ||
                                SharedPrefManager.getInstance(SplashActivity.this).designation().equalsIgnoreCase("Manager") ||
                                SharedPrefManager.getInstance(SplashActivity.this).designation().equalsIgnoreCase("Software Developer") ||
                                SharedPrefManager.getInstance(SplashActivity.this).designation().equalsIgnoreCase("CEO/Director") ||
                                SharedPrefManager.getInstance(SplashActivity.this).designation().equalsIgnoreCase("Sr Manager")) {
                            if (arrayList.size() > 0) {
                                openElectionPopup("ccalling");
                            } else {
                                Toast.makeText(SplashActivity.this, "List is empty ! Please wait reloading...", Toast.LENGTH_SHORT).show();
                                checkAndCallApi();
                            }
                        } else {
                            Toast.makeText(SplashActivity.this, "Access is denied !", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(SplashActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        karhitcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callRecordx.stopCallReceiver();
                if (new CheckConnection(SplashActivity.this).isNetworkConnected()) {
                    if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Toast.makeText(SplashActivity.this, "Grant Calling Permission First !", Toast.LENGTH_SHORT).show();
                    } else {

                        if (arrayList.size() > 0) {
                            openElectionPopup("KKHT");
                        } else {
                            Toast.makeText(SplashActivity.this, "List is empty ! Please wait reloading...", Toast.LENGTH_SHORT).show();
                            checkAndCallApi();
                        }
                    }
                } else {
                    Toast.makeText(SplashActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        round1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callRecordx.stopCallReceiver();
                if (new CheckConnection(SplashActivity.this).isNetworkConnected()) {
                    if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Toast.makeText(SplashActivity.this, "Grant Calling Permission First !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (arrayList.size() > 0) {
                            openElectionPopup("round1");
                        } else {
                            Toast.makeText(SplashActivity.this, "List is empty ! Please wait reloading...", Toast.LENGTH_SHORT).show();
                            checkAndCallApi();
                        }
                        //startActivity(new Intent(SplashActivity.this, SocietyListOfQC.class));
                    }
                } else {
                    Toast.makeText(SplashActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        round2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callRecordx.stopCallReceiver();
                if (new CheckConnection(SplashActivity.this).isNetworkConnected()) {
                    if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Toast.makeText(SplashActivity.this, "Grant Calling Permission First !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (arrayList.size() > 0) {
                            openElectionPopup("round2");
                        } else {
                            Toast.makeText(SplashActivity.this, "List is empty ! Please wait reloading...", Toast.LENGTH_SHORT).show();
                            checkAndCallApi();
                        }
                        //startActivity(new Intent(SplashActivity.this, SocietyListOfQC.class));
                    }
                } else {
                    Toast.makeText(SplashActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        round3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new CheckConnection(SplashActivity.this).isNetworkConnected()) {
                    if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Toast.makeText(SplashActivity.this, "Grant Calling Permission First !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (arrayList.size() > 0) {
                            openElectionPopup("round3");
                        } else {
                            Toast.makeText(SplashActivity.this, "List is empty ! Please wait reloading...", Toast.LENGTH_SHORT).show();
                            checkAndCallApi();
                        }
                    }
                } else {
                    Toast.makeText(SplashActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callRecordx.stopCallReceiver();
                if (new CheckConnection(SplashActivity.this).isNetworkConnected()) {
                    if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Toast.makeText(SplashActivity.this, "Grant Calling Permission First !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (SharedPrefManager.getInstance(SplashActivity.this).designation().equalsIgnoreCase("Social Media") ||
                                SharedPrefManager.getInstance(SplashActivity.this).designation().equalsIgnoreCase("Admin and Other") ||
                                SharedPrefManager.getInstance(SplashActivity.this).designation().equalsIgnoreCase("Manager") ||
                                SharedPrefManager.getInstance(SplashActivity.this).designation().equalsIgnoreCase("Software Developer") ||
                                SharedPrefManager.getInstance(SplashActivity.this).designation().equalsIgnoreCase("CEO/Director") ||
                                SharedPrefManager.getInstance(SplashActivity.this).designation().equalsIgnoreCase("Sr Manager")) {
                            Intent intent = new Intent(SplashActivity.this, WardWiseBirthdayImageList.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SplashActivity.this, "Access is denied !", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(SplashActivity.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        checkUserStatus();

        checkAndCallApi();

    }

    private void checkUserStatus() {
        Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().CheckUserStatus(SharedPrefManager.getInstance(SplashActivity.this).username(), SharedPrefManager.getInstance(SplashActivity.this).executiveCd(), "Status");
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                if (res.isError()) {
                    SharedPrefManager.getInstance(SplashActivity.this).clear();
                    Toast.makeText(SplashActivity.this, res.getErrormsg(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SplashActivity.this, LoginAcivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                Toast.makeText(SplashActivity.this, "Unable to check user status !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAndCallApi() {
        if (new CheckConnection(SplashActivity.this).isNetworkConnected()) {
            getSpinnersData();
        } else {
            Snackbar snackbar = Snackbar
                    .make(activity_splash, "No Internet", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Re Try", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkAndCallApi();
                        }
                    });

            snackbar.show();
        }
    }

    private void openElectionPopup(final String type) {
        final Dialog dialog = new Dialog(SplashActivity.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.election_pop_up);
        Button close = dialog.findViewById(R.id.closebtn);
        Button update = dialog.findViewById(R.id.updatebtn);
        final Spinner spinresponse1 = dialog.findViewById(R.id.edtspnelection);

        ArrayAdapter<String> adapterx = new ArrayAdapter<String>(SplashActivity.this, android.R.layout.simple_spinner_item, arrayList);
        adapterx.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinresponse1.setAdapter(adapterx);
        String prevselelectionname = SharedPrefManager.getInstance(SplashActivity.this).getElectionName();
        if (prevselelectionname != null && !prevselelectionname.equalsIgnoreCase("")) {
            int spinnerPosition = adapterx.getPosition(prevselelectionname);
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

                if (SharedPrefManager.getInstance(SplashActivity.this).addElectionName(spinresponse1.getSelectedItem().toString())) {
                    dialog.dismiss();
                    if (type.equalsIgnoreCase("QC")) {
                        startActivity(new Intent(SplashActivity.this, SocietyListOfQC.class));
                    } else if (type.equalsIgnoreCase("MQC")) {
                        startActivity(new Intent(SplashActivity.this, OtherQCSocietyList.class));
                    } else if (type.equalsIgnoreCase("iconnect")) {
                        startActivity(new Intent(SplashActivity.this, IConnectQCActivity.class));
                    } else if (type.equalsIgnoreCase("round1")) {
                        startActivity(new Intent(SplashActivity.this, RoundWiseQCActivity.class).putExtra("roundno", "round1"));
                    } else if (type.equalsIgnoreCase("round2")) {
                        startActivity(new Intent(SplashActivity.this, RoundWiseQCActivity.class).putExtra("roundno", "round2"));
                    } else if (type.equalsIgnoreCase("round3")) {
                        startActivity(new Intent(SplashActivity.this, RoundWiseQCActivity.class).putExtra("roundno", "round3"));
                    } else if (type.equalsIgnoreCase("ccalling")) {
                        startActivity(new Intent(SplashActivity.this, ClientCallingActivity.class));
                    } else if (type.equalsIgnoreCase("KKHT")) {
                        startActivity(new Intent(SplashActivity.this, KaryakartaHitchintakActivity.class));
                    }
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

    private void initializeDB() {
        SQLiteDatabase dbvoter = null;

        /*try
        {
            dbvoter = (new DataBaseHelper(this)).
                    getWritableDatabase();
        } catch ( Exception e ) {
            e.printStackTrace();

        } finally {

            dbvoter.close();
        }*/
    }

    private void progressBar(String msg) {
        try {
            if (mProgressDialog == null)
                mProgressDialog = new ProgressDialog(this);

            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgress(0);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage(msg);
            mProgressDialog.show();

            initializeDbTask = new InitializeDbTask();
            initializeDbTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class InitializeDbTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                initializeDB();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
            Intent startActivityIntent = new
                    Intent(SplashActivity.this, MainActivity.class);
            startActivity(startActivityIntent);
            //SplashActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Please click Back" +
                " again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void getSpinnersData() {
        arrayList.clear();
        electionlist.clear();
        final ProgressDialog progressBar = new ProgressDialog(SplashActivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait ......");
        progressBar.show();

        String ele = SharedPrefManager.getInstance(SplashActivity.this).getElectionName() == null ? "" : SharedPrefManager.getInstance(SplashActivity.this).getElectionName();

        Call<SpinnerResponse> call1 = RetrofitClient
                .getInstance().getApi().spinnerOptionForRep(SharedPrefManager.getInstance(SplashActivity.this).username(), ele);
        call1.enqueue(new Callback<SpinnerResponse>() {
            @Override
            public void onResponse(Call<SpinnerResponse> call1, Response<SpinnerResponse> response) {
                SpinnerResponse res = response.body();
                progressBar.dismiss();
                if (res.getElectionList().size() > 0) {
                    electionlist = res.getElectionList();
                    for (int i = 0; i < electionlist.size(); i++) {
                        arrayList.add(electionlist.get(i).getElectionName());
                    }

                    if (!SharedPrefManager.getInstance(SplashActivity.this).isElectionExists()) {
                        SharedPrefManager.getInstance(SplashActivity.this).addElectionName(arrayList.get(0));
                    }
                    /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(SplashActivity.this, android.R.layout.simple_spinner_item, arrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    electionname.setAdapter(adapter);*/
                }

            }

            @Override
            public void onFailure(Call<SpinnerResponse> call1, Throwable t) {
                progressBar.dismiss();
                //Toast.makeText(SplashActivity.this, "Failed to fetch data !", Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar
                        .make(activity_splash, "Failed to fetch data !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Re Try", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                checkAndCallApi();
                            }
                        });

                snackbar.show();
            }
        });
    }

/*    public boolean checkFolder() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QCCallRecordsX/";
        Log.d("folder---->", "creation and setup"+path);
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

    }*/
}
