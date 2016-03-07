package jp.android_group.student.abc2016spring.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.List;

import jp.android_group.student.abc2016spring.R;
import jp.android_group.student.abc2016spring.consts.Const;
import jp.android_group.student.abc2016spring.datasource.action.FavoriteAction;
import jp.android_group.student.abc2016spring.domain.model.Conference;

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
        final Conference conference = mData.get(holder.getAdapterPosition());

        if (holder.getAdapterPosition() != 0) {
            if (conference.getId().equals(mData.get(holder.getAdapterPosition() - 1).getId())) {
                holder.mFavoriteButton.setVisibility(View.INVISIBLE);
            } else {
                holder.mFavoriteButton.setVisibility(View.VISIBLE);
            }
        }

        holder.mTime.setText(conference.getStartTime().substring(11, 16) + " - " + conference.getEndTime().substring(11, 16));
        holder.mTitle.setText(conference.getTitle());

        StringBuilder speakerNames = new StringBuilder(conference.getSpeaker().getName().get(0));
        for (int i = 1; i < conference.getSpeaker().getName().size(); i++) {
            speakerNames.append(" / ").append(conference.getSpeaker().getName().get(i));
        }
        holder.mSpeaker.setText(speakerNames.toString());

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
                mCallback.onCellClick(holder.getAdapterPosition());
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
