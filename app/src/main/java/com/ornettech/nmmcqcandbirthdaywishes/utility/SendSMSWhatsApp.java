package com.ornettech.nmmcqcandbirthdaywishes.utility;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;

import android.telephony.PhoneNumberUtils;
import android.widget.Toast;

import java.io.File;
import java.net.URLEncoder;


/**
 * Created by New on 12/07/2017.
 */

public class SendSMSWhatsApp {
	
	public static String SMS = "SMS";
    public static String WHATSAPP = "WHP";

//    public static void dialogSMS(final Context context,
//                                 final String mob, String name,
//                                 final String type) {
//
//        final Dialog myDialog = new Dialog(context);
//        myDialog.setContentView(R.layout.message_dialogbox);
//        myDialog.setCancelable(false);
//
//        Button sendsms = myDialog.findViewById(R.id.btn_sendsms);
//        Button btncancel = myDialog.findViewById(R.id.btn_cnclsms);
//        TextView txtmob = myDialog.findViewById(R.id.txtmsg_mob);
//        TextView txtname = myDialog.findViewById(R.id.txtmsg_fullName);
//
//        txtmob.setText(mob);
//        txtname.setText(name);
//
//        myDialog.show();
//        btncancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDialog.cancel();
//            }
//        });
//
//        sendsms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                EditText edttext = myDialog.findViewById(R.id.edt_txtmsg);
//                String smsText = edttext.getText().toString().trim();
//
//                String msg_mob = chkMobNo(mob);
//                String msg_sms = chkSMS(smsText);
//
//                if (msg_mob.length() > 0)
//                    Toast.makeText(context, msg_mob, Toast.LENGTH_LONG).show();
//
//                else if (msg_sms.length() > 0)
//                    Toast.makeText(context, msg_sms, Toast.LENGTH_LONG).show();
//
//                else {
//
//                    String msg = "";
//
//                    if(type.contentEquals("WHP"))
//                        msg = sendWhatsApp(context, mob, smsText);
//
//                    else if(type.contentEquals("SMS"))
//                        msg = sendSMS(mob, smsText);
//
//                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//                    myDialog.dismiss();
//                }
//            }
//        });
//    }
//
public static void sentToWhatsapp(Context mycontext, File imageurl, String mobno, String msg){
    Uri imgUri = Uri.parse(imageurl.getAbsolutePath());
    String smsNumber = "91"+mobno;
    boolean isWhatsappInstalled = whatsappInstalledOrNot(mycontext,"com.whatsapp");
    if (isWhatsappInstalled) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);

        sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.ContactPicker"));//"com.whatsapp.Conversation"
        //sendIntent.setPackage("com.whatsapp");
        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mycontext.startActivity(sendIntent);
    } else {
        Uri uri = Uri.parse("market://details?id=com.whatsapp");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        Toast.makeText(mycontext, "WhatsApp not Installed",
                Toast.LENGTH_SHORT).show();
        mycontext.startActivity(goToMarket);
    }
}

    public static String sendWhatsApp(Context context, String number, String msg){

        String wh_msg = "";

        boolean isWhatsappInstalled = whatsappInstalledOrNot(context, "com.whatsapp");
        if (isWhatsappInstalled) {
            if(number.length()==10) {
                PackageManager packageManager = context.getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);
                try {
                    String url = "https://api.whatsapp.com/send?phone="
                            + "+91" + number + "&text=" + URLEncoder.encode(msg, "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));

                    if (i.resolveActivity(packageManager) != null)
                        context.startActivity(i);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                if(msg.length()>65536){
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, msg.substring(0,65536));
                }else{
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, msg);
                }
                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    context.startActivity(whatsappIntent);
                }catch (Exception e){
                    Toast.makeText(context, "Error while opening whatsapp !", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            wh_msg = "WhatsApp not Installed";
            context.startActivity(goToMarket);
        }
        return wh_msg;
    }

    private static boolean  whatsappInstalledOrNot(Context context, String uri) {

        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static void callMobNo(Context context, String mob) {

        String msg_mob = chkMobNo(mob);
        if (msg_mob.length() == 0) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + mob));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            context.startActivity(callIntent);
        }else{
            Toast.makeText(context,msg_mob, Toast.LENGTH_LONG).show();
        }
    }

//    public static String sendSMS(String mobno, String text) {
//
//        String msg = "";
//        try {
//            SmsManager smsdiv = SmsManager.getDefault();
//            ArrayList<String> parts = smsdiv.divideMessage(text);
//            smsdiv.sendMultipartTextMessage( mobno,null, parts,
//                    null, null);
//
//            msg = "SMS Sent.!";
//        } catch (Exception e) {
//            msg = "SMS Not Sent.!";
//            e.toString();
//        }
//
//        return msg;
//    }

    public static String chkMobNo(String mobno){
        String msg = "";

        if( mobno != null && mobno.length() == 0 )
            msg = "Mobile No Required..!";

        return msg;
    }

    public static String chkSMS(String sms){
        String msg = "";

        if( sms != null && sms.length() == 0 )
            msg = "Enter text to send sms";

        return msg;
    }

    public static void dialMobNo(Context context, String mob) {

        String msg_mob = chkMobNo(mob);
        if (msg_mob.length() == 0) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + mob));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            context.startActivity(callIntent);
        }else{
            Toast.makeText(context,msg_mob, Toast.LENGTH_LONG).show();
        }
    }

//    public void sendMessage( Context context, String number, String msg) {
//
//        String msg_mob = chkMobNo(number);
//        String msg_sms = chkSMS(msg);
//
//        if (msg_mob.length() > 0)
//            Toast.makeText(context, msg_mob, Toast.LENGTH_LONG).show();
//
//        else if (msg_sms.length() > 0)
//            Toast.makeText(context, msg_sms, Toast.LENGTH_LONG).show();
//
//        else {
//            String sms_status = sendSMS(number, msg);
//            Toast.makeText(context, sms_status, Toast.LENGTH_LONG).show();
//        }
//    }

}
