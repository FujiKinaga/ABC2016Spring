package jagsc.org.abc.info.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jagsc.org.abc.info.R;
import jagsc.org.abc.info.datasource.repository.ConferenceRepositoryImpl;
import jagsc.org.abc.info.domain.executor.UIThread;
import jagsc.org.abc.info.domain.model.Conference;
import jagsc.org.abc.info.domain.repository.ConferenceRepository;
import jagsc.org.abc.info.domain.usecase.ConferenceUseCase;
import jagsc.org.abc.info.domain.usecase.ConferenceUseCaseImpl;
import jagsc.org.abc.info.presenter.ShowConferencePresenter;
import jagsc.org.abc.info.ui.activity.ListConferenceActivity;
import jagsc.org.abc.info.ui.adapter.ListConferenceAdapter;
import jagsc.org.abc.info.ui.view.Fab;

public class ListConferenceFragment extends Fragment implements ShowConferencePresenter.ShowConferenceView,
        ObservableScrollViewCallbacks, ListConferenceAdapter.ConferenceCallback {

    @Bind(R.id.list)
    ObservableRecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private ListConferenceAdapter mAdapter;

    private Fab mFab;
    private ImageView mMapConference;
    private ImageView mMapBazaar;

    private ShowConferencePresenter mConferencePresenter;

    private SparseArrayCompat<ArrayList<Conference>> mConferenceList = new SparseArrayCompat<>();

    public ListConferenceFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConferenceRepository conferenceRepository = ConferenceRepositoryImpl.getRepository();
        ConferenceUseCase conferenceUseCase = ConferenceUseCaseImpl.getUseCase(conferenceRepository, UIThread.getInstance());
        mConferencePresenter = new ShowConferencePresenter(conferenceUseCase);
        mConferencePresenter.setConferenceView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_conference, container, false);
        ButterKnife.bind(this, rootView);

        mFab = (Fab) getActivity().findViewById(R.id.fab);
        mMapConference = (ImageView) getActivity().findViewById(R.id.map_conference);
        mMapBazaar = (ImageView) getActivity().findViewById(R.id.map_bazaar);

        if (!mFab.isShown()) {
            mFab.show();
        }
        mMapConference.setVisibility(View.VISIBLE);
        mMapBazaar.setVisibility(View.GONE);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.setScrollViewCallbacks(this);

        mConferencePresenter.getConferenceData();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mConferencePresenter.resume();
    }

    @Override
    public void onPause() {
        mConferencePresenter.pause();
        super.onPause();
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
    public void showResult(SparseArrayCompat<ArrayList<Conference>> conferenceList) {
        mConferenceList = conferenceList;

        mAdapter = new ListConferenceAdapter(getActivity(), mConferenceList);
        mAdapter.setConferenceCallback(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCellClick(int position, String title) {
        ListConferenceActivity.startConferenceListDetailActivity(mConferenceList.valueAt(position), title, getActivity());
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
