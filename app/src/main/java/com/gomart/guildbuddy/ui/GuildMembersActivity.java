package com.gomart.guildbuddy.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ProgressBar;

import com.gomart.guildbuddy.Constants;
import com.gomart.guildbuddy.R;
import com.gomart.guildbuddy.helper.DialogHelper;
import com.gomart.guildbuddy.helper.NetworkHelper;
import com.gomart.guildbuddy.ui.presenter.GuildPresenter;
import com.gomart.guildbuddy.manager.DataManager;
import com.gomart.guildbuddy.vo.Character;
import com.gomart.guildbuddy.vo.CharacterClass;
import com.gomart.guildbuddy.vo.CharacterRace;
import com.gomart.guildbuddy.vo.Guild;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by glaubermartins on 2016-11-29.
 */

@Deprecated
public class GuildMembersActivity extends AppCompatActivity {

    public static final String GUILD_MEMBER = "GUILD_MEMBER";

    private GuildPresenter guildPresenter;
    private ArrayList<CharacterClass> classes;
    private ArrayList<CharacterRace> races;
    private ArrayList<Character> characters;

    /*private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;*/

    private ProgressBar progress;

    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_roster);

        //GuildBuddy.app().getAppComponent().inject(this);

        Intent i = getIntent();

        Guild g;
        classes = new ArrayList<>();
        races = new ArrayList<>();


        if (i.getSerializableExtra(Constants.KEY_GUILD) != null) {
            g = (Guild) i.getSerializableExtra(Constants.KEY_GUILD);
            classes = (ArrayList<CharacterClass>) i.getSerializableExtra(Constants.KEY_CLASSES);
            races = (ArrayList<CharacterRace>) i.getSerializableExtra(Constants.KEY_RACES);
            dataManager.setGuild(g);
            dataManager.setClasses(classes);
            dataManager.setRaces(races);
        }else{
            g = dataManager.getGuild();
            classes = dataManager.getClasses();
            races = dataManager.getRaces();
        }

        progress = (ProgressBar)findViewById(R.id.progress);

        guildPresenter = new GuildPresenter(this, g);

        if (NetworkHelper.isConnected(this)){
            //getGuildMembers();

            /*recyclerView = (RecyclerView)findViewById(R.id.recycler);

            layoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new CustomGridItem(2, dpToPixel(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());*/
        }else{
            DialogHelper.showOkDialog(this, getString(R.string.oops), getString(R.string.no_connection));
        }
    }

    /*private void getGuildMembers(){
        progress.setVisibility(View.VISIBLE);
        guildPresenter.getGuild(new Callback<GetGuildMembersResponse>() {
            @Override
            public void onResponse(Call<GetGuildMembersResponse> call, Response<GetGuildMembersResponse> response) {
                //Log.d("#MAIN", response.body().toString());
                characters = new ArrayList<>();
                for (GuildMember gm:response.body().getMembers()) {
                    for (int i=0; i < classes.size(); i++) {
                        if (gm.character.charClass == classes.get(i).getId()){
                            for (int j=0; j < races.size(); j++) {
                                if (gm.character.race == races.get(j).getId()){
                                    //Log.d("#MEMBER", gm.getCharacter().getName()+" / "+classes.get(i).getName()+" / "+races.get(j).getName());
                                    characters.add(gm.character);
                                }
                            }
                        }
                    }
                }
                adapter = new GuildMembersAdapter(GuildMembersActivity.this, characters);
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
            final int position = parent.getChildAdapterPosition(view);
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

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkHelper.isConnected(GuildMembersActivity.this)){
                        Intent i = new Intent(GuildMembersActivity.this, GuildMemberProfileActivity.class);
                        i.putExtra(GUILD_MEMBER, characters.get(position));
                        startActivity(i);
                    }else{
                        DialogHelper.showOkDialog(GuildMembersActivity.this, getString(R.string.oops), getString(R.string.no_connection));
                    }
                }
            });
        }
    }

    //CONVERTING DP TO PIXEL
    private int dpToPixel(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (NetworkHelper.isConnected(getApplicationContext())){
                    ArrayList<Character> auxCharacters = new ArrayList(characters);
                    int position;
                    for (Character character: characters) {
                        *//*if (!character.name.toLowerCase().contains(query.toLowerCase())){
                            position = auxCharacters.indexOf(character);
                            auxCharacters.remove(position);
                        }*//*
                    }
                    *//*adapter = new GuildMembersAdapter(GuildMembersActivity.this, auxCharacters);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();*//*
                }else{
                    DialogHelper.showOkDialog(GuildMembersActivity.this, getString(R.string.oops), getString(R.string.no_connection));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                *//*adapter = new GuildMembersAdapter(GuildMembersActivity.this, characters);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();*//*
                return false;
            }
        });

        return true;
    }*/
}
