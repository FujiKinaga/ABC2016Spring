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
import jp.android_group.student.abc2015summer.api.ConferenceXmlParser;
import jp.android_group.student.abc2015summer.api.Session;
import jp.android_group.student.abc2015summer.api.SessionXmlParser;

public class TestSessionListFragment extends ListFragment {

    public TestSessionListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = getListView();
        SessionXmlParser sessionXmlParser = new SessionXmlParser(getActivity());

        List<Session> list = sessionXmlParser.loadSession();
        listView.setAdapter(new SessionAdapter(getActivity(),
                R.layout.test_session_item, list));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_session_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // クリック時の画面遷移など
    }

    public static class SessionAdapter extends ArrayAdapter<Session> {

        LayoutInflater inflater;
        int resId;
        List<Session> list;

        public SessionAdapter(Context context, int resource, List<Session> list) {
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

            TextView sessionId = (TextView) convertView.findViewById(R.id.session_id);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView group = (TextView) convertView.findViewById(R.id.group);
            TextView content = (TextView) convertView.findViewById(R.id.content);
            TextView url = (TextView) convertView.findViewById(R.id.url);
            TextView startTime = (TextView) convertView.findViewById(R.id.start_time);
            TextView endTime = (TextView) convertView.findViewById(R.id.end_time);
            TextView roomId = (TextView) convertView.findViewById(R.id.room_id);
            TextView room = (TextView) convertView.findViewById(R.id.room);

            Session session = list.get(position);

            sessionId.setText(Integer.toString(session.getId()));
            title.setText(session.getTitle());
            group.setText(session.getGroup());
            content.setText(session.getContent());
            url.setText(session.getUrl());
            startTime.setText(session.getStartTime());
            endTime.setText(session.getEndTime());
            roomId.setText(session.getRoomId());
            room.setText(session.getRoom());

            return convertView;
        }
    }
}
