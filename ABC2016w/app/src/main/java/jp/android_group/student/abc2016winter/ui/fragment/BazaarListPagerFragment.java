package jp.android_group.student.abc2016winter.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.android_group.student.abc2016winter.R;
import jp.android_group.student.abc2016winter.datasource.repository.BazaarRepository;
import jp.android_group.student.abc2016winter.datasource.repository.BazaarRepositoryImpl;
import jp.android_group.student.abc2016winter.domain.executor.UIThread;
import jp.android_group.student.abc2016winter.domain.model.Bazaar;
import jp.android_group.student.abc2016winter.domain.usecase.BazaarUseCase;
import jp.android_group.student.abc2016winter.domain.usecase.BazaarUseCaseImpl;
import jp.android_group.student.abc2016winter.event.BusHolder;
import jp.android_group.student.abc2016winter.event.StarClickedEvent;
import jp.android_group.student.abc2016winter.presenter.ShowBazaarPresenter;
import jp.android_group.student.abc2016winter.ui.activity.BazaarDetailActivity;
import jp.android_group.student.abc2016winter.ui.activity.MainActivity;
import jp.android_group.student.abc2016winter.ui.adapter.BazaarListAdapter;
import jp.android_group.student.abc2016winter.ui.view.Fab;

public class BazaarListPagerFragment extends Fragment implements ShowBazaarPresenter.ShowBazaarView,
        BazaarListAdapter.BazaarCallback, ObservableScrollViewCallbacks {

    @Bind(R.id.list)
    ObservableRecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private BazaarListAdapter mAdapter;

    private Fab mFab;

    private List<Bazaar> mBazaarList = new ArrayList<>();

    private ShowBazaarPresenter mBazaarPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BazaarRepository bazaarRepository = BazaarRepositoryImpl.getRepository();
        BazaarUseCase bazaarUseCase = BazaarUseCaseImpl.getUseCase(bazaarRepository, UIThread.getInstance());
        mBazaarPresenter = new ShowBazaarPresenter(bazaarUseCase);
        mBazaarPresenter.setBazaarView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bazaar_list, container, false);
        ButterKnife.bind(this, rootView);

        mFab = (Fab) getActivity().findViewById(R.id.fab);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.setScrollViewCallbacks(this);

        mBazaarPresenter.getBazaarData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusHolder.get().register(this);
        mBazaarPresenter.resume();
    }

    @Override
    public void onPause() {
        BusHolder.get().unregister(this);
        mBazaarPresenter.pause();
        super.onPause();
    }

    @Subscribe
    public void subscribe(StarClickedEvent event) {
        mAdapter.notifyItemChanged(event.position);
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

        mAdapter = new BazaarListAdapter(getActivity(), mBazaarList);
        mAdapter.setBazaarCallback(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCellClick(int position) {
        BazaarDetailActivity.startBazaarDetailActivity(mBazaarList.get(position), position, getActivity());
    }

    @Override
    public void onFavoriteClick() {
        ((MainActivity) getActivity()).setStarLayout();
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
