package jp.android_group.student.abc2015summer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
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
import java.util.List;
import jp.android_group.student.abc2015summer.api.Youtube;
import jp.android_group.student.abc2015summer.api.YoutubeXmlParser;

public class YoutubeListFragment extends ListFragment{
    private List<Youtube> mYoutubes;

    public static YoutubeListFragment newInstance(String roomId) {
        Bundle args = new Bundle();
        args.putString(Youtube.TAG_ROOM_ID, roomId);
        YoutubeListFragment fragment = new YoutubeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public YoutubeListFragment() {
    }

    private String roomId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        this.roomId = getArguments().getString(Youtube.TAG_ROOM_ID);

        YoutubeXmlParser parser = new YoutubeXmlParser(getActivity());
        List<Youtube> all = parser.loadYoutube();
        mYoutubes = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            Youtube youtube = all.get(i);
            if (youtube.getRoomId().equals(roomId)) {
                mYoutubes.add(youtube);
            }
        }
        View rootView = inflater.inflate(R.layout.fragment_youtube_list, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = getListView();
        YoutubeListAdapter adapter = new YoutubeListAdapter(getActivity(), R.layout.youtube_list_item, mYoutubes);
        listView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Youtube youtube = mYoutubes.get(position);

        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.container
                , YoutubeDetailFragment.newInstance(youtube)).addToBackStack(null).commit();
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

    private class YoutubeListAdapter extends ArrayAdapter<Youtube> {
        private LayoutInflater inflater;
        private int resId;
        private List<Youtube> list;

        public YoutubeListAdapter(Context context, int resource, List objects) {
            super(context, resource, objects);
            inflater = LayoutInflater.from(context);
            resId = resource;
            list = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(resId, null);
            }

            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView group = (TextView) convertView.findViewById(R.id.group);

            Youtube youtube = list.get(position);

            time.setText(youtube.getStartTime() + " - " + youtube.getEndTime());
            title.setText(youtube.getTitle());
            group.setText(youtube.getGroup());

            ImageButton btnWatch = (ImageButton) convertView.findViewById(R.id.btnWatch);
            Resources resources = getResources();
            Bitmap playSource = BitmapFactory.decodeResource(resources, R.drawable.play_button);
            Bitmap playCircle = getRoundedBitmap(playSource);
            btnWatch.setImageBitmap(playCircle);

            btnWatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YoutubeListAdapter adapter = new YoutubeListAdapter(getActivity(),R.layout.youtube_list_item,mYoutubes);
                    Youtube youtube = adapter.getItem(position);
                    String videoId = youtube.getUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("vnd.youtube:" + videoId));
                    intent.putExtra("VIDEO_ID", videoId);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }

    public static Bitmap getRoundedBitmap(final Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }
}