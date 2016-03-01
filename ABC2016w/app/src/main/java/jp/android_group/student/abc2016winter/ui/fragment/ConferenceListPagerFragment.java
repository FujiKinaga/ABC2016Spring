package jp.android_group.student.abc2016winter.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.android_group.student.abc2016winter.R;
import jp.android_group.student.abc2016winter.datasource.repository.ConferenceRepository;
import jp.android_group.student.abc2016winter.datasource.repository.ConferenceRepositoryImpl;
import jp.android_group.student.abc2016winter.domain.executor.UIThread;
import jp.android_group.student.abc2016winter.domain.model.Conference;
import jp.android_group.student.abc2016winter.domain.usecase.ConferenceUseCase;
import jp.android_group.student.abc2016winter.domain.usecase.ConferenceUseCaseImpl;
import jp.android_group.student.abc2016winter.presenter.ShowConferencePresenter;
import jp.android_group.student.abc2016winter.ui.activity.ConferenceListDetailActivity;
import jp.android_group.student.abc2016winter.ui.adapter.ConferenceListAdapter;
import jp.android_group.student.abc2016winter.ui.view.Fab;

public class ConferenceListPagerFragment extends Fragment implements ShowConferencePresenter.ShowConferenceView,
        ObservableScrollViewCallbacks, ConferenceListAdapter.ConferenceCallback {

    @Bind(R.id.list)
    ObservableRecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private ConferenceListAdapter mAdapter;

    private Fab mFab;

    private ShowConferencePresenter mConferencePresenter;

    private SparseArrayCompat<ArrayList<Conference>> mConferenceList = new SparseArrayCompat<>();

    public ConferenceListPagerFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_conference_list, container, false);
        ButterKnife.bind(this, rootView);

        mFab = (Fab) getActivity().findViewById(R.id.fab);

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

        mAdapter = new ConferenceListAdapter(getActivity(), mConferenceList);
        mAdapter.setConferenceCallback(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCellClick(int position, String title) {
        ConferenceListDetailActivity.startConferenceListDetailActivity(mConferenceList.valueAt(position), title, getActivity());
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
