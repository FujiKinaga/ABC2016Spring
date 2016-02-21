package jp.android_group.student.abc2015summer;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , View.OnClickListener {

    private static final String STATE_POSITION = "selectedPosition";
    private int mCurrentPosition;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initDrawer();

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_drawer);
        mToolbar.setNavigationOnClickListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container
                    , new TimeTablePagerFragment()).commit();
            mNavigationView.getMenu().getItem(0).setChecked(true);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.footer, new DrawerFooterFragment()).commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.pink_accent));
        }
    }

    private void initDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar
                , R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_POSITION, mCurrentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentPosition = savedInstanceState.getInt(STATE_POSITION, 0);
        Menu menu = mNavigationView.getMenu();
        menu.getItem(mCurrentPosition).setChecked(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // 詳細情報からNavigationDrawerクリックされたときは，BackStackを全削除
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        menuItem.setChecked(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (menuItem.getItemId()) {
            case R.id.drawer_time_table:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new TimeTablePagerFragment()).commit();
                mTitle = getString(R.string.title_time_table);
                mCurrentPosition = 0;
                break;
            case R.id.drawer_my_schedule:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new MySchedulePagerFragment()).commit();
                mTitle = getString(R.string.title_my_schedule);
                mCurrentPosition = 1;
                break;
            case R.id.drawer_conference:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ConferencePagerFragment()).commit();
                mTitle = getString(R.string.title_conference);
                mCurrentPosition = 2;
                break;
            case R.id.drawer_bazaar:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new BazaarListPagerFragment()).commit();
                mTitle = getString(R.string.title_bazaar);
                mCurrentPosition = 3;
                break;
            case R.id.drawer_session:
                //fragmentManager.beginTransaction().replace(R.id.container, new TestSessionListFragment()).addToBackStack(null).commit();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new SessionPagerFragment()).commit();
                mTitle = getString(R.string.title_session);
                mCurrentPosition = 4;
                break;
            case R.id.youtube_live:
                fragmentManager.beginTransaction()
                        .replace(R.id.container,new YoutubePagerFragment()).commit();
                mCurrentPosition = 5;
                mTitle = getString(R.string.title_youtube);
                break;
            case R.id.drawer_official:
                String abcUrl = "http://abc.android-group.jp/2015s/";
                Uri uri = Uri.parse(abcUrl);
                Intent intentBrowser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intentBrowser);
                mCurrentPosition = 6;
                break;
            case R.id.drawer_about:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new AboutFragment()).commit();
                mCurrentPosition = 7;
                mTitle = getString(R.string.title_about);
                break;
        }
        mToolbar.setTitle(mTitle);
        mDrawerLayout.closeDrawer(mNavigationView);
        return true;
    }

    @Override
    public void onClick(View v) {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private boolean doubleBackToExitPressedOnce;

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawers();
            return;
        }

        final int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

        if (backStackCount > 0 || this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "もう一度押すと終了します", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
