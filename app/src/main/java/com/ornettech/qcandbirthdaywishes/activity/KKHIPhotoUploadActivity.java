package com.ornettech.qcandbirthdaywishes.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.qcandbirthdaywishes.utility.AndroidMultiPartEntity;
import com.ornettech.qcandbirthdaywishes.utility.DBConnIP;
import com.ornettech.qcandbirthdaywishes.utility.SharedPrefManager;

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

import id.zelory.compressor.Compressor;

public class KKHIPhotoUploadActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog progressBar = null;
    private String  fileUri = null;
    private ImageView imgPreview;
    public EditText ward;
    LinearLayout lin1;
    private Button btnUpload;
    long totalSize = 0;
    String type="",electionname,kkhicd,username,wardno="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_k_h_i_photo_upload);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#v'>Upload Image</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        btnUpload = findViewById(R.id.btnUpload);
        imgPreview = findViewById(R.id.imgPreview);

        username = SharedPrefManager.getInstance(KKHIPhotoUploadActivity.this).username();

        Intent i = getIntent();

        type = i.getStringExtra("options");
        fileUri = i.getStringExtra("fileUri");
        electionname = i.getStringExtra("electionname");
        wardno = i.getStringExtra("wardno");
        kkhicd = i.getStringExtra("kkhicd");

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
                new UploadFileToServer().execute();

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
            progressBar = new ProgressDialog(KKHIPhotoUploadActivity.this);
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setMessage("Uploading data to server.....(0 %)");
            progressBar.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            progressBar.setMessage("Uploading data to server.....("+progress[0]+" %)");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(RetrofitClient.BASE_URL + "KKHIImageUpload.php");

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
                File compressedImageFile = new Compressor(KKHIPhotoUploadActivity.this).compressToFile(sourceFile);
                // Adding file data to http body
                entity.addPart("image", new FileBody(compressedImageFile));

                // Extra parameters if you want to pass to server
                entity.addPart("elecname", new StringBody(electionname));
                entity.addPart("username", new StringBody(username));
                entity.addPart("wardno", new StringBody(wardno));
                entity.addPart("kkhicd", new StringBody(kkhicd));
                entity.addPart("utype", new StringBody(type));

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
        DBConnIP.runmethod = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(KKHIPhotoUploadActivity.this);
        builder.setCancelable(false);
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
