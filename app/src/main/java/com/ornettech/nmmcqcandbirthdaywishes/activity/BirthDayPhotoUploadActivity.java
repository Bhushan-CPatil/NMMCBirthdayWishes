package com.ornettech.nmmcqcandbirthdaywishes.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.ornettech.nmmcqcandbirthdaywishes.R;
import com.ornettech.nmmcqcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.nmmcqcandbirthdaywishes.utility.AndroidMultiPartEntity;
import com.ornettech.nmmcqcandbirthdaywishes.utility.SharedPrefManager;

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

public class BirthDayPhotoUploadActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog progressBar = null;
    private String  fileUri = null;
    private ImageView imgPreview;
    public EditText ward;
    LinearLayout lin1;
    private Button btnUpload;
    private ImageButton addmore;
    long totalSize = 0;
    String op="",electionname,sendwardnos,corpcd,username,wardno="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_day_photo_upload);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#v'>Upload Image</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        btnUpload = findViewById(R.id.btnUpload);
        imgPreview = findViewById(R.id.imgPreview);
        ward = findViewById(R.id.ward);
        lin1 = findViewById(R.id.lin1);
        addmore = findViewById(R.id.addmore);
        username = SharedPrefManager.getInstance(BirthDayPhotoUploadActivity.this).username();

        Intent i = getIntent();

        op = i.getStringExtra("options");
        fileUri = i.getStringExtra("fileUri");
        electionname = i.getStringExtra("electionname");
        corpcd = i.getStringExtra("corpcd");
        wardno = i.getStringExtra("wardno");
        ward.setText(wardno);
        ward.setEnabled(false);

        if (fileUri != null) {
            // Displaying the image or video on the screen
            previewMedia();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnUpload.setEnabled(false);
                // uploading the file to server
                if(ward.getText().toString().length()>0) {
                    sendwardnos = ward.getText().toString().trim();
                    new UploadFileToServer().execute();
                }else{
                    ward.setError("Please enter ward number.");
                    ward.requestFocus();
                }
            }
        });

        addmore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ward.setEnabled(true);
            }
        });
    }

    private void previewMedia() {
        imgPreview.setVisibility(View.VISIBLE);
        File file = new File(fileUri);
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        if (file.exists() && fileSizeInKB > 5) {
            Glide.with(this).load(fileUri).into(imgPreview);
        }
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar = new ProgressDialog(BirthDayPhotoUploadActivity.this);
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setMessage("Uploading data to server.....(0 %)");
            progressBar.show();
            //progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setMessage("Uploading data to server.....("+progress[0]+" %)");
            //progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            //progressBar.setProgress(progress[0]);

            // updating percentage value
            //txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(RetrofitClient.BASE_URL + "birthDayWishImageUpload.php");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });
                //Log.d("file path", filePath);
                File sourceFile = new File(fileUri);
                entity.addPart("image", new FileBody(sourceFile));

                // Extra parameters if you want to pass to server
                entity.addPart("elecname", new StringBody(electionname));
                entity.addPart("username", new StringBody(username));
                entity.addPart("wardno", new StringBody(sendwardnos));
                entity.addPart("corpcd", new StringBody(corpcd));

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
            //Log.e(TAG, "Response from server: " + result);
            progressBar.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (!jsonObject.getBoolean("error")) {
                    // showing the server response in an alert dialog
                    showAlert(jsonObject.getString("errormsg"));

                } else {
                    Snackbar snackbar = Snackbar.make(lin1, jsonObject.getString("errormsg"), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //super.onPostExecute(result);
        }

    }

    /**
     * Method to show alert dialog
     */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BirthDayPhotoUploadActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Alert ?");
        builder.setMessage(message);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
