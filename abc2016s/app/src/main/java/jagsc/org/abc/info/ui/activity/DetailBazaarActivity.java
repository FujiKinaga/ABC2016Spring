package jagsc.org.abc.info.ui.activity;

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
import butterknife.OnClick;
import jagsc.org.abc.info.R;
import jagsc.org.abc.info.datasource.action.FavoriteAction;
import jagsc.org.abc.info.domain.model.Bazaar;
import jagsc.org.abc.info.event.BusHolder;
import jagsc.org.abc.info.event.StarClickedEvent;
import jagsc.org.abc.info.ui.view.Fab;
import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailBazaarActivity extends AppCompatActivity {

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
        if (FavoriteAction.isFavoriteBazaar(DetailBazaarActivity.this, mBazaar.getId())) {
            FavoriteAction.unFavoriteBazaar(DetailBazaarActivity.this, mBazaar.getId());
            mBtnFavorite.setBackgroundResource(R.drawable.ic_star_border_grey_600_36dp);
        } else {
            FavoriteAction.favoriteBazaar(DetailBazaarActivity.this, mBazaar.getId());
            mBtnFavorite.setBackgroundResource(R.drawable.ic_star_yellow_600_36dp);
        }
    }

    private PhotoViewAttacher mAttacher;

    private MaterialSheetFab materialSheetFab;

    private Bazaar mBazaar;
    private int mPosition;

    private boolean isChange = false;

    public static void startBazaarDetailActivity(Bazaar bazaar, int position, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, DetailBazaarActivity.class);
        intent.putExtra("bazaar", bazaar);
        intent.putExtra("position", position);
        startingActivity.startActivity(intent);
        startingActivity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bazaar);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.title_bazaar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawable bitmap = getResources().getDrawable(R.drawable.ic_map_bazaar);
        mMapView.setImageDrawable(bitmap);

        mAttacher = new PhotoViewAttacher(mMapView);

        int sheetColor = getResources().getColor(R.color.view_background);
        int fabColor = getResources().getColor(R.color.main_color);
        materialSheetFab = new MaterialSheetFab<>(mFab, mSheet, mOverlay, sheetColor, fabColor);

        mBazaar = getIntent().getParcelableExtra("bazaar");
        mPosition = getIntent().getIntExtra("position", 0);

        mTitle.setText(mBazaar.getTitle());
        mGroup.setText(mBazaar.getGroup());
        mLocation.setText(mBazaar.getLocation());
        mContent.setText(mBazaar.getContent());

        mBtnFavorite.setBackgroundResource(FavoriteAction.isFavoriteBazaar(this, mBazaar.getId()) ?
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
        mAttacher.cleanup();
        if (isChange) {
            BusHolder.get().post(new StarClickedEvent(mPosition));
        }
    }
}
