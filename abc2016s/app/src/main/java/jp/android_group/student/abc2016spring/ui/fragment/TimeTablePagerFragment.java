package jp.android_group.student.abc2016spring.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.android_group.student.abc2016spring.R;
import jp.android_group.student.abc2016spring.datasource.repository.TimeTableRepositoryImpl;
import jp.android_group.student.abc2016spring.domain.executor.UIThread;
import jp.android_group.student.abc2016spring.domain.model.Conference;
import jp.android_group.student.abc2016spring.domain.repository.TimeTableRepository;
import jp.android_group.student.abc2016spring.domain.usecase.TimeTableUseCase;
import jp.android_group.student.abc2016spring.domain.usecase.TimeTableUseCaseImpl;
import jp.android_group.student.abc2016spring.presenter.ShowTimeTablePresenter;
import jp.android_group.student.abc2016spring.ui.view.Fab;

public class TimeTablePagerFragment extends Fragment implements ShowTimeTablePresenter.ShowTimeTableView {

    @Bind(R.id.smart_tab)
    SmartTabLayout mSmartTab;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;

    private Fab mFab;

    private FragmentPagerItemAdapter adapter;

    private ShowTimeTablePresenter mTimeTablePresenter;

    private SparseArrayCompat<ArrayList<Conference>> mTimeTableList = new SparseArrayCompat<>();

    public static int mShowPosition = 0;

    public TimeTablePagerFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimeTableRepository timeTableRepository = TimeTableRepositoryImpl.getRepository();
        TimeTableUseCase timeTableUseCase = TimeTableUseCaseImpl.getUseCase(timeTableRepository, UIThread.getInstance());
        mTimeTablePresenter = new ShowTimeTablePresenter(timeTableUseCase);
        mTimeTablePresenter.setTimeTableView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //mSectionNum = getArguments().getInt(ARG_SECTION_NUMBER);
        View rootView = inflater.inflate(R.layout.fragment_time_table, container, false);
        ButterKnife.bind(this, rootView);

        mShowPosition = 0;

        mFab = (Fab) getActivity().findViewById(R.id.fab);

        mTimeTablePresenter.getTimeTableData();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimeTablePresenter.resume();
    }

    @Override
    public void onPause() {
        mTimeTablePresenter.pause();
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
    public void showResult(SparseArrayCompat<ArrayList<Conference>> timeTableList) {
        mTimeTableList = timeTableList;

        adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add(R.string.time_1p, TimeTableListFragment.class, new Bundler().putParcelableArrayList("list", mTimeTableList.get(11)).putInt("position", 0).get())
                .add(R.string.time_2p, TimeTableListFragment.class, new Bundler().putParcelableArrayList("list", mTimeTableList.get(12)).putInt("position", 1).get())
                .add(R.string.time_3p, TimeTableListFragment.class, new Bundler().putParcelableArrayList("list", mTimeTableList.get(13)).putInt("position", 2).get())
                .add(R.string.time_4p, TimeTableListFragment.class, new Bundler().putParcelableArrayList("list", mTimeTableList.get(14)).putInt("position", 3).get())
                .add(R.string.time_5p, TimeTableListFragment.class, new Bundler().putParcelableArrayList("list", mTimeTableList.get(15)).putInt("position", 4).get())
                .add(R.string.time_6p, TimeTableListFragment.class, new Bundler().putParcelableArrayList("list", mTimeTableList.get(16)).putInt("position", 5).get())
                .create());

        mViewpager.setOffscreenPageLimit(6);
        mViewpager.setAdapter(adapter);

        mSmartTab.setViewPager(mViewpager);
        mSmartTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mShowPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2) mFab.hide();
                if (state == 0) mFab.show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}