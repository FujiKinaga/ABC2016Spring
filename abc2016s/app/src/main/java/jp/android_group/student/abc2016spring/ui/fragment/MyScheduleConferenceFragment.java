package jp.android_group.student.abc2016spring.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.andexert.library.RippleView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.android_group.student.abc2016spring.R;
import jp.android_group.student.abc2016spring.domain.repository.MyScheConferenceRepository;
import jp.android_group.student.abc2016spring.datasource.repository.MyScheConferenceRepositoryImpl;
import jp.android_group.student.abc2016spring.domain.executor.UIThread;
import jp.android_group.student.abc2016spring.domain.model.Conference;
import jp.android_group.student.abc2016spring.domain.usecase.MyScheConferenceUseCase;
import jp.android_group.student.abc2016spring.domain.usecase.MyScheConferenceUseCaseImpl;
import jp.android_group.student.abc2016spring.event.BusHolder;
import jp.android_group.student.abc2016spring.event.StarClickedEvent;
import jp.android_group.student.abc2016spring.presenter.ShowMyScheConferencePresenter;
import jp.android_group.student.abc2016spring.ui.activity.ConferenceDetailActivity;
import jp.android_group.student.abc2016spring.ui.activity.MainActivity;
import jp.android_group.student.abc2016spring.ui.adapter.MyScheConferenceListAdapter;
import jp.android_group.student.abc2016spring.ui.view.Fab;

public class MyScheduleConferenceFragment extends Fragment implements ShowMyScheConferencePresenter.ShowMyScheConferenceView,
        MyScheConferenceListAdapter.MyScheConferenceCallback, ObservableScrollViewCallbacks {

    @Bind(R.id.list)
    ObservableRecyclerView mRecyclerView;
    @Bind(R.id.search_time_table_ripple)
    RippleView mSearchTimeTableRipple;
    @Bind(R.id.search_conference_ripple)
    RippleView mSearchConferenceRipple;
    @Bind(R.id.empty_view)
    RelativeLayout mEmptyView;

    private LinearLayoutManager mLinearLayoutManager;
    private MyScheConferenceListAdapter mAdapter;

    private Fab mFab;

    private List<Conference> mConferenceList = new ArrayList<>();

    private ShowMyScheConferencePresenter mMyScheConferencePresenter;

    public MyScheduleConferenceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyScheConferenceRepository conferenceRepository = MyScheConferenceRepositoryImpl.getRepository();
        MyScheConferenceUseCase conferenceUseCase = MyScheConferenceUseCaseImpl.getUseCase(conferenceRepository, UIThread.getInstance());
        mMyScheConferencePresenter = new ShowMyScheConferencePresenter(conferenceUseCase);
        mMyScheConferencePresenter.setMyScheConferenceView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_schedule_conference, container, false);
        ButterKnife.bind(this, rootView);

        mFab = (Fab) getActivity().findViewById(R.id.fab);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.setScrollViewCallbacks(this);

        mSearchConferenceRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                ((MainActivity) getActivity()).searchConference();
            }
        });

        mSearchTimeTableRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                ((MainActivity) getActivity()).searchTimeTable();
            }
        });

        mMyScheConferencePresenter.getMyScheConferenceData();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusHolder.get().register(this);
        mMyScheConferencePresenter.resume();
    }

    @Override
    public void onPause() {
        BusHolder.get().unregister(this);
        mMyScheConferencePresenter.pause();
        super.onPause();
    }

    @Subscribe
    public void subscribe(StarClickedEvent event) {
        if (MySchedulePagerFragment.mShowPosition == 0) {
            mConferenceList.remove(event.position);
            mAdapter.notifyItemRemoved(event.position);

            if (mConferenceList.isEmpty()) {
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNoResultCase() {

    }

    @Override
    public void hideNoResultCase() {

    }

    @Override
    public void showResult(List<Conference> conferenceList) {
        mConferenceList = conferenceList;

        if (mConferenceList.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }

        mAdapter = new MyScheConferenceListAdapter(getActivity(), mConferenceList);
        mAdapter.setMyScheConferenceCallback(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCellClick(int position) {
        ConferenceDetailActivity.startConferenceDetailActivity(mConferenceList.get(position), position, getActivity());
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (scrollState == ScrollState.UP) {
            mFab.hide();
        } else if (scrollState == ScrollState.DOWN) {
            mFab.show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
