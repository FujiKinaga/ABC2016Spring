package jp.android_group.student.abc2015summer;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.android_group.student.abc2015summer.api.TimeTable;
import jp.android_group.student.abc2015summer.api.TimeTableXmlParser;

public class TimeTableListFragment extends ListFragment {

    private List<TimeTable> mTimeTable;



    public static TimeTableListFragment newInstance(int startTime, int endTime) {
        Bundle args = new Bundle();
        args.putInt(TimeTable.TAG_START_TIME, startTime);
        args.putInt(TimeTable.TAG_END_TIME, endTime);
        TimeTableListFragment fragment = new TimeTableListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TimeTableListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int startHour = 0;
        int startMinute = 0;
        int endHour = 0;
        int endMinute = 0;
        long xmlMillisStart = 0;
        long xmlMillisEnd = 0;
        int startTime = getArguments().getInt(TimeTable.TAG_START_TIME);
        int endTime = getArguments().getInt(TimeTable.TAG_END_TIME);
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        Calendar xmlStartCal = Calendar.getInstance();
        Calendar xmlEndCal = Calendar.getInstance();
        startCal.set(Configuration.CALENDAR_YEAR, Configuration.CALENDAR_MONTH, Configuration.CALENDAR_DATE, startTime, Configuration.CALENDAR_MINUTE);
        endCal.set(Configuration.CALENDAR_YEAR, Configuration.CALENDAR_MONTH, Configuration.CALENDAR_DATE, endTime, Configuration.CALENDAR_MINUTE);
        long millisStart = startCal.getTimeInMillis();
        long millisEnd = endCal.getTimeInMillis() - 1;

        mTimeTable = new ArrayList<>();
        String putCheck = "initialize";
        TimeTableXmlParser parser = new TimeTableXmlParser(getActivity());
        List<TimeTable> all = parser.loadTimeTable();
        for (int i = 0; i < all.size(); i++) {
            TimeTable timeTable = all.get(i);
            String[] startTimes = timeTable.getStartTime().split(":", 0);
            String[] endTimes = timeTable.getEndTime().split(":", 0);

            try {
                startHour = Integer.parseInt(startTimes[0]);
                startMinute = Integer.parseInt(startTimes[1]);
                endHour = Integer.parseInt(endTimes[0]);
                endMinute = Integer.parseInt(endTimes[1]);
            } catch (Exception e) {
                    e.printStackTrace();
                    continue;
            }
            xmlStartCal.set(Configuration.CALENDAR_YEAR, Configuration.CALENDAR_MONTH, Configuration.CALENDAR_DATE, startHour, startMinute);
            xmlEndCal.set(Configuration.CALENDAR_YEAR, Configuration.CALENDAR_MONTH, Configuration.CALENDAR_DATE, endHour, endMinute);
            xmlMillisStart = xmlStartCal.getTimeInMillis();
            xmlMillisEnd = xmlEndCal.getTimeInMillis() - 1;





            if((millisStart <= xmlMillisStart && xmlMillisStart <= millisEnd)||(millisStart <= xmlMillisEnd && xmlMillisEnd <= millisEnd)||(millisStart >= xmlMillisStart && millisEnd <= xmlMillisEnd)){
                if(!(putCheck.equals(timeTable.getId()))){
                    //後で直す(後で直す)
                    //TAG_ID_SESSIONが1のデータを連続で2回取得している問題の修正
                    mTimeTable.add(timeTable);
                }
                putCheck = timeTable.getId();

            }
        }
        View rootView = inflater.inflate(R.layout.fragment_time_table_list, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = getListView();
        TimeTableListAdapter adapter = new TimeTableListAdapter(getActivity()
                , R.layout.time_tabre_list_item, mTimeTable);
        listView.setAdapter(adapter);

        View footer = View.inflate(getActivity(), R.layout.comment, null);
        TextView comment = (TextView) footer.findViewById(R.id.official_url);
        comment.setText("http://abc.android-group.jp/2015s/timetables/");
        listView.addFooterView(footer);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        TimeTable timeTable = mTimeTable.get(position);

        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.container
                , TimeTableDetailFragment.newInstance(timeTable)).addToBackStack(null).commit();
    }

    private class TimeTableListAdapter extends ArrayAdapter<TimeTable> {

        private LayoutInflater inflater;
        private int resId;
        private List<TimeTable> list;

        public TimeTableListAdapter(Context context, int resource, List objects) {
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
            TimeTable timeTable = list.get(position);

            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView speaker = (TextView) convertView.findViewById(R.id.speaker);
            TextView group = (TextView) convertView.findViewById(R.id.group);
            TextView type = (TextView) convertView.findViewById(R.id.type);
            time.setText(timeTable.getStartTime() + " - " + timeTable.getEndTime());
            title.setText(timeTable.getTitle());

            if(timeTable.getType().equals("conference")) {
                speaker.setText(timeTable.getSpeaker());
                group.setVisibility(View.GONE);
                type.setText("カンファレンス  " + timeTable.getLocation());
            }else{
                group.setText(timeTable.getGroup());
                speaker.setVisibility(View.GONE);
                type.setText("セッション  " + timeTable.getRoom());
            }

            ImageButton btnFav = (ImageButton) convertView.findViewById(R.id.btn_favorite);
            btnFav.setVisibility(Configuration.favoriteButtonIsVisible);

            SharedPreferences pref = getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
            if("true".equals(pref.getString(timeTable.getId(), "false"))){
                btnFav.setImageResource(R.drawable.star_pink_pushed);
            }else{
                btnFav.setImageResource(R.drawable.star_pink);
            }

            final View finalConvertView = convertView;
            final TimeTable finalTimeTable = timeTable;
            btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences pref = getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    ImageButton btn = (ImageButton) finalConvertView.findViewById(R.id.btn_favorite);
                    if("true".equals(pref.getString(finalTimeTable.getId(), "false"))){
                        editor.putString(finalTimeTable.getId(), "false");
                        btn.setImageResource(R.drawable.star_pink);
                        Snackbar.make(finalConvertView, "マイスケジュールから削除しました！", Snackbar.LENGTH_LONG).show();
                    } else {
                        editor.putString(finalTimeTable.getId(), "true");
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
