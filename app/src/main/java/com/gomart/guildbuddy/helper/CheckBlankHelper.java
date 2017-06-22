package com.gomart.guildbuddy.helper;

import android.widget.EditText;

import java.util.List;

/**
 * Created by glaubermartins on 2017-01-26.
 */

public class CheckBlankHelper {

    public static boolean checkBlankFields(List<EditText> fields){
        for (EditText edt:fields) {
            if (edt.getText().length() > 0){
                return true;
            }
        }
        return false;
    }
}
