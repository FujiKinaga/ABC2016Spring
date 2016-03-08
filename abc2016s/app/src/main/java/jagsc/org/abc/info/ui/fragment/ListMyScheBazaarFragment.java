package jagsc.org.abc.info.ui.fragment;


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
import jagsc.org.abc.info.R;
import jagsc.org.abc.info.datasource.repository.MyScheBazaarRepositoryImpl;
import jagsc.org.abc.info.domain.executor.UIThread;
import jagsc.org.abc.info.domain.model.Bazaar;
import jagsc.org.abc.info.domain.repository.MyScheBazaarRepository;
import jagsc.org.abc.info.domain.usecase.MyScheBazaarUseCase;
import jagsc.org.abc.info.domain.usecase.MyScheBazaarUseCaseImpl;
import jagsc.org.abc.info.event.BusHolder;
import jagsc.org.abc.info.event.StarClickedEvent;
import jagsc.org.abc.info.presenter.ShowMyScheBazaarPresenter;
import jagsc.org.abc.info.ui.activity.DetailBazaarActivity;
import jagsc.org.abc.info.ui.activity.MainActivity;
import jagsc.org.abc.info.ui.adapter.ListMyScheBazaarAdapter;
import jagsc.org.abc.info.ui.view.Fab;

public class ListMyScheBazaarFragment extends Fragment implements ShowMyScheBazaarPresenter.ShowMyScheBazaarView,
        ListMyScheBazaarAdapter.MyScheBazaarCallback, ObservableScrollViewCallbacks {

    @Bind(R.id.list)
    ObservableRecyclerView mRecyclerView;
    @Bind(R.id.search_bazaar_ripple)
    RippleView mSearchBazaarRipple;
    @Bind(R.id.empty_view)
    RelativeLayout mEmptyView;

    private LinearLayoutManager mLinearLayoutManager;
    private ListMyScheBazaarAdapter mAdapter;

    private Fab mFab;

    private List<Bazaar> mBazaarList = new ArrayList<>();

    private ShowMyScheBazaarPresenter mMyScheBazaarPresenter;

    public ListMyScheBazaarFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_list_my_sche_bazaar, container, false);
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
        if (PagerMyScheFragment.mShowPosition == 1) {
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

        mAdapter = new ListMyScheBazaarAdapter(getActivity(), mBazaarList);
        mAdapter.setMyScheBazaarCallback(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCellClick(int position) {
        DetailBazaarActivity.startBazaarDetailActivity(mBazaarList.get(position), position, getActivity());
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
