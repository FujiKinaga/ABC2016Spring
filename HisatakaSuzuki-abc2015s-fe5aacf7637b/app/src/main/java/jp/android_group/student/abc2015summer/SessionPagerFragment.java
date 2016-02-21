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

public class SessionPagerFragment extends Fragment {
    private SessionPagerAdapter mAdapter;

    public SessionPagerFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager, container, false);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        mAdapter = new SessionPagerAdapter(getChildFragmentManager());

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

    public class SessionPagerAdapter extends FragmentPagerAdapter {

        public static final String KEY_P1 = "P1",
                KEY_P2 = "P2",
                KEY_P3 = "P3",
                KEY_P4 = "P4",
                KEY_P5 = "P5";

        public SessionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String roomId;
            switch (position) {
                case 0:
                    roomId = KEY_P1;
                    break;
                case 1:
                    roomId = KEY_P2;
                    break;
                case 2:
                    roomId = KEY_P3;
                    break;
                case 3:
                    roomId = KEY_P4;
                    break;
                case 4:
                    roomId = KEY_P5;
                    break;
                default:
                    throw new IllegalStateException("bug");
            }
            return SessionListFragment.newInstance(roomId);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.sess_p1);
                case 1:
                    return getString(R.string.sess_p2);
                case 2:
                    return getString(R.string.sess_p3);
                case 3:
                    return getString(R.string.sess_p4);
                case 4:
                    return getString(R.string.sess_p5);
                default:
                    throw new IllegalStateException("bug");
            }
        }
    }
}
