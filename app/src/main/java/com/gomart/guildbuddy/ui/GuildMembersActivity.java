package com.gomart.guildbuddy.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;

import com.gomart.guildbuddy.R;
import com.gomart.guildbuddy.api.GetGuildMembersResponse;
import com.gomart.guildbuddy.api.controller.GuildController;
import com.gomart.guildbuddy.component.DaggerGuildComponent;
import com.gomart.guildbuddy.component.GuildComponent;
import com.gomart.guildbuddy.model.Character;
import com.gomart.guildbuddy.model.CharacterClass;
import com.gomart.guildbuddy.model.CharacterRace;
import com.gomart.guildbuddy.model.Guild;
import com.gomart.guildbuddy.model.GuildMember;
import com.gomart.guildbuddy.module.GuildModule;
import com.gomart.guildbuddy.ui.adapter.RecyclerViewCustomAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by glaubermartins on 2016-11-29.
 */

public class GuildMembersActivity extends AppCompatActivity {

    private GuildController gCtrl;
    private ArrayList<CharacterClass> classes;
    private ArrayList<CharacterRace> races;
    private ArrayList<Character> characters;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private CardView cView;
    private RecyclerView.LayoutManager layoutManager;

    private ProgressBar progress;

    private GuildComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_members);

        Intent i = getIntent();

        Guild g = (Guild) i.getSerializableExtra("guild");

        component = DaggerGuildComponent.builder()
                .guildModule(new GuildModule(g))
                .build();
        component.inject(this);

        /*component = DaggerGuildComponent.builder()
                .guildModule(new GuildModule(this))
                .build();
        component.inject(
                g);*/

        progress = (ProgressBar)findViewById(R.id.progress);



        classes = new ArrayList<>();
        classes = (ArrayList<CharacterClass>) i.getSerializableExtra("classes");

        races = new ArrayList<>();
        races = (ArrayList<CharacterRace>) i.getSerializableExtra("races");

        gCtrl = new GuildController();
        gCtrl.init(this, g);

        getGuildMembers();

        recyclerView = (RecyclerView)findViewById(R.id.recycler);

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new CustomGridItem(2, dpToPixel(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void getGuildMembers(){
        progress.setVisibility(View.VISIBLE);
        gCtrl.getGuild(new Callback<GetGuildMembersResponse>() {
            @Override
            public void onResponse(Call<GetGuildMembersResponse> call, Response<GetGuildMembersResponse> response) {
                //Log.d("#MAIN", response.body().toString());
                characters = new ArrayList<>();
                for (GuildMember gm:response.body().getMembers()) {
                    for (int i=0; i < classes.size(); i++) {
                        if (gm.getCharacter().getCharClass() == classes.get(i).getId()){
                            for (int j=0; j < races.size(); j++) {
                                if (gm.getCharacter().getRace() == races.get(j).getId()){
                                    //Log.d("#MEMBER", gm.getCharacter().getName()+" / "+classes.get(i).getName()+" / "+races.get(j).getName());
                                    characters.add(gm.getCharacter());
                                }
                            }
                        }
                    }
                }
                adapter = new RecyclerViewCustomAdapter(GuildMembersActivity.this, characters);
                recyclerView.setAdapter(adapter);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GetGuildMembersResponse> call, Throwable t) {
                Log.d("#MAIN", t.getMessage());
            }
        });
    }

    public class CustomGridItem extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public CustomGridItem(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }

    //CONVERTING DP TO PIXEL
    private int dpToPixel(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
