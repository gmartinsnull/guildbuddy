package com.gomart.guildbuddy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.gomart.guildbuddy.R;
import com.gomart.guildbuddy.api.GetCharacterClassesResponse;
import com.gomart.guildbuddy.api.GetCharacterRacesResponse;
import com.gomart.guildbuddy.api.controller.CharacterController;
import com.gomart.guildbuddy.manager.DataManager;
import com.gomart.guildbuddy.model.CharacterClass;
import com.gomart.guildbuddy.model.CharacterRace;
import com.gomart.guildbuddy.model.Guild;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private CharacterController cCtrl;

    private CharacterClass charClass;
    private CharacterRace charRace;
    private ArrayList<CharacterClass> classes;
    private ArrayList<CharacterRace> races;

    private EditText edtGuildName;
    private EditText edtRealm;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtRealm = (EditText)findViewById(R.id.edt_realm);
        edtGuildName = (EditText)findViewById(R.id.edt_guildName);
        btnSearch = (Button)findViewById(R.id.btn_search);

        cCtrl = new CharacterController();

        getClasseNames();
        getRaceNames();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guild g = new Guild(edtGuildName.getText().toString(), edtRealm.getText().toString());

                ArrayList<String> fields = new ArrayList<>();
                fields.add("members");

                g.setFields(fields);

                Intent i = new Intent(MainActivity.this, GuildMembersActivity.class);
                i.putExtra("classes", classes);
                i.putExtra("races", races);
                i.putExtra("guild", g);
                startActivity(i);
            }
        });

    }

    private void getClasseNames(){
        cCtrl.init(this);

        cCtrl.getClassName(new Callback<GetCharacterClassesResponse>() {
            @Override
            public void onResponse(Call<GetCharacterClassesResponse> call, Response<GetCharacterClassesResponse> response) {
                classes = new ArrayList<>();
                for (CharacterClass cc : response.body().getClasses()) {
                    //Log.d("#CLASSNAME", cc.getName());
                    charClass = new CharacterClass(cc.getId(), cc.getPowerType(), cc.getName());
                    classes.add(charClass);

                }
                DataManager.getInstance().setClasses(classes);
            }

            @Override
            public void onFailure(Call<GetCharacterClassesResponse> call, Throwable t) {
                Log.d("#CLASSNAME", t.getMessage());
            }
        });

    }

    private void getRaceNames(){
        cCtrl.init(this);

        cCtrl.getRaceName(new Callback<GetCharacterRacesResponse>() {
            @Override
            public void onResponse(Call<GetCharacterRacesResponse> call, Response<GetCharacterRacesResponse> response) {
                races = new ArrayList<>();
                for (CharacterRace cr : response.body().getRaces()) {
                    //Log.d("#CLASSNAME", cr.getName());
                    charRace = new CharacterRace(cr.getId(), cr.getSide(), cr.getName());
                    races.add(charRace);
                }
                DataManager.getInstance().setRaces(races);
            }

            @Override
            public void onFailure(Call<GetCharacterRacesResponse> call, Throwable t) {
                Log.d("#CLASSNAME", t.getMessage());
            }
        });

    }
}
