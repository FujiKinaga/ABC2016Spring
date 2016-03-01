package jp.android_group.student.abc2016winter.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.DimOverlayFrameLayout;
import com.gordonwong.materialsheetfab.MaterialSheetFab;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.android_group.student.abc2016winter.R;
import jp.android_group.student.abc2016winter.datasource.api.FavoriteAction;
import jp.android_group.student.abc2016winter.domain.model.Bazaar;
import jp.android_group.student.abc2016winter.event.BusHolder;
import jp.android_group.student.abc2016winter.event.StarClickedEvent;
import jp.android_group.student.abc2016winter.ui.view.Fab;
import uk.co.senab.photoview.PhotoViewAttacher;

public class BazaarDetailActivity extends AppCompatActivity {

    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.btn_favorite)
    ImageButton mBtnFavorite;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.group)
    TextView mGroup;
    @Bind(R.id.location)
    TextView mLocation;
    @Bind(R.id.content)
    TextView mContent;
    @Bind(R.id.overlay)
    DimOverlayFrameLayout mOverlay;
    @Bind(R.id.map_view)
    ImageView mMapView;
    @Bind(R.id.sheet)
    CardView mSheet;
    @Bind(R.id.fab)
    Fab mFab;

    private PhotoViewAttacher mAttacher;

    private MaterialSheetFab materialSheetFab;

    private Bazaar mBazaar;
    private int mPosition;

    private boolean isChange = false;

    public static void startBazaarDetailActivity(Bazaar bazaar, int position, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, BazaarDetailActivity.class);
        intent.putExtra("bazaar", bazaar);
        intent.putExtra("position", position);
        startingActivity.startActivity(intent);
        startingActivity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazaar_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.title_bazaar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable bitmap = getResources().getDrawable(R.drawable.ic_map);
        mMapView.setImageDrawable(bitmap);

        mAttacher = new PhotoViewAttacher(mMapView);

        int sheetColor = getResources().getColor(R.color.view_background);
        int fabColor = getResources().getColor(R.color.main_color);
        materialSheetFab = new MaterialSheetFab<>(mFab, mSheet, mOverlay, sheetColor, fabColor);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFab.getVisibility() == View.VISIBLE) {
                    materialSheetFab.showSheet();
                }
            }
        });

        mOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFab.getVisibility() != View.VISIBLE) {
                    materialSheetFab.hideSheet();
                }
            }
        });

        mBazaar = getIntent().getParcelableExtra("bazaar");
        mPosition = getIntent().getIntExtra("position", 0);

        mTitle.setText(mBazaar.getTitle());
        mGroup.setText(mBazaar.getGroup());
        mLocation.setText(mBazaar.getLocation());
        mContent.setText(mBazaar.getContent());

        mBtnFavorite.setBackgroundResource(FavoriteAction.isFavoriteBazaar(this, mBazaar.getId()) ?
                R.drawable.ic_star_yellow_600_36dp : R.drawable.ic_star_border_grey_600_36dp);

        mBtnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChange = !isChange;
                if (FavoriteAction.isFavoriteBazaar(BazaarDetailActivity.this, mBazaar.getId())) {
                    FavoriteAction.unFavoriteBazaar(BazaarDetailActivity.this, mBazaar.getId());
                    mBtnFavorite.setBackgroundResource(R.drawable.ic_star_border_grey_600_36dp);
                } else {
                    FavoriteAction.favoriteBazaar(BazaarDetailActivity.this, mBazaar.getId());
                    mBtnFavorite.setBackgroundResource(R.drawable.ic_star_yellow_600_36dp);
                    //mCallback.onFavoriteClick();
                }
            }
        });
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
        mAttacher.cleanup();
        if (isChange) {
            BusHolder.get().post(new StarClickedEvent(mPosition));
        }
    }
}
