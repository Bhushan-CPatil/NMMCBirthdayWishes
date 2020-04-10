package com.ornettech.qcandbirthdaywishes.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.qcandbirthdaywishes.model.LoginResponse;
import com.ornettech.qcandbirthdaywishes.utility.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAcivity extends AppCompatActivity {

    EditText name,mobileno;
    Button login;
    ProgressDialog progressBar;

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acivity);

        name = findViewById(R.id.username);
        mobileno = findViewById(R.id.mobno);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a1 = name.getText().toString().trim();
                String a2 = mobileno.getText().toString().trim();

                if (a1.isEmpty()) {
                    name.setError("User name is required");
                    name.requestFocus();
                    return;
                }

                if (a2.isEmpty()) {
                    mobileno.setError("Password required");
                    mobileno.requestFocus();
                    return;
                }

                loginApi(a1,a2);

            }
        });
    }

    private void loginApi(String username, String mobilenumber) {

        progressBar = new ProgressDialog(LoginAcivity.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait...");
        progressBar.show();

        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().userLogin(username, mobilenumber, "NMMC");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                progressBar.dismiss();
                if (!loginResponse.isError()) {

                        SharedPrefManager.getInstance(LoginAcivity.this)
                                .saveUser(loginResponse);

                        Intent intent = new Intent(LoginAcivity.this, SplashActivity.class);
                        startActivity(intent);

                } else {
                    Toast.makeText(LoginAcivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(LoginAcivity.this, "Failed to process your request !", Toast.LENGTH_LONG).show();
            }
        });
    }
}
