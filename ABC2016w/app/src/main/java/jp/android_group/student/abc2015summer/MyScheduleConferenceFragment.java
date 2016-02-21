package jp.android_group.student.abc2015summer;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.android_group.student.abc2015summer.api.MyScheduleComparator;
import jp.android_group.student.abc2015summer.api.TimeTable;
import jp.android_group.student.abc2015summer.api.TimeTableXmlParser;


    public class MyScheduleConferenceFragment extends ListFragment {

        private List<TimeTable> mMySchedule;

        public static MyScheduleConferenceFragment newInstance() {
            Bundle args = new Bundle();
            MyScheduleConferenceFragment fragment = new MyScheduleConferenceFragment();
            fragment.setArguments(args);
            return fragment;
        }

        public MyScheduleConferenceFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            SharedPreferences pref = getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
            mMySchedule = new ArrayList<>();
            TimeTableXmlParser parser = new TimeTableXmlParser(getActivity());
            List<TimeTable> all = parser.loadTimeTable();
            for (int i = 0; i < all.size(); i++) {
                TimeTable timeTable = all.get(i);
                if("true".equals(pref.getString(timeTable.getId(), "false"))) {
                    mMySchedule.add(timeTable);
                }
            }
            Collections.sort(mMySchedule, new MyScheduleComparator());
            View rootView = inflater.inflate(R.layout.fragment_my_schedule_list, container, false);

            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            ListView listView = getListView();
            TimeTableListAdapter adapter = new TimeTableListAdapter(getActivity()
                    , R.layout.time_tabre_list_item, mMySchedule);
            listView.setAdapter(adapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);

            TimeTable timeTable = mMySchedule.get(position);

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


                //マイスケジュール一覧では誤タッチを防ぐためお気に入り追加削除を表示させない
                ImageButton btnFav = (ImageButton) convertView.findViewById(R.id.btn_favorite);
                btnFav.setVisibility(View.GONE);
//                SharedPreferences pref = getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
//                if("true".equals(pref.getString(timeTable.getId(), "false"))){
//                    btnFav.setImageResource(R.drawable.star_pink_pushed);
//                }else{
//                    btnFav.setImageResource(R.drawable.star_pink);
//                }
//
//                final View finalConvertView = convertView;
//                final TimeTable finalTimeTable = timeTable;
//                btnFav.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SharedPreferences pref = getActivity().getSharedPreferences("ABC2015S", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = pref.edit();
//                        ImageButton btn = (ImageButton) finalConvertView.findViewById(R.id.btn_favorite);
//                        if("true".equals(pref.getString(finalTimeTable.getId(), "false"))){
//                            editor.putString(finalTimeTable.getId(), "false");
//                            btn.setImageResource(R.drawable.star_pink);
//                            Snackbar.make(finalConvertView, "マイスケジュールから削除しました！", Snackbar.LENGTH_LONG).show();
//                        } else {
//                            editor.putString(finalTimeTable.getId(), "true");
//                            btn.setImageResource(R.drawable.star_pink_pushed);
//                            Snackbar.make(finalConvertView, "マイスケジュールに追加しました！", Snackbar.LENGTH_LONG).show();
//                        }
//                        editor.commit();
//                    }
//                });

                return convertView;
            }
        }
    }
