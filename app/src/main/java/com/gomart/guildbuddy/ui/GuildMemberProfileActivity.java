package com.gomart.guildbuddy.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gomart.guildbuddy.BuildConfig;
import com.gomart.guildbuddy.Constants;
import com.gomart.guildbuddy.GuildBuddy;
import com.gomart.guildbuddy.R;
import com.gomart.guildbuddy.manager.DataManager;
import com.gomart.guildbuddy.model.Character;
import com.gomart.guildbuddy.network.GetCharacterResponse;
import com.gomart.guildbuddy.ui.presenter.GuildMemberProfilePresenter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by glaubermartins on 2017-01-27.
 */

public class GuildMemberProfileActivity extends AppCompatActivity {

    private TextView name, race, charClass, level, achievements, itemLevel, pvp, role;
    private ImageView thumb;
    private ProgressBar progress;

    private GuildMemberProfilePresenter presenter;

    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

        GuildBuddy.app().getAppComponent().inject(this);

        Intent intent = getIntent();
        final Character c = intent.getParcelableExtra(GuildMembersActivity.GUILD_MEMBER);
        //Log.d("INTENT", i.getParcelableExtra(GuildMembersActivity.GUILD_MEMBER).toString());

        name = (TextView) findViewById(R.id.txt_name);
        race = (TextView) findViewById(R.id.txt_race);
        charClass = (TextView) findViewById(R.id.txt_class);
        role = (TextView) findViewById(R.id.txt_role);
        thumb = (FloatingActionButton) findViewById(R.id.thumbnail);
        level = (TextView) findViewById(R.id.txt_level);
        itemLevel = (TextView) findViewById(R.id.txt_item_lvl);
        achievements = (TextView) findViewById(R.id.txt_achievements);
        pvp = (TextView) findViewById(R.id.txt_pvp);
        progress = (ProgressBar) findViewById(R.id.progress);

        LinearLayout layout = (LinearLayout) findViewById(R.id.container_profile);
        for (int i = 0; i < layout.getChildCount(); i++) {
            Log.d("CHILD", layout.getChildCount() + " " + i);
            setTextViewBold((TextView) layout.getChildAt(i));
            i++;
        }

        progress.setVisibility(View.VISIBLE);
        presenter = new GuildMemberProfilePresenter(this, c, dataManager.get(Constants.KEY_REALM, ""));
        presenter.getCharacter(new Callback<GetCharacterResponse>() {
            @Override
            public void onResponse(Call<GetCharacterResponse> call, Response<GetCharacterResponse> response) {
                //Log.d("PROFILE", response.body().toString());
                progress.setVisibility(View.GONE);
                itemLevel.setText(String.valueOf(response.body().getItems().getAvarageItemLevel()));
                pvp.setText(String.valueOf(response.body().getTotalHonorableKills()));
            }

            @Override
            public void onFailure(Call<GetCharacterResponse> call, Throwable t) {

            }
        });


        name.setText(c.getName());
        race.setText(dataManager.getCharacterRace(c.getRace()));
        charClass.setText(dataManager.getCharacterClass(c.getCharClass()));
        role.setText(c.getSpec().getName());
        level.setText(String.valueOf(c.getLevel()));
        achievements.setText(String.valueOf(c.getAchievementPoints()));

        String url = BuildConfig.CHAR_URL + c.getThumbnail() + "?locale=" + BuildConfig.LOCALE + "?apikey=" + BuildConfig.API_KEY;
        dataManager.loadPictureBitmap(this, url, target);

    }

    private void setTextViewBold(TextView view) {
        view.setTypeface(null, Typeface.BOLD);
    }

    private Bitmap cropImageRound(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            thumb.setImageBitmap(cropImageRound(bitmap));
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };
}
