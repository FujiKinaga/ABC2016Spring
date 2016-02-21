package jp.android_group.student.abc2015summer.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import jp.android_group.student.abc2015summer.R;
import jp.android_group.student.abc2015summer.api.Conference;
import jp.android_group.student.abc2015summer.api.ConferenceXmlParser;

public class TestConferenceListFragment extends ListFragment {

    public TestConferenceListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = getListView();
        ConferenceXmlParser conferenceXmlParser = new ConferenceXmlParser(getActivity());

        List<Conference> list = conferenceXmlParser.loadConference();
        listView.setAdapter(new ConferenceTestAdapter(getActivity(),
                R.layout.test_conference_item, list));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_conference_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // クリック時の画面遷移など
    }

    public static class ConferenceTestAdapter extends ArrayAdapter<Conference> {

        LayoutInflater inflater;
        int resId;
        List<Conference> list;

        public ConferenceTestAdapter(Context context, int resource, List<Conference> list) {
            super(context, resource, list);
            inflater = LayoutInflater.from(context);
            resId = resource;
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(resId, null);
            }

            TextView lecId = (TextView) convertView.findViewById(R.id.lec_id);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView abst = (TextView) convertView.findViewById(R.id.abst);
            TextView speaker = (TextView) convertView.findViewById(R.id.speaker);
            TextView startTime = (TextView) convertView.findViewById(R.id.start_time);
            TextView endTime = (TextView) convertView.findViewById(R.id.end_time);

            Conference conference = list.get(position);

            lecId.setText(conference.getId());
            title.setText(conference.getTitle());
            abst.setText(conference.getAbst());
            speaker.setText(conference.getSpeaker());
            startTime.setText(conference.getStartTime());
            endTime.setText(conference.getEndTime());


            return convertView;
        }
    }
}
