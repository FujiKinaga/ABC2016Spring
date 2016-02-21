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
import jp.android_group.student.abc2015summer.api.Bazaar;
import jp.android_group.student.abc2015summer.api.BazaarXmlParser;

public class TestBazaarListFragment extends ListFragment {

    public TestBazaarListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = getListView();
        BazaarXmlParser bazaarXmlParser = new BazaarXmlParser(getActivity());

        List<Bazaar> list = bazaarXmlParser.loadBazaar();
        listView.setAdapter(new BazaarAdapter(getActivity(),
                R.layout.test_bazaar_item, list));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_bazaar_list_fragment, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // クリック時の画面遷移など
    }

    public static class BazaarAdapter extends ArrayAdapter<Bazaar> {

        LayoutInflater inflater;
        int resId;
        List<Bazaar> list;

        public BazaarAdapter(Context context, int resource, List<Bazaar> list) {
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

            TextView bazaarId = (TextView) convertView.findViewById(R.id.bazaar_id);
            TextView group = (TextView) convertView.findViewById(R.id.group);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView content = (TextView) convertView.findViewById(R.id.content);
            TextView location = (TextView) convertView.findViewById(R.id.location);

            Bazaar bazaar = list.get(position);

            bazaarId.setText(Integer.toString(bazaar.getId()));
            group.setText(bazaar.getGroup());
            title.setText(bazaar.getTitle());
            content.setText(bazaar.getContent());
            location.setText(bazaar.getLocation());

            if (bazaar.getLocation().startsWith("No.")) {

            }

            return convertView;
        }
    }
}
