package com.gomart.guildbuddy.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by glaubermartins on 2017-01-27.
 */

public class DialogHelper {

    public static void showOkDialog(Context context, String title, String body){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(title != null)
            builder.setTitle(title);
        if (body != null) {
            builder.setMessage(body);
        }

        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

}
