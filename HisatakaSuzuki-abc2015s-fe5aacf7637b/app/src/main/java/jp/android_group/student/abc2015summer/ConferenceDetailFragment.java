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

import jp.android_group.student.abc2015summer.api.Conference;
import uk.co.senab.photoview.PhotoView;


public class ConferenceDetailFragment extends android.support.v4.app.Fragment
        implements View.OnClickListener, MenuItem.OnMenuItemClickListener {

    private boolean isFavorite;
    private View rootView;
    private Conference conference;

    public static ConferenceDetailFragment newInstance(Conference conference) {
        Bundle args = new Bundle();
        args.putString(Conference.TAG_TITLE, conference.getTitle());
        args.putString(Conference.TAG_ID, conference.getId());
        args.putString(Conference.TAG_START_TIME, conference.getStartTime());
        args.putString(Conference.TAG_END_TIME, conference.getEndTime());
        args.putString(Conference.TAG_ABSTRACT, conference.getAbst());
        args.putString(Conference.TAG_SPEAKER, conference.getSpeaker());
        args.putString(Conference.TAG_PROFILE, conference.getProfile());
        args.putString(Conference.TAG_LOCATION, conference.getLocation());
        args.putString(Conference.TAG_LOCATION_ID, conference.getLocationId());
        ConferenceDetailFragment fragment = new ConferenceDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ConferenceDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_conference_detail, container, false);

        conference = new Conference();

        if (getArguments() != null) {
            Bundle args = getArguments();
            conference.setTitle(args.getString(Conference.TAG_TITLE));
            conference.setId(args.getString(Conference.TAG_ID));
            conference.setStartTime(args.getString(Conference.TAG_START_TIME));
            conference.setEndTime(args.getString(Conference.TAG_END_TIME));
            conference.setAbst(args.getString(Conference.TAG_ABSTRACT));
            conference.setSpeaker(args.getString(Conference.TAG_SPEAKER));
            conference.setProfile(args.getString(Conference.TAG_PROFILE));
            conference.setLocation(args.getString(Conference.TAG_LOCATION));
            conference.setLocationId(args.getString(Conference.TAG_LOCATION_ID));
        }

        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView time = (TextView) rootView.findViewById(R.id.time);
        TextView abst = (TextView) rootView.findViewById(R.id.abst);
        TextView speaker = (TextView) rootView.findViewById(R.id.speaker);
        TextView profile = (TextView) rootView.findViewById(R.id.profile);
        TextView location = (TextView) rootView.findViewById(R.id.location);

        title.setText(conference.getTitle());
        time.setText(conference.getStartTime() + " - " + conference.getEndTime());
        abst.setText(conference.getAbst());
        speaker.setText("講師：" + conference.getSpeaker());
        profile.setText("プロフィール：" + conference.getProfile());
        location.setText(conference.getLocation());


        ImageButton btnFav = (ImageButton) rootView.findViewById(R.id.btn_favorite);
        btnFav.setVisibility(Configuration.favoriteButtonIsVisible);
        SharedPreferences pref = this.getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
        if("true".equals(pref.getString(conference.getId(), "false"))){
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
            editor.putString(conference.getId(), "false");
            isFavorite = false;
            btn.setImageResource(R.drawable.star_pink);
            Snackbar.make(rootView, "マイスケジュールから削除しました！", Snackbar.LENGTH_LONG).show();
        } else {
            editor.putString(conference.getId(), "true");
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
        if (conference.getLocationId().equals(ConferencePagerFragment.ConferencePagerAdapter.KEY_T0)) {
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
}
