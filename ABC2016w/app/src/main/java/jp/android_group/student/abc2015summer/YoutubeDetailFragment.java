package jp.android_group.student.abc2015summer;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import jp.android_group.student.abc2015summer.api.Youtube;

public class YoutubeDetailFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private View rootView;
    private String videoId;

    public static YoutubeDetailFragment newInstance(Youtube youtube) {
        Bundle args = new Bundle();

        args.putString(Youtube.TAG_GROUP, youtube.getGroup());
        args.putString(Youtube.TAG_TITLE, youtube.getTitle());
        args.putString(Youtube.TAG_CONTENT, youtube.getContent());
        args.putString(Youtube.TAG_URL, youtube.getUrl());
        args.putString(Youtube.TAG_START_TIME, youtube.getStartTime());
        args.putString(Youtube.TAG_END_TIME, youtube.getEndTime());
        args.putString(Youtube.TAG_ROOM_ID, youtube.getRoomId());
        args.putString(Youtube.TAG_ROOM, youtube.getRoom());

        YoutubeDetailFragment fragment = new YoutubeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public YoutubeDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_youtube_detail, container, false);

        setHasOptionsMenu(true);
        Youtube youtube = new Youtube();

        if (getArguments() != null) {
            Bundle args = getArguments();

            youtube.setGroup(args.getString(Youtube.TAG_GROUP));
            youtube.setTitle(args.getString(Youtube.TAG_TITLE));
            youtube.setContent(args.getString(Youtube.TAG_CONTENT));
            youtube.setUrl(args.getString(Youtube.TAG_URL));
            youtube.setStartTime(args.getString(Youtube.TAG_START_TIME));
            youtube.setEndTime(args.getString(Youtube.TAG_END_TIME));
            youtube.setRoomId(args.getString(Youtube.TAG_ROOM_ID));
            youtube.setRoom(args.getString(Youtube.TAG_ROOM));
        }

        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView time = (TextView) rootView.findViewById(R.id.time);
        TextView content = (TextView) rootView.findViewById(R.id.content);

        title.setText(youtube.getTitle());
        time.setText(youtube.getStartTime() + " - " + youtube.getEndTime());
        content.setText(youtube.getContent());
        videoId = youtube.getUrl();

        ImageButton btnWatch = (ImageButton) rootView.findViewById(R.id.btnWatch);

        Resources resources = getResources();
        Bitmap playSource = BitmapFactory.decodeResource(resources, R.drawable.play_button);
        Bitmap playCircle = getRoundedBitmap(playSource);

        btnWatch.setImageBitmap(playCircle);
        btnWatch.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        setHasOptionsMenu(false);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("vnd.youtube:" + videoId));
        intent.putExtra("VIDEO_ID", videoId);
        startActivity(intent);
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