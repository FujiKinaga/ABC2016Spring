package jagsc.org.abc.info.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.DimOverlayFrameLayout;
import com.gordonwong.materialsheetfab.MaterialSheetFab;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jagsc.org.abc.info.R;
import jagsc.org.abc.info.datasource.action.FavoriteAction;
import jagsc.org.abc.info.domain.model.Conference;
import jagsc.org.abc.info.event.BusHolder;
import jagsc.org.abc.info.event.StarClickedEvent;
import jagsc.org.abc.info.ui.view.Fab;
import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailConferenceActivity extends AppCompatActivity {

    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.btn_favorite)
    ImageButton mBtnFavorite;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.time)
    TextView mTime;
    @Bind(R.id.room)
    TextView mRoom;
    @Bind(R.id.category)
    TextView mCategory;
    @Bind(R.id.abst)
    TextView mAbst;
    @Bind(R.id.speaker_cell)
    LinearLayout mSpeakerCell;
    @Bind(R.id.overlay)
    DimOverlayFrameLayout mOverlay;
    @Bind(R.id.map_conference)
    ImageView mMapConference;
    @Bind(R.id.sheet)
    CardView mSheet;
    @Bind(R.id.fab)
    Fab mFab;

    @OnClick(R.id.fab)
    void fab() {
        if (mFab.getVisibility() == View.VISIBLE) {
            materialSheetFab.showSheet();
        }
    }

    @OnClick(R.id.overlay)
    void overlay() {
        if (mFab.getVisibility() != View.VISIBLE) {
            materialSheetFab.hideSheet();
        }
    }

    @OnClick(R.id.btn_favorite)
    void favorite() {
        isChange = !isChange;
        if (FavoriteAction.isFavoriteConference(DetailConferenceActivity.this, mConference.getTitle())) {
            FavoriteAction.unFavoriteConference(DetailConferenceActivity.this, mConference.getTitle());
            mBtnFavorite.setBackgroundResource(R.drawable.ic_star_border_grey_600_36dp);
        } else {
            FavoriteAction.favoriteConference(DetailConferenceActivity.this, mConference.getTitle());
            mBtnFavorite.setBackgroundResource(R.drawable.ic_star_yellow_600_36dp);
        }
    }

    private PhotoViewAttacher mAttacherConference;

    private MaterialSheetFab materialSheetFab;

    private Conference mConference;
    private int mPosition;

    private boolean isChange = false;

    public static void startConferenceDetailActivity(Conference conference, int position, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, DetailConferenceActivity.class);
        intent.putExtra("conference", conference);
        intent.putExtra("position", position);
        startingActivity.startActivity(intent);
        startingActivity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_conference);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.title_conference));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable bitmapConference = getResources().getDrawable(R.drawable.ic_map_conference);
        mMapConference.setImageDrawable(bitmapConference);

        mAttacherConference = new PhotoViewAttacher(mMapConference);

        int sheetColor = getResources().getColor(R.color.view_background);
        int fabColor = getResources().getColor(R.color.main_color);
        materialSheetFab = new MaterialSheetFab<>(mFab, mSheet, mOverlay, sheetColor, fabColor);

        mConference = getIntent().getParcelableExtra("conference");
        mPosition = getIntent().getIntExtra("position", 0);

        mTitle.setText(mConference.getTitle());
        mTime.setText(mConference.getStartTime().substring(11, 16) + " - " + mConference.getEndTime().substring(11, 16));
        switch (mConference.getRoom_id()) {
            case 1:
                mRoom.setText(R.string.room_1);
                break;
            case 2:
                mRoom.setText(R.string.room_2);
                break;
            case 3:
                mRoom.setText(R.string.room_3);
                break;
            case 4:
                mRoom.setText(R.string.room_4);
                break;
            case 5:
                mRoom.setText(R.string.room_5);
                break;
            case 6:
                mRoom.setText(R.string.room_6);
                break;
            case 7:
                mRoom.setText(R.string.room_7);
                break;
            case 8:
                mRoom.setText(R.string.room_8);
                break;
        }
        switch (mConference.getCategory_id()) {
            case 1:
                mCategory.setText(R.string.category_1);
                break;
            case 2:
                mCategory.setText(R.string.category_2);
                break;
            case 3:
                mCategory.setText(R.string.category_3);
                break;
            case 4:
                mCategory.setText(R.string.category_4);
                break;
            case 5:
                mCategory.setText(R.string.category_5);
                break;
            case 6:
                mCategory.setText(R.string.category_6);
                break;
            case 7:
                mCategory.setText(R.string.category_7);
                break;
            case 8:
                mCategory.setText(R.string.category_8);
                break;
            case 9:
                mCategory.setText(R.string.category_9);
                break;
        }
        mAbst.setText(mConference.getAbst());

        for (int i = 0; i < mConference.getSpeaker().getName().size(); i++) {
            TextView name = new TextView(this);
            name.setText(mConference.getSpeaker().getName().get(i));
            name.setTextSize(16);
            name.setTypeface(Typeface.DEFAULT_BOLD);
            name.setPadding(0, 16, 0, 0);
            mSpeakerCell.addView(name);
            TextView profile = new TextView(this);
            profile.setText(mConference.getSpeaker().getProfile().get(i));
            profile.setPadding(0, 4, 0, 16);
            mSpeakerCell.addView(profile);
        }

        mBtnFavorite.setBackgroundResource(FavoriteAction.isFavoriteConference(this, mConference.getTitle()) ?
                R.drawable.ic_star_yellow_600_36dp : R.drawable.ic_star_border_grey_600_36dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Need to call clean-up
        mAttacherConference.cleanup();
        if (isChange) {
            BusHolder.get().post(new StarClickedEvent(mPosition));
        }
    }
}
