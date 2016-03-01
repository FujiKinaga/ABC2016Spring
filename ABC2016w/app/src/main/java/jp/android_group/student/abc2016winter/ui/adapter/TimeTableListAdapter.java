package jp.android_group.student.abc2016winter.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.List;

import jp.android_group.student.abc2016winter.R;
import jp.android_group.student.abc2016winter.consts.Const;
import jp.android_group.student.abc2016winter.datasource.api.FavoriteAction;
import jp.android_group.student.abc2016winter.domain.model.Conference;

/**
 * Created by kinagafuji on 16/02/27.
 */
public class TimeTableListAdapter extends RecyclerView.Adapter<Const.TimeTableViewHolder> {

    private Context mContext;

    private List<Conference> mData = new ArrayList<>();

    private TimeTableCallback mCallback;

    public TimeTableListAdapter(Context context, List<Conference> data) {
        mContext = context;
        mData = data;
    }

    public void setTimeTableCallback(TimeTableCallback callback) {
        mCallback = callback;
    }

    public void setData() {
        this.notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mData.isEmpty();
    }

    @Override
    public Const.TimeTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.time_table_list_item, parent, false);
        return new Const.TimeTableViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Const.TimeTableViewHolder holder, final int position) {
        final Conference conference = mData.get(position);

        holder.mTime.setText(conference.getStartTime().substring(11, 16) + " - " + conference.getEndTime().substring(11, 16));
        holder.mTitle.setText(conference.getTitle());
        holder.mSpeaker.setText(conference.getSpeaker().getName());

        holder.mFavoriteButton.setBackgroundResource(FavoriteAction.isFavoriteConference(mContext, conference.getId()) ?
                R.drawable.ic_star_yellow_600_36dp : R.drawable.ic_star_border_grey_600_36dp);

        holder.mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FavoriteAction.isFavoriteConference(mContext, conference.getId())) {
                    holder.mFavoriteButton.setBackgroundResource(R.drawable.ic_star_border_grey_600_36dp);
                    FavoriteAction.unFavoriteConference(mContext, conference.getId());
                } else {
                    holder.mFavoriteButton.setBackgroundResource(R.drawable.ic_star_yellow_600_36dp);
                    mCallback.onFavoriteClick();
                    FavoriteAction.favoriteConference(mContext, conference.getId());
                }
            }
        });

        holder.mCellRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                mCallback.onCellClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface TimeTableCallback {
        void onCellClick(int position);

        void onFavoriteClick();
    }
}
