package com.gomart.guildbuddy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gomart.guildbuddy.GuildBuddy;
import com.gomart.guildbuddy.R;
import com.gomart.guildbuddy.Constants;
import com.gomart.guildbuddy.helper.NetworkHelper;
import com.gomart.guildbuddy.network.GetCharacterClassesResponse;
import com.gomart.guildbuddy.network.GetCharacterRacesResponse;
import com.gomart.guildbuddy.helper.CheckBlankHelper;
import com.gomart.guildbuddy.helper.DialogHelper;
import com.gomart.guildbuddy.manager.DataManager;
import com.gomart.guildbuddy.model.CharacterClass;
import com.gomart.guildbuddy.model.CharacterRace;
import com.gomart.guildbuddy.model.Guild;
import com.gomart.guildbuddy.ui.presenter.GuildMemberPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String GUILD_MEMBERS = "members";

    private GuildMemberPresenter presenter;

    private CharacterClass charClass;
    private CharacterRace charRace;
    private ArrayList<CharacterClass> classes;
    private ArrayList<CharacterRace> races;

    private EditText edtGuildName;
    private EditText edtRealm;
    private Button btnSearch;

    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GuildBuddy.app().getAppComponent().inject(this);

        edtRealm = (EditText)findViewById(R.id.edt_realm);
        edtGuildName = (EditText)findViewById(R.id.edt_guildName);
        btnSearch = (Button)findViewById(R.id.btn_search);

        presenter = new GuildMemberPresenter(this, edtRealm.getText().toString());

        if (NetworkHelper.isConnected(this)){
            getClassNames();
            getRaceNames();
        }else{
            DialogHelper.showOkDialog(this, getString(R.string.oops), getString(R.string.no_connection));
        }

        if (dataManager.get(Constants.KEY_GUILD, "") != null && dataManager.get(Constants.KEY_GUILD, "").length() > 0 && dataManager.get(Constants.KEY_REALM, "") != null && dataManager.get(Constants.KEY_REALM, "").length() > 0){
            edtGuildName.setText(dataManager.get(Constants.KEY_GUILD, ""));
            edtRealm.setText(dataManager.get(Constants.KEY_REALM, ""));
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkHelper.isConnected(getApplicationContext())){
                    List<EditText> inputs = new ArrayList<>();
                    inputs.add(edtRealm);
                    inputs.add(edtGuildName);

                    if (!CheckBlankHelper.emptyFieldExist(inputs)){
                        Guild g = new Guild(edtGuildName.getText().toString(), edtRealm.getText().toString());

                        ArrayList<String> fields = new ArrayList<>();
                        fields.add(GUILD_MEMBERS);

                        g.setFields(fields);

                        dataManager.save(Constants.KEY_GUILD, edtGuildName.getText().toString());
                        dataManager.save(Constants.KEY_REALM, edtRealm.getText().toString());

                        Intent i = new Intent(MainActivity.this, GuildMembersActivity.class);
                        i.putExtra(Constants.KEY_CLASSES, classes);
                        i.putExtra(Constants.KEY_RACES, races);
                        i.putExtra(Constants.KEY_GUILD, g);
                        startActivity(i);
                    }else{
                        DialogHelper.showOkDialog(MainActivity.this, getString(R.string.oops), getString(R.string.empty_fields));
                    }
                }else{
                    DialogHelper.showOkDialog(MainActivity.this, getString(R.string.oops), getString(R.string.no_connection));
                }
            }
        });

    }

    private void getClassNames(){
        presenter.getClassName(new Callback<GetCharacterClassesResponse>() {
            @Override
            public void onResponse(Call<GetCharacterClassesResponse> call, Response<GetCharacterClassesResponse> response) {
                classes = new ArrayList<>();
                if (response.isSuccessful()){
                    for (CharacterClass cc : response.body().getClasses()) {
                        //Log.d("#CLASSNAME", cc.getName());
                        charClass = new CharacterClass(cc.getId(), cc.getPowerType(), cc.getName());
                        classes.add(charClass);

                    }
                    dataManager.setClasses(classes);
                }
            }

            @Override
            public void onFailure(Call<GetCharacterClassesResponse> call, Throwable t) {
                Log.d("#CLASSNAME", t.getMessage());
            }
        });

    }

    private void getRaceNames(){
        presenter.getRaceName(new Callback<GetCharacterRacesResponse>() {
            @Override
            public void onResponse(Call<GetCharacterRacesResponse> call, Response<GetCharacterRacesResponse> response) {
                races = new ArrayList<>();
                for (CharacterRace cr : response.body().getRaces()) {
                    //Log.d("#CLASSNAME", cr.getName());
                    charRace = new CharacterRace(cr.getId(), cr.getSide(), cr.getName());
                    races.add(charRace);
                }
                dataManager.setRaces(races);
            }

            @Override
            public void onFailure(Call<GetCharacterRacesResponse> call, Throwable t) {
                Log.d("#RACENAME", t.getMessage());
            }
        });

    }
}
