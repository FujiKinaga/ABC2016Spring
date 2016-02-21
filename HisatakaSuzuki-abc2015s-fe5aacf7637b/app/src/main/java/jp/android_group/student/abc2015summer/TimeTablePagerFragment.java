package jp.android_group.student.abc2015summer;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

public class TimeTablePagerFragment extends Fragment {

    private TimeTablePagerAdapter mAdapter;

    public TimeTablePagerFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager, container, false);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        mAdapter = new TimeTablePagerAdapter(getChildFragmentManager());

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(pager);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(year == Configuration.CALENDAR_YEAR
                && month == Configuration.CALENDAR_MONTH
                && day == Configuration.CALENDAR_DATE){
            int hour_position = hour - 10;
            if(hour_position > -1 && hour_position < 9){
                pager.setCurrentItem(hour_position);
            }
        }

        System.currentTimeMillis();
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.saveState();
    }

    public class TimeTablePagerAdapter extends FragmentPagerAdapter {

        private static final int KEY_HOUR_10 = 10,
                KEY_HOUR_11 = 11,
                KEY_HOUR_12 = 12,
                KEY_HOUR_13 = 13,
                KEY_HOUR_14 = 14,
                KEY_HOUR_15 = 15,
                KEY_HOUR_16 = 16,
                KEY_HOUR_17 = 17,
                KEY_HOUR_18 = 18,
                KEY_HOUR_19 = 19;

        public TimeTablePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int startTime;
            int endTime;
            switch (position) {
                case 0:
                    startTime = KEY_HOUR_10;
                    endTime = KEY_HOUR_11;
                    break;
                case 1:
                    startTime = KEY_HOUR_11;
                    endTime = KEY_HOUR_12;
                    break;
                case 2:
                    startTime = KEY_HOUR_12;
                    endTime = KEY_HOUR_13;
                    break;
                case 3:
                    startTime = KEY_HOUR_13;
                    endTime = KEY_HOUR_14;
                    break;
                case 4:
                    startTime = KEY_HOUR_14;
                    endTime = KEY_HOUR_15;
                    break;
                case 5:
                    startTime = KEY_HOUR_15;
                    endTime = KEY_HOUR_16;
                    break;
                case 6:
                    startTime = KEY_HOUR_16;
                    endTime = KEY_HOUR_17;
                    break;
                case 7:
                    startTime = KEY_HOUR_17;
                    endTime = KEY_HOUR_18;
                    break;
                case 8:
                    startTime = KEY_HOUR_18;
                    endTime = KEY_HOUR_19;
                    break;
                default:
                    return null;
            }
            return TimeTableListFragment.newInstance(startTime,endTime);
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.time_1p);
                case 1:
                    return getString(R.string.time_2p);
                case 2:
                    return getString(R.string.time_3p);
                case 3:
                    return getString(R.string.time_4p);
                case 4:
                    return getString(R.string.time_5p);
                case 5:
                    return getString(R.string.time_6p);
                case 6:
                    return getString(R.string.time_7p);
                case 7:
                    return getString(R.string.time_8p);
                case 8:
                    return getString(R.string.time_9p);
//                case 9:
//                    return getString(R.string.time_10p);
            }
            return null;
        }
    }

}
