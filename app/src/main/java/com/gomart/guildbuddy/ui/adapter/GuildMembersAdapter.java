package com.gomart.guildbuddy.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gomart.guildbuddy.BuildConfig;
import com.gomart.guildbuddy.GuildBuddy;
import com.gomart.guildbuddy.R;
import com.gomart.guildbuddy.manager.DataManager;
import com.gomart.guildbuddy.model.Character;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by glaubermartins on 2016-11-29.
 */

public class GuildMembersAdapter extends RecyclerView.Adapter<GuildMembersAdapter.CustomViewHolder> {

    private Context c;
    private ArrayList<Character> characters;

    @Inject
    DataManager dataManager;

    public GuildMembersAdapter(Context c, ArrayList<Character> characters) {
        this.c = c;
        this.characters = characters;
        GuildBuddy.app().getAppComponent().inject(this);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        private TextView name, race, charClass, level;
        private ImageView thumb;

        public CustomViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            race = (TextView)itemView.findViewById(R.id.race);
            charClass = (TextView)itemView.findViewById(R.id.charClass);
            thumb = (ImageView)itemView.findViewById(R.id.thumb);
            level = (TextView) itemView.findViewById(R.id.level);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guild_member_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Character character =  characters.get(position);
        holder.name.setText(character.getName());
        holder.level.setText(String.valueOf(character.getLevel()));
        holder.race.setText(dataManager.getCharacterRace(character.getRace()));
        holder.charClass.setText(dataManager.getCharacterClass(character.getCharClass()));

        String url = BuildConfig.CHAR_URL+character.getThumbnail()+"?locale="+BuildConfig.LOCALE+"?apikey="+BuildConfig.API_KEY;
        dataManager.loadPicture(c, url, holder.thumb);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public interface CustomClickListener {
        void onItemClick(int position, View v);
    }


}
