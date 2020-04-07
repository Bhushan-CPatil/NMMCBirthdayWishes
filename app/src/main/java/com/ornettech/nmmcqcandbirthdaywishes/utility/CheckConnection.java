package com.ornettech.nmmcqcandbirthdaywishes.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by New on 04/21/2017.
 */

public class CheckConnection {

    Context context;

    public CheckConnection(Context contxt) {
        this.context = contxt;
    }

    public boolean isNetworkConnected() {
        //String sURL = "http://www.electionsoftware.net/";
        String sURL = "http://"+DBConnIP.CONN+"/";
        int DNSTimeout = 3000;

        //Get the IP of the Host
        //DNS TimeOut
        URL url= null;
        try {
            url = ResolveHostIP(sURL,DNSTimeout);
        } catch (MalformedURLException e) {
            Log.d("INFO",e.getMessage());
        }
        if(url==null){
            Toast.makeText(context.getApplicationContext(),
                    "Internet Connection Range Not Found!",Toast.LENGTH_LONG).show();
            return false;
        }else {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni == null) {
                return false;
            } else {
                return true;
            }
        }
    }

    //public static URL ResolveHostIP (String sURL, int timeout) throws MalformedURLException {
    public URL ResolveHostIP (String sURL, int timeout) throws MalformedURLException {
        URL url= new URL(sURL);
        //Resolve the host IP on a new thread
        DNSResolver dnsRes = new DNSResolver(url.getHost());
        Thread t = new Thread(dnsRes);
        t.start();
        //Join the thread for some time
        try {
            t.join(timeout);
        } catch (InterruptedException e) {
            Log.d("DEBUG", "DNS lookup interrupted");
            return null;
        }

        //get the IP of the host
        InetAddress inetAddr = dnsRes.get();
        if(inetAddr==null) {
            Log.d("DEBUG", "DNS timed out.");
            return null;
        }

        //rebuild the URL with the IP and return it
        Log.d("DEBUG", "DNS solved.");

        return new URL(url.getProtocol(),
                inetAddr.getHostAddress(),
                url.getPort(),url.getFile());
    }
}
