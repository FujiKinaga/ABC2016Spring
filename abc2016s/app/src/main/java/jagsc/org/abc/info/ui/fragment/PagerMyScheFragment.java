package jagsc.org.abc.info.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;
import butterknife.ButterKnife;
import jagsc.org.abc.info.R;
import jagsc.org.abc.info.ui.view.Fab;
import jagsc.org.abc.info.ui.view.HackyViewPager;

public class PagerMyScheFragment extends Fragment {

    @Bind(R.id.smart_tab)
    SmartTabLayout mSmartTab;
    @Bind(R.id.viewpager)
    HackyViewPager mViewpager;

    private Fab mFab;
    private ImageView mMapConference;
    private ImageView mMapBazaar;

    private FragmentPagerItemAdapter adapter;

    public static int mShowPosition = 0;

    public PagerMyScheFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager_my_sche, container, false);
        ButterKnife.bind(this, rootView);

        mShowPosition = 0;

        mFab = (Fab) getActivity().findViewById(R.id.fab);
        mMapConference = (ImageView) getActivity().findViewById(R.id.map_conference);
        mMapBazaar = (ImageView) getActivity().findViewById(R.id.map_bazaar);

        if (!mFab.isShown()) {
            mFab.show();
        }

        mMapConference.setVisibility(View.VISIBLE);
        mMapBazaar.setVisibility(View.GONE);

        adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add(R.string.section_conference_session, ListMyScheConferenceFragment.class)
                .add(R.string.section_bazaar, ListMyScheBazaarFragment.class)
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
                if (mShowPosition == 0) {
                    mMapConference.setVisibility(View.VISIBLE);
                    mMapBazaar.setVisibility(View.GONE);
                } else {
                    mMapConference.setVisibility(View.GONE);
                    mMapBazaar.setVisibility(View.VISIBLE);
                }
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
