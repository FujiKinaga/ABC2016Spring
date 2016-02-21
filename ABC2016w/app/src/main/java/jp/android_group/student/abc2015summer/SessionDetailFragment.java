package jp.android_group.student.abc2015summer;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import jp.android_group.student.abc2015summer.api.Session;
import uk.co.senab.photoview.PhotoView;

public class SessionDetailFragment extends android.support.v4.app.Fragment implements View.OnClickListener, MenuItem.OnMenuItemClickListener {

    private boolean isFavorite;
    private View rootView;
    private Session session;

    public static SessionDetailFragment newInstance(Session session) {
        Bundle args = new Bundle();

        args.putInt(Session.TAG_ID, session.getId());
        args.putString(Session.TAG_GROUP, session.getGroup());
        args.putString(Session.TAG_TITLE, session.getTitle());
        args.putString(Session.TAG_CONTENT, session.getContent());
        args.putString(Session.TAG_URL, session.getUrl());
        args.putString(Session.TAG_START_TIME, session.getStartTime());
        args.putString(Session.TAG_END_TIME, session.getEndTime());
        args.putString(Session.TAG_ROOM_ID, session.getRoomId());
        args.putString(Session.TAG_ROOM, session.getRoom());

        SessionDetailFragment fragment = new SessionDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SessionDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_session_detail, container, false);

        setHasOptionsMenu(true);
        session = new Session();

        if (getArguments() != null) {
            Bundle args = getArguments();
            session.setId(args.getInt(Session.TAG_ID));
            session.setGroup(args.getString(Session.TAG_GROUP));
            session.setTitle(args.getString(Session.TAG_TITLE));
            session.setContent(args.getString(Session.TAG_CONTENT));
            session.setUrl(args.getString(Session.TAG_URL));
            session.setStartTime(args.getString(Session.TAG_START_TIME));
            session.setEndTime(args.getString(Session.TAG_END_TIME));
            session.setRoomId(args.getString(Session.TAG_ROOM_ID));
            session.setRoom(args.getString(Session.TAG_ROOM));
        }

        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView time = (TextView) rootView.findViewById(R.id.time);
        TextView content = (TextView) rootView.findViewById(R.id.content);
        TextView url = (TextView) rootView.findViewById(R.id.url);
        TextView group = (TextView) rootView.findViewById(R.id.group);
        TextView room = (TextView) rootView.findViewById(R.id.room);

        title.setText(session.getTitle());
        time.setText(session.getStartTime() + " - " + session.getEndTime());
        content.setText(session.getContent());
        url.setText(session.getUrl());
        group.setText("講師：" + session.getGroup());
        room.setText(session.getRoom());

        ImageButton btnFav = (ImageButton) rootView.findViewById(R.id.btn_favorite);
        btnFav.setVisibility(Configuration.favoriteButtonIsVisible);
        SharedPreferences pref = this.getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
        if("true".equals(pref.getString(String.valueOf(session.getId()), "false"))){
            isFavorite = true;
            btnFav.setImageResource(R.drawable.star_pink_pushed);
        }else{
            isFavorite = false;
            btnFav.setImageResource(R.drawable.star_pink);
        }

        btnFav.setOnClickListener(this);

        return rootView;
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
    public void onClick(View v) {
        SharedPreferences pref = this.getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        ImageButton btn = (ImageButton) rootView.findViewById(R.id.btn_favorite);
        if (isFavorite) {
            editor.putString(String.valueOf(session.getId()), "false");
            isFavorite = false;
            btn.setImageResource(R.drawable.star_pink);
            Snackbar.make(rootView, "マイスケジュールから削除しました！", Snackbar.LENGTH_LONG).show();
        } else {
            editor.putString(String.valueOf(session.getId()), "true");
            isFavorite = true;
            btn.setImageResource(R.drawable.star_pink_pushed);
            Snackbar.make(rootView, "マイスケジュールに追加しました！", Snackbar.LENGTH_LONG).show();
        }
        editor.commit();
    }
}
