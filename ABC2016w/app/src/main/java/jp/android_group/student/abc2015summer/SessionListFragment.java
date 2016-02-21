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

import jp.android_group.student.abc2015summer.api.Session;
import jp.android_group.student.abc2015summer.api.SessionXmlParser;
import uk.co.senab.photoview.PhotoView;

public class SessionListFragment extends ListFragment implements MenuItem.OnMenuItemClickListener {

    private List<Session> mSessions;

    public static SessionListFragment newInstance(String roomId) {
        Bundle args = new Bundle();
        args.putString(Session.TAG_ROOM_ID, roomId);
        SessionListFragment fragment = new SessionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SessionListFragment() {
    }

    private String roomId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        this.roomId = getArguments().getString(Session.TAG_ROOM_ID);

        SessionXmlParser parser = new SessionXmlParser(getActivity());
        List<Session> all = parser.loadSession();
        mSessions = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            Session session = all.get(i);
            if (session.getRoomId().equals(roomId)) {
                mSessions.add(session);
            }
        }
        View rootView = inflater.inflate(R.layout.fragment_session_list, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = getListView();
        SessionListAdapter adapter = new SessionListAdapter(getActivity(), R.layout.session_list_item, mSessions);
        listView.setAdapter(adapter);

        View footer = View.inflate(getActivity(), R.layout.comment, null);
        TextView comment = (TextView) footer.findViewById(R.id.official_url);
        comment.setText("http://abc.android-group.jp/2015s/timetables/");
        listView.addFooterView(footer);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Session session = mSessions.get(position);

        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.container
                , SessionDetailFragment.newInstance(session)).addToBackStack(null).commit();
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
        final int mapId = R.drawable.floor10;
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

    private class SessionListAdapter extends ArrayAdapter<Session> {
        private LayoutInflater inflater;
        private int resId;
        private List<Session> list;
        private boolean isFavorite;

        public SessionListAdapter(Context context, int resource, List objects) {
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
            TextView group = (TextView) convertView.findViewById(R.id.group);

            Session session = list.get(position);
            time.setText(session.getStartTime() + " - " + session.getEndTime());
            title.setText(session.getTitle());
            group.setText(session.getGroup());

            ImageButton btnFav = (ImageButton) convertView.findViewById(R.id.btn_favorite);
            btnFav.setVisibility(Configuration.favoriteButtonIsVisible);
            SharedPreferences pref = getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
            if("true".equals(pref.getString(String.valueOf(session.getId()), "false"))){
                btnFav.setImageResource(R.drawable.star_pink_pushed);
            }else{
                btnFav.setImageResource(R.drawable.star_pink);
            }

            final View finalConvertView = convertView;
            final Session finalSession = session;
            btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences pref = getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    ImageButton btn = (ImageButton) finalConvertView.findViewById(R.id.btn_favorite);
                    if("true".equals(pref.getString(String.valueOf(finalSession.getId()), "false"))){
                        editor.putString(String.valueOf(finalSession.getId()), "false");
                        btn.setImageResource(R.drawable.star_pink);
                        Snackbar.make(finalConvertView, "マイスケジュールから削除しました！", Snackbar.LENGTH_LONG).show();
                    } else {
                        editor.putString(String.valueOf(finalSession.getId()), "true");
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
