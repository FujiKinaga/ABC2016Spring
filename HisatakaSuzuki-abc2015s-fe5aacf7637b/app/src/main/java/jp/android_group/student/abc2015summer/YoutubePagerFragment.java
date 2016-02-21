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

public class YoutubePagerFragment extends Fragment {

    private YoutubePagerAdapter mAdapter;

    public YoutubePagerFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_youtube, container, false);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        mAdapter = new YoutubePagerAdapter(getChildFragmentManager());

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

    public class YoutubePagerAdapter extends FragmentPagerAdapter {

        public static final String KEY_P1 = "P1";
        public YoutubePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String roomId;
            switch (position) {
                case 0:
                    roomId = KEY_P1;
                    break;
                default:
                    throw new IllegalStateException("bug");
            }
            return YoutubeListFragment.newInstance(roomId);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "YoutubeLive";
                default:
                    throw new IllegalStateException("bug");
            }
        }
    }
}