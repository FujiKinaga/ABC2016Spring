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

import jp.android_group.student.abc2015summer.api.TimeTable;
import uk.co.senab.photoview.PhotoView;

public class TimeTableDetailFragment extends android.support.v4.app.Fragment
        implements View.OnClickListener, MenuItem.OnMenuItemClickListener {

    private boolean isFavorite;
    private View rootView;
    private TimeTable timeTable;

    public static TimeTableDetailFragment newInstance(TimeTable timeTable) {
        Bundle args = new Bundle();
        args.putString(TimeTable.TAG_TITLE, timeTable.getTitle());
        args.putString(TimeTable.TAG_START_TIME, timeTable.getStartTime());
        args.putString(TimeTable.TAG_END_TIME, timeTable.getEndTime());
        args.putString(TimeTable.TAG_TYPE, timeTable.getType());
        if(timeTable.getType().equals("conference")) {
            args.putString(TimeTable.TAG_ID_LEC, timeTable.getId());
            args.putString(TimeTable.TAG_ABSTRACT, timeTable.getAbst());
            args.putString(TimeTable.TAG_SPEAKER, timeTable.getSpeaker());
            args.putString(TimeTable.TAG_PROFILE, timeTable.getProfile());
            args.putString(TimeTable.TAG_LOCATION, timeTable.getLocation());
            args.putString(TimeTable.TAG_LOCATION_ID, timeTable.getLocationId());
        }else if(timeTable.getType().equals("session")) {
            args.putString(TimeTable.TAG_ID_SESSION, timeTable.getId());
            args.putString(TimeTable.TAG_GROUP, timeTable.getGroup());
            args.putString(TimeTable.TAG_CONTENT, timeTable.getContent());
            args.putString(TimeTable.TAG_URL, timeTable.getUrl());
            args.putString(TimeTable.TAG_ROOM_ID, timeTable.getRoomId());
            args.putString(TimeTable.TAG_ROOM, timeTable.getRoom());
        }

        TimeTableDetailFragment fragment = new TimeTableDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TimeTableDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_time_table_detail, container, false);

        timeTable = new TimeTable();
        if (getArguments() != null) {
            Bundle args = getArguments();
            timeTable.setType(args.getString(TimeTable.TAG_TYPE));
            timeTable.setTitle(args.getString(TimeTable.TAG_TITLE));
            timeTable.setStartTime(args.getString(TimeTable.TAG_START_TIME));
            timeTable.setEndTime(args.getString(TimeTable.TAG_END_TIME));
            if(timeTable.getType().equals("conference")) {
                timeTable.setId(args.getString(TimeTable.TAG_ID_LEC));
                timeTable.setAbst(args.getString(TimeTable.TAG_ABSTRACT));
                timeTable.setSpeaker(args.getString(TimeTable.TAG_SPEAKER));
                timeTable.setProfile(args.getString(TimeTable.TAG_PROFILE));
                timeTable.setLocation(args.getString(TimeTable.TAG_LOCATION));
                timeTable.setLocationId(args.getString(TimeTable.TAG_LOCATION_ID));
            }else if(timeTable.getType().equals("session")) {
                timeTable.setId(args.getString(TimeTable.TAG_ID_SESSION));
                timeTable.setGroup(args.getString(TimeTable.TAG_GROUP));
                timeTable.setContent(args.getString(TimeTable.TAG_CONTENT));
                timeTable.setUrl(args.getString(TimeTable.TAG_URL));
                timeTable.setRoomId(args.getString(TimeTable.TAG_ROOM_ID));
                timeTable.setRoom(args.getString(TimeTable.TAG_ROOM));
            }
        }

        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView time = (TextView) rootView.findViewById(R.id.time);
        TextView abst = (TextView) rootView.findViewById(R.id.abst);
        TextView speaker = (TextView) rootView.findViewById(R.id.speaker);
        TextView profile = (TextView) rootView.findViewById(R.id.profile);
        TextView location = (TextView) rootView.findViewById(R.id.location);
        TextView group = (TextView) rootView.findViewById(R.id.group);
        TextView content = (TextView) rootView.findViewById(R.id.content);
        TextView url = (TextView) rootView.findViewById(R.id.url);
        TextView room = (TextView) rootView.findViewById(R.id.room);

        title.setText(timeTable.getTitle());
        time.setText(timeTable.getStartTime() + " - " + timeTable.getEndTime());
        if(timeTable.getType().equals("conference")) {
            abst.setText(timeTable.getAbst());
            speaker.setText("講師：" + timeTable.getSpeaker());
            profile.setText("プロフィール：" + timeTable.getProfile());
            location.setText(timeTable.getLocation());
            group.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            url.setVisibility(View.GONE);
            room.setVisibility(View.GONE);
            rootView.findViewById(R.id.divider_below_room).setVisibility(View.GONE);
            rootView.findViewById(R.id.divider_below_content).setVisibility(View.GONE);

        }else if(timeTable.getType().equals("session")) {
            group.setText("講師：" + timeTable.getGroup());
            content.setText(timeTable.getContent());
            url.setText(timeTable.getUrl());
            room.setText(timeTable.getRoom());
            abst.setVisibility(View.GONE);
            speaker.setVisibility(View.GONE);
            profile.setVisibility(View.GONE);
            location.setVisibility(View.GONE);
            rootView.findViewById(R.id.divider_below_location).setVisibility(View.GONE);
            rootView.findViewById(R.id.divider_below_abst).setVisibility(View.GONE);
        }

        ImageButton btnFav = (ImageButton) rootView.findViewById(R.id.btn_favorite);
        btnFav.setVisibility(Configuration.favoriteButtonIsVisible);

        SharedPreferences pref = this.getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
        if("true".equals(pref.getString(timeTable.getId(), "false"))){
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
    public void onClick(View v) {
        SharedPreferences pref = this.getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        ImageButton btn = (ImageButton) rootView.findViewById(R.id.btn_favorite);
        if (isFavorite) {
            editor.putString(timeTable.getId(), "false");
            isFavorite = false;
            btn.setImageResource(R.drawable.star_pink);
            Snackbar.make(rootView, "マイスケジュールから削除しました！", Snackbar.LENGTH_LONG).show();
        } else {
            editor.putString(timeTable.getId(), "true");
            isFavorite = true;
            btn.setImageResource(R.drawable.star_pink_pushed);
            Snackbar.make(rootView, "マイスケジュールに追加しました！", Snackbar.LENGTH_LONG).show();
        }
        editor.commit();
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
        if(timeTable.getType().equals("conference")) {
            if (timeTable.getLocationId().equals(ConferencePagerFragment.ConferencePagerAdapter.KEY_T0)) {
                mapId = R.drawable.floor01;
            } else {
                mapId = R.drawable.floor09;
            }
        }else{
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
}