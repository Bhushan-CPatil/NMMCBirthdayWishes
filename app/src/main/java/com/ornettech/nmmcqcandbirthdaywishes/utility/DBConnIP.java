package com.ornettech.nmmcqcandbirthdaywishes.utility;

import com.ornettech.nmmcqcandbirthdaywishes.model.VoterCallingQCPojoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by New on 05/20/2017.
 */

public class DBConnIP {
    public static String CONN = "103.14.99.154";
    //public static String CONN = "www.electionsoftware.net";
    public static List<String> arrayListToDialog = new ArrayList<>();
    public static List<VoterCallingQCPojoItem> newlist = new ArrayList<>();
    public static boolean status;
    public static boolean updateQCResponse_status;
}
