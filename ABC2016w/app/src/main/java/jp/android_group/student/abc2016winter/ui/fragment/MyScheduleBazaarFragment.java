package jp.android_group.student.abc2016winter.ui.fragment;


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
import jp.android_group.student.abc2016winter.R;
import jp.android_group.student.abc2016winter.domain.repository.MyScheBazaarRepository;
import jp.android_group.student.abc2016winter.datasource.repository.MyScheBazaarRepositoryImpl;
import jp.android_group.student.abc2016winter.domain.executor.UIThread;
import jp.android_group.student.abc2016winter.domain.model.Bazaar;
import jp.android_group.student.abc2016winter.domain.usecase.MyScheBazaarUseCase;
import jp.android_group.student.abc2016winter.domain.usecase.MyScheBazaarUseCaseImpl;
import jp.android_group.student.abc2016winter.event.BusHolder;
import jp.android_group.student.abc2016winter.event.StarClickedEvent;
import jp.android_group.student.abc2016winter.presenter.ShowMyScheBazaarPresenter;
import jp.android_group.student.abc2016winter.ui.activity.BazaarDetailActivity;
import jp.android_group.student.abc2016winter.ui.activity.MainActivity;
import jp.android_group.student.abc2016winter.ui.adapter.MyScheBazaarListAdapter;
import jp.android_group.student.abc2016winter.ui.view.Fab;

public class MyScheduleBazaarFragment extends Fragment implements ShowMyScheBazaarPresenter.ShowMyScheBazaarView,
        MyScheBazaarListAdapter.MyScheBazaarCallback, ObservableScrollViewCallbacks {

    @Bind(R.id.list)
    ObservableRecyclerView mRecyclerView;
    @Bind(R.id.search_bazaar_ripple)
    RippleView mSearchBazaarRipple;
    @Bind(R.id.empty_view)
    RelativeLayout mEmptyView;

    private LinearLayoutManager mLinearLayoutManager;
    private MyScheBazaarListAdapter mAdapter;

    private Fab mFab;

    private List<Bazaar> mBazaarList = new ArrayList<>();

    private ShowMyScheBazaarPresenter mMyScheBazaarPresenter;

    public MyScheduleBazaarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyScheBazaarRepository bazaarRepository = MyScheBazaarRepositoryImpl.getRepository();
        MyScheBazaarUseCase bazaarUseCase = MyScheBazaarUseCaseImpl.getUseCase(bazaarRepository, UIThread.getInstance());
        mMyScheBazaarPresenter = new ShowMyScheBazaarPresenter(bazaarUseCase);
        mMyScheBazaarPresenter.setMyScheBazaarView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_schedule_bazaar, container, false);
        ButterKnife.bind(this, rootView);

        mFab = (Fab) getActivity().findViewById(R.id.fab);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.setScrollViewCallbacks(this);

        mSearchBazaarRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                ((MainActivity) getActivity()).searchBazaar();
            }
        });

        mMyScheBazaarPresenter.getMyScheBazaarData();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusHolder.get().register(this);
        mMyScheBazaarPresenter.resume();
    }

    @Override
    public void onPause() {
        BusHolder.get().unregister(this);
        mMyScheBazaarPresenter.pause();
        super.onPause();
    }

    @Subscribe
    public void subscribe(StarClickedEvent event) {
        if (MySchedulePagerFragment.mShowPosition == 1) {
            mBazaarList.remove(event.position);
            mAdapter.notifyItemRemoved(event.position);

            if (mBazaarList.isEmpty()) {
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
    public void showResult(List<Bazaar> bazaarList) {
        mBazaarList = bazaarList;

        if (mBazaarList.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }

        mAdapter = new MyScheBazaarListAdapter(getActivity(), mBazaarList);
        mAdapter.setMyScheBazaarCallback(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCellClick(int position) {
        BazaarDetailActivity.startBazaarDetailActivity(mBazaarList.get(position), position, getActivity());
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
