package jagsc.org.abc.info.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gordonwong.materialsheetfab.DimOverlayFrameLayout;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import jagsc.org.abc.info.R;
import jagsc.org.abc.info.Util;
import jagsc.org.abc.info.ui.fragment.AboutFragment;
import jagsc.org.abc.info.ui.fragment.ListBazaarFragment;
import jagsc.org.abc.info.ui.fragment.ListConferenceFragment;
import jagsc.org.abc.info.ui.fragment.PagerMyScheFragment;
import jagsc.org.abc.info.ui.fragment.OfficialFragment;
import jagsc.org.abc.info.ui.fragment.PagerTimeTableFragment;
import jagsc.org.abc.info.ui.fragment.VoteFragment;
import jagsc.org.abc.info.ui.view.Fab;
import jagsc.org.abc.info.ui.view.StarLayout;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.star_layout)
    StarLayout mStarLayout;
    @Bind(R.id.fab)
    Fab mFab;
    @Bind(R.id.overlay)
    DimOverlayFrameLayout mOverlay;
    @Bind(R.id.map_bazaar)
    ImageView mMapBazaar;
    @Bind(R.id.map_4f)
    ImageView mMap4F;
    @Bind(R.id.map_6f)
    ImageView mMap6F;
    @Bind(R.id.sheet)
    CardView mSheet;
    @Bind(R.id.map_conference)
    LinearLayout mMapConference;

    private PhotoViewAttacher mAttacherBazaar;
    private PhotoViewAttacher mAttacher4f;
    private PhotoViewAttacher mAttacher6f;

    private Drawer result;

    private MaterialSheetFab materialSheetFab;

    private float pointX;
    private float pointY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMapBazaar.setVisibility(View.GONE);
        mMapConference.setVisibility(View.VISIBLE);

        Drawable bitmap = getResources().getDrawable(R.drawable.ic_map_bazaar);
        Drawable bitmap_4f = getResources().getDrawable(R.drawable.ic_map_time_table_4f);
        Drawable bitmap_6f = getResources().getDrawable(R.drawable.ic_map_time_table_6f);
        mMapBazaar.setImageDrawable(bitmap);
        mMap4F.setImageDrawable(bitmap_4f);
        mMap6F.setImageDrawable(bitmap_6f);

        mAttacherBazaar = new PhotoViewAttacher(mMapBazaar);
        mAttacher4f = new PhotoViewAttacher(mMap4F);
        mAttacher6f = new PhotoViewAttacher(mMap6F);

        mToolBar.setTitle(getString(R.string.title_time_table));
        setSupportActionBar(mToolBar);

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolBar)
                .withHeader(R.layout.drawer_header)
                .withFooter(R.layout.drawer_footer)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(getString(R.string.title_time_table)).withIdentifier(1),
                        new PrimaryDrawerItem().withName(getString(R.string.title_my_schedule)).withIdentifier(2),
                        new PrimaryDrawerItem().withName(getString(R.string.title_conference)).withIdentifier(3),
                        new PrimaryDrawerItem().withName(getString(R.string.title_bazaar)).withIdentifier(4),
                        new PrimaryDrawerItem().withName(getString(R.string.title_vote)).withIdentifier(5),
                        new PrimaryDrawerItem().withName(getString(R.string.title_official_site)).withIdentifier(6),
                        new PrimaryDrawerItem().withName(getString(R.string.title_about)).withIdentifier(7)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            if (drawerItem.getIdentifier() == 1) {
                                mToolBar.setTitle(getString(R.string.title_time_table));
                                getSupportFragmentManager().beginTransaction().replace(R.id.container
                                        , new PagerTimeTableFragment()).commit();
                            } else if (drawerItem.getIdentifier() == 2) {
                                mToolBar.setTitle(getString(R.string.title_my_schedule));
                                getSupportFragmentManager().beginTransaction().replace(R.id.container
                                        , new PagerMyScheFragment()).commit();
                            } else if (drawerItem.getIdentifier() == 3) {
                                mToolBar.setTitle(getString(R.string.title_conference));
                                getSupportFragmentManager().beginTransaction().replace(R.id.container
                                        , new ListConferenceFragment()).commit();
                            } else if (drawerItem.getIdentifier() == 4) {
                                mToolBar.setTitle(getString(R.string.title_bazaar));
                                getSupportFragmentManager().beginTransaction().replace(R.id.container
                                        , new ListBazaarFragment()).commit();
                            } else if (drawerItem.getIdentifier() == 5) {
                                mToolBar.setTitle(getString(R.string.title_vote));
                                getSupportFragmentManager().beginTransaction().replace(R.id.container
                                        , new VoteFragment()).commit();
                            } else if (drawerItem.getIdentifier() == 6) {
                                mToolBar.setTitle(getString(R.string.title_official_site));
                                getSupportFragmentManager().beginTransaction().replace(R.id.container
                                        , new OfficialFragment()).commit();
                            } else if (drawerItem.getIdentifier() == 7) {
                                mToolBar.setTitle(getString(R.string.title_about));
                                getSupportFragmentManager().beginTransaction().replace(R.id.container
                                        , new AboutFragment()).commit();
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(1)
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View view) {
                        finish();
                        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                        return true;
                    }
                })
                .build();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container
                    , new PagerTimeTableFragment()).commit();
        }

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

        mStarLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                //final float y = Util.getScreenHeightInPx(TimelineActivity.this) - event.getRawY();
                pointX = event.getX();
                pointY = event.getY();
                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Need to call clean-up
        mAttacherBazaar.cleanup();
        mAttacher4f.cleanup();
        mAttacher6f.cleanup();
    }

    public void setStarLayout() {
        final float y = Util.getScreenHeightInPx(this) - pointY;
        mStarLayout.post(new Runnable() {
            @Override
            public void run() {
                mStarLayout.addStar(pointX, y);
            }
        });
    }

    public void searchTimeTable() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        mToolBar.setTitle(getString(R.string.title_time_table));
        getSupportFragmentManager().beginTransaction().replace(R.id.container
                , new PagerTimeTableFragment()).commit();
        result.setSelection(1);
    }

    public void searchConference() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        mToolBar.setTitle(getString(R.string.title_conference));
        getSupportFragmentManager().beginTransaction().replace(R.id.container
                , new ListConferenceFragment()).commit();
        result.setSelection(3);
    }

    public void searchBazaar() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        mToolBar.setTitle(getString(R.string.title_bazaar));
        getSupportFragmentManager().beginTransaction().replace(R.id.container
                , new ListBazaarFragment()).commit();
        result.setSelection(4);
    }
}
