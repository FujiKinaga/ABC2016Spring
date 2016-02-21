package jp.android_group.student.abc2015summer;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

public class MySchedulePagerFragment extends android.support.v4.app.Fragment {

    private MySchedulePagerAdapter mAdapter;

    public MySchedulePagerFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager, container, false);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        mAdapter = new MySchedulePagerAdapter(getChildFragmentManager());

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(pager);


        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.saveState();
    }

    public class MySchedulePagerAdapter extends FragmentPagerAdapter {

        public MySchedulePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
                return MyScheduleConferenceFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.section_conference_session).toUpperCase(l);
                case 1:
                    return getString(R.string.section_bazaar).toUpperCase(l);
            }
            return null;
        }
    }

}
