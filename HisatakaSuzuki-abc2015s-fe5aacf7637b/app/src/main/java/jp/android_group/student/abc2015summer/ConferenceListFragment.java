package jp.android_group.student.abc2015summer;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.android_group.student.abc2015summer.api.Conference;
import jp.android_group.student.abc2015summer.api.ConferenceXmlParser;
import uk.co.senab.photoview.PhotoView;

public class ConferenceListFragment extends ListFragment implements MenuItem.OnMenuItemClickListener {

    private List<Conference> mConferences;
    private String locationId;

    public static ConferenceListFragment newInstance(String locationId) {
        Bundle args = new Bundle();
        args.putString(Conference.TAG_LOCATION_ID, locationId);
        ConferenceListFragment fragment = new ConferenceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ConferenceListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        locationId = getArguments().getString(Conference.TAG_LOCATION_ID);
        ConferenceXmlParser parser = new ConferenceXmlParser(getActivity());
        List<Conference> all = parser.loadConference();
        mConferences = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            Conference conference = all.get(i);
            if (conference.getLocationId().equals(locationId)) {
                mConferences.add(conference);
            }
        }
        View rootView = inflater.inflate(R.layout.fragment_conference_list, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = getListView();
        ConferenceListAdapter adapter = new ConferenceListAdapter(getActivity()
                , R.layout.conference_list_item, mConferences);
        listView.setAdapter(adapter);

        View footer = View.inflate(getActivity(), R.layout.comment, null);
        TextView comment = (TextView) footer.findViewById(R.id.official_url);
        comment.setText("http://abc.android-group.jp/2015s/timetables/");
        listView.addFooterView(footer);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Conference conference = mConferences.get(position);

        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.container
                , ConferenceDetailFragment.newInstance(conference)).addToBackStack(null).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.add(Menu.NONE, 0, 0, getString(R.string.action_map));
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setIcon(R.drawable.ic_action_place);
        menuItem.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int mapId;
        if (locationId.equals(ConferencePagerFragment.ConferencePagerAdapter.KEY_T0)) {
            mapId = R.drawable.floor01;
        } else {
            mapId = R.drawable.floor09;
        }
        PhotoView photoView = new PhotoView(getActivity());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mapId);
        photoView.setImageBitmap(bitmap);

        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(photoView);
        dialog.show();
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        setHasOptionsMenu(false);
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
    }

    private class ConferenceListAdapter extends ArrayAdapter<Conference> {

        private LayoutInflater inflater;
        private int resId;
        private List<Conference> list;
        private boolean isFavorite;

        public ConferenceListAdapter(Context context, int resource, List objects) {
            super(context, resource, objects);
            inflater = LayoutInflater.from(context);
            resId = resource;
            list = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(resId, null);
            }

            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView speaker = (TextView) convertView.findViewById(R.id.speaker);

            Conference conference = list.get(position);
            time.setText(conference.getStartTime() + " - " + conference.getEndTime());
            title.setText(conference.getTitle());
            speaker.setText(conference.getSpeaker());


            ImageButton btnFav = (ImageButton) convertView.findViewById(R.id.btn_favorite);
            btnFav.setVisibility(Configuration.favoriteButtonIsVisible);
            SharedPreferences pref = getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
            if("true".equals(pref.getString(conference.getId(), "false"))){
                btnFav.setImageResource(R.drawable.star_pink_pushed);
            }else{
                btnFav.setImageResource(R.drawable.star_pink);
            }

            final View finalConvertView = convertView;
            final Conference finalConference = conference;
            btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences pref = getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    ImageButton btn = (ImageButton) finalConvertView.findViewById(R.id.btn_favorite);
                    if("true".equals(pref.getString(finalConference.getId(), "false"))){
                        editor.putString(finalConference.getId(), "false");
                        btn.setImageResource(R.drawable.star_pink);
                        Snackbar.make(finalConvertView, "マイスケジュールから削除しました！", Snackbar.LENGTH_LONG).show();
                    } else {
                        editor.putString(finalConference.getId(), "true");
                        btn.setImageResource(R.drawable.star_pink_pushed);
                        Snackbar.make(finalConvertView, "マイスケジュールに追加しました！", Snackbar.LENGTH_LONG).show();
                    }
                    editor.commit();
                }
            });

            return convertView;
        }
    }
}
