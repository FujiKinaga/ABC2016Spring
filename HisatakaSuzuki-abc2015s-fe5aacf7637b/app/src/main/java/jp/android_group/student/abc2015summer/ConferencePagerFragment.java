package jp.android_group.student.abc2015summer;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jp.android_group.student.abc2015summer.api.Conference;

public class ConferencePagerFragment extends Fragment {

    private ConferencePagerAdapter mAdapter;

    public ConferencePagerFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager, container, false);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        mAdapter = new ConferencePagerAdapter(getChildFragmentManager());

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

    public class ConferencePagerAdapter extends FragmentPagerAdapter {

        public static final String KEY_T0 = "T0",
                                KEY_T1 = "T1",
                                KEY_T2 = "T2",
                                KEY_T3 = "T3";

        public ConferencePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            String locationId;
            switch (position) {
                case 0:
                    locationId = KEY_T0;
                    break;
                case 1:
                    locationId = KEY_T1;
                    break;
                case 2:
                    locationId = KEY_T2;
                    break;
                case 3:
                    locationId = KEY_T3;
                    break;
                default:
                    return null;
            }
            return ConferenceListFragment.newInstance(locationId);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.loc_t0);
                case 1:
                    return getString(R.string.loc_t1);
                case 2:
                    return getString(R.string.loc_t2);
                case 3:
                    return getString(R.string.loc_t3);
            }
            return null;
        }
    }

}
