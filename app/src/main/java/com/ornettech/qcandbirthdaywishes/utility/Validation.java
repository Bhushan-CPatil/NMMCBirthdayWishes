package com.ornettech.qcandbirthdaywishes.utility;

import android.content.Context;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by New on 11/15/2017.
 */

public class Validation {

    Context context;
    public static final int TAB_HOME = 0;
    public static final int TAB_DAINANDINI = 1;
    public static final int TAB_FOLLOWUP = 2;
    public static final int TAB_REPORT = 3;

    public static final int TAB_YOJANA = 0;
    public static final int TAB_BUDGET = 1;
    public static final int TAB_RECRUIT = 2;

    public Validation(Context context) {
        this.context = context;
    }

    public Validation() {
    }

    public String ismobileNoValid(String mobno){

       String msg = "";

        try {
            Pattern p=Pattern.compile("(91|0)?[7-9][0-9]{9}");
            Matcher m=p.matcher(mobno);

            if(m.find()&& m.group().equals(mobno)){
                msg = "";
            }else{
                msg = "Please enter correct mobile number";
            }
        } catch (Exception e) {
            e.toString();
        }

        return msg;
    }

    public String isEmailValid(String email) {

        String msg = "";

        String expression = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        Pattern patternObj = Pattern.compile(expression);
        Matcher matcherObj = patternObj.matcher(email);
        if (matcherObj.matches()) {
            msg = "";
        } else {
            msg = "Please enter valid email-id";
        }
        return msg;
    }

    public String dateFormatShow(String date){

        String rtndate = "";
        SimpleDateFormat parseDate = new
                SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat dateFormatter = new
                SimpleDateFormat("dd-MM-yyyy", Locale.US);
        try {
            Date datePar = parseDate.parse(date);
            rtndate = dateFormatter.format(datePar);
        } catch (ParseException e) {
            e.printStackTrace();}

        return rtndate;
    }

    public String dateFormatDB(String date){
        
        String rtndate = "";

        try {
            if( date != null && date.length()>0 && date.contains("/")){
                String [] dob = date.split("/");
                String day = dob[0];
                String mon = dob[1];

                if( day.length() == 1 )
                    day = "0" + day;
                if( mon.length() == 1)
                    mon = "0" + mon;

                date = day + "-" + mon + "-" + dob[2];
            }

            SimpleDateFormat dateFormatter = new
                    SimpleDateFormat("dd-MM-yyyy", Locale.US);
            SimpleDateFormat parseDate = new
                    SimpleDateFormat("yyyy-MM-dd", Locale.US);

            Date join_Dt = dateFormatter.parse(date);
            rtndate = parseDate.format(join_Dt);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rtndate;
    }


    public String localVotDate(String dates){

        String rtndate = "";
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("MM/dd/yyyy");
            rtndate = sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();}

        return rtndate;
    }


    public String localNonVotDate(String dates){

        String rtndate = "";
        try {

            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dates);
            rtndate =date.toString();
        } catch (Exception e) {
            e.printStackTrace();}

        return rtndate;
    }




    public String systemdateFormatDB(){

        String rtndate = "";
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            rtndate = sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();}

        return rtndate;
    }

    public String systemDate(){

        String rtndate = "";
        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd");
        rtndate = sdf.format(new Date());

        return rtndate;
    }

    public String isFromToDateValid(String fromDt, String toDt){

        String msg = "";
        Date datefrom = null, dateto = null;

        if( fromDt.length() > 0 && toDt.length() > 0 ) {

            if (fromDt != null && fromDt.length() > 0 && fromDt.contains("/")) {
                String[] dob = fromDt.split("/");
                String day = dob[0];
                String mon = dob[1];

                if (day.length() == 1)
                    day = "0" + day;
                if (mon.length() == 1)
                    mon = "0" + mon;

                fromDt = day + "-" + mon + "-" + dob[2];
            }
            if ( toDt != null && toDt.length() > 0 && toDt.contains("/")) {
                String[] dob = toDt.split("/");
                String day = dob[0];
                String mon = dob[1];

                if (day.length() == 1)
                    day = "0" + day;
                if (mon.length() == 1)
                    mon = "0" + mon;

                toDt = day + "-" + mon + "-" + dob[2];
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                datefrom = sdf.parse(fromDt);
                dateto = sdf.parse(toDt);
            } catch (Exception e) {
                e.toString();
            }
            if (datefrom.compareTo(dateto) > 0) {
                msg = "Please select lesser from date or greater to date";
            } else {
                msg = "";
            }
        }
        return msg;
    }

    public String isDateValid(String date){
        String rtndate = "", day = "",
                mon = "", yr = "";

        String[] dob = {};
        try {

            if (!TextUtils.isEmpty(date) && date.length() < 11) {

                if (date.contains("/") || date.contains("-")) {

                    if(date.contains("/"))
                        dob = date.split("/");

                    else if(date.contains("-"))
                        dob = date.split("-");

                    else if(date.contains("."))
                        dob = date.split(".");

                    try {
                        day = dob[0];
                    } catch (Exception e) {
                        rtndate = "Please Enter Valid Date";
                    }
                    try {
                        mon = dob[1];
                    } catch (Exception e) {
                        rtndate = "Please Enter Valid Date";
                    }
                    try {
                        yr = dob[2];
                    } catch (Exception e) {
                        rtndate = "Please Enter Valid Date";
                    }

                    if (day.length() > 0 && mon.length() > 0 && yr.length() > 0) {

                        if (day.length() == 1)
                            day = "0" + day;
                        if (mon.length() == 1)
                            mon = "0" + mon;

                        int dd = Integer.valueOf(day);
                        int mm = Integer.valueOf(mon);

                        if (dd == 00 || dd > 31)
                            rtndate = "Please Enter Valid Day";

                        else if (mm > 12 || mm == 00)
                            rtndate = "Please Enter Valid Month";

                        else if (yr.length() < 4 || yr.length() > 4
                                || yr.contentEquals("0000"))
                            rtndate = "Please Enter Valid 4 digit Year";

                        else if (yr.length() > 0) {
                            yr = yr.substring(0, 2);
                            int dbyy = Integer.valueOf(yr);

                            if (dbyy == 19 || dbyy == 20)
                                rtndate = "";
                            else
                                rtndate = "Year must starts with 19 or 20";
                        }
                    }
                }else{
                    rtndate = "Please Enter Valid Date";
                }
            }else{
                rtndate = "";
            }
        }catch (Exception e){
            e.toString();}

        return rtndate;
    }

    public String datetimeshowDate(String datetime){

        String shwdt = "";
        try{
            if(datetime != null && datetime.length() > 12){
                String dt = datetime.substring(0, 10);
                shwdt = dateFormatShow(dt);
            }
        }catch (Exception e){
            e.toString();
        }
        return shwdt;
    }

//    public String getUsername(){
//
//        String username = "";
//
//        SharedPreferences sharedPref = context.getSharedPreferences(APIUrl.
//                SHARED_PREF_NAME, MODE_PRIVATE);
//        username = sharedPref.getString(APIUrl.SH_USERNAME, "");
//
//        return username;
//    }

}
