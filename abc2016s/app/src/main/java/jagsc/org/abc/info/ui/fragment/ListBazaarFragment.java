package jagsc.org.abc.info.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jagsc.org.abc.info.R;
import jagsc.org.abc.info.datasource.repository.BazaarRepositoryImpl;
import jagsc.org.abc.info.domain.executor.UIThread;
import jagsc.org.abc.info.domain.model.Bazaar;
import jagsc.org.abc.info.domain.repository.BazaarRepository;
import jagsc.org.abc.info.domain.usecase.BazaarUseCase;
import jagsc.org.abc.info.domain.usecase.BazaarUseCaseImpl;
import jagsc.org.abc.info.event.BusHolder;
import jagsc.org.abc.info.event.StarClickedEvent;
import jagsc.org.abc.info.presenter.ShowBazaarPresenter;
import jagsc.org.abc.info.ui.activity.DetailBazaarActivity;
import jagsc.org.abc.info.ui.activity.MainActivity;
import jagsc.org.abc.info.ui.adapter.ListBazaarAdapter;
import jagsc.org.abc.info.ui.view.Fab;

public class ListBazaarFragment extends Fragment implements ShowBazaarPresenter.ShowBazaarView,
        ListBazaarAdapter.BazaarCallback, ObservableScrollViewCallbacks {

    @Bind(R.id.list)
    ObservableRecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private ListBazaarAdapter mAdapter;

    private Fab mFab;
    private ImageView mMapConference;
    private ImageView mMapBazaar;

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
        View rootView = inflater.inflate(R.layout.fragment_list_bazaar, container, false);
        ButterKnife.bind(this, rootView);

        mFab = (Fab) getActivity().findViewById(R.id.fab);
        mMapConference = (ImageView) getActivity().findViewById(R.id.map_conference);
        mMapBazaar = (ImageView) getActivity().findViewById(R.id.map_bazaar);

        if (!mFab.isShown()) {
            mFab.show();
        }
        mMapConference.setVisibility(View.GONE);
        mMapBazaar.setVisibility(View.VISIBLE);

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

        mAdapter = new ListBazaarAdapter(getActivity(), mBazaarList);
        mAdapter.setBazaarCallback(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCellClick(int position) {
        DetailBazaarActivity.startBazaarDetailActivity(mBazaarList.get(position), position, getActivity());
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
