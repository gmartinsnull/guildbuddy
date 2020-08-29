package com.gomart.guildbuddy.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gomart.guildbuddy.Constants;
import com.gomart.guildbuddy.R;
import com.gomart.guildbuddy.helper.DialogHelper;
import com.gomart.guildbuddy.helper.NetworkHelper;
import com.gomart.guildbuddy.manager.DataManager;
import com.gomart.guildbuddy.vo.Character;
import com.gomart.guildbuddy.network.GetCharacterResponse;
import com.gomart.guildbuddy.ui.presenter.GuildMemberPresenter;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by glaubermartins on 2017-01-27.
 */

@Deprecated
public class GuildMemberProfileActivity extends AppCompatActivity {

    private TextView name, race, charClass, level, achievements, itemLevel, pvp, role;
    private ImageView thumb;
    private ProgressBar progress;

    private GuildMemberPresenter presenter;

    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        //GuildBuddy.app().getAppComponent().inject(this);

        Intent intent = getIntent();
        final Character character = intent.getParcelableExtra(GuildMembersActivity.GUILD_MEMBER);
        //Log.d("INTENT", i.getParcelableExtra(GuildMembersActivity.GUILD_MEMBER).toString());

        name = (TextView) findViewById(R.id.txt_name);
        race = (TextView) findViewById(R.id.txt_race);
        charClass = (TextView) findViewById(R.id.txt_class);
        role = (TextView) findViewById(R.id.txt_role);
        //thumb = (FloatingActionButton) findViewById(R.id.thumbnail);
        level = (TextView) findViewById(R.id.txt_level);
        itemLevel = (TextView) findViewById(R.id.txt_item_lvl);
        achievements = (TextView) findViewById(R.id.txt_achievements);
        pvp = (TextView) findViewById(R.id.txt_pvp);
        progress = (ProgressBar) findViewById(R.id.progress);

        LinearLayout layout = (LinearLayout) findViewById(R.id.container_profile);
        for (int i = 0; i < layout.getChildCount(); i++) {
            //Log.d("CHILD", layout.getChildCount() + " " + i);
            setTextViewBold((TextView) layout.getChildAt(i));
            i++;
        }

        progress.setVisibility(View.VISIBLE);
        presenter = new GuildMemberPresenter(this, dataManager.get(Constants.KEY_REALM, ""));
        presenter.setCharacterFields(character);
        if (NetworkHelper.isConnected(this)){
            presenter.getCharacter(new Callback<GetCharacterResponse>() {
                @Override
                public void onResponse(Call<GetCharacterResponse> call, Response<GetCharacterResponse> response) {
                    //Log.d("PROFILE", response.body().toString());
                    progress.setVisibility(View.GONE);
                    //itemLevel.setText(String.valueOf(response.body().getItems().getAvarageItemLevel()));
                    pvp.setText(String.valueOf(response.body().getTotalHonorableKills()));

                    //String url = BuildConfig.CHAR_URL + character.thumbnail + "?locale=" + BuildConfig.LOCALE + "?apikey=" + BuildConfig.API_KEY;
                    //dataManager.loadPictureBitmap(GuildMemberProfileActivity.this, url, target);
                }

                @Override
                public void onFailure(Call<GetCharacterResponse> call, Throwable t) {

                }
            });
        }else{
            DialogHelper.showOkDialog(this, getString(R.string.oops), getString(R.string.no_connection));
        }

//        name.setText(character.name);
//        race.setText(dataManager.getCharacterRace(character.race));
//        charClass.setText(dataManager.getCharacterClass(character.charClass));
//        role.setText(character.spec.name);
//        level.setText(String.valueOf(character.level));
//        achievements.setText(String.valueOf(character.achievementPoints));



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

    /*private Target target = new Target() {
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
    };*/
}
