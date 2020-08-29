package com.gomart.guildbuddy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gomart.guildbuddy.R;
import com.gomart.guildbuddy.manager.DataManager;
import com.gomart.guildbuddy.vo.Character;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by glaubermartins on 2016-11-29.
 */
@Deprecated
public class GuildMembersAdapter extends RecyclerView.Adapter<GuildMembersAdapter.CustomViewHolder> {

    private Context c;
    private ArrayList<Character> characters;

    @Inject
    DataManager dataManager;

    public GuildMembersAdapter(Context c, ArrayList<Character> characters) {
        this.c = c;
        this.characters = characters;
        //GuildBuddy.app().getAppComponent().inject(this);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Character character =  characters.get(position);
        /*holder.name.setText(character.name);
        holder.level.setText(String.valueOf(character.level));
        holder.race.setText(dataManager.getCharacterRace(character.race));
        holder.charClass.setText(dataManager.getCharacterClass(character.charClass));

        String url = BuildConfig.CHAR_URL+ character.thumbnail +"?locale="+BuildConfig.LOCALE+"?apikey="+BuildConfig.API_KEY;
        dataManager.loadPicture(c, url, holder.thumb);*/
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public interface CustomClickListener {
        void onItemClick(int position, View v);
    }




}
