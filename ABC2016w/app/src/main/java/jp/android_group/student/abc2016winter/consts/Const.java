package jp.android_group.student.abc2016winter.consts;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.SyncHttpClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.android_group.student.abc2016winter.R;

/**
 * Created by kinagafuji on 16/02/22.
 */
public class Const {

    private static final String BASE_URL = "http://abc.android-group.jp/2014w/api/";

    public static final String URL_BAZZAAR = BASE_URL + "bazaar/";
    public static final String URL_CONFERENCE = BASE_URL + "conference/";
    public static final String URL_LIVE = BASE_URL + "live/";

    public static final AsyncHttpClient sAsyncHttpClient = new AsyncHttpClient();
    public static final SyncHttpClient sSyncHttpClient = new SyncHttpClient();

    public static final class TimeTableViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.btn_favorite)
        public ImageButton mFavoriteButton;
        @Bind(R.id.group)
        public TextView mTime;
        @Bind(R.id.title)
        public TextView mTitle;
        @Bind(R.id.speaker)
        public TextView mSpeaker;
        @Bind(R.id.cell_ripple)
        public RippleView mCellRipple;

        public TimeTableViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static final class BazaarViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.location)
        public TextView mLocation;
        @Bind(R.id.title)
        public TextView mTitle;
        @Bind(R.id.group)
        public TextView mGroup;
        @Bind(R.id.cell_ripple)
        public RippleView mCellRipple;
        @Bind(R.id.btn_favorite)
        public ImageButton mFavoriteButton;

        public BazaarViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static final class ConferenceViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.category)
        public TextView mCategory;
        @Bind(R.id.cell_ripple)
        public RippleView mCellRipple;

        public ConferenceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static final class MyScheBazaarViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.location)
        public TextView mLocation;
        @Bind(R.id.title)
        public TextView mTitle;
        @Bind(R.id.group)
        public TextView mGroup;
        @Bind(R.id.cell_ripple)
        public RippleView mCellRipple;

        public MyScheBazaarViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static final class MyScheConferenceViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.group)
        public TextView mTime;
        @Bind(R.id.title)
        public TextView mTitle;
        @Bind(R.id.speaker)
        public TextView mSpeaker;
        @Bind(R.id.room)
        public TextView mRoom;
        @Bind(R.id.cell_ripple)
        public RippleView mCellRipple;

        public MyScheConferenceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
