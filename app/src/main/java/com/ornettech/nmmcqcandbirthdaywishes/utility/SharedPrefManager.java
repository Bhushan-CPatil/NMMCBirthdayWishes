package com.ornettech.nmmcqcandbirthdaywishes.utility;
import android.content.Context;
import android.content.SharedPreferences;

import com.ornettech.nmmcqcandbirthdaywishes.model.LoginResponse;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "NMMC_QC";
    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }


    public void saveUser(LoginResponse user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("exe_id", user.getExecutiveCd());
        editor.putString("designation", user.getDesignation());
        editor.putString("name", user.getExecutiveName());
        editor.putString("username", user.getUserName());
        editor.apply();
    }

    public boolean addElectionName(String electionname) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("electionname", electionname);
        editor.apply();

        return sharedPreferences.getString("electionname", null) != null;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("exe_id", null) != null;
    }

    public boolean isElectionExists() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("electionname", null) != null;
    }

    public String executiveCd() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("exe_id", null);
    }

    public String name() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", null);
    }

    public String username() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", null);
    }

    public String designation() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("designation", null);
    }

    public String getElectionName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("electionname", null);
    }

    /*public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString("id", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("pincode", null),
                sharedPreferences.getString("contact", null),
                sharedPreferences.getString("address", null),
                sharedPreferences.getString("city", null),
                sharedPreferences.getString("state", null),
                sharedPreferences.getString("country", null)
        );
    }*/

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
