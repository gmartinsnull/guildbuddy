package com.gomart.guildbuddy.helper;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by glaubermartins on 2017-06-28.
 */

public class NetworkHelper {

    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
