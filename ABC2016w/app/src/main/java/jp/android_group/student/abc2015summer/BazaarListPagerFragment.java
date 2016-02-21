package jp.android_group.student.abc2015summer;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import jp.android_group.student.abc2015summer.api.Bazaar;
import jp.android_group.student.abc2015summer.api.BazaarXmlParser;
import uk.co.senab.photoview.PhotoView;

public class BazaarListPagerFragment extends Fragment {

    private BazaarPagerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager, container, false);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        mAdapter = new BazaarPagerAdapter(getChildFragmentManager());

        pager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(pager);

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.saveState();
    }

    public class BazaarPagerAdapter extends FragmentPagerAdapter {

        public BazaarPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            if (position == 0) {
                return new BazaarSiteListFragment();
            } else {
                return new BazaarLobbyListFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.bazaar_site);
                case 1:
                    return getString(R.string.lobby);
            }
            return null;
        }
    }

    public static class BazaarSiteListFragment extends ListFragment implements View.OnClickListener {

        private BazaarAdapter mAdapter;
        private List<Bazaar> mList;
        private ImageView mImage;


        public BazaarSiteListFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_bazaar_list, container, false);
            BazaarXmlParser bazaarXmlParser = new BazaarXmlParser(getActivity());
            mList = bazaarXmlParser.loadBazaarOn4F();
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            ListView listView = getListView();

            mImage = new ImageView(getActivity());
            mImage.setImageResource(R.drawable.bazaar_site);
            mImage.setScaleType(ImageView.ScaleType.FIT_XY);
            mImage.setOnClickListener(this);

            listView.addHeaderView(mImage);
            listView.setDivider(null);
            mAdapter = new BazaarAdapter(getActivity(), R.layout.bazaar_list_item, mList);
            listView.setAdapter(mAdapter);

            View footer = View.inflate(getActivity(), R.layout.comment, null);
            TextView comment = (TextView) footer.findViewById(R.id.official_url);
            comment.setText("http://abc.android-group.jp/2015s/bazaar/");
            listView.addFooterView(footer);
        }

        @Override
        public void onDestroyView() {
            setListAdapter(null);
            super.onDestroyView();
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);

            Bazaar bazaar = mAdapter.getItem(position - 1);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BazaarDetailFragment.newInstance(bazaar))
                    .addToBackStack(null).commit();
        }

        @Override
        public void onClick(View v) {
            PhotoView photoView = new PhotoView(getActivity());
            Bitmap bitmap = ((BitmapDrawable)mImage.getDrawable()).getBitmap();
            photoView.setImageBitmap(bitmap);
            photoView.setScaleType(ImageView.ScaleType.FIT_XY);

            Dialog dialog = new Dialog(getActivity());
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(photoView);
            dialog.show();
        }
    }

    public static class BazaarLobbyListFragment extends ListFragment implements View.OnClickListener {

        private BazaarAdapter mAdapter;
        private List<Bazaar> mList;
        private ImageView mImage;

        public BazaarLobbyListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_bazaar_list, container, false);
            BazaarXmlParser bazaarXmlParser = new BazaarXmlParser(getActivity());
            mList = bazaarXmlParser.loadBazaarOn1F();
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            ListView listView = getListView();

            mImage = new ImageView(getActivity());
            mImage.setImageResource(R.drawable.floor01);
            mImage.setOnClickListener(this);

            listView.addHeaderView(mImage);
            listView.setDivider(null);
            mAdapter = new BazaarAdapter(getActivity(), R.layout.bazaar_list_item, mList);
            listView.setAdapter(mAdapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);

            Bazaar bazaar = mAdapter.getItem(position - 1);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BazaarDetailFragment.newInstance(bazaar))
                    .addToBackStack(null).commit();
        }

        @Override
        public void onClick(View v) {
            PhotoView photoView = new PhotoView(getActivity());
            Bitmap bitmap = ((BitmapDrawable)mImage.getDrawable()).getBitmap();
            photoView.setImageBitmap(bitmap);

            Dialog dialog = new Dialog(getActivity());
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(photoView);
            dialog.show();
        }
    }

    public static class BazaarAdapter extends ArrayAdapter<Bazaar> {

        LayoutInflater inflater;
        int resId;
        List<Bazaar> list;
        Context context;

        public BazaarAdapter(Context context, int resource, List<Bazaar> list) {
            super(context, resource, list);
            inflater = LayoutInflater.from(context);
            resId = resource;
            this.list = list;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = inflater.inflate(resId, null);

            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView group = (TextView) convertView.findViewById(R.id.group);
            TextView location = (TextView) convertView.findViewById(R.id.location);

            LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.layout_bazaar_item);

            Bazaar bazaar = list.get(position);

            title.setText(bazaar.getTitle());
            group.setText(bazaar.getGroup());
            location.setText(bazaar.getLocation());

            if (position > 0) {
                Bazaar above = list.get(position - 1);
                String colorAbove = above.getColor();
                String colorThis = bazaar.getColor();
                if (!colorAbove.equals(colorThis)) {
                    View divider = convertView.findViewById(R.id.divider);
                    divider.setVisibility(View.VISIBLE);
                }
            }

            int color;
            switch (bazaar.getColor()) {
                case "blue":
                    color = R.color.blue;
                    break;
                case "green":
                    color = R.color.green;
                    break;
                case "light_blue":
                    color = R.color.light_blue;
                    break;
                case "purple":
                    color = R.color.purple;
                    break;
                case "pink":
                    color = R.color.pink;
                    break;
                case "orange":
                    color = R.color.orange;
                    break;
                case "lime":
                    color = R.color.lime;
                    break;
                default:
                    color = R.color.red;
                    break;
            }
            layout.setBackgroundColor(context.getResources().getColor(color));

            return convertView;
        }
    }
}
