package jp.android_group.student.abc2016winter.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.android_group.student.abc2016winter.R;
import jp.android_group.student.abc2016winter.ui.view.Fab;

public class MySchedulePagerFragment extends Fragment {

    @Bind(R.id.smart_tab)
    SmartTabLayout mSmartTab;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;

    private Fab mFab;

    private FragmentPagerItemAdapter adapter;

    public static int mShowPosition = 0;

    public MySchedulePagerFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_schedule_pager, container, false);
        ButterKnife.bind(this, rootView);

        mShowPosition = 0;

        mFab = (Fab) getActivity().findViewById(R.id.fab);

        adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add(R.string.section_conference_session, MyScheduleConferenceFragment.class)
                .add(R.string.section_bazaar, MyScheduleBazaarFragment.class)
                .create());

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

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
