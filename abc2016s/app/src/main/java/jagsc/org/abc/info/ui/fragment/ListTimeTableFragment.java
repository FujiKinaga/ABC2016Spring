package jagsc.org.abc.info.ui.fragment;

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
import jagsc.org.abc.info.R;
import jagsc.org.abc.info.domain.model.Conference;
import jagsc.org.abc.info.event.BusHolder;
import jagsc.org.abc.info.event.StarClickedEvent;
import jagsc.org.abc.info.ui.activity.DetailConferenceActivity;
import jagsc.org.abc.info.ui.activity.MainActivity;
import jagsc.org.abc.info.ui.adapter.ListTimeTableAdapter;
import jagsc.org.abc.info.ui.view.Fab;

/**
 * Created by kinagafuji on 16/02/27.
 */
public class ListTimeTableFragment extends Fragment implements ListTimeTableAdapter.TimeTableCallback, ObservableScrollViewCallbacks {

    @Bind(R.id.list)
    ObservableRecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private ListTimeTableAdapter mAdapter;

    private Fab mFab;

    private List<Conference> mConferenceList = new ArrayList<>();

    private int mPosition;

    public ListTimeTableFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_time_table, container, false);
        ButterKnife.bind(this, rootView);

        mConferenceList = getArguments().getParcelableArrayList("list");
        mPosition = getArguments().getInt("position");

        mFab = (Fab) getActivity().findViewById(R.id.fab);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.setScrollViewCallbacks(this);
        mAdapter = new ListTimeTableAdapter(getActivity(), mConferenceList);
        mAdapter.setTimeTableCallback(this);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusHolder.get().register(this);
    }

    @Override
    public void onPause() {
        BusHolder.get().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void subscribe(StarClickedEvent event) {
        if (PagerTimeTableFragment.mShowPosition == mPosition) {
            mAdapter.notifyItemChanged(event.position);
        }
    }

    @Override
    public void onCellClick(int position) {
        DetailConferenceActivity.startConferenceDetailActivity(mConferenceList.get(position), position, getActivity());
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
