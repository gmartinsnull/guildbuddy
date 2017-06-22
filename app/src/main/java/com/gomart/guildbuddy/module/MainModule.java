package com.gomart.guildbuddy.module;

import android.widget.EditText;

import com.gomart.guildbuddy.ui.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by glaubermartins on 2017-06-13.
 */

@Module
public class MainModule {

    EditText edtGuild;
    EditText edtRealm;

    public MainModule(EditText guild, EditText realm){
        edtGuild = guild;
        edtRealm = realm;
    }

    @Provides
    EditText getGuild(){
        return edtGuild;
    }

    @Provides
    EditText getRealm(){
        return edtRealm;
    }
}
