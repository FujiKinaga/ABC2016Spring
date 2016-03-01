package jp.android_group.student.abc2016winter.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.android_group.student.abc2016winter.R;
import jp.android_group.student.abc2016winter.domain.model.Conference;
import jp.android_group.student.abc2016winter.ui.adapter.TimeTableListAdapter;

public class ConferenceListDetailActivity extends AppCompatActivity implements TimeTableListAdapter.TimeTableCallback, ObservableScrollViewCallbacks {

    private List<Conference> mConferenceList = new ArrayList<>();

    @Bind(R.id.list)
    ObservableRecyclerView mRecyclerView;
    @Bind(R.id.tool_bar)
    Toolbar mToolBar;

    private LinearLayoutManager mLinearLayoutManager;
    private TimeTableListAdapter mAdapter;

    public static void startConferenceListDetailActivity(ArrayList<Conference> conferenceList, String title, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, ConferenceListDetailActivity.class);
        intent.putParcelableArrayListExtra("conferenceList", conferenceList);
        intent.putExtra("title", title);
        startingActivity.startActivity(intent);
        startingActivity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference_list_detail);
        ButterKnife.bind(this);

        mConferenceList = getIntent().getParcelableArrayListExtra("conferenceList");
        String title = getIntent().getStringExtra("title");

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.setScrollViewCallbacks(this);
        mAdapter = new TimeTableListAdapter(this, mConferenceList);
        mAdapter.setTimeTableCallback(this);
        mRecyclerView.setAdapter(mAdapter);
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
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    public void onCellClick(int position) {
        ConferenceDetailActivity.startConferenceDetailActivity(mConferenceList.get(position), position, this);
    }

    @Override
    public void onFavoriteClick() {

    }
}
